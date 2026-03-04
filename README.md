# task management API

REST API for task management built with **Spring Boot**.  
provides jwt authentication, user ownership isolation, and task lifecycle management.

---

## tech Stack

- java 17
- spring boot
- spring security (jwt auth)
- spring data JPA / hibernate
- postgresql
- flyway (database migrations)
- h2 database (testing)
- maven

---

## features

- user registration and authentication using jwt
- secure user-scoped task management
- full CRUD operations for tasks
- task status workflow management
- pagination and filtering support
- database versioning with Flyway
- unit and integration tests
- openAPI / swagger documentation

---

## getting started

### requirements

make sure you have installed:

- java 17+
- postgreSQL
- git

maven wrapper included

---

## 1. clone Repository

```bash
git clone https://github.com/ibrakhim2906/task_management_api.git
cd task_management_api
```

---

## 2. configure Environment Variables

create `.env` file based on the provided example:

```bash
cp .env.example .env
```

example configuration:

```
JWT_SECRET=your-32-character-secret-key
DB_URL=jdbc:postgresql://localhost:5432/taskdb
DB_USER=postgres
DB_PASSWORD=password
```

---

## 3. run Application

linux / macOS:

```bash
./mvnw spring-boot:run
```

windows:

```bash
mvnw.cmd spring-boot:run
```

---

## 4. open swagger UI

after starting the application:

```
http://localhost:8080/swagger-ui/index.html
```

---

## authentication flow

1. register a user  
2. login  
3. receive JWT token  
4. use token in requests:

```
Authorization: Bearer <token>
```

---

## API overview

### authentication

| method | endpoint | description |
|--------|----------|-------------|
| POST | `/auth/register` | register user |
| POST | `/auth/login` | login and receive jwt |

### tasks

| method | endpoint | description |
|--------|----------|-------------|
| POST | `/tasks` | create task |
| GET | `/tasks` | get tasks (pagination & filtering) |
| PUT | `/tasks/{id}` | update task |
| PATCH | `/tasks/{id}/status` | change task status |
| DELETE | `/tasks/{id}` | delete task |

---

## running tests

```bash
./mvnw test
```

---

## example requests

### login

```bash
curl -X POST http://localhost:8080/auth/login \
-H "Content-Type: application/json" \
-d '{"email":"user@example.com","password":"password123"}'
```

---

### create task

```bash
curl -X POST http://localhost:8080/tasks \
-H "Authorization: Bearer YOUR_TOKEN" \
-H "Content-Type: application/json" \
-d '{
  "details":"Example task",
  "dueDate":"2026-03-01T12:00:00",
  "status":"TODO"
}'
```

---

### get tasks

```bash
curl -X GET "http://localhost:8080/tasks?page=0&size=10" \
-H "Authorization: Bearer YOUR_TOKEN"
```
