# 🎫 Event Ticket Platform API

A comprehensive REST API for event management and ticket sales, built with Spring Boot and secured with Keycloak authentication. This platform enables event organizers to create, manage events, and handle ticket sales while providing users with a seamless ticket purchasing experience.

---

## ✨ Features

- **Event Management**: Create, update, delete, and list events with detailed information
- **Ticket System**: Purchase tickets, validate tickets with QR codes, and track ticket status
- **Published Events**: Public access to published events for ticket purchasing
- **Ticket Types**: Multiple ticket types per event with different pricing and availability
- **Ticket Validation**: QR code and manual validation methods for event entry
- **User Authentication**: Keycloak-based JWT authentication and authorization
- **RESTful API**: Clean and intuitive API design following REST principles
- **Pagination**: Efficient data retrieval with pagination support
- **API Documentation**: Comprehensive OpenAPI 3.1 documentation with Swagger UI

---

## 🛠️ Tech Stack

### Backend
- **Java 17+**
- **Spring Boot 3.x**
- **Spring Security**
- **Spring Data JPA**
- **Spring Web**
- **Spring Validation**

### Security & Authentication
- **Keycloak**: Identity and Access Management
- **JWT (JSON Web Tokens)**: Stateless authentication
- **Bearer Token Authentication**: Secure API access

### Database
- **PostgreSQL**: Production database
- **JPA/Hibernate**: ORM framework

### Documentation
- **OpenAPI 3.1**: API specification
- **Swagger UI**: Interactive API documentation

### Build Tool
- **Maven**: Dependency management and build automation

---

## 🚀 Installation

### Prerequisites
- Java 17 or higher
- Maven 3.8+
- PostgreSQL 13+
- Keycloak 18+

### 1. Clone the Repository
```bash
git clone https://github.com/furkanerd1/event-ticket.git
cd event-ticket
```

### 2. Database Setup
The application uses PostgreSQL with default settings:
- **Database**: `postgres` (default database)
- **Username**: `your_username`
- **Password**: `your_password`
- **Port**: `5432`

For local development, you can use the provided Docker Compose setup or install PostgreSQL manually.

### 3. Configure Application Properties
Update `application.yml`:
```yaml
server:
  port: 8082

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/postgres
    username: your_username
    password: your_password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

keycloak:
  auth-server-url: http://localhost:9090
  realm: event-ticket
  resource: event-ticket-api
```

### 4. Setup Keycloak
1. Keycloak will be available at `http://localhost:9090`
2. Login with admin credentials (username/password)
3. Create a new realm: `event-ticket`
4. Create a client: `event-ticket-api`
5. Configure client settings and obtain credentials

### 5. Access Services
After running Docker Compose, you can access:
- **API**: `http://localhost:8082`
- **Keycloak**: `http://localhost:9090`
- **Adminer (Database UI)**: `http://localhost:8888`
- **PostgreSQL**: `localhost:5432`

### 6. Build and Run
```bash
mvn clean install
mvn spring-boot:run
```

The application will start on `http://localhost:8082`

---

## 🐳 Docker Usage

### Using Docker Compose
```bash
# Clone the repository
git clone https://github.com/furkanerd1/event-ticket.git
cd event-ticket

# Start all services
docker-compose up -d
```


### Docker Compose Services
```yaml
services:
  app:
    build: .
    ports:
      - "8082:8082"
    depends_on:
      - db
      - keycloak
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/postgres
      - SPRING_DATASOURCE_USERNAME=your_username
      - SPRING_DATASOURCE_PASSWORD=your_password
      - KEYCLOAK_AUTH_SERVER_URL=http://keycloak:8080
    restart: always

  db:
    image: postgres:latest
    ports:
      - "5432:5432"
    restart: always
    environment:
      POSTGRES_PASSWORD: your_password

  adminer:
    image: adminer:latest
    restart: always
    ports:
      - 8888:8080

  keycloak:
    image: quay.io/keycloak/keycloak:latest
    ports:
      - "9090:8080"
    environment:
      KEYCLOAK_ADMIN: admin
      KEYCLOAK_ADMIN_PASSWORD: admin
      KC_HOSTNAME: localhost
      KC_HOSTNAME_STRICT: false
      KC_HOSTNAME_PORT: 9090
    volumes:
      - keycloak-data:/opt/keycloak/data
    command: >
      start-dev --db=dev-file

volumes:
  keycloak-data:
    driver: local
```

---

## 🔌 API Endpoints Summary

### Authentication
All endpoints require JWT Bearer token authentication except for published events listing.

###  Events Management
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/events` | List all events (paginated) |
| POST | `/api/v1/events` | Create a new event |
| GET | `/api/v1/events/{eventId}` | Get event details |
| PUT | `/api/v1/events/{eventId}` | Update event |
| DELETE | `/api/v1/events/{eventId}` | Delete event |

###  Published Events (Public Access)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/published-events` | List published events with search |
| GET | `/api/v1/published-events/{eventId}` | Get published event details |

### Ticket Operations
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/tickets` | List user's tickets (paginated) |
| GET | `/api/v1/tickets/{ticketId}` | Get ticket details |
| POST | `/api/v1/events/{eventId}/ticket-types/{ticketTypeId}/tickets` | Purchase ticket |
| GET | `/api/v1/tickets/{ticketId}/qr-codes` | Get ticket QR code |

###  Ticket Validation
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/ticket-validations` | Validate ticket (QR scan or manual) |


## 📮 Postman Collection

Import the Postman collection to test all API endpoints:

1. Download the collection: [Event Ticket API.postman_collection.json](postman/event-ticket.postman_collection.json)
)
2. Import into Postman
3. Set up environment variables:
   - `baseUrl`: `http://localhost:8082`
   - `bearerToken`: Your JWT token from Keycloak

---

## 📚 API Documentation

Access the interactive API documentation:
- **Swagger UI**: `http://localhost:8082/swagger-ui/index.html`
- **OpenAPI Spec**: `http://localhost:8082/v3/api-docs`

---

## 🔄 Status Flows

### Event Status Flow
- **DRAFT**: Event is being created/edited
- **PUBLISHED**: Event is live and tickets can be purchased
- **CANCELLED**: Event has been cancelled
- **COMPLETED**: Event has finished

### Ticket Status Flow
- **PURCHASED**: Ticket successfully purchased
- **CANCELLED**: Ticket has been cancelled/refunded

### QrCode Status Flow
- **ACTİVE**: Qr Code is active
- **EXPIRED**: Qr Code is expired

### Ticket Validation Method Status Flow
- **QR_SCAN**:  Ticket validation with QR Code scan
- **MANUAL**: Ticket validation with manual (id)

### Ticket Validation Status Flow
- **VALID**: Ticket exists in the system  
- **INVALID**: Ticket ID not found in the system  
- **EXPIRED**: Valid ticket exists but:  
  - Event has already ended  
  - Ticket's validity period has passed  
  - Sales end date/time has elapsed  

---

## 🏗️ UML Diagram

![UMLDiagram](https://github.com/user-attachments/assets/df0e3510-2ad2-44e1-89f2-4cbe48de5be3)

## SWAGGER UI
![swaggerui](https://github.com/user-attachments/assets/24d433ef-6266-4ce7-b842-84c9f45739a3)



---
