## Проект магазин (книжный)

#### Table of Contents  
[Диаграмма](#диаграмма)  
[Rest API](#rest-api)  
[Пользовательские сценарии](#пользовательские-сценарии)  

## Диаграмма

![Alt text](pictures/diagram1.png?raw=true "Title")

## Rest API

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

## Пользовательские сценарии

### Создание книги

./scripts/getAdminToken.sh - получить токен админской сессии  
./scripts/addBook.sh - создание книги  

### Создание заказа

./scripts/getUserToken.sh - получить токен пользовательской сессии
./scripts/createOrder.sh - создать заказ

### Получить список книг

./scripts/getBookList.sh - получить список книг  


