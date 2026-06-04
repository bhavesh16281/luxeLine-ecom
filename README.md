# LuxeLine E-Commerce Platform

A modern, full-featured e-commerce platform built with **Spring Boot 4.0.0** and **Java 21**. LuxeLine provides a complete backend solution for managing products, orders, carts, and user authentication with role-based access control.

## 📋 Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Configuration](#configuration)
  - [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Database Schema](#database-schema)
- [Security](#security)
- [Project Configuration](#project-configuration)
- [Troubleshooting](#troubleshooting)

## ✨ Features

### Authentication & Authorization
- User registration and login with JWT-based authentication
- JWT cookie-based session management
- Role-based access control (RBAC)
- Three user roles: **ADMIN**, **SELLER**, and **USER**
- Secure password encoding using BCrypt

### Product Management
- Browse and search products
- Product categories
- Product images and details
- Inventory management

### Shopping Cart
- Add/remove items from cart
- Cart item management
- Real-time cart updates

### Orders & Payments
- Order creation and tracking
- Order item details
- Payment integration ready
- Order status management

### Address Management
- User addresses for shipping
- Multiple address support

### API Documentation
- Swagger/OpenAPI integration for interactive API documentation
- Auto-generated API specs at `/swagger-ui.html`

## 🛠 Tech Stack

| Component | Technology | Version |
|-----------|-----------|---------|
| Framework | Spring Boot | 4.0.0 |
| Language | Java | 21 |
| Database | MySQL | Latest |
| ORM | Hibernate/JPA | 7.1.8 |
| Security | Spring Security | 7.0.0 |
| Authentication | JWT (JJWT) | 0.12.3 |
| API Docs | SpringDoc OpenAPI | 3.0.2 |
| Mapping | ModelMapper | 3.0.0 |
| Build Tool | Maven | Latest |
| Validation | Jakarta Validation | 3.1.1 |
| Lombok | Lombok | Latest |

## 📁 Project Structure

```
luxeLine-ecom/
├── src/main/java/com/bhavesh16281/ecommerce/luxeLine_ecom/
│   ├── config/              # Configuration classes
│   │   ├── AppConfig.java   # Bean configurations
│   │   └── AppConstants.java
│   ├── controller/          # REST API endpoints
│   │   ├── AuthController.java
│   │   ├── ProductController.java
│   │   ├── CartController.java
│   │   ├── OrderController.java
│   │   ├── CategoryController.java
│   │   └── AddressController.java
│   ├── model/               # Entity classes
│   │   ├── User.java
│   │   ├── Product.java
│   │   ├── Order.java
│   │   ├── Cart.java
│   │   ├── CartItem.java
│   │   ├── Category.java
│   │   ├── Address.java
│   │   ├── Payment.java
│   │   ├── OrderItem.java
│   │   ├── Role.java
│   │   └── AppRole.java
│   ├── repositories/        # JPA Repository interfaces
│   │   ├── UserRepository.java
│   │   ├── ProductRepository.java
│   │   ├── CartRepository.java
│   │   ├── OrderRepository.java
│   │   ├── CategoryRepository.java
│   │   └── ...
│   ├── service/             # Business logic layer
│   │   ├── ProductService.java
│   │   ├── CartService.java
│   │   ├── OrderService.java
│   │   ├── CategoryService.java
│   │   ├── AddressService.java
│   │   ├── FileService.java
│   │   └── *Impl.java       # Service implementations
│   ├── security/            # Security configurations
│   │   ├── WebSecurityConfig.java      # Main security config
│   │   ├── jwt/
│   │   │   ├── JwtUtils.java           # JWT token utilities
│   │   │   ├── AuthTokenFilter.java    # JWT token filter
│   │   │   └── AuthEntryPointJwt.java  # JWT error handler
│   │   ├── service/
│   │   │   ├── UserDetailsServiceImpl.java
│   │   │   └── UserDetailsImpl.java
│   │   ├── request/
│   │   │   ├── LoginRequest.java
│   │   │   └── SignupRequest.java
│   │   └── response/
│   │       ├── UserInfoResponse.java
│   │       └── MessageResponse.java
│   ├── dto/                 # Data Transfer Objects
│   │   ├── ProductDTO.java
│   │   ├── CartDTO.java
│   │   ├── OrderDTO.java
│   │   ├── CategoryDTO.java
│   │   ├── AddressDTO.java
│   │   └── ...
│   ├── exceptions/          # Custom exceptions & handlers
│   │   └── GlobalExceptionHandler.java
│   ├── util/                # Utility classes
│   │   └── AuthUtil.java
│   └── LuxeLineEcomApplication.java
├── src/main/resources/
│   └── application.properties
├── pom.xml                  # Maven configuration
└── README.md
```

## 🚀 Getting Started

### Prerequisites

- **Java Development Kit (JDK) 21** or higher
- **Maven 3.6+** (for building the project)
- **MySQL 8.0+** (for database)
- **Git** (for version control)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/bhavesh16281/luxeLine-ecom.git
   cd luxeLine-ecom
   ```

2. **Build the project**
   ```bash
   ./mvnw clean install
   ```
   (On Windows, use `mvnw.cmd` instead of `./mvnw`)

### Configuration

#### Database Setup

Edit `src/main/resources/application.properties`:

**For Local MySQL:**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/luxeline-ecom
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

**For AWS RDS (Already Configured):**
```properties
spring.datasource.url=jdbc:mysql://luxeline-ecommerce-application.cit5mgggm5vs.us-east-1.rds.amazonaws.com:3306/luxeline-ecom
spring.datasource.username=admin
spring.datasource.password=admin4dev
```

#### JWT Configuration

The application uses JWT for stateless authentication:

```properties
spring.app.jwtSecret=mySecretKey123912738aopsgjnspkmndfsopkvajoirjg94gf2opfng2moknm
spring.app.jwtExpirationMs=3000000        # 50 minutes
spring.app.jwtCookieName=LuxeLineJWT
```

⚠️ **Security Warning**: Change the `jwtSecret` and credentials in production!

#### Logging Configuration

```properties
logging.level.org.springframework=DEBUG
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.springframework.security=DEBUG
logging.level.com.bhavesh16281.ecommerce.luxeLine_ecom=DEBUG
```

#### Image Storage

```properties
project.image=images/    # Directory for storing product images
```

### Running the Application

```bash
./mvnw spring-boot:run
```

The application will start on **http://localhost:8080**

### Access API Documentation

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

## 📡 API Endpoints

### Authentication
- `POST /api/auth/signup` - Register a new user
- `POST /api/auth/signin` - Login user
- `GET /api/auth/user` - Get current user details
- `GET /api/auth/username` - Get current username
- `POST /api/auth/signout` - Logout user

### Products
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `POST /api/products` - Create product (Admin)
- `PUT /api/products/{id}` - Update product (Admin)
- `DELETE /api/products/{id}` - Delete product (Admin)

### Categories
- `GET /api/categories` - Get all categories
- `GET /api/categories/{id}` - Get category by ID
- `POST /api/categories` - Create category (Admin)
- `PUT /api/categories/{id}` - Update category (Admin)
- `DELETE /api/categories/{id}` - Delete category (Admin)

### Cart
- `GET /api/cart` - Get user's cart
- `POST /api/cart/add` - Add item to cart
- `PUT /api/cart/{id}` - Update cart item
- `DELETE /api/cart/{id}` - Remove item from cart

### Orders
- `GET /api/orders` - Get user's orders
- `POST /api/orders` - Create new order
- `GET /api/orders/{id}` - Get order details
- `PUT /api/orders/{id}` - Update order status (Admin)

### Address
- `GET /api/addresses` - Get user's addresses
- `POST /api/addresses` - Add new address
- `PUT /api/addresses/{id}` - Update address
- `DELETE /api/addresses/{id}` - Delete address

## 🗄 Database Schema

### Key Entities

**User**
- id (PK)
- username (unique)
- email (unique)
- password (encrypted)
- roles (M2M with Role)
- cart, orders, addresses

**Product**
- id (PK)
- name
- description
- price
- image
- category_id (FK)

**Cart**
- id (PK)
- user_id (FK)
- cartItems (1:M with CartItem)

**Order**
- id (PK)
- user_id (FK)
- orderItems (1:M with OrderItem)
- payment_id (FK)
- address_id (FK)

**Category**
- id (PK)
- name
- description

**Role**
- id (PK)
- roleName (ROLE_USER, ROLE_ADMIN, ROLE_SELLER)

## 🔒 Security

### Authentication Flow
1. User registers via `/api/auth/signup`
2. User logs in via `/api/auth/signin`
3. Server generates JWT token and sets it as HTTP-only cookie
4. Client sends cookie with subsequent requests
5. `AuthTokenFilter` validates JWT on every request

### Authorization
- Role-based endpoint protection using Spring Security
- Three roles: `ROLE_USER`, `ROLE_ADMIN`, `ROLE_SELLER`
- Users assigned roles during registration

### Features
- ✅ BCrypt password encryption
- ✅ JWT token-based stateless authentication
- ✅ HTTP-only JWT cookies (prevents XSS)
- ✅ CSRF protection
- ✅ Role-based access control
- ✅ Secure password validation

## ⚙️ Project Configuration

### Maven Dependencies

Core dependencies include:
- Spring Boot Web Starter
- Spring Boot Security Starter
- Spring Data JPA
- MySQL Connector
- JJWT (JWT library)
- SpringDoc OpenAPI (Swagger)
- ModelMapper (DTO mapping)
- Lombok (Boilerplate reduction)
- Validation (Jakarta Validation API)

### Spring Boot Properties

| Property | Value | Purpose |
|----------|-------|---------|
| spring.jpa.show-sql | true | Display SQL queries in logs |
| spring.jpa.hibernate.ddl-auto | update | Auto-update database schema |
| spring.jpa.properties.hibernate.dialect | MySQLDialect | MySQL-specific optimizations |

## 🐛 Troubleshooting

### Build Issues

**Problem**: Maven command not found
```bash
# Windows - Use the wrapper script
mvnw.cmd clean install

# Linux/Mac
./mvnw clean install
```

### Database Issues

**Problem**: Cannot connect to MySQL
- Verify MySQL is running: `mysql -u root -p`
- Check connection URL and credentials in `application.properties`
- Ensure database exists: `CREATE DATABASE luxeline-ecom;`

**Problem**: Hibernate dialect error (`MySQL8Dialect` not found)
- Update `application.properties` to use `MySQLDialect`:
  ```properties
  spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
  ```

### JWT Issues

**Problem**: Token expired
- Adjust `spring.app.jwtExpirationMs` in `application.properties`

**Problem**: Cannot find JWT claims
- Verify JWT secret key is correct and consistent

### Port Already in Use

```bash
# Change port in application.properties
server.port=8081
```

## 📝 License

This project is created for educational purposes.

## 👨‍💻 Author

**Bhavesh** - [@bhavesh16281](https://github.com/bhavesh16281)

---

**Last Updated**: June 2026

