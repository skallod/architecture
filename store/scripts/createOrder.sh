curl -X POST \
  'http://localhost:18181/api/v1/order/add' \
  -H 'authorization: Basic SkFkYW1zQGdtYWlsLmNvbTpw' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{"customerId":"1", "orderItemSet" : [{"price":77500, "quantity":2,"bookId":1}]}'

#  -H 'x-auth-token: 103ed0ed-5a1b-40df-a841-2c59fb7c71fd'\
