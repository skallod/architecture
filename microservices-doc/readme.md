Интернет магазин микросервисная архитектура

Диаграмма микросервисов
./pictures/store-microservice.png

Временная диаграмму создания заказа
можно посмотреть по пути:
./pictures/store-order-time-diagram.png

паттерны  
1 outbox table - сохранение аггрегата и исходящего события в одной транзакции.  
2 коммутативность - описано в Сложных случаях п 1.  
3 хореографическая сага.  
4 дедупликация входящих сообщений.  
5 eventualy consistency.
6 database per service.  

сложные случаи  
1 обеспечение коммутативности если pay_pending_event придет раньше чем booked_event
, в таком случае booked event отбрасывается - предполагается что pay_pending_event не может придти без
того чтобы продукты были не забронированы.  
2 с кнопки оплата уйдет два запроса на создание и оплату заказа - 
  сделать блокировку на zookeeper(если несколько сервисов order-service) по customer_id 
  и не давать создавать больше одного неоплаченного заказа  
3 дедубликация сообщений - хранить uid входящих сообщений, проверять и отбрасывать если уже было  
4 покупатель оплатил не всю сумму - перевод в статус partion_pay, если приходит новый запрос
 на платеж, проверка статуса, проверка платежей, которые в процессе.  
5 платеж не прошел - нужно откатить сагу - разбронировать продукты.  
6 каждый аггрегат содержит версию и статус для обеспечения коммутативности при необходимости.  
7 каждый евент хранит статус и версию аггрегата, а также node-id откуда евент пришел.  

Commerce order service  
Entities  
CommerceOrder{id, itemsList, fixedPrice, status, version}  
OrderItem{id, quantity}  
OrderStatus{NEW,BOOKING_PENDING,BOOKED,PAY_PENDING,PAID,PAID_PARTION,SHIPPING_PENDING,COMPLETED}  
OrderEvent{id, orderId, orderVersion, orderStatus, itemsList, fixedPrice, orderServiceNodeId}  
PayEvent{id, orderId, orderVersion, payServiceNodeId, payId, payVersion, payStatus}  
ShippingEvent{id, orderId, orderVersion, shippingServiceNodeId, shippingStatus}  

StockService
StockEvent{id, orderId, orderVersion, stockStatus, fixedPride, stockServiceNodeId}  
StockStatus{PENDING,BOOKED,ERROR}
Item{id, weight, length, width, height, quantity}
OrderItem{id, quantity, orderId , status}
OrderItemStatus{BOOKED,SHIPPED,RETURNED}

PaymentService
Payment{id, status, version, orderId, orderVersion, redirectUrl, systemPayId, systemStatus}
PaymentStatus{NEW, PENDING, PAID}
PayEvent{id, orderId, orderVersion, paymentId, paymentStatus}

Product catalogue
Product{id, photo, description, price}

Shipping service

Notification service

Customer service

пользовательские сценарии
1 поиск по каталогу заказов
2 личный кабинет
3 сделать заказ и оплатить см рис


? Надо ли рисовать блок outbox table
  нарисовал
? Надо ли хранить order-version
  надо на всякий
? Надо ли идти обратно через stock
  надо максимально быстро
? Надо ли хранить в евентах id аггрегатов payment
  в принципе пеймент можно вытащить по ордер айди
