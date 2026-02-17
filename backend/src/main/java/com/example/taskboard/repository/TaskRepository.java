// ============================================================================
// Task Repository Interface
// ============================================================================
// PURPOSE:
// This interface provides database access methods for the Task entity.
// Instead of writing SQL queries or boilerplate JDBC code, we declare
// this interface and Spring Data JPA auto-generates all CRUD operations.
//
// KEY CONCEPTS:
//
// 1. REPOSITORY PATTERN: A Repository is a data access abstraction. It sits
//    between your business logic and the database, hiding SQL complexity.
//    Think of it like a librarian who fetches books (data) without you
//    needing to know where they're shelved (SQL).
//
// 2. JPAREPOSITORY: The interface that provides pre-built methods:
//    - findAll()      → SELECT * FROM task
//    - findById(id)   → SELECT * FROM task WHERE id = ?
//    - save(task)     → INSERT or UPDATE
//    - deleteById(id) → DELETE FROM task WHERE id = ?
//    - count()        → SELECT COUNT(*) FROM task
//    And many more. Spring auto-implements all of them.
//
// 3. GENERICS <Task, Long>:
//    - Task    = the entity type (your JPA class)
//    - Long    = the type of the primary key (@Id field)
//    These tell Spring Data what table and ID type to work with.
//
// 4. @REPOSITORY: Tells Spring "this is a data access component."
//    It's optional (Spring infers it from extends JpaRepository),
//    but it's good practice for clarity and error handling.
//
// 5. WHY NO METHOD IMPLEMENTATIONS?
//    Spring Data JPA uses reflection and bytecode generation at startup
//    to create method implementations. You just declare the interface
//    and Spring wires everything up automatically.
//
// FRONTEND ENGINEER NOTE:
// This is like defining a REST client interface—you declare the contract
// (what methods you need), and a library implements it for you.
// ============================================================================

package com.example.taskboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.taskboard.model.Task;

// @Repository: Spring annotation marking this as a data access component.
// It enables exception translation (database errors → Spring exceptions)
// and makes it available for dependency injection.
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {}
