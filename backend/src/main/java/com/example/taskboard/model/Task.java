package com.example.taskboard.model;


// What is a JPA Entity?
// A JPA Entity is a Java class that maps to a database table. Each instance of the class represents a row in that table.

// Key Takeaways:
// @Entity — Tells Spring/Hibernate "this class is a database table"
// @Id + @GeneratedValue — Marks the primary key and auto-increments it
// Field annotations — @NotBlank, @Size validate data before saving
// Lifecycle hooks — @PrePersist and @PreUpdate auto-set timestamps without manual code
// Automatic table creation — With spring.jpa.hibernate.ddl-auto=update, Hibernate creates/updates the table schema from your class definition
// Lombok @Data — Enterprise approach to auto-generate getters/setters/toString

// One-Liner Explanation
// "A JPA Entity is a Java class annotated with @Entity that Hibernate automatically maps to a database table—each field becomes a column, each object becomes a row."

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max=255)
    private String title;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}