curl -X POST \
  'http://localhost:18181/api/v1/order/add' \
  -H 'x-auth-token: 40778de6-8f35-4f36-9877-6f019209df16'\
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{"customerId":"1", "orderItemSet" : [{"price":77500, "quantity":2,"bookId":1}]}'
