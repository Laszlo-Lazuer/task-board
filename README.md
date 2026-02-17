# task-board

Portfolio project focused on upskilling my backend knowledge to Sr. Backend Engineer level. This repository is a learning vehicle for building and iterating on a full-stack task board with production-grade backend practices.

## Goals
- Strengthen backend fundamentals (API design, data modeling, validation, error handling).
- Practice scalable architecture patterns and clean code boundaries.
- Improve performance, observability, testing, and documentation.
- Explore security and operational readiness (auth, config, environments, CI).

## Stack
- Backend: Java/Spring Boot
- Frontend: React + Vite + TypeScript

## H2 Console (Dev Database)

The project uses an in-memory H2 database for development. You can inspect the database via the H2 Console.

### Access the Console

1. Start the backend: `mvn spring-boot:run`
2. Open your browser: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
3. Login with:
   - **JDBC URL:** `jdbc:h2:mem:taskdb`
   - **User Name:** `sa`
   - **Password:** *(leave blank)*
4. Click **Connect**

### Example Queries

```sql
-- List all tables
SHOW TABLES;

-- View task table structure
SHOW COLUMNS FROM TASK;

-- View all tasks
SELECT * FROM TASK;

-- Insert a test task
INSERT INTO TASK (TITLE, DESCRIPTION, STATUS, CREATED_AT, UPDATED_AT)
VALUES ('Test Task', 'My first task', 'TODO', NOW(), NOW());
```

### Notes
- Data is **reset on every restart** (in-memory database).
- Spring Boot 4.x requires the `spring-boot-h2console` dependency for the console to work.

## Notes
This project is intentionally iterative; features, structure, and conventions may evolve as I apply new backend concepts.
