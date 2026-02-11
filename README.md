# task manager api

task management rest api with jwt authentication, ownership isolation and task lifecycle.

tech stack implemented - java 17+, spring (boot, security, data jpa/hibernate), postgresql, flyway,
h2 (testing). build helper - maven.

features: user register/login with jwt, tasks crud (proper task workflow included).
db data access/modification is being optimized via pagination and filtering. flyway is being used for proper db migration.

## documentation
### api overview

users: <p>
POST /auth/register → user registration<p>
POST /auth/login → user credentials are entered, jwt token <p>
tasks: <p>
POST /tasks → creating new task (all task requests are user isolated and need proper token of ownership) <p>
GET /tasks → get tasks (filtering by status and due date + pagination and filtering parameters) <p>
PUT /tasks/{id} → updating task information <p>
PATCH /tasks/{id}/status → changing task status <p>
DELETE /tasks/{id} → delete tasks (again, ownership required) <p>

### authentication flow: 
register→login→receive jwt→use header (Authorization: Bearer 'token')

### environment variables
JWT_SECRET - 32 digit randomly generated key for correct jwt usage <p>
DB_URL - url address that connects to db local server
DB_USER - default login name to connect to db
DB_PASSWORD - default password name to connect to db

### testing

some tests were included already, room for expansion of tests exists.<p>

explaining every test would be quite verbose, <p> all the needed information can be found through logs by running

`mvn test`

### additional examples of requests

#### login

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "password123"
  }'
```

#### create task
```bash
curl -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN_HERE" \
  -d '{
    "details": "info",
    "dueDate": "2026-03-01T12:00:00",
    "status": "TODO"
  }'
```

#### list tasks
```bash
curl -X GET "http://localhost:8080/tasks?page=0&size=10" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN_HERE"
```














