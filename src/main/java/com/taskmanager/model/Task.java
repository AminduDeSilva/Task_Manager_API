package com.taskmanager.model;

/**
 * Task model representing a to-do item with encapsulated data and behavior
 */
public class Task {
    private int id;
    private String title;
    private String description;
    private TaskStatus status;

    public enum TaskStatus {
        PENDING, DONE
    }

    // Default constructor
    public Task() {}

    // Constructor with parameters
    public Task(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = TaskStatus.PENDING;
    }

    // Constructor without ID (for new tasks)
    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.status = TaskStatus.PENDING;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    // Business logic method - demonstrates encapsulated behavior
    public void markDone() {
        this.status = TaskStatus.DONE;
    }

    public void markPending() {
        this.status = TaskStatus.PENDING;
    }

    public boolean isCompleted() {
        return this.status == TaskStatus.DONE;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
