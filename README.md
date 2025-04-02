# springboot-microservices

Demonstration of how springboot applications can be deployed as microservices (e.g. see Customer API and Product API) using a Service Registry and an API Gateway. These would each be deployed on separate containers but can all be run locally here.  APIs could be scaled separately.


## service-registry: 

Manages API services, their status / availability as well as their location(s)


## customer API: 

Simple API to manage customer data, addresses as well as customers associated products (see http://localhost:8081/customer)


## product API: 

Simple API to manage a list of products (see http://localhost:8082/product)


## api-gateway: 

A reverse proxy that routes calls to APIs or between APIs (e.g. http://localhost:8080/customer OR http://localhost:8080/product)


## Steps

git clone https://github.com/b-vic/springboot-microservices.git

mvn clean package

java -jar ./service-registry/target/service-registry-0.0.1-SNAPSHOT.jar &

java -jar ./api-gateway/target/api-gateway-0.0.1-SNAPSHOT.jar &

java -jar ./customer/target/customer-0.0.1-SNAPSHOT.jar &

java -jar ./product/target/product-0.0.1-SNAPSHOT.jar &



Now launch a separate window:



**Browse the products:**

curl 'http://localhost:8080/product/page?pageNumber=0&pageSize=5'


**Create a new customer with a linked product:**

curl --location 'http://localhost:8080/customer' \
--header 'Content-Type: application/json' \
--data '{
    "customerId": "CUST-140",
    "firstName": "CustFirst",
    "lastName": "CustLast",
    "products": [
        {
            "sku": "SAMPLE-101"
        }
    ],
    "address": [
        {
            "city": "London",
            "street": "123 Main"
        }
    ]
}'

**Retrieve the customer and associated their products details:**

Let's see Customer API call the Product API to fetch more information about the products:

curl http://localhost:8080/customer/CUST-140/products


**Add an address to the customer:**

curl --location --request PUT 'http://localhost:8080/customer' \
--header 'Content-Type: application/json' \
--data '{
    "customerId": "CUST-140",
    "address": [
        {
            "city": "Toronto",
            "street": "123 Front"
        }
    ]
}'

**Add a product to the customer:**

curl --location --request PUT 'http://localhost:8080/customer' \
--header 'Content-Type: application/json' \
--data '{
    "customerId": "CUST-140",
    "products": [
        {
            "sku": "SAMPLE-100"
        }
    ]
}'

**Create a new product:**

curl --location 'http://localhost:8080/product' \
--header 'Content-Type: application/json' \
--data '    {
        "sku": "SAMPLE-200",
        "name": "Sample 200",
        "description": "A sample product 200"
    }'


**Check the data in the database:**

jdbc:h2:mem:customerdb

http://localhost:8081/h2-console/login.do

jdbc:h2:mem:productdb

http://localhost:8082/h2-console/login.do


**TODO: **

- Add Security


