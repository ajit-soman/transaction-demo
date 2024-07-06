# Transaction Routine App
Transaction Routine App is a small application that consist of 3 API which does following things:

- Create an account
- Get details of an account
- Perform transaction

## TechStack

- java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Swagger
- Docker
- H2

## Getting Started

### Dependencies

- PostgreSQL (_Mandatory_)
- Docker (_Optional_)
- IntelliJ or any IDE (_Optional_)

### Database Setup
For Database setup, we need to login to postgreSQL and create database using below command:
```
CREATE DATABASE transaction;
```

Tables will be created by application itself. So no need to create table now

> There 3 ways to run the application. You can pick any one of these to run the application
### #1 Executing via command line

1) Clone the project in local
2) Open project folder and navigate to `src/main/resources/` location and open `application-dev.properties` and change database username and password according to your local 
   ```
   spring.datasource.username=<YOUR_USERNAME>
   spring.datasource.password=<YOUR_PASSWORD>
   ```

3) Execute below command to start the server:
    ```
    mvnw spring-boot:run
    ```
### #2 Executing via IntelliJ or any IDE

1) Clone the project in local
2) Import project in IDE
3) Search for file `application-dev.properties` in workspace using file search functionality provided by IDE
4) open `application-dev.properties` and change database username and password according to your local
   ```
   spring.datasource.username=<YOUR_USERNAME>
   spring.datasource.password=<YOUR_PASSWORD>
   ```
5) Search for file `TransactionDemoApplication` and execute it's `main(...)` method to start the server
### #3 Executing via Docker

1) Clone the project in local
2) Open project folder 
3) Build jar file using below command
   ```
   mvnw clean package -Dmaven.test.skip=true 
   ```
4) Build docker image using below command
   ```
   docker build -t transaction-routine:1.0.0 .
   ```
5) Execute docker image using below command
   ```
   docker run -d -p 8080:8080 transaction-routine:1.0.0
   ```
## Documentation
We have used swagger for documentation.  Swagger is accessible at this URL: `http://localhost:8080/swagger-ui/index.html`
