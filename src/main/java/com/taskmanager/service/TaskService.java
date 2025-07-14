package com.taskmanager.service;

import com.taskmanager.model.Task;
import com.taskmanager.model.Task.TaskStatus;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * TaskService handles all business logic for task management
 * Uses thread-safe collections for concurrent access
 */
public class TaskService {
    private final Map<Integer, Task> tasks;
    private final AtomicInteger idCounter;

    public TaskService() {
        this.tasks = new ConcurrentHashMap<>();
        this.idCounter = new AtomicInteger(1);
    }

    /**
     * Add a new task to the collection
     */
    public Task addTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }

        // Assign a unique ID if not set
        if (task.getId() == 0) {
            task.setId(idCounter.getAndIncrement());
        }

        tasks.put(task.getId(), task);
        return task;
    }

    /**
     * Get all tasks as a list
     */
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    /**
     * Get a task by its ID
     */
    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    /**
     * Update an existing task
     */
    public boolean updateTask(int id, Task updatedTask) {
        if (updatedTask == null || !tasks.containsKey(id)) {
            return false;
        }

        Task existingTask = tasks.get(id);

        // Update fields while preserving the ID
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setStatus(updatedTask.getStatus());

        return true;
    }

    /**
     * Mark a task as done
     */
    public boolean markTaskDone(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            task.markDone();
            return true;
        }
        return false;
    }

    /**
     * Mark a task as pending
     */
    public boolean markTaskPending(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            task.markPending();
            return true;
        }
        return false;
    }

    /**
     * Delete a task by ID
     */
    public boolean deleteTask(int id) {
        return tasks.remove(id) != null;
    }

    /**
     * Get tasks by status
     */
    public List<Task> getTasksByStatus(TaskStatus status) {
        return tasks.values().stream()
                .filter(task -> task.getStatus() == status)
                .toList();
    }

    /**
     * Get total count of tasks
     */
    public int getTaskCount() {
        return tasks.size();
    }
}
