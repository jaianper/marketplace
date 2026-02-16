# 📋 Development Roadmap

## Phase 1: Foundation & Architecture

- [x] **Project Setup**
    - [x] Initialize Spring Boot WebFlux project with necessary dependencies.
    - [x] Set up `.editorconfig` and `.gitignore`.
- [x] **Infrastructure**
    - [x] Create `docker-compose.yml` for PostgreSQL and MongoDB.
    - [x] Configure database connections (R2DBC and Reactive Mongo).
    - [x] Set up Flyway for PostgreSQL migrations.
- [x] **Architecture**
    - [x] Define package structure for Hexagonal Architecture (`domain`, `application`, `infrastructure`, `api`).

## Phase 2: User Identity & Security (DDD Identity Context)

- [x] **Domain Layer**
    - [x] Implement `User` Aggregate and Value Objects (`Email`, `PasswordHash`, `Role`).
    - [x] Define `UserRepository` port.
- [x] **Infrastructure Layer**
    - [x] Implement `R2DBCUserRepository` adapter.
    - [x] Implement `Bcrypt` or `Argon2` password encoder.
    - [x] Implement JWT Token Provider (Generation & Validation).
- [x] **Application Layer**
    - [x] Implement `RegisterUserUseCase`.
    - [x] Implement `LoginUseCase`.
- [x] **API Layer**
    - [x] Create `AuthController` (Signup/Login endpoints).
    - [x] Configure `SecurityWebFilterChain` for stateless JWT auth.

## Phase 3: CI/CD & Automation

- [ ] **Continuous Integration**
    - [x] Configure GitHub Actions Pipeline.
    - [x] Add Linter (Spotless).
    - [x] Add SpotBugs + FindSecBugs (Static Analysis).
    - [x] Add OWASP Dependency-Check (Vulnerability Scanning).

## Phase 4: Marketplace Core (DDD Marketplace Context)

- [x] **Service Offers**
    - [x] Implement `ServiceOffer` Aggregate.
    - [x] Create API for Providers to CRUD services.
- [x] **Orders & Transactions**
    - [x] Implement `Order` Aggregate (`Pending`, `In_Progress`, `Completed`).
    - [x] Implement order creation flow.
    - [x] Implement basic state machine.ts\*\*
- [x] **Domain Events**
    - [x] Define events: `UserRegistered`, `OrderPlaced`, `ServicePublished`.
    - [x] Implement simple in-memory event bus (or Spring `ApplicationEventPublisher`).

## Phase 5: Audit & Advanced Features

- [x] **Audit Log**
    - [x] Implement `AuditLog` entity (MongoDB).
    - [x] Create event listeners to capture `DomainEvents` and save to MongoDB.
- [x] **Testing**
    - [x] Unit tests for Domain entities.
    - [x] Integration tests (Skipped).
- [x] **Documentation**
    - [x] Integrate OpenAPI (Swagger) for API documentation.
    - [x] Add License and Readme Badges.
- [x] **Deployment Prep**
    - [x] Add `spring-dotenv` for automatic `.env` loading.
