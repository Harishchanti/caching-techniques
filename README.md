This is project contains Spring boot applcition demonstrating how to use Aerospike and Redis cache.Factory Design pattren is used

Test this application as below:

Prerequisite

Install Arospike :
https://www.aerospike.com/docs/operations/install/tools/ubuntu/index.html

Install Redis:
https://redis.io/topics/quickstart

To put Data in Aerospike:

curl -X POST \
  'http://localhost:8080/api/book?key=second' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: fc489fdb-2d19-ff6b-6e1a-92fd5352e95c' \
  -d '{
	"id":456,
	"name":"C++",
	"cost" : 500.56
}'


curl -X GET \
  'http://localhost:8080/api/book?key=second' \
  -H 'cache-control: no-cache' \
  -H 'postman-token: 86d11d32-3aec-045e-57e1-eb37bf76bdf7'


To put data in Redis: 

curl -X POST \
  'http://localhost:8080/api/book?key=second&type=redis' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 6746a9df-0bfc-f078-9400-78ad1008f9b7' \
  -d '{
	"id":456,
	"name":"C++",
	"cost" : 500.56
}'

curl -X GET \
  'http://localhost:8080/api/book?key=second&type=redis' \
  -H 'cache-control: no-cache' \
  -H 'postman-token: 8f14793c-a915-a92b-02f8-c6161646473d'

