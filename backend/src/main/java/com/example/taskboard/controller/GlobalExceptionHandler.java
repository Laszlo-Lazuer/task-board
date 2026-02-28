package com.example.taskboard.controller;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Global Exception Handler for the REST API
 *
 * FRONTEND PERSPECTIVE:
 * This class is your "error translator" between the backend business logic and HTTP responses.
 * When the backend throws an exception (like "Task not found"), this class converts it into
 * a proper HTTP status code (404) that your frontend can understand and act on.
 *
 * WITHOUT this handler: Backend exceptions → 500 Internal Server Error (confusing, unhelpful)
 * WITH this handler: Specific exceptions → Specific HTTP codes (clear, actionable)
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle "Not Found" scenarios (e.g., trying to fetch/update/delete a task that doesn't exist)
     *
     * @param ex The IllegalArgumentException thrown by TaskService when a task ID doesn't exist
     * @return A structured 404 response with error details
     *
     * FRONTEND USAGE:
     * When your React component calls `fetch('/api/tasks/999')`:
     * - Status will be 404 (not 500)
     * - You can check `response.status === 404` to show a "Not found" message
     * - You get the error message in the JSON body for user-friendly error displays
     * - You get a timestamp for debugging/logging
     *
     * EXAMPLE Frontend code:
     * ```
     * const res = await fetch('/api/tasks/999');
     * if (res.status === 404) {
     *   const error = await res.json();
     *   console.log(`Error: ${error.message}`); // "Task not found with id: 999"
     *   showErrorToUser("This task no longer exists");
     * }
     * ```
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(IllegalArgumentException ex) {
        // Build a structured error response that the frontend can parse
        // Map.of() creates an immutable JSON object with key-value pairs
        Map<String, Object> errorResponse = Map.of(
            "error", "Not Found",           // Standard error type for 404s
            "message", ex.getMessage(),     // Specific reason (e.g., "Task not found with id: 999")
            "timestamp", LocalDateTime.now() // When the error occurred (useful for logging)
        );

        // Return HTTP 404 with the error details in the body
        // This tells the frontend: "Resource doesn't exist" (it's not a server bug, it's expected)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}