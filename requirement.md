# Microservice Architecture

In the following exercise you will create a microservice architecture system, which includes the following elements:

- Functional microservices - separate java applications, each performing some business function
- Authentication service, for user identity verification
- Discovery server - functional microservices can register themselves
- Specification based Rest API generator
- API Gateway (optional)

---
Architecture Diagram
![Architecture Diagram](https://raw.githubusercontent.com/epam-java-cre/exercise-specification-images/main/microservices/architecture.png)
---

## Prerequisite knowledge

- Spring Framework, Spring Boot
- RESTful API, OpenAPI
- Implement RESTful service with Spring MVC
- Concept of microservices
- Eureka Discovery Server, Feign Client
- JWT (JavaScript Web Token)

On top of the already known technologies, you will practice spring-cloud components.


# Problem domain

The architecture consists of two functional microservices: **Order Service** and **Shipping Service**.

Order service manages the orders of an imaginary web shop.
Once the order has been shipped, customers can track the package using the Shipping Service.  

The two services represent the backend system of the web shop application.

- Customers can view their submitted orders - Order Service handles those requests.
- Once a customer get a package tracking id, customer can query the package with the Shipping Service.
- The system makes it possible to query aggregated order: details of both the order and the related package.
  To support this functionality, Order service handles the aggregated order query.
  Order Service takes the order from its database, and performs a remote call to the Shipping Service to get the package details.
  (service-to-service communication).


# Authorization Service

We would like to hide from the user that there are multiple backend services that serves the requests.
We apply **Federated Authentication**: the user authenticates himself/herself with the Authentication Service
(Centralized Identity Management), which issues a **Web Token** on successful authentication.

The functional services do not need to authenticate the user, or manage user's session (store in memory or any other storage).
They accept the token issued by the authentication server and get any user details that are needed (username, roles, etc). 

Token authenticity and integrity is ensured by **digital signature**.
Functional services (or resource servers in other terminology) trusts the Authentication Server and can (and must)
verify the token with the authentication server's public key.

## Token Propagation

Both order service and Shipping service need token to identify the user who had initiated the service call and check access.
When Order Service calls Shipping Service endpoint, it must also provide the JWT token. This requires extra development,
see **interceptor** later.


## Preparation

Please run the **key-generator** application to generate public / private key pair for the auth server for token signing.

Add the private key to authentication server configuration (application.yml file), configuration key: `token.secret.privateKey`

Save the public key - the functional services will need it to verify the token.

## Test the service

Authentication server is ready for use (except the private key configuration). You do not need to write any code in it.

It runs on port 9000, and provides the login endpoint at `/api/v1/login` url.

Credentials must be provided in the request body in the following format (working example):

```json
{
  "email":"david.brown@users.com",
  "password": "password"
}
```

Please make a service call in Postman (or other tool that supports Rest API testing) and get the result.

The returned token can viewed by https://jwt.io/ website.

Look at the different values in the token:
- roles
- name
- sub (subject, email address)
- iat (issued at)
- exp (expiration)


---
JWT token on jwt.io
![](https://raw.githubusercontent.com/epam-java-cre/exercise-specification-images/aad06c22482ba2192b03c1aa373faeddb574168b/microservices/token.png "JWT Token Example")
---

Notes:
- Authentication server defines users in application.yml, at user-store.users key
- Best practice for token based authentication is to use OAuth protocol. To keep the project simple, we chose a simpler solution here.


# Discovery service

In a microservice architecture there can be large number of running services.

To scale the architecture horizontally, one service can run in multiple instances.
Services can start and stop any time, due to scaling or service failure.
Not only the number, but the location of the available services  are dynamic and can change.

Discovery server plays the role in the architecture to enable services to register themselves,
and query it to get the address of other services.

# Create Discovery Service

Spring Cloud Netflix umbrella project provides Eureka Server as a Discovery Server implementation.

The following steps describes how to create a Eureka Server Spring Boot application.

Create a Spring Boot application in `discovery-service` folder of the repository.

Create maven build file (pom.xml) similarly to other existing services in the repository.
(Make sure the use the same Spring Boot version.)

Add the spring-cloud version property to the project properties:

```xml
<spring-cloud.version>2021.0.8</spring-cloud.version>
```
Add dependencyManagement:

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>${spring-cloud.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

Add dependency: (there should be only one dependency):

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```

Add the following annotation to the `Application` (the class that you annotated with `@SpringBootApplication`):

```java
@EnableEurekaServer
```

Create `application.yml` with the following content:

```yaml
server:
  port: 8761
eureka:
  instance:
    hostname: localhost
  client:
    registerWithEureka: false
    fetchRegistry: false
    serviceUrl:
      defaultZone: http://localhost:${server.port}/eureka/
```

Start the server and navigate to the following address in a browser: http://localhost:8761/

## Additional Details 

You can try Load Balancing later in the lifecycle of this project.

However, there is a limitation: each service has its separate in-memory database. If you scale one service,
the instances should share the same database. (You might run stand-alone H2 database to achieve it.)

# Order Service

Order service is already created and functional. There is only a missing configuration.


## Order Service Configuration

Authentication Service's public key must be configured in Order Service to validate JWT token.

Add the following configuration in order-service-app `application.yml` file:

```yaml
token:
  secret:
    publicKey: <insert generated key here>
```

## Order Service Test Call

Start Order Service and make a test request call from Postman.

Add the jwt token (you can take from Authentication service login response) as Authorization header, tyep: **Bearer Token**.

`GET http://localhost:8080/api/v1/orders/1`


## Make Order Service Eureka client

Change Order Service to be a Eureka client application.

Add **spring-cloud-dependencies** to the dependencyManagement section of the maven build (take configuration from **Discover Server**)

Add the following dependency to order-service-app pom.xml:

```xml
<!-- service is eureka client -->
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

Add the following configuration to `application.yml`

```yaml
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
```


### Test Order Service Eureka Client

Start Order Service (Discovery Server should be running).

Navigate to Discovery Server console and check if Order Server is listed. Application name: **order-service**.

---
Eureka server screen:
![](https://raw.githubusercontent.com/epam-java-cre/exercise-specification-images/aad06c22482ba2192b03c1aa373faeddb574168b/microservices/Eureka.png "Eureka home")
---


# Shipping Service

## Shipping Service Configuration

Apply the same configuration change as for the Order Service.

## Shipping Service Test Call

Execute the following Rest call from Postman.

GET http://localhost:8090/api/v1/packages/1001

(Add Authorization header the same way as in case of order service.)

## Shipping Service Eureka client

Apply the same change as for Order Service.

### Test Shipping Service Eureka Client

Start Shipping Service (Discovery Server should be running).

Navigate to Discovery Server console and check if Package Server is listed (**shipping-service** application name).

# Order to Shipping

In this chapter you find the steps to implement the aggregated order query.

## Add dependencies to Order service 

Shipping service exposes a Feign Client (`ShippingApiClient`) to make its endpoints available for remote callers.
It is available in `shipping-service-api` maven dependency. Please add this dependency to `order-service-app` project.

Add shipping-service-api dependency to order-service.

Also add the following dependency:

```xml
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

## Order Service API Changes

Take `PackageModel` from `shipping-api.yml` in shipping-service-api, and copy to `order-api.yml` in shipping-service-api, components/schemas section.

Also add the following type:

```yml
OrderAndPackageModel:
  type: object
  description: the order and the model objects together
  properties:
    order:
      $ref: "#/components/schemas/OrderModel"
    package:
      $ref: "#/components/schemas/PackageModel"
```

Add the following endpoint:

```yaml
  /api/v1/aggregatedorders/{id}:
    get:
      tags:
        - Order
      operationId: getAggregatedOrder
      description: Returns the order and the package associated with the provided id in path
      parameters:
        - name: id
          in: path
          description: The id of the queried order
          required: true
          schema:
            type: integer
            format: int64
      responses:
        '200':
          description: Normal response
          content:
            application/json:
              schema:
                $ref: "#/components/schemas/OrderAndPackageModel"
        '404':
          description: Not found
          content:
            application/json:
              schema:
                $ref: "#/components/schemas/ErrorModel"
```
Regenerate the API source files with a maven build: `install` goal in `order-service-api` project.

## Order Service Implementation changes

Enable Feign Clients in order-service-app, so that you will be able to perform shipping service remote calls.
Add the following annotation to `OrderApplication`:

```java
@EnableFeignClients("com.epam.training.shipping.api")
```

## Implementation of the Rest Operation

`OrderController`'s base class `OrderApi` defines `getAggregatedOrder` method, which is not implemented yet in `OrderController`.

Please add the following (to OrderController?):

- Add `ShippingApiClient` Spring dependency.
- Implement `getAggregatedOrder` method.
  - Load the order from the database.
  - Call `ShippingApiClient`'s `getPackage` method with the order's packageId.
  - Create a OrderAndPackageModel object, and populate its fields with the order and package model.
  - Add another map method to `OrderModelApiMapper`, that performs the order and package conversion.

## Token propagation

Shipping service operations require authentication. However, the client interface `ShippingApiClient` does not define parameter for it.
Anyway, it would not be a nice solution to pass the security token manually for each service call. Instead, we implement a generic solution:
Feign request interceptor.

Crete a new class: `JwtTokenRequestInterceptor` in `com.epam.training.order.clientsecurity` package.

- Implement `feign.RequestInterceptor`
- Add "Authorization" header to each call. Value should be "Bearer " concatenated with the JWT token. The token is available in `SecurityContextHolder` (`JwtUserDetails`).

To activate the interceptor, add the following configuration: 

```yaml
feign:
  client:
    config:
      default:
        requestInterceptors:
          com.epam.training.order.clientsecurity.JwtTokenRequestInterceptor
```

## Test the endpoint

Call `/api/v1/aggregatedorders` in Postman the same way as you did for `/api/v1/orders` endpoint.
Execution includes order-service to shipping-service call.

## Load Balancing (optional)

You can test the Feign client Load Balancing feature.
If multiple instances of the same service registers in Discovery Server,
Feign clients get this information from the Discovery Server and calls the endpoint on different instances.

To test this functionality, start another instance of the Shipping Service (it should allocate another HTTP port, see server.port configuration).

Please make sure that Shipping Service writes some message in its log when a REST operation gets executed.

When you call the `/api/v1/aggregatedorders` endpoint multiple times, one of Shipping services will serve the request once,
and the other will serve the other time.

# Autocode

This distributed application can not be tested in Autocode. Compilation of the project by Autocode is not implemented either.
Autocode is used for **template project storage** in this exercise.
If you have completed the exercise, press **Submit solution**. It will fail without a valid pom.xml file in the repository root directory. 
Do not worry, simply press **Finish**.

# Further Improvements

This example architecture is quite minimal. It does not contain the following components:

## API Gateway

API Gateway can be:

- Router to functional services.
- Load Balancer of the functional services.
- Implement centralized security check.

## Configuration Server

Centralized store of configuration values to avoid redundancy.

## Messaging

Components of the architecture could communicate with each other in an asynchronous way.

Example use case in the current application: authentication service could send messages in case of user creation or termination,
so that functional services can update their local user table.
