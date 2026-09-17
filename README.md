# E-Commerce Microservices Application

A simple and scalable **E-Commerce backend application** built using Spring Boot Microservices. The application provides authentication, product management, and order management with JWT-based security, service discovery, inter-service communication, PostgreSQL databases, and Docker.

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

### 4. API Gateway

* Single entry point for clients
* Routes requests to respective microservices
* Uses Eureka service discovery

### 5. Service Registry

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

| Service            | Port |
| ------------------ | ---: |
| API Gateway        | 8080 |
| Auth Service       | 8081 |
| Product Service    | 8082 |
| Order Service      | 8083 |
| Eureka Server      | 8761 |
| Auth PostgreSQL    | 5433 |
| Product PostgreSQL | 5434 |
| Order PostgreSQL   | 5435 |

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

The Order Service communicates with other services using **OpenFeign**.

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

## 🗄️ Database Architecture

Each microservice has its own PostgreSQL database.

```text
Auth Service     → auth_db
Product Service  → product_db
Order Service    → order_db
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
* Docker Containerization
* Docker Compose
* Inter-Service Communication
* Database-per-Service

## 👨‍💻 Author

**Uday Shinde**

Computer Engineering | Java | Spring Boot | Microservices | React

GitHub: https://github.com/uday7027
