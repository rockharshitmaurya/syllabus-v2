# 🚀 Syllabus-V2

## 🌐 Introduction
Welcome to the Syllabus-v2! This robust system efficiently manages syllabus entries and tags through three microservices: Gateway (Eureka Discovery Server), Syllabus-MSA, and Tag-V2. Dive into the comprehensive details about each microservice, dependencies, and the exciting features they offer.

### 🛠️ Gateway Microservice
- **Purpose**: Eureka Discovery Server
- **Folder Structure**:
    ```
    └── com
        └── syllabus
            └── gateway
                └── EurekaServerApplication.java
    ```

### 🔧 Syllabus-MSA Microservice
- **Dependencies**:
    - Spring Boot Starter Data JPA
    - Spring Boot Starter Web
    - Spring Boot Starter Test
    - PostgreSQL
    - Spring Boot Starter AOP
    - Spring Boot Starter Validation
    - Spring Cloud Starter Netflix Eureka Client
    - Spring Boot Starter Data Redis
    - Jedis
    - Springdoc OpenAPI
- **Folder Structure**:
    ```
    └── com
        └── syllabus
            └── api
                ├── aspectLogger
                │   ├── persistLog
                │   │   ├── Logger.java
                │   │   └── logs
                │   │       ├── server_24_01_2024.log
                │   │       ├── server_25_01_2024.log
                │   │       └── server_30_01_2024.log
                │   └── RequestLoggingAspect.java
                ├── controller
                │   └── SyllabusController.java
                ├── dto
                │   ├── request_dto
                │   │   └── SyllabusDto.java
                │   └── response_dto
                ├── entity
                │   └── Syllabus.java
                ├── exception
                │   └── GlobalExceptionHandler.java
                ├── model
                │   └── Message.java
                ├── redis
                │   └── RedisConfig.java
                ├── service
                │   ├── SyllabusServiceImpl.java
                │   └── SyllabusService.java
                ├── SyllabusMsaApplication.java
                ├── test
                └── validation
                    └── InputValidation.java
    ```
- **Endpoints**:
    - `PUT` /syllabus-v2/updateSyllabus/{id}
    - `POST` /syllabus-v2/createSyllabus
    - `GET` /syllabus-v2
    - `GET` /syllabus-v2/syllabus/getSyllabusById/{id}
    - `GET` /syllabus-v2/syllabus/exists/{id}
    - `GET` /syllabus-v2/getAllSyllabus
    - `DELETE` /syllabus-v2/deleteSyllabus/{id}
- **Features**:
    - Swagger for API documentation
    - FeignClient for external service calls
    - Aspect-Oriented Programming (AOP) for logging
    - Redis for caching
    - Logging with log files
    - Spring Actuator for monitoring

### 🏷️ Tag-V2 Microservice
- **Dependencies**:
    - Spring Boot Starter Data JPA
    - Spring Boot Starter Web
    - Spring Boot Starter Test
    - PostgreSQL
    - Spring Boot Starter AOP
    - Spring Boot Starter Validation
    - Lombok
    - Spring Cloud Starter Netflix Eureka Client
    - Spring Cloud Starter OpenFeign
    - Springdoc OpenAPI
- **Folder Structure**:
    ```
    └── com
        └── syllabus
            └── tagv2
                ├── aspectLogger
                │   ├── persistLog
                │   │   ├── Logger.java
                │   │   └── logs
                │   │       ├── server_24_01_2024.log
                │   │       ├── server_25_01_2024.log
                │   │       └── server_30_01_2024.log
                │   └── RequestLoggingAspect.java
                ├── controller
                │   ├── TagController.java
                │   └── TagRelationController.java
                ├── dto
                │   ├── request_dto
                │   │   ├── TagDto.java
                │   │   └── TagRelationDto.java
                │   └── response_dto
                │       ├── SyllabusWithTagsResponseDTO.java
                │       └── TagResponseDTO.java
                ├── entity
                │   ├── SyllabusTag.java
                │   └── Tag.java
                ├── feign
                │   └── SyllabusInterface.java
                ├── model
                │   └── Message.java
                ├── repository
                │   └── TagRepository.java
                ├── service
                │   ├── ManageTagRelationImpl.java
                │   ├── ManageTagRelation.java
                │   ├── TagServiceCrudImpl.java
                │   └── TagServiceCrud.java
                └── TagV2Application.java
    ```
- **Endpoints**:
    - Tag-Controller:
        - PUT /syllabus-v2/tag/update/{tagId}
        - POST /syllabus-v2/tag/add
        - GET /syllabus-v2/tag
        - GET /syllabus-v2/tag/{tagId}
        - GET /syllabus-v2/tag/all
        - DELETE /syllabus-v2/tag/delete/{tagId}
    - Tag-Relation-Controller:
        - POST /syllabus-v2/tag/manage/detach-tag
        - POST /syllabus-v2/tag/manage/attach-tag
        - GET /syllabus-v2/tag/manage/getSyllabusWithTags

## 🛠️ Installation

### 1. Clone the Repository
- Open a terminal or command prompt.
- Run the following command:
    ```bash
    git clone https://github.com/rockharshitmaurya/syllabus-v2
    ```

### 2. Build and Run the Gateway Microservice
- Navigate to the `gateway` folder.
- Run the following commands:
    ```bash
    cd gateway
    ./mvnw clean install
    ./mvnw spring-boot:run
    ```
    The Eureka Discovery Server should start successfully.

### 3. Build and Run the Syllabus-MSA Microservice
- Navigate to the `syllabus-msa` folder.
- Run the following commands:
    ```bash
    cd syllabus-msa
    ./mvnw clean install
    ./mvnw spring-boot:run
    ```
    Ensure that the necessary dependencies are resolved, and the Syllabus microservice is up and running.

### 4. Build and Run the Tag-V2 Microservice
- Navigate to the `tag-v2` folder.
- Run the following commands:
    ```bash
    cd tag-v2
    ./mvnw clean install
    ./mvnw spring-boot:

run
    ```
    Verify that the Tag microservice starts without any issues.
    ```

### 5. Access the System
- Once all microservices are up and running, access the following URLs:
    - Gateway Eureka Dashboard: [http://localhost:8083/](http://localhost:8083/)
    - API Documentation (Syllabus-MSA): [http://localhost:8081/swagger-ui.html](http://localhost:8080/swagger-ui.html)
    - Documentation (Tag-V2): [http://localhost:8082/swagger-ui.html](http://localhost:8081/swagger-ui.html)

## 🧪 Unit Testing with Mockito

- Extensive unit testing has been performed using JUnit and Mockito in the Syllabus-MSA microservice.
- Test coverage has achieved 100% for service classe.
- Below is an example of the unit test coverage report, displaying the percentage of coverage for each class:

![Unit Test Coverage](https://github.com/rockharshitmaurya/syllabus-v2/assets/46915044/7fc8fba3-3e21-48d6-815d-b8ccbe31eaa3)


Apologies for the oversight. Let's include information about the implemented features and unit testing using Mockito in the README:

## Implemented Features

### 1. Swagger
- Swagger has been integrated into both the Syllabus-MSA and Tag-V2 microservices for easy API documentation.
- Explore the APIs using Swagger UI:
    - Syllabus-MSA: [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)
    - Tag-V2: [http://localhost:8082/swagger-ui.html](http://localhost:8082/swagger-ui.html)

### 2. FeignClient Call
- The Syllabus-MSA microservice incorporates FeignClient for making external service calls.

### 3. AOP (Aspect-Oriented Programming)
- Logging aspects have been implemented using AOP to provide detailed logs for better system understanding and debugging.
- Log files are organized by date for easier tracking:
    - Example Log Folder: `/com/syllabus/api/aspectLogger/persistLog/logs`

### 4. Redis
- Redis has been integrated into the Syllabus-MSA microservice for caching purposes.

### 5. Logging
- Logging is implemented throughout the Syllabus-MSA microservice, and log files are generated for each day.
- Example Log Folder: `/com/syllabus/api/aspectLogger/persistLog/logs`

### 6. Actuator
- Spring Boot Actuator is incorporated for monitoring the microservices. You can access actuator endpoints to check the health, metrics, and more.
- Example Actuator Endpoints:
    - [http://localhost:8081/actuator/health](http://localhost:8081/actuator/health) (Syllabus-MSA)
    - [http://localhost:8082/actuator/health](http://localhost:8082/actuator/health) (Tag-V2)
