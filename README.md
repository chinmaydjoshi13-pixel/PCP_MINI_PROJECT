# Enterprise Meeting Room Booking System (PCP Mini Project)

An enterprise-ready Meeting Room Booking System built with **Spring Boot 3**, **Spring Data JPA**, and an in-memory **H2 Database**.

---

## 🚀 Features

- **Room Management**: Search, filter, and inspect meeting room availability and facilities (Projector, Whiteboard, Video Conference, etc.).
- **Booking Management**: Reserve meeting rooms with conflict detection, check user schedules, and manage cancellations.
- **User Management**: Manage users and organizers.
- **Seed Data**: Pre-loaded mock dataset initialized on startup for quick testing.
- **H2 Web Console**: Built-in database browser for debugging and inspecting tables.
- **API Testing Ready**: Includes Postman collections for instant endpoint testing.

---

## 🛠️ Tech Stack

- **Backend**: Java 17, Spring Boot 3.3.4
- **Persistence**: Spring Data JPA, Hibernate
- **Database**: H2 In-Memory Database
- **Build Tool**: Apache Maven
- **API Testing**: Postman Collection included

---

## 📦 Getting Started

### Prerequisites
- JDK 17 or higher
- Maven 3.8+

### Running the Application

1. **Clone the repository**:
   ```bash
   git clone https://github.com/chinmaydjoshi13-pixel/PCP_MINI_PROJECT.git
   cd PCP_MINI_PROJECT
   ```

2. **Build and run**:
   ```bash
   mvn clean spring-boot:run
   ```

3. The application will start on `http://localhost:8080`.

---

## 🗄️ Database Access (H2 Console)

- **URL**: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: *(leave blank)*

---

## 📮 API Documentation & Postman

A ready-to-import Postman collection is included in the root directory:
- [`MeetingRoomBooking_Postman_Collection.json`](./MeetingRoomBooking_Postman_Collection.json)
- Postman environment and globals under [`postman/`](./postman/)

### Key Endpoints:

#### Rooms
- `GET /api/rooms` - Get all meeting rooms
- `GET /api/rooms/{id}` - Get room by ID
- `POST /api/rooms` - Create a new room
- `GET /api/rooms/available` - Check room availability for a time slot

#### Bookings
- `GET /api/bookings` - List all bookings
- `GET /api/bookings/{id}` - Get booking details
- `POST /api/bookings` - Create a new booking
- `DELETE /api/bookings/{id}` - Cancel a booking

#### Users
- `GET /api/users` - List all users
- `POST /api/users` - Create user