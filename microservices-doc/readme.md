## Интернет магазин микросервисная архитектура

#### Table of Contents  
- [Диаграмма микросервисов](#диаграмма-микросервисов)  
- [Временная диаграмма создания заказа](#временнная-диаграмма-создания-заказа)  
- [Паттерны](#паттерны)  
- [Сложные случаи](#сложные-случаи)  
- [Модуль message outbox sidecar](#модуль-message-outbox-sidecar)  
  * [Сущности message outbox sidecar](#cущности-message-outbox-sidecar)  
- [Сервис store](#сервис-store)  
  * [Сущности store](#cущности-store)  
  * [Rest API](#rest-api)  
- [Модуль notification sidecar](#модуль-notification-sidecar)  
  * [Сущности notification sidecar](#cущности-notification-sidecar)  
- [Сервис notification mail](#сервис-notification-mail)  
- [Сервис notification sms](#сервис-notification-sms)  
- [Пользовательские сценарии](#пользовательские-сценарии)  

## Диаграмма микросервисов
./pictures/store-microservice.png

## Временная диаграмма создания заказа
можно посмотреть по пути:
./pictures/store-order-time-diagram.png

## Паттерны  
1 outbox table - сохранение аггрегата и исходящего события в одной транзакции.  
2 коммутативность - описано в Сложных случаях п 1.  
3 хореографическая сага.  
4 дедупликация входящих сообщений.  
5 eventualy consistency.
6 database per service.  

## Cложные случаи  
1 обеспечение коммутативности если pay_pending_event придет раньше чем booked_event
, в таком случае booked event отбрасывается - предполагается что pay_pending_event не может придти без
того чтобы продукты были не забронированы.  
2 с кнопки оплата уйдет два запроса на создание и оплату заказа - 
  сделать блокировку на zookeeper(если несколько сервисов order-service) по customer_id 
  и не давать создавать больше одного неоплаченного заказа  
3 дедубликация сообщений - хранить uid входящих сообщений, проверять и отбрасывать если уже было  
4 покупатель оплатил не всю сумму - перевод в статус partion_pay, если приходит новый запрос
 на платеж, проверка статуса, проверка платежей, которые в процессе.  
5 платеж не прошел - нужно откатить сагу - разбронировать продукты - статус заказа ошибка.  
6 каждый аггрегат содержит версию и статус для обеспечения коммутативности при необходимости.  
7 каждый евент хранит статус и версию аггрегата, а также node-id откуда евент пришел.  
8 нужно поменять содержимое евента - хранить версию евента и приводить к актуальной  

Commerce order service  
Entities  
CommerceOrder{id, itemsList, fixedPrice, status, version}  
OrderItem{id, quantity}  
OrderStatus{NEW,BOOKING_PENDING,BOOKED,PAY_PENDING,PAID,PAID_PARTION,SHIPPING_PENDING,COMPLETED}  
OrderEvent{id, orderId, orderVersion, orderStatus, itemsList, fixedPrice, orderServiceNodeId}  
PayEvent{id, orderId, orderVersion, payServiceNodeId, payId, payVersion, payStatus}  
ShippingEvent{id, orderId, orderVersion, shippingServiceNodeId, shippingStatus}  
Rest  
GET /order/{id} - получить json заказа
POST /order/add - создать заказ , нужны пользовательские права

StockService  
Entities  
StockEvent{id, orderId, orderVersion, stockStatus, fixedPride, stockServiceNodeId}  
StockStatus{PENDING,BOOKED,ERROR,ASSEMBLED}  
Item{id, weight, length, width, height, quantity}  
OrderItem{id, quantity, orderId , status}  
OrderItemStatus{BOOKED,SHIPPED,RETURNED}  
Rest  
GET /stock/freeItems/{id}  
  
PaymentService
Payment{id, status, version, orderId, orderVersion, redirectUrl, systemPayId, systemStatus}
PaymentStatus{NEW, PENDING, PAID}
PayEvent{id, orderId, orderVersion, paymentId, paymentVersion, paymentStatus}
Rest
GET /pay/redirectUrl - получить url для перехода в платежную систему  
GET /payments/{orderId} - получить список платежей по id заказа  
  
Product catalogue  
Entities  
Product{id, photo, description, price}  
Rest  
GET /product/{id} - получить продукт json  
GET /products - получить список продуктов json  
POST /product/add - создать книгу , нужны админ права  
  
Shipping service  
Order{id, orderItemList, weight, length, width, height}  
OrderItem{id, name, quantity}  
ShippingEvent{id, status, orderId}  
Rest  
GET /shipping/{id} - получить текущий статус доставки  

Notification service
enum EventType{ORDER_PAID, ORDER_SHIPPED}  
CommerceOrder { id, customerId, orderItems, orderStatus}  
CustomerDto { id, name, email, communications}  


Customer service  
Entities  
Customer{customerId=email, name, lastName, registrationDate, lastModified, communications}  
Rest  
POST /customer
PUT /customer  
  
Auth service  
У сервисов, которые работают с jwt, есть секретный ключ и соль для проверки подписи.
Entities  
UserDetails{customerId, password , lastModified, isEnable}  
UserRole{customerId, role}  
Role{CUSTOMER, OPERATOR, STOCK_OPERATOR, ADMIN, SHIPPING_OPERATOR}  
Logout{token}
Token{customerId, role, expiredTime}  
Rest  
GET /token - в хедере уходит basic auth в ответе jwt token  
GET /checkToken - проверка, что токен не expired и при необходимости обновление токена.  
POST /user/logout - заносим токен в таблицу logout  

Promocode service
Entities  
Promocode{ token }
Rule{} - правило применения промокода  
Rest  
POST /calcPrice {items} - посчитать новую цену с учетом скидок  
  
пользовательские сценарии  
1 поиск по каталогу  
Базовый поиск product-catalogue  
Возможность искать по акциям + promo code-service  
Возможность видеть количество товара + stock service  
2 личный кабинет  
Заказы (order-service) статус оплаты(payment-service) статус доставки(shipping-service)  
3 сделать заказ и оплатить, рис ./pictures/store-order-time-diagram  
4 добавление продукта  
Добавить в каталог (product-catalogue)  
