This is project contains Spring boot applcition demonstrating how to use Aerospike and Redis cache and monotoring using prometheus (time serices data base).

- Used Factory Design pattren


Test this application as below:

Prerequisite

Install Arospike :
https://www.aerospike.com/docs/operations/install/tools/ubuntu/index.html

Install Redis:
https://redis.io/topics/quickstart

Install Promotheus and the targate scrapping endpoint to this Application:
you can start seeing application metrics at  http://localhost:9090/metrics

https://prometheus.io/docs/prometheus/latest/getting_started/


Use Grapfana to represent the metrics graphically.

RUN GRAFANA:
$ docker run -d -p 3000:3000 grafana/grafana

or sudo docker run -d -p 5000:3000 grafana/grafana


http://localhost:5000/login



Once the application starts runnning 
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

