package com.example.taskboard.service;

// Spring annotation used to register this class as a Service-layer bean.
// Think of this as the "business logic layer" between controllers (API routes)
// and repositories (database access).
import org.springframework.stereotype.Service;

// JPA entity model for one task row.
import com.example.taskboard.model.Task;

// Data-access abstraction. Spring Data gives us CRUD methods automatically.
import com.example.taskboard.repository.TaskRepository;

// Java collection type used for returning multiple tasks.
import java.util.List;

// Marks this class as a Spring-managed service component.
@Service
public class TaskService {
    // final means this dependency is assigned once in the constructor.
    // This is preferred for immutability and easier reasoning.
    private final TaskRepository taskRepository;

    // Constructor injection: Spring provides TaskRepository when creating this service.
    // No @Autowired is needed on single-constructor classes.
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // READ (all): equivalent to listing all items in a frontend data table.
    // Delegates to repository; no extra business rules needed here yet.
    public List<Task> getAllTasks() { return taskRepository.findAll();}

    // READ (one): fetch a single task by id.
    // findById returns Optional<Task>, so we convert "missing data" into a clear exception.
    // Similar to throwing a 404-style error path instead of returning undefined.
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + id));
    }

    // CREATE: persists a new task row.
    // JPA handles INSERT and generated id assignment.
    public Task createTask(Task task) { return taskRepository.save(task);}

    // UPDATE: always read existing first so we fail fast if id does not exist.
    // Then patch allowed fields and save.
    // This mirrors frontend state updates: find item, merge editable fields, persist.
    public Task updateTask(Long id, Task updated) {
        Task existing = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + id));

        // Field-by-field update keeps intent explicit and avoids replacing the whole object blindly.
        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setStatus(updated.getStatus());

        // save(existing) performs UPDATE because existing already has an id.
        return taskRepository.save(existing);
    }

    // DELETE: verify existence first for consistent error behavior,
    // then remove by primary key.
    public void deleteTask(Long id) {
        taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + id));
        taskRepository.deleteById(id);
    }
}