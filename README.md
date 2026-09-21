# E-Commerce Microservices Application

A simple and scalable **E-Commerce backend application** built using Spring Boot Microservices. The application provides authentication, product management, order management, and event-driven notifications with JWT-based security, service discovery, inter-service communication, Apache Kafka, PostgreSQL databases, and Docker.

## 🚀 Features

* User registration and login
* JWT-based authentication
* Role-based authorization using `ADMIN` and `USER`
* Admin product management
* User product viewing and order placement
* View user's own orders
* Service discovery using Eureka
* API Gateway for routing requests
* Inter-service communication using OpenFeign
* Event-driven communication using Apache Kafka
* Notification service for order events
* Separate PostgreSQL database for each service
* Dockerized services and databases using Docker Compose

## 🏗️ Architecture

```text
                         Client / Postman
                                |
                                v
                       API Gateway :8080
                                |
              +-----------------+-----------------+
              |                 |                 |
              v                 v                 v
        Auth Service      Product Service    Order Service
           :8081              :8082              :8083
              |                 |                 |
              v                 v                 v
          auth-db           product-db          order-db
                                                    |
                                                    | Kafka Producer
                                                    v
                                             +-------------+
                                             |    Kafka    |
                                             | :9092       |
                                             +------+------+
                                                    |
                                             order-events
                                                    |
                                                    v
                                      Notification Service
                                             :8084
                                                    |
                                                    v
                                           Order Notification


                         Eureka Server
                            :8761
```

## 🧩 Microservices

### 1. Auth Service

* User registration and login
* Password encryption using BCrypt
* JWT token generation
* Role management
* User profile and user lookup

### 2. Product Service

* Add products
* View products
* Update products
* Delete products
* Admin-only product management

### 3. Order Service

* Place orders
* Fetch user's orders
* Product and user information through OpenFeign
* Stock availability check
* Order total calculation
* Publishes order events to Kafka

### 4. Notification Service

* Consumes order events from Kafka
* Processes order notification events
* Demonstrates event-driven communication
* Currently logs notifications to the console

### 5. API Gateway

* Single entry point for clients
* Routes requests to respective microservices
* Uses Eureka service discovery

### 6. Service Registry

* Eureka Server
* Registers and discovers microservices

## 🔐 Security

The application uses **Spring Security and JWT** for authentication and authorization.

### Roles

**ADMIN**

* Login
* Add products
* Update products
* Delete products
* View products

**USER**

* Register
* Login
* View products
* Place orders
* View own orders

Passwords are securely stored using **BCrypt hashing**.

## 🛠️ Technologies Used

* Java 21
* Spring Boot
* Spring Security
* JWT
* Spring Data JPA / Hibernate
* PostgreSQL
* Spring Cloud Eureka
* Spring Cloud Gateway
* OpenFeign
* Apache Kafka
* Spring Kafka
* Resilience4j
* Spring Boot Actuator
* Maven
* Docker
* Docker Compose
* Lombok

## 📁 Project Structure

```text
ecommerce-microservices/
│
├── api-gateway/
├── auth-service/
├── product-service/
├── order-service/
├── notification-service/
├── service-registry/
│
├── docker-compose.yml
├── .gitignore
└── README.md
```

## 🐳 Running with Docker

Make sure Docker Desktop is installed and running.

Build and start all services:

```bash
docker compose up --build
```

To run the containers in the background:

```bash
docker compose up --build -d
```

Check running containers:

```bash
docker ps
```

Stop the application:

```bash
docker compose down
```

## 🌐 Service Ports

| Service              | Port |
| -------------------- | ---: |
| API Gateway          | 8080 |
| Auth Service         | 8081 |
| Product Service      | 8082 |
| Order Service        | 8083 |
| Notification Service | 8084 |
| Eureka Server        | 8761 |
| Kafka                | 9092 |
| Auth PostgreSQL      | 5433 |
| Product PostgreSQL   | 5434 |
| Order PostgreSQL     | 5435 |

## 🔗 API Endpoints

### Authentication

```text
POST /api/auth/register
POST /api/auth/login
GET  /api/auth/profile
```

### Products

```text
POST   /api/products
GET    /api/products
GET    /api/products/{id}
PUT    /api/products/{id}
DELETE /api/products/{id}
```

### Orders

```text
POST /api/orders
GET  /api/orders/my
```

All client requests can be sent through the API Gateway:

```text
http://localhost:8080
```

## 🔄 Inter-Service Communication

The application uses both **synchronous and asynchronous communication**.

### Synchronous Communication

Order Service communicates with Auth Service and Product Service using **OpenFeign**.

```text
Order Service
     |
     +-----> Auth Service
     |        Get user information
     |
     +-----> Product Service
              Get product information
```

Services are discovered dynamically through **Eureka Service Registry**.

### Asynchronous Communication

Kafka is used for event-driven communication between Order Service and Notification Service.

```text
Order Service
     |
     | OrderEvent
     v
   Kafka
     |
     | order-events
     v
Notification Service
     |
     v
Notification
```

When an order is successfully placed, Order Service publishes an `OrderEvent` containing information such as:

```json
{
  "orderId": 1,
  "userId": 5,
  "productId": 10,
  "quantity": 2,
  "totalAmount": 1999.98
}
```

The Notification Service consumes this event and processes the notification.

## 📨 Kafka

### Topic

```text
order-events
```

### Producer

```text
Order Service
```

### Consumer

```text
Notification Service
```

Kafka enables asynchronous communication so that the Order Service does not need to directly call the Notification Service.

## 🛡️ Resilience and Monitoring

The application uses **Resilience4j** to improve resilience of inter-service communication.

### Circuit Breaker

Circuit breakers are implemented around Product Service communication to prevent repeated calls when the dependent service is unavailable.

```text
Order Service
      |
      v
    Retry
      |
      v
Circuit Breaker
      |
      v
Product Service
```

### Retry

Transient failures can be retried before triggering the fallback mechanism.

### Actuator

Spring Boot Actuator provides monitoring endpoints for application health and Resilience4j metrics.

```text
/actuator/health
/actuator/metrics
/actuator/circuitbreakers
```

## 🗄️ Database Architecture

Each microservice has its own PostgreSQL database.

```text
Auth Service          → auth_db
Product Service       → product_db
Order Service         → order_db
Notification Service  → No database
```

This follows the **database-per-service** approach used in microservice architectures.

## 📌 Key Concepts Demonstrated

* Microservices Architecture
* REST APIs
* JWT Authentication
* Role-Based Access Control
* Spring Security
* JPA/Hibernate
* PostgreSQL
* Service Discovery
* API Gateway
* OpenFeign
* Apache Kafka
* Event-Driven Architecture
* Asynchronous Communication
* Retry Pattern
* Circuit Breaker Pattern
* Spring Boot Actuator
* Docker Containerization
* Docker Compose
* Inter-Service Communication
* Database-per-Service

## 👨‍💻 Author

**Uday Shinde**

Computer Engineering | Java | Spring Boot | Microservices | React

GitHub: https://github.com/uday7027
