
# 🏌️‍♂️ Golf Club Tournament API

This project is a Spring Boot REST API designed to manage a Golf Club's membership and tournaments, demonstrating Object Relational Mapping (ORM), common software design patterns, and Docker containerization for easy deployment. The API allows users to perform full CRUD operations on members and tournaments, associate members with tournaments, and perform complex searches across both entities.

## ✅ Features

### 📇 Member Management
- Add new members
- Get list of members
- Search members by:
  - Name
  - Membership type
  - Phone number
  - Start date of membership

### 🏆 Tournament Management
- Add new tournaments
- Get list of tournaments
- Add members to tournaments
- Search tournaments by:
  - Start date
  - Location
  - Retrieve all members in a tournament


## 🧱 Technologies Used
- Java 17
- Spring Boot
- Spring Data JPA (Hibernate ORM)
- MySQL
- Docker & Docker Compose
- RESTful architecture
- Maven

## 🛠️ Prerequisites

- Java 17
- Maven
- Docker & Docker Compose
- MySQL

## 🚀 Getting Started

### 🐳 Docker Setup

1. Clone the repo:
   ```bash
   git clone https://github.com/AceOfSpades98/Docker-QAP.git
   cd golf-club-api
   ```
2. Run Docker Compose:
    ```
    docker-compose up --build
    ```
    
## Screenshots

Screenshots will go here

## 🛣️ Roadmap

- Add enumerator for Member types
- add methods to Member and MemberService to reflect type
- complete controller classes
- complete tests

### 1. Setup
- Create Spring Boot Maven project
- Configure local PostgreSQL database
- Set up JPA/Hibernate ORM

### 2. Model Data
- Define `Member` and `Tournament` entities with appropriate fields and relationships

### 3. Implement Logic
- Create repositories and services for data access and business logic

### 4. Build REST API
- Develop endpoints to add/get Members and Tournaments
- Add Members to Tournaments
- Implement search functionality for Members and Tournaments

### 5. Test API
- Use Postman to test all endpoints and document results

### 6. Dockerize
- Create Docker setup to run app and database containers

### 7. AWS Deployment
- Switch database connection to AWS RDS
- Test and document deployment process

## License

Distibuted under the MIT License. See LICENSE.txt for more information.


## Author

- [@AceOfSpades98](https://github.com/AceOfSpades98)

