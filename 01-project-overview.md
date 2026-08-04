# Table of Contents

1. Project Objective
2. Technologies and Rationale
3. Selected Architecture
4. Initial Microservices
5. Functional Requirements
6. Non-Functional Requirements
7. Development Roadmap


# 1. Project Objective
The objective of this project is to develop an **e-commerce backend platform** based on a **microservices architecture**, designed to simulate a real-world professional development environment.

The project will serve as a practical learning platform to study, apply, and demonstrate knowledge of technologies widely used in modern Java backend development, including:

- Spring Boot
- Spring Data JPA / Hibernate
- OpenAPI
- Swagger
- Apache Kafka
- Docker
- Jenkins
- Kubernetes
- AWS

It will also incorporate architectural principles such as **Domain-Driven Design (DDD)** and **Hexagonal Architecture**.

The project will be developed incrementally, implementing each component while following industry best practices in software design, testing, continuous integration, and deployment.

The goal is not only to build a functional application but also to understand the role of each technology within a distributed system and gain practical experience comparable to that found in real enterprise software projects.

## 1.1 Functional Objectives
- Develop REST APIs for managing users, products, inventory, and orders.
- Implement authentication and authorization using JWT.
- Manage the complete order lifecycle, from creation to completion or cancellation.
- Process asynchronous events through Apache Kafka to coordinate communication between microservices.

## 1.2 Technical Objectives
- Design a microservices architecture following Domain-Driven Design (DDD) and Hexagonal Architecture principles.
- Document all REST APIs using OpenAPI and Swagger.
- Implement unit and integration tests using JUnit and Mockito.
- Containerize every service using Docker and Docker Compose.
- Automate builds and testing through Jenkins.
- Deploy the application on Kubernetes.
- Deploy the solution to AWS using enterprise-standard cloud services.
- Maintain a professional project structure with proper documentation, version control, and software development best practices.

# 2. Technologies and Rationale
The following technologies have been selected because they are widely used in modern enterprise backend development and provide an excellent opportunity to learn industry best practices.

## Java 21
The primary programming language for the project. Java 21 is the current Long-Term Support (LTS) version and is widely adopted in modern enterprise applications.

## Spring Boot
The main framework used to develop microservices quickly while following the standard Java ecosystem. It simplifies application configuration and promotes maintainable, production-ready code.

## Spring Data JPA + Hibernate
Provides a high-level abstraction for data access using the Java Persistence API (JPA), while Hibernate acts as the Object-Relational Mapping (ORM) framework. Together, they simplify interaction with PostgreSQL without requiring SQL for common database operations.

## PostgreSQL
A robust and reliable relational database management system widely used in enterprise applications.

## Spring Security + JWT
Provides authentication and authorization using JSON Web Tokens (JWT), a common standard for securing REST APIs.

## JUnit 5
The primary framework for writing unit and integration tests.

## Mockito
A mocking framework used to isolate business logic during testing by simulating dependencies. One of the main learning objectives of this project is to gain practical experience using Mockito effectively.

## OpenAPI + Swagger
Automatically generates REST API documentation and provides an interactive web interface for testing API endpoints.

## Apache Kafka
An event-driven messaging platform used for asynchronous communication between microservices, reducing coupling and improving scalability.

## Docker
Containerizes every service to ensure a consistent and reproducible development and deployment environment.

## Docker Compose
Orchestrates the local development environment by managing containers such as databases, Kafka, and microservices.

## Jenkins
Automates Continuous Integration (CI) processes, including project builds, automated testing, and Docker image creation.

## Kubernetes
Orchestrates and deploys containers in an environment that closely resembles a production infrastructure.

## AWS
Provides the cloud platform where the project will eventually be deployed using enterprise-standard services such as:

- Amazon EC2
- Amazon ECR
- Amazon S3
- Amazon CloudWatch

As a long-term objective, the application will also be deployed on **Amazon EKS (Elastic Kubernetes Service)**.

## Git + GitHub
Used for version control, branch management, collaboration, and project documentation.


# 3. Selected Architecture

## 3.1 General Architecture
The application will follow a **microservices architecture**, where each service is an independent application responsible for a single business capability.

Each microservice will be autonomous and can be developed, deployed, and scaled independently.

Communication between services will be achieved through two different mechanisms:

### Synchronous Communication
REST APIs will be used for operations that require an immediate response.

### Asynchronous Communication
Apache Kafka will be used to propagate business events between microservices.

This approach reduces coupling between services while improving the scalability, maintainability, and resilience of the overall system.

---

## 3.2 Internal Architecture of Each Microservice
Every microservice will follow the same internal structure based on **Hexagonal Architecture (Ports and Adapters)**.

The domain layer will remain completely independent of Spring Boot, JPA, Kafka, or any other external technology.

```
REST API
    │
Controllers
    │
Application Layer (Use Cases)
    │
Domain Layer (Business Rules)
    │
Ports (Interfaces)
    │
Infrastructure (JPA, Kafka, REST Clients, etc.)
```

This architecture provides several advantages:

- Business logic remains independent of implementation technologies.
- Unit tests can be written without relying on infrastructure components.
- Responsibilities are clearly separated, resulting in cleaner and more maintainable code.

---

## 3.3 Domain Design
The project will adopt **Domain-Driven Design (DDD)** principles in a pragmatic way.

Rather than implementing every DDD pattern, only those that provide real value to the project will be used.

The main concepts included are:

- Entities
- Value Objects
- Application Services (Use Cases)
- Repositories (Ports)
- Domain Events
- Bounded Contexts, represented by the different microservices

The primary objective is to ensure that business logic resides within the **Domain Layer**, not inside controllers or repository implementations.

---

## 3.4 Architectural Style
The system will follow an **Event-Driven Architecture (EDA)**.

Whenever an important business event occurs, the responsible microservice will publish an event to Apache Kafka.

```
Client
   │
   ▼
Order Service
   │
   ▼
Order Stored
   │
   ▼
OrderCreatedEvent
   │
   ▼
Apache Kafka
   │
   ├──────────────► Inventory Service
   └──────────────► Notification Service
```

This approach allows each microservice to react only to the events that are relevant to it, eliminating unnecessary direct dependencies between services.

The overall architecture combines several complementary architectural patterns commonly found in modern enterprise Java applications:

| Pattern | Responsibility |
|---------|----------------|
| **Microservices** | Organize the system into independent business services |
| **Hexagonal Architecture** | Structure the internal code of each microservice |
| **Domain-Driven Design (DDD)** | Model the business domain and business logic |
| **Event-Driven Architecture (EDA)** | Enable asynchronous communication through business events |


# 4. Initial Microservices
The project will be divided into microservices that represent actual **bounded contexts** within the business domain, rather than simply mirroring database tables.

Each service will own its business logic, data, and responsibilities, ensuring low coupling and high cohesion.

---

## User Service
Responsible for managing all user-related information.

### Features
- User registration
- User authentication (login)
- Profile management
- Role management (Customer and Administrator)
- JWT generation and validation

### Database Responsibility
The database owned by this service will store **only user-related information**. It has no knowledge of products, orders, or any other business domain.

---

## Product Service
Responsible for managing the product catalog.

### Features
- Product management
- Category management
- Product search
- Product filtering
- Product images (to be stored in Amazon S3 in a later phase)

### Database Responsibility
This service stores **only product-related data**. It has no knowledge of users or orders.

---

## Order Service
Responsible for managing the complete order lifecycle. This is the core business service of the application.

### Features
- Create orders
- Cancel orders
- View order history
- Update order status
- Calculate the total order amount

### Responsibilities
The Order Service **does not directly update inventory or send notifications**.

Instead, it publishes business events to Apache Kafka, allowing other microservices to react asynchronously.

---

## Inventory Service
Responsible for managing product inventory.

### Features
- Reserve stock
- Release reserved stock
- Update inventory quantities
- Check product availability

### Responsibilities
This service contains its own business rules, completely independent from the Product Service and its catalog management.

---

## Notification Service
Responsible for sending notifications triggered by business events.

### Supported Notifications
- Order created
- Order shipped
- Order cancelled
- Password recovery (planned for a future version)

### Responsibilities
This service will **not expose REST endpoints for communication with other microservices**.

Instead, it will consume events published to Apache Kafka and react accordingly.

---

## Future Microservices
Additional services may be introduced in future iterations of the project, including:

- Shopping Cart Service
- Payment Service

These services will be evaluated once the core business functionality has been completed.


# 5. Functional Requirements

## FR-01. User Management
The system shall allow users to:

- Register a new account.
- Authenticate using JWT.
- View and update their profile information.
- Be assigned a role (**CUSTOMER** or **ADMIN**).

---

## FR-02. Product Catalog Management
The system shall allow users to:

- Browse the product catalog.
- Search products by name.
- Filter products by category.
- View detailed product information.

Administrators shall be able to:

- Create products.
- Update products.
- Delete products.
- Manage product categories.

---

## FR-03. Shopping Cart Management
Customers shall be able to:

- Add products to the shopping cart.
- Update product quantities.
- Remove products from the cart.
- Empty the shopping cart.

---

## FR-04. Order Management
Customers shall be able to:

- Create an order from their shopping cart.
- View their order history.
- View order details.
- Cancel an order while its current status allows it.

Administrators shall be able to:

- View all orders.
- Update an order's status (e.g., **PENDING**, **PAID**, **SHIPPED**, **DELIVERED**, **CANCELLED**).

---

## FR-05. Inventory Management
The system shall:

- Check product availability.
- Reserve stock when an order is created.
- Release reserved stock when an order is cancelled.
- Update inventory after an order is confirmed.

---

## FR-06. Inter-Microservice Communication
The system shall use **Apache Kafka** to exchange domain events between microservices.

Examples of business events include:

- Order created
- Order cancelled
- Inventory updated
- Order shipped

---

## FR-07. Notifications
The system shall send notifications when any of the following events occur:

- Order confirmation
- Order status update
- Order cancellation

In the initial version, notifications may be simulated by writing messages to the application logs. A real email provider will be integrated in a later phase of the project.

---

## FR-08. API Documentation
All microservices shall expose their REST API documentation using **OpenAPI** and **Swagger**.


# 6. Non-Functional Requirements

## NFR-01. Architecture
The system shall be composed of independent microservices organized according to the principles of **Hexagonal Architecture** and **Domain-Driven Design (DDD)**.

---

## NFR-02. Scalability
Each microservice shall be independently deployable and scalable, allowing the system to grow without affecting other services.

---

## NFR-03. Security
The system shall provide:

- JWT-based authentication.
- Role-based authorization.
- Password encryption using BCrypt.
- Input data validation.

---

## NFR-04. Data Ownership
Each microservice shall own and manage its own data.

No microservice shall access another service's database directly. All communication must occur through APIs or asynchronous events.

---

## NFR-05. Code Quality
The project shall follow software development best practices, including:

- Clear separation of responsibilities.
- Compliance with the SOLID principles.
- Clean, readable, and maintainable code.
- Consistent naming conventions.

---

## NFR-06. Testing
All relevant business use cases shall be covered by automated tests, including:

- Unit tests using **JUnit 5** and **Mockito**.
- Integration tests whenever appropriate.

---

## NFR-07. Containerization
All services shall be containerized using Docker and be executable in a local development environment through **Docker Compose**.

---

## NFR-08. Continuous Integration
The project shall include a **Jenkins** pipeline that automates, at a minimum:

- Project compilation.
- Automated test execution.
- Docker image creation.

Future improvements may include static code analysis and automatic publishing of Docker images to a container registry.

---

## NFR-09. Deployment
The application shall be deployable on **Kubernetes** and, in later stages of the project, on **Amazon Web Services (AWS)**.

---

## NFR-10. Observability *(Future Version)*
Observability features will be introduced in a future version of the project and will include:

- Centralized logging.
- Basic application monitoring.
- Service metrics.

These capabilities will complement the Kubernetes and AWS infrastructure using tools such as:

- Spring Boot Actuator
- Prometheus
- Grafana


# 7. Development Roadmap
The project will be developed incrementally, introducing new features and technologies in each phase. Every stage has a clear objective and leaves the system in a functional and deployable state.

---

## Phase 0 – Analysis and Design
Establish the project's foundation before development begins.

### Tasks
- Define the project objectives.
- Select the technologies.
- Design the overall architecture.
- Identify the initial microservices.
- Define the functional and non-functional requirements.
- Create the development roadmap.

---

## Phase 1 – Project Setup
Prepare the development environment and the initial project structure.

### Tasks
- Create the GitHub repository.
- Define the branching strategy.
- Create the repository structure.
- Configure the development environment.
- Write the initial project documentation.

### Technologies
- Git
- GitHub

---

## Phase 2 – User Service Development
Implement the first microservice and establish the project's architectural foundation.

### Tasks
- Create the Spring Boot project.
- Apply Hexagonal Architecture.
- Implement Domain-Driven Design (DDD).
- Configure PostgreSQL.
- Implement JWT authentication.
- Document the REST API using OpenAPI and Swagger.

### Technologies
- Spring Boot
- Spring Security
- JWT
- Hibernate
- Spring Data JPA
- PostgreSQL
- OpenAPI
- Swagger

---

## Phase 3 – Product Service Development
Implement the product catalog.

### Tasks
- Product management.
- Category management.
- Product search and filtering.
- Initial inventory management.

### Technologies
- Spring Boot
- Spring Data JPA
- PostgreSQL
- OpenAPI

---

## Phase 4 – Order Service Development
Implement shopping cart and order management.

### Tasks
- Shopping cart.
- Order creation.
- Order status management.
- Order history.

Once this phase is completed, the project will provide the first complete end-to-end business workflow.

---

## Phase 5 – Inventory Service Development
Separate inventory management from the product catalog.

### Tasks
- Reserve stock.
- Release reserved stock.
- Update inventory quantities.

---

## Phase 6 – Apache Kafka Integration
Decouple microservices through event-driven communication.

### Tasks
- Configure Apache Kafka.
- Publish domain events.
- Consume domain events.
- Implement the Notification Service.
- Handle basic error recovery and retry mechanisms.

### Technologies
- Apache Kafka

After this phase, microservices will no longer rely on direct communication for selected business operations.

---

## Phase 7 – Testing
Ensure software quality through automated testing.

### Tasks
- Unit testing.
- Integration testing.
- Coverage of the main business use cases.

### Technologies
- JUnit 5
- Mockito

---

## Phase 8 – Containerization
Run the complete system using containers.

### Tasks
- Create a Dockerfile for each microservice.
- Configure Docker Compose.
- Containerize PostgreSQL and Apache Kafka.
- Start the complete environment with a single command.

### Technologies
- Docker
- Docker Compose

---

## Phase 9 – Continuous Integration
Automate the build and validation process.

### Tasks
- Configure Jenkins.
- Automate project builds.
- Execute tests automatically.
- Build Docker images.

### Technologies
- Jenkins

---

## Phase 10 – Kubernetes Deployment
Deploy and orchestrate the microservices in a production-like environment.

### Tasks
- Create Kubernetes Deployments.
- Configure Services.
- Manage ConfigMaps and Secrets.
- Configure Ingress.
- Scale individual microservices.

### Technologies
- Kubernetes

---

## Phase 11 – AWS Deployment
Deploy the project to the cloud.

### Tasks
- Deploy services on Amazon EC2.
- Store product images in Amazon S3.
- Publish Docker images to Amazon ECR.
- Monitor the application with Amazon CloudWatch.
- As a final objective, deploy the entire platform to an Amazon EKS cluster.

### Technologies
- Amazon EC2
- Amazon S3
- Amazon ECR
- Amazon CloudWatch
- Amazon EKS