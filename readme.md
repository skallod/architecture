## Проект магазин (книжный)

#### Table of Contents  
- [Работа с проектом](#работа-с-проектом)  
- [Диаграмма](#диаграмма)  
- [Модуль message inbox sidecar](#модуль-message-inbox-sidecar)  
  * [Сущности massge inbox sidecar](#cущности-message-inbox-sidecar)  
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

<!--    + [дто](#дто-1)-->

## Работа с проектом

Сборка проекта docker-compose build  
Запуск проекта docker-compose up  
Проверка запросов ./postman/store-test-collection.postman_collection.json  

## Диаграмма

![Alt text](pictures/diagram2.png?raw=true "Title")

## Модуль message inbox sidecar

Модуль скрывающий транспортный уровень получения сообщений из очереди kafka. Служит для того
 чтобы избежать дублирования кода, а также для возможности замены kafka на другую очередь.
 Настройки: сокет брокера kafka, названия топиков, на которые подписан сервис.  

### Сущности message inbox sidecar

Message {  
const "message-type"; - название хедера, в котором хранится тип события или комманды для дальнейшей маршрутизации и десериализации  
String id; - уникальный идентификатор сообщения  
Map<String, String> headerMap; - набор хедеров  
String body;} - сериализованное в json событие или комманда  


## Модуль message outbox sidecar

Модуль скрывающий транспортный уровень отправки сообщений в очередь kafka. Служит для того
 чтобы избежать дублирования кода, а также для возможности замены kafka на другую очередь.
 Настройки: сокет брокера kafka, название топика указывается во время отправки.  

### Сущности message outbox sidecar

Message - такой же как в модуле module inbox sidecar  

## Сервис store

Сервис store представляет собой монолит, который содержит в себе каталог книг, сервис
 пользователей, сервис заказов. Использует модуль message-outbox-sidecar для отправки
 события "заказ создан". Использует бд postgres.  

### Сущности store

Book {long id, String title, String author,String publisher, String publicationDate, String language
, String category, int numberOfPages, String format, String isbn, long shippingWeight, long price
, boolean active = true, String description, int inStockQuantity}  

CommerceOrder {long id, long customerId, List<OrderItem> orderItems, OrderStatus orderStatus}  

OrderItem {long id; long price; int quantity; long bookId; CommerceOrder commerceOrder;  

enum OrderStatus { NEW,ERROR,BOOKED,MANUAL_SUPPORT,PAYED,PAY_PENDING,SHIPPED,SHIPPING_PENDING,COMPLETED}  

OrderEvent {String orderId;} - id заказа  

enum EventType {ORDER_CREATED}  

Customer { Long id; String name; String password; Role role; String lastName; String email; String phone;}  

### Rest API

Префикс всех запросов http://localhost:18181/api/v1  
  
GET /token - в хедере уходит basic auth в ответе token сессии  
GET /checkSession - проверка, что работаем под пользователм и сессия жива  
GET /checkAdmin - проверка, что работаем под админом и сессия жива  
POST /user/logout - logout + убиваем сессию  
  
GET /book/{id} - получить json книги  
GET /book/bookList - получить список json книг  
POST /book/add - создать книгу , нужны админ права  

GET /order/{id} - получить json заказа  
POST /order/add - создать заказ , нужны пользовательские права  

## Модуль notification sidecar

Модуль позволяет избежать дублирование кода. Использует модуль message-inbox-sidecar для получения события
 "заказ создан". После получения этого события notification-sidecar делает rest запросы в сервис заказов,
  сервис покупателей, чтобы собрать необходимую информацию для отправки сообщений пользователю.  
  
### Сущности notification sidecar

CommerceOrder { long id; long customerId; List<OrderItem> orderItems; OrderStatus orderStatus;}  

CustomerDto { Long id; String name; String email; String phone;}  

enum EventType { ORDER_CREATED}  

OrderEvent { String orderId;}

OrderItem  { long id; long price; int quantity; long bookId;}

enum OrderStatus { NEW}

## Сервис notification mail

Сервис отправляет email пользователю. В текущей реализации пишется сообщение в лог, реальное письмо
 не отправляется. В лог пишется сообщение "MAIL SEND to email {}, mail content {}". Сервис использует
 модуль notification sidecar и использует его сущности.
 
## Сервис notification sms

Сервис отправляет email пользователю. В текущей реализации пишется сообщение в лог, реальное письмо
 не отправляется. В лог пишется сообщение "SMS SEND to number {}, message content {}". Сервис использует
 модуль notification sidecar и использует его сущности.

## Пользовательские сценарии

Выполняем запрос из коллекции postman create-order. В сервисе store происходит сохранение сущности в бд
 postgres, отправка события "заказ создан" order_created в очередь. Сервисы notification-mail, notificatio-sms
 читают событие "заказ создан" из очереди. Далее эти сервисы делают rest запросы в сервис store для получения
 сущностей заказа CommerceOrder, покупателя CustomerDto. Далее формируется текст сообщения для покупателя
 и отправляется email, sms соответственно.  Реальное email и sms не отправляются, но в логах можно
 наблюдать следующие записи mail-service "MAIL SEND to email {}, mail content {}", sms-service
 "SMS SEND to number {}, message content {}".


