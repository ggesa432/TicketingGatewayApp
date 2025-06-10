# Ticketing Gateway

## Introduction

Ticketing Gateway is a full-stack web application designed to streamline the ticket submission, management, and notification process for organizations. The portal allows users to raise tickets with attachments, tracks the progress and history of each ticket, and leverages asynchronous messaging (ActiveMQ, JMS) and microservices for notifications (email). The solution is robust, supports multi-file uploads and downloads, and is designed for extensibility and deployment in a microservices architecture.

## Features

- Interactive web portal for ticket submission with multi-file upload support
- Ticket status tracking, ticket history, and audit trail
- Asynchronous notification via email microservice (JMS, ActiveMQ)
- Download attachments and manage multiple file types
- Admin functions for pending ticket review and notifications
- Integration with Spring Boot, MySQL, ActiveMQ, and AWS (optional)

## Technologies Used

- Java 17
- Spring Boot 3 / Spring 5 (Core, MVC, Data JPA, JMS)
- HTML5, CSS3, JSP
- Bootstrap
- MySQL
- Maven
- ActiveMQ (JMS)
- AWS EC2 (for deployment)
- GitHub

## Main Modules

### 1. Ticket Management
- Submit, update, and view ticket details
- Attach multiple files per ticket (screenshots, documents)
- Track ticket history (who changed what and when)

### 2. Notification System
- Asynchronous email notifications via microservice
- Uses JMS queue (ActiveMQ) for message passing
- Batch notifications for pending tickets

### 3. File Upload & Download
- Upload multiple files per ticket (stored in `/uploads/tickets/{ticketId}/`)
- Download individual attachments via secure URLs

### 4. Admin Panel
- Approve/reject tickets
- Monitor all ticket activity and history

## Setup & Running

1. **Clone the repository:**
    ```bash
    git clone <your-repo-url>
    cd ticketing-gateway
    ```

2. **Configure MySQL Database:**
    - Update `application.properties` with your DB credentials.
    - Run the provided schema SQL to set up tables.

3. **Start ActiveMQ Broker:**
    - Download and start Apache ActiveMQ (`activemq start`)

4. **Build and Run Spring Boot Application:**
    ```bash
    mvn clean package
    java -jar target/ticketing-gateway-<version>.jar
    ```

5. **Email Microservice:**
    - Set up and run your email microservice (see `/email-service/`).

6. **Access the Portal:**
    - Open [http://localhost:8080/](http://localhost:8080/) in your browser.

## Usage

- **User:** Submit tickets, upload files, track status, and receive email updates.
- **Admin:** Approve tickets, download attachments, review pending tickets, and receive batch email notifications.

## Skill Sets Demonstrated

- Java, Spring Boot, JMS, JSP, HTML5, CSS, Bootstrap
- Microservices, REST APIs, Maven, MySQL, ActiveMQ, AWS EC2

## Screenshots

*(Add your screenshots here!)*

## License

This project is for educational/demo purposes.

---

*Created by [GesangZeren], 2025*
