# Employee Management System

## Overview
The **Employee & Department Management System** is a Spring Boot-based application that manages employees and departments using JPA.
It supports CRUD operations, pagination, sorting, advanced querying, and transaction management.

## Key Features

### Employee Management
- Add, update, delete, and list employees.
- Employees are linked to departments (Many-to-One relationship).
- CRUD operations on employee records.

### Department Management
- Add, update, delete, and list departments.
- Each department can have multiple employees.
- Retrieve the number of employees in each department.

### Advanced Features
- **Pagination & Sorting**: Retrieve employee and department lists efficiently.
- **JPQL Queries**:
  - Fetch employees based on salary or join date.
  - Retrieve department-wise employee counts.
- **Transaction Management**: Transfer employees between departments.
- **Custom Repository**: Bulk salary updates for employees.

## Endpoints

### Employee API
- Add, update, delete, and list employees.

### Department API
- Add, update, delete, and list departments.
- Retrieve department statistics (e.g., employee count).

## Technologies Used
- **Spring Boot** with **JPA (Hibernate)** for ORM.
- **MySQL** for database management.
- **Swagger** for API documentation.
- **Validation** to ensure data integrity.

## Getting Started
### Prerequisites
- Java 17+
- MySQL Database
- Maven

### Installation
1. Clone the repository:
   ```sh
   git clone (https://github.com/Lokanatham-Latesh/EmployeeManagementSystem.git)
   cd EmployeeManagementSystem
   ```
2. Configure the database in `application.properties`.
3. Build and run the application:
   ```sh
   mvn spring-boot:run
   ```

## API Documentation
Swagger UI is available at:
```
http://localhost:8080/swagger-ui.html
```

---
### 🚀 Contributions & Feedback
Contributions, issues, and feedback are welcome! Feel free to submit a PR or open an issue.

