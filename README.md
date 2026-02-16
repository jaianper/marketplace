# Reactive Marketplace Backend (RMB)

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.2-brightgreen)
[![License](https://img.shields.io/npm/l/marketplace.svg)](LICENSE)
![GitHub language top](https://img.shields.io/github/languages/top/jaianper/marketplace)
[![CI](https://github.com/jaianper/marketplace/actions/workflows/ci.yml/badge.svg)](https://github.com/jaianper/marketplace/actions/workflows/ci.yml)

> Backend system for a digital services marketplace, designed for high concurrency, security, and scalability using reactive programming.

## 🎯 Project Overview

This project implements a backend for a freelancing marketplace (similar to Fiverr/Upwork) using **Spring Boot WebFlux** (High Concurrency) and **Hexagonal Architecture**. It is a comprehensive example of modern Java backend development.

### Implemented Features

- **User Identity (Auth Context)**:
    - Registration & Login with JWT Authentication.
    - Role-Based Access Control (RBAC).
    - Argon2 Password Hashing.

- **Marketplace Core (Domain Context)**:
    - **Service Offers**: Create, Publish, and Manage freelancer services.
    - **Orders**: Full purchasing flow with Order State Machine (`PENDING` -> `ACCEPTED` -> `COMPLETED`).
    - **Domain Events**: Event-driven architecture using `ApplicationEventPublisher`.

- **Audit & Security**:
    - **Audit Logging**: Asynchronous logging of critical events to MongoDB.
    - **Security**: Stateless JWT, strict input validation, and secure headers.

---

## 🛠️ Technology Stack

- **Language**: Java 21+
- **Framework**: Spring Boot 3.x (WebFlux)
- **Databases**:
    - PostgreSQL (Relational - Users, Services, Orders)
    - MongoDB (Document - Audit Logs, Events)
- **Persistence Access**: Spring Data R2DBC / Reactive Mongo
- **Security**: Spring Security WebFlux + JWT + Argon2
- **Build Tool**: Gradle
- **CI/CD**: GitHub Actions
- **Quality**: Spotless, SpotBugs, FindSecBugs, OWASP Dependency-Check
- **Testing**: JUnit 5, Mockito

---

## 💻 Development Principles

To ensure maintainability, scalability, and code quality, this project strictly adheres to:

- **OOP & SOLID Principles**:
    - **S**ingle Responsibility Principle.
    - **O**pen/Closed Principle.
    - **L**iskov Substitution Principle.
    - **I**nterface Segregation Principle.
    - **D**ependency Inversion Principle.
- **Clean Code**: Readable, simple, and self-documenting code.
- **Design Patterns (GoF)**: Applied where appropriate (e.g., Factory, Strategy, Adapter, Observer, Builder).
- **DTO Pattern**: Data Transfer Objects (DTOs) are used to transfer data between layers, keeping domain entities isolated from the API and persistence layers.

---

## 🛡️ Security & Best Practices

This project implements robust security measures aligned with **OWASP Top 10**:

- **Authentication**: Stateless JWT (JSON Web Token) strategy with strict expiration and signature verification.
- **Password Security**: Strong hashing using **Argon2** or **BCrypt** (adaptive hashing).
- **Data Protection**:
    - **HTTPS/TLS**: Enforced in production.
    - **Least Privilege**: Repository and Service layers expose only necessary operations.
- **Injection Prevention**: Usage of **R2DBC** and parameterized queries to prevent SQL Injection.
- **Input Validation**: Strict validation on all DTOs and Value Objects.

---

## 🧱 Architecture

The project follows **Vertical Slicing (Modular Monolith)** combined with **Hexagonal Architecture** within each module:

```
com.jaianper.marketplace
├── user                # User Module (Identity & Access Context)
│   ├── domain          # User, Email, Role, UserRepository (Ports)
│   ├── application     # Use Cases (Register, Login)
│   └── infrastructure  # Persistence (R2DBC), Web (Controllers)
├── serviceoffer        # Service Offer Module (Catalog Context)
│   ├── domain          # ServiceOffer Aggregate
│   ├── application     # Use Cases (Create, Get)
│   └── infrastructure  # Persistence, Web
├── order               # Order Module (Ordering Context)
│   ├── domain          # Order Aggregate
│   ├── application     # Use Cases (Create, Get)
│   └── infrastructure  # Persistence, Web
├── shared              # Shared Kernel
│   └── security        # Security Config & JWT
└── MarketplaceApplication.java
```

---

## 🚀 Getting Started

### Prerequisites

- JDK 21+
- Gradle
- Supabase Account (or PostgreSQL instance)

### Running the Application

1. **Configure Environment**:

    Copy the example environment file:

    ```bash
    cp .env.example .env
    ```

    Edit `.env` and fill in your **Supabase (PostgreSQL)** and **MongoDB** credentials.

2. **Run the Application**:

    ```bash
    ./gradlew bootRun
    ```

3. **Access API Documentation**:
    - Swagger UI: `http://localhost:8080/webjars/swagger-ui/index.html` (Once configured)

4. **Run Quality Checks**:

    ```bash
    ./gradlew spotlessApply         # Format code
    ./gradlew spotbugsMain          # Static analysis
    ./gradlew dependencyCheckAnalyze # Vulnerability scan
    ```

---

## 📝 License

Reactive Marketplace Backend is released under the **MIT License**.

For more details, see the [LICENSE](./LICENSE) file or visit: https://opensource.org/licenses/MIT
