# 🛒 Product Service – Spring Boot REST API

A production-ready **Spring Boot RESTful application** to manage products, built using a clean **Controller → Service → Repository** architecture.  
The application demonstrates **best practices**, **SOLID principles**, **DTO usage**, **exception handling**, **pagination**, **soft delete**, and **comprehensive JUnit testing**.

---

## 📌 Features

- Create, Read, Update, Patch, and Delete Products
- Soft delete implementation (no physical DB deletion)
- Pagination, sorting, and filtering support
- Duplicate product validation
- Global exception handling
- DTO-based API contracts
- JUnit tests for Controller layer
- Clean code & layered architecture

---

## 🧱 Tech Stack

| Technology | Version |
|-----------|--------|
| Java      | **21** |
| Spring Boot | **3.3.4** |
| Database | **MySQL** |
| ORM | Spring Data JPA (Hibernate) |
| Build Tool | Maven |
| Testing | JUnit 5, Mockito, MockMvc |
| API Docs | Swagger / OpenAPI (optional) |

---

## 🏗️ Architecture
Controller → Service → Repository
↓ ↓ ↓
DTOs Business Database
Logic

- **Controller**: Handles HTTP requests and responses
- **Service**: Contains business logic
- **Repository**: Handles persistence using JPA
- **DTOs**: Separate API contracts from entity models

---

## 📦 Product Entity

```json
{
  "id": 1,
  "name": "Laptop",
  "price": 50000,
  "quantity": 5,
  "category": "Electronics"
}
```
# 🔗 REST API Endpoints

➕ Create Product
```
POST /api/products/addproduct
```

🔍 Get Product by ID
```
GET /api/products/findproduct/{id}
```

📄 Get All Products (Pagination + Filter)
```
GET /api/products/findpage
```
Query Parameters
- page (default: 0)
- size (default: 5)
- sortBy (id, price, quantity)
- sortDir (asc / desc)
- category
- minPrice, maxPrice
- minQty, maxQty

✏️ Update Product (Full)
```
PUT /api/products/putproduct/{id}
```

🩹 Patch Product (Partial)
```
PATCH /api/products/patchproduct/{id}
```

❌ Delete Product (Soft Delete)
```
DELETE /api/products/deleteproduct/{id}
```
⚠️ Products are soft deleted using a deleted flag.

# ⚠️ Exception Handling
Handled globally using @RestControllerAdvice

Exception	HTTP Status
DuplicateResourceException	409 CONFLICT
ResourceNotFoundException	404 NOT FOUND

---
# 🧪 Testing
Controller Layer Tests
- Implemented using @WebMvcTest
- Uses MockMvc and Mockito
- Service layer is mocked using @MockBean

Covered Scenarios
- Create product
- Get product by ID (success & failure)
- Update product
- Patch product
- Delete product
- Exception handling
---
# 🧰 Database Setup (MySQL)
1️⃣ Create Database
```
CREATE DATABASE product_db;
```

2️⃣ application.properties
```
spring.datasource.url=jdbc:mysql://localhost:3306/product_db
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
```

▶️ Build & Run
Build
```
mvn clean install
```

Run
```
mvn spring-boot:run
```

Application runs on:
```
http://localhost:8080
```
---
# 📘 Swagger / OpenAPI (Optional)

Add dependency:
```
<dependency>
  <groupId>org.springdoc</groupId>
  <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
  <version>2.5.0</version>
</dependency>
```

Access:
```
http://localhost:8080/swagger-ui.html
```
---
# 🧠 Design & Best Practices Followed
- SOLID principles
- DTO pattern
- Soft delete strategy
- Clean separation of concerns
- final usage where applicable
- Centralized exception handling
- Testable and maintainable code
---
# 📂 Project Structure
```
src/main/java
 └── com.jarvis.product
     ├── controller
     ├── service
     ├── repository
     ├── entity
     ├── dto
     └── exception

src/test/java
 └── com.jarvis.product
     └── ProductControllerTest
```

# 👨‍💻 Author

Soubhagya Ranjan Swain
Java | Spring Boot | REST APIs | Microservices
