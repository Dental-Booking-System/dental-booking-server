# 🌐 Dental Booking Server - Spring Boot

This **Spring Boot backend** manages authentication, appointments, API handling, and data storage.

## ✨ Features

- **Authentication**: Secure login and API routes with Firebase Admin SDK.
- **Business Logic**: Handles appointment booking, cancellations, and clinic operations.

## 🚀 Getting Started

### Prerequisites

Ensure the following tools are installed:

- Java Development Kit (JDK) 11+
- Maven  
- MySQL Server  
- Docker (optional for containerization)

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/Dental-Booking-System/dental-booking-server.git
   cd dental-booking-server
   ```

2. Configure the database:
   - Create a MySQL database and update the `application.properties` file:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/dental_booking
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     ```

3. Build and run the application:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. (Optional) Run with Docker:
   ```bash
   docker build -t dental-backend .
   docker run -p 8080:8080 dental-backend
   ```

## 🧑‍💻 Contributors

- **Tony Vu** – [LinkedIn](https://linkedin.com/in/duyquocvu) | [GitHub](https://github.com/quocduyvu6262)
