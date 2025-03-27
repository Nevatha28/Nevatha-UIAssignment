#Nevatha-UIAssignment
Features
1. User Authentication & Authorization
    Spring Security with JWT for authentication.
    JWT Token Generation & Validation – Secure API access.
2. Customer Management
    Customer Registration & Login – Secure onboarding.
    User Authentication – Access based on JWT tokens.
    Password Hashing using BCryptPasswordEncoder.
3. Transaction Management
    CRUD Operations for Transactions – Add, Retrieve, and Delete.
    Mapping transactions to customers – Store transactions in DB.
4. Reward Points Calculation
    Dynamic reward points calculation based on transaction amount.
    Points awarded based on spending thresholds (e.g., 2x points for amounts over $100).
    Stored & Retrieved Rewards per Customer for tracking.
5. Database Integration with JPA & MySQL
   Spring Data JPA for repository layer (Customer & Transactions).
   MySQL database for data persistence.
   Schema auto-generation using JPA/Hibernate.
6. Unit & Integration Testing (JUnit 5 & Mockito)
   Test cases for Service, Controller, and Repository Layers.
   Mocking dependencies using Mockito for isolation testing.

Technology Stack
Programming Language - Java 8           
Backend FrameWork    - Spring Boot 3
Authentication & Authorization - spring Security 
JWT - secure token-based Authentication
Spring Data JPA  - ORM for DataBase interactions
MySQL - Relational database
Junit5 - Testing framework
Maven - Dependency management

