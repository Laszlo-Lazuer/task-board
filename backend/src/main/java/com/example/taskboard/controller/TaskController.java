// ============================================================================
// Task Controller
// ============================================================================
// PURPOSE:
// This controller handles all HTTP requests related to tasks (GET, POST, PUT, DELETE).
// It acts as the "router" between the frontend (REST client) and the backend database.
// Each method handles a specific endpoint and delegates work to the TaskRepository.
//
// KEY CONCEPTS:
//
// 1. @RESTCONTROLLER: Tells Spring this class handles HTTP requests and returns JSON.
//    - Every method returns JSON instead of HTML
//    - Without this annotation, Spring would treat it as a traditional web controller
//
// 2. @REQUESTMAPPING("/api/tasks"): Sets the base URL for all methods in this class.
//    - This controller handles all URLs starting with /api/tasks
//    - /api/tasks/count, /api/tasks/all, /api/tasks/1 etc. go through here
//    - Think of it like "mounting" this controller at a base path
//
// 3. @AUTOWIRED (Dependency Injection): Tells Spring "give me a TaskRepository."
//    - Spring automatically creates and injects a TaskRepository instance
//    - You don't call `new TaskRepository()` manually
//    - This follows the "Inversion of Control" (IoC) pattern
//    - In frontend terms: like requesting a service from a provider, not creating it yourself
//
// 4. @GETMAPPING("/count"): Handles GET requests to /api/tasks/count.
//    - The full URL becomes: http://localhost:8080/api/tasks/count
//    - Similar annotations: @PostMapping, @PutMapping, @DeleteMapping
//
// 5. HTTP METHODS:
//    - GET    = retrieve data (read-only, safe)
//    - POST   = create new data
//    - PUT    = update existing data
//    - DELETE = remove data
//
// 6. RETURN TYPES:
//    - Spring automatically converts return values to JSON
//    - `long` becomes JSON number: 0, 5, 10, etc.
//    - Objects become JSON objects with fields as properties
//
// FRONTEND ENGINEER NOTE:
// A controller is basically an API endpoint handler. You define HTTP routes
// and what code runs when someone calls that route. It's like defining
// Express.js routes in Node.js or Next.js API routes.
// ============================================================================

package com.example.taskboard.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.example.taskboard.repository.TaskRepository;
import com.example.taskboard.service.TaskService;

import com.example.taskboard.model.Task;
import java.util.List;

// @RestController: Marks this as an HTTP request handler. All methods return JSON.
@RestController
// @RequestMapping: Base URL path for all methods in this class. /api/tasks/count, /api/tasks/all, etc.
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // ============================================================================
    // READ ALL: GET /api/tasks
    // ============================================================================
    // Returns all tasks from the database.
    // HTTP Status: 200 OK (implicit)
    @GetMapping("/")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    // ============================================================================
    // READ ONE: GET /api/tasks/{id}
    // ============================================================================
    // Returns a single task by ID.
    // @PathVariable extracts the {id} from the URL path.
    // HTTP Status: 200 OK (implicit)
    // If task not found: taskService throws IllegalArgumentException → 500
    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }


    // ============================================================================
    // CREATE: POST /api/tasks
    // ============================================================================
    // Creates a new task and returns it with generated ID.
    // @RequestBody: Deserializes JSON request body into a Task object.
    // @ResponseStatus(HttpStatus.CREATED): Forces HTTP 201 response (not 200).
    // HTTP Status: 201 Created
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    // ============================================================================
    // UPDATE: PUT /api/tasks/{id}
    // ============================================================================
    // Updates an existing task by ID.
    // @PathVariable Long id: task to update
    // @RequestBody Task updated: new field values
    // HTTP Status: 200 OK (implicit)
    // If task not found: taskService throws IllegalArgumentException → 500
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updated) {
        return taskService.updateTask(id, updated);
    }

    // ============================================================================
    // DELETE: DELETE /api/tasks/{id}
    // ============================================================================
    // Deletes a task by ID.
    // @ResponseStatus(HttpStatus.NO_CONTENT): Forces HTTP 204 response (no body).
    // HTTP Status: 204 No Content
    // If task not found: taskService throws IllegalArgumentException → 500
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

}