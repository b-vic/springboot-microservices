# springboot-microservices

## customer: 

API to manage customer data, their addresses as well as their associated products (see http://localhost:8081/customer)

## product: 

API to manage a list of products (see http://localhost:8082/product)

## api-gateway: 

A reverse proxy that routes calls to APIs or between APIs (e.g. http://localhost:8080/customer OR http://localhost:8080/product)

## service-registry: 

Job is to manage API services, their status / availability and their location

## Steps

git clone https://github.com/b-vic/springboot-microservices.git
mvn clean package

java -jar ./service-registry/target/service-registry-0.0.1-SNAPSHOT.jar &
java -jar ./api-gateway/target/api-gateway-0.0.1-SNAPSHOT.jar &
java -jar ./customer/target/customer-0.0.1-SNAPSHOT.jar &
java -jar ./product/target/product-0.0.1-SNAPSHOT.jar &

**See all the products:**

curl 'http://localhost:8080/product/page?pageNumber=0&pageSize=5'

**Create a customer with a linked product:**

curl -X POST -H'Content-type: application/json' http://localhost:8080/customer --data '{"customerId":"CUST-140","firstName":"CustFirst","lastName":"CustLast","address": [{"street": "123 Main", "city": "London"}],"products":[{"sku":"SAMPLE-101"}]}'

**Retrieve customer and associated products details:**

curl http://localhost:8080/customer/CUST-140/products

**Add an address to the customer:**

curl -X PUT ...

**Add a product to the customer:**

curl -X PUT ...


