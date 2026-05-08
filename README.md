# Serverless Java Products API

Serverless CRUD REST API built with Java 21, Spring Cloud Function, AWS Lambda, API Gateway, DynamoDB, and Serverless Framework.

This project was developed as part of the AWS API Gateway CRUD REST API challenge.

---

# Architecture

```text
API Gateway
    ↓
AWS Lambda
    ↓
Spring Cloud Function
    ↓
Service Layer
    ↓
DynamoDB
```

---

# Technologies Used

  -  Java 21
  -  Spring Boot 3
  -  Spring Cloud Function
  -  AWS Lambda
  -  AWS API Gateway
  -  DynamoDB
  -  DynamoDB Enhanced Client
  -  Serverless Framework
  -  Maven
  -  GitHub Actions (planned)
  -  Infrastructure as Code (IaC)

---

# Features

  - Create Product
  -  Get Product by ID
  - Get All Products
  -  Update Product
  - Delete Product
---
# API Endpoints

| Method | Endpoint         | Description       |
| ------ | ---------------- | ----------------- |
| POST   | `/products`      | Create a product  |
| GET    | `/products/{id}` | Get product by ID |
| GET    | `/products`      | Get all products  |
| PATCH  | `/products/{id}` | Update product    |
| DELETE | `/products/{id}` | Delete product    |

---
# Example Request

- Create Product
  - POST /products
    Content-Type: application/json
  ```json
  {
  "name": "Laptop",
  "description": "Gaming laptop",
  "price": 1500,
  "stock": 10,
  "category": "Electronics"
   }
  ```

    - Response
  ```json
    {
    "id": "41ad7346-7bb8-441a-8396-3c5e95b10adf",
    "name": "Laptop",
    "description": "Gaming laptop",
    "price": 1500,
    "stock": 10,
    "category": "Electronics",
    "createdAt": "2026-05-07T23:30:57Z",
    "updatedAt": "2026-05-07T23:30:57Z"
    }
  ```
---
# Project Structure
```text


src/main/java
│
├── config
│   └── DynamoDbConfig
│
├── dto
│   ├── CreateProductRequest
│   ├── UpdateProductRequest
│   └── ProductResponse
│
├── function
│   ├── CreateProductFunction
│   ├── GetProductFunction
│   ├── ListProductsFunction
│   ├── UpdateProductFunction
│   └── DeleteProductFunction
│
├── model
│   └── Product
│
├── repository
│   └── ProductRepository
│
├── service
│   └── ProductService
│
└── ServerlessJavaProductsApiApplication

```
---
# Infrastructure as Code

 - All AWS infrastructure is provisioned using Infrastructure as Code with Serverless Framework.
 - Resources created automatically:
   - AWS Lambda Functions
   - API Gateway REST API
   - DynamoDB Table
   - IAM Permissions
---

# Deployment

 - Prerequisites
   - Java 21
   - Maven
   - Node.js
   - AWS CLI
   - Serverless Framework

---

# Build

 - mvnw.cmd clean package

# Deploy

- serverless deploy --stage dev

# Logs

- serverless logs -f createProduct --stage dev

---

# DynamoDB Table

- The application uses a DynamoDB table created automatically by Serverless Framework.
  - Table naming convention: 
    - serverless-java-products-api-dev-products
     
---

# Technical Decisions

- Spring Cloud Function
  - Spring Cloud Function was used to integrate Spring Boot with AWS Lambda while keeping business logic decoupled from 
  AWS-specific implementations.
  
---
# Serverless Framework

- Infrastructure as code
- Lambda deployment
- API Gateway configuration
- Environment management
- Multi-stage deployments

---

# Layered Architecture

- Function Layer
- Service Layer
- Repository Layer
- Persistence Layer

---

# CI/CD

- This project uses GitHub Actions to automatically build and deploy the Serverless application
---

# Author
- Andres Felipe Arredondo Hernandez