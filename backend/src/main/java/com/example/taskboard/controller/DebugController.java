package com.example.taskboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class DebugController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/api/debug/tables")
    public List<Map<String, Object>> getTables() {
        return jdbcTemplate.queryForList("SHOW TABLES");
    }

    @GetMapping("/api/debug/task-schema")
    public List<Map<String, Object>> getTaskSchema() {
        return jdbcTemplate.queryForList("SHOW COLUMNS FROM TASK");
    }
}
