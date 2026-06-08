# Student Management System

A secure, role-based REST API for student academic management, built with **Spring Boot 3.5.14**, **Java 17**, and **MySQL**. It supports three roles — ADMIN, TEACHER, and STUDENT — with **JWT-based authentication** and includes **Swagger/OpenAPI documentation** for interactive API exploration.

---

## Tech Stack

| Layer         | Technology                     |
| ------------- | ------------------------------ |
| Language      | Java 17                        |
| Framework     | Spring Boot 3.5.14             |
| Security      | Spring Security + JWT (JJWT)   |
| Persistence   | Spring Data JPA + MySQL        |
| Documentation | Springdoc OpenAPI / Swagger UI |
| Build Tool    | Maven                          |

---

## Roles & Permissions

| Role      | Permissions                                                  |
| --------- | ------------------------------------------------------------ |
| `ADMIN`   | Register teachers and students, full CRUD on student records |
| `TEACHER` | Mark attendance, add marks, view student academic data       |
| `STUDENT` | View own profile, own attendance, own marks                  |

---

## Project Structure

```text
src/main/java/com/gaurav/student_management_system/
├── config/
├── controller/
├── dto/
├── entity/
├── exception/
├── repository/
├── security/
├── service/
└── service/impl/
```

---

## API Reference

### Auth

| Method | Endpoint                 | Access                      |
| ------ | ------------------------ | --------------------------- |
| `POST` | `/auth/register-admin`   | Public — initial setup only |
| `POST` | `/auth/register-teacher` | `ADMIN`                     |
| `POST` | `/auth/register-student` | `ADMIN`                     |
| `POST` | `/auth/login`            | Public                      |

### Students

| Method   | Endpoint         | Access                 |
| -------- | ---------------- | ---------------------- |
| `POST`   | `/students`      | `ADMIN`                |
| `GET`    | `/students`      | `ADMIN`, `TEACHER`     |
| `GET`    | `/students/{id}` | `ADMIN`, `TEACHER`     |
| `GET`    | `/students/me`   | `STUDENT` (own record) |
| `PUT`    | `/students/{id}` | `ADMIN`                |
| `DELETE` | `/students/{id}` | `ADMIN`                |

### Attendance

| Method | Endpoint                          | Access                 |
| ------ | --------------------------------- | ---------------------- |
| `POST` | `/attendance`                     | `TEACHER`              |
| `GET`  | `/attendance/student/{studentId}` | `ADMIN`, `TEACHER`     |
| `GET`  | `/attendance/me`                  | `STUDENT` (own record) |

### Marks

| Method | Endpoint                     | Access                 |
| ------ | ---------------------------- | ---------------------- |
| `POST` | `/marks`                     | `TEACHER`              |
| `GET`  | `/marks/student/{studentId}` | `ADMIN`, `TEACHER`     |
| `GET`  | `/marks/me`                  | `STUDENT` (own record) |

---

## Authentication Flow

1. Bootstrap the first admin via `POST /auth/register-admin` (run once).
2. Admin logs in via `POST /auth/login` and receives a JWT.
3. Admin uses that JWT to register teachers and students.
4. Teachers and students log in via `POST /auth/login` and receive their own JWTs.
5. All protected endpoints require the header: `Authorization: Bearer <token>`

---

## Setup

### 1. Configure the database

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/YOUR_DATABASE_NAME
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
spring.jpa.hibernate.ddl-auto=update

app.jwt.secret=YOUR_SECRET_KEY
app.jwt.expiration=86400000
```

### 2. Build and run

```bash
mvn clean install
mvn spring-boot:run
```

### 3. Open Swagger UI

```text
http://localhost:8080/swagger-ui.html
                 or
http://localhost:8080/swagger-ui/index.html
```

---

## Sample Requests

### Register Admin

```http
POST /auth/register-admin
Content-Type: application/json

{
  "username": "admin1",
  "password": "admin123"
}
```

### Login

```http
POST /auth/login
Content-Type: application/json

{
  "username": "admin1",
  "password": "admin123"
}
```

Response:

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "tokenType": "Bearer",
  "username": "admin1",
  "role": "ADMIN"
}
```

### Create Student (ADMIN)

```http
POST /students
Authorization: Bearer <admin-token>
Content-Type: application/json

{
  "fullName": "Ravi Kumar",
  "email": "ravi@gmail.com",
  "phone": "9876543210",
  "rollNumber": "RK101",
  "department": "Computer Science",
  "course": "BCA",
  "semester": 3,
  "userId": 3
}
```

### Mark Attendance (TEACHER)

```http
POST /attendance
Authorization: Bearer <teacher-token>
Content-Type: application/json

{
  "attendanceDate": "2026-05-17",
  "present": true,
  "studentId": 1
}
```

### Add Marks (TEACHER)

```http
POST /marks
Authorization: Bearer <teacher-token>
Content-Type: application/json

{
  "subject": "Math",
  "score": 85,
  "maxMarks": 100,
  "examType": "Mid Term",
  "studentId": 1
}
```

---

## Security Notes

* Passwords are hashed using BCrypt before storage; plain-text passwords are never persisted.
* JWTs are user-specific; endpoint access is enforced by role-based authorization rules.
* Use `hasRole('TEACHER')` or `hasRole('ADMIN')` on specific endpoints for strict separation.
* **Remove or protect `/auth/register-admin` after first use in production.**
* **Store the JWT secret in environment variables — never hardcode it in source.**

---

## Testing

* **Postman** — Import `student-management-system.postman_collection.json` and use the built-in Bearer token support to authenticate requests after login.
* **Swagger UI** — Open `http://localhost:8080/swagger-ui/index.html` and use the Authorize button to pass the JWT token.
