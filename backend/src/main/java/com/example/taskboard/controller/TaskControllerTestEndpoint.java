package com.example.taskboard.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.taskboard.model.Task;
import com.example.taskboard.service.TaskService;

import java.util.Map;
import java.util.LinkedHashMap;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class TaskControllerTestEndpoint {
    private final TaskService taskService;

    public TaskControllerTestEndpoint(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/api/verify/task-service")
    public Map<String, Object> verifyTaskService() {
        Map<String, Object> out = new LinkedHashMap<>();

        Task t = new Task();
        t.setTitle("verify");
        t.setDescription("service flow");
        t.setStatus("TODO");

        Task created = taskService.createTask(t);
        out.put("createdId", created.getId());
        out.put("createdTitle", created.getTitle());

        Task fetched = taskService.getTaskById(created.getId());
        out.put("fetchedTitle", fetched.getTitle());
        out.put("fetchedStatus", fetched.getStatus());

        // UPDATE: modify allwed fields only
        Task patch = new Task();
        patch.setTitle("verify-updated");
        patch.setDescription("update desc");
        patch.setStatus("DONE");

        Task updated = taskService.updateTask(created.getId(), patch);
        out.put("updatedTitle", updated.getTitle());
        out.put("updatedStatus", updated.getStatus());

        // DELETE: remove the task
        taskService.deleteTask(created.getId());
        out.put("deleted", true);

        // VERIFY: confirm task is gone (should throw exception)
        try {
            taskService.getTaskById(created.getId());
            out.put("postDeletedLookup", "ERROR: unexpectedly found");
        } catch (IllegalArgumentException ex) {
            out.put("postDeleteLookup", ex.getMessage());
        }


        return out;
    }

}