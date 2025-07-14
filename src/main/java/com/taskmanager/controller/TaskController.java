package com.taskmanager.controller;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.taskmanager.model.Task;
import com.taskmanager.service.TaskService;

import static spark.Spark.*;

/**
 * TaskController handles all REST API endpoints for task management
 * Uses Spark Java framework for lightweight HTTP handling
 */
public class TaskController {
    private final TaskService taskService;
    private final Gson gson;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
        this.gson = new Gson();
    }

    /**
     * Initialize all REST API routes
     */
    public void initializeRoutes() {
        // Enable CORS for browser requests
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
        });

        // Handle preflight OPTIONS requests
        options("/*", (request, response) -> "OK");

        // Set JSON content type for all responses
        before((request, response) -> response.type("application/json"));

        // GET /api/tasks - Get all tasks
        get("/api/tasks", (request, response) -> {
            try {
                return gson.toJson(taskService.getAllTasks());
            } catch (Exception e) {
                response.status(500);
                return gson.toJson(new ErrorResponse("Failed to retrieve tasks: " + e.getMessage()));
            }
        });

        // GET /api/tasks/:id - Get a specific task by ID
        get("/api/tasks/:id", (request, response) -> {
            try {
                int id = Integer.parseInt(request.params(":id"));
                Task task = taskService.getTaskById(id);

                if (task != null) {
                    return gson.toJson(task);
                } else {
                    response.status(404);
                    return gson.toJson(new ErrorResponse("Task not found with ID: " + id));
                }
            } catch (NumberFormatException e) {
                response.status(400);
                return gson.toJson(new ErrorResponse("Invalid task ID format"));
            } catch (Exception e) {
                response.status(500);
                return gson.toJson(new ErrorResponse("Failed to retrieve task: " + e.getMessage()));
            }
        });

        // POST /api/tasks - Create a new task
        post("/api/tasks", (request, response) -> {
            try {
                Task task = gson.fromJson(request.body(), Task.class);

                if (task == null || task.getTitle() == null || task.getTitle().trim().isEmpty()) {
                    response.status(400);
                    return gson.toJson(new ErrorResponse("Task title is required"));
                }

                Task createdTask = taskService.addTask(task);
                response.status(201);
                return gson.toJson(createdTask);

            } catch (JsonSyntaxException e) {
                response.status(400);
                return gson.toJson(new ErrorResponse("Invalid JSON format"));
            } catch (Exception e) {
                response.status(500);
                return gson.toJson(new ErrorResponse("Failed to create task: " + e.getMessage()));
            }
        });

        // PUT /api/tasks/:id - Update an existing task
        put("/api/tasks/:id", (request, response) -> {
            try {
                int id = Integer.parseInt(request.params(":id"));
                Task updatedTask = gson.fromJson(request.body(), Task.class);

                if (updatedTask == null) {
                    response.status(400);
                    return gson.toJson(new ErrorResponse("Invalid task data"));
                }

                boolean updated = taskService.updateTask(id, updatedTask);

                if (updated) {
                    Task task = taskService.getTaskById(id);
                    return gson.toJson(task);
                } else {
                    response.status(404);
                    return gson.toJson(new ErrorResponse("Task not found with ID: " + id));
                }

            } catch (NumberFormatException e) {
                response.status(400);
                return gson.toJson(new ErrorResponse("Invalid task ID format"));
            } catch (JsonSyntaxException e) {
                response.status(400);
                return gson.toJson(new ErrorResponse("Invalid JSON format"));
            } catch (Exception e) {
                response.status(500);
                return gson.toJson(new ErrorResponse("Failed to update task: " + e.getMessage()));
            }
        });

        // PATCH /api/tasks/:id/done - Mark task as done
        patch("/api/tasks/:id/done", (request, response) -> {
            try {
                int id = Integer.parseInt(request.params(":id"));
                boolean updated = taskService.markTaskDone(id);

                if (updated) {
                    Task task = taskService.getTaskById(id);
                    return gson.toJson(task);
                } else {
                    response.status(404);
                    return gson.toJson(new ErrorResponse("Task not found with ID: " + id));
                }

            } catch (NumberFormatException e) {
                response.status(400);
                return gson.toJson(new ErrorResponse("Invalid task ID format"));
            } catch (Exception e) {
                response.status(500);
                return gson.toJson(new ErrorResponse("Failed to mark task as done: " + e.getMessage()));
            }
        });

        // PATCH /api/tasks/:id/pending - Mark task as pending
        patch("/api/tasks/:id/pending", (request, response) -> {
            try {
                int id = Integer.parseInt(request.params(":id"));
                boolean updated = taskService.markTaskPending(id);

                if (updated) {
                    Task task = taskService.getTaskById(id);
                    return gson.toJson(task);
                } else {
                    response.status(404);
                    return gson.toJson(new ErrorResponse("Task not found with ID: " + id));
                }

            } catch (NumberFormatException e) {
                response.status(400);
                return gson.toJson(new ErrorResponse("Invalid task ID format"));
            } catch (Exception e) {
                response.status(500);
                return gson.toJson(new ErrorResponse("Failed to mark task as pending: " + e.getMessage()));
            }
        });

        // DELETE /api/tasks/:id - Delete a task
        delete("/api/tasks/:id", (request, response) -> {
            try {
                int id = Integer.parseInt(request.params(":id"));
                boolean deleted = taskService.deleteTask(id);

                if (deleted) {
                    response.status(204);
                    return "";
                } else {
                    response.status(404);
                    return gson.toJson(new ErrorResponse("Task not found with ID: " + id));
                }

            } catch (NumberFormatException e) {
                response.status(400);
                return gson.toJson(new ErrorResponse("Invalid task ID format"));
            } catch (Exception e) {
                response.status(500);
                return gson.toJson(new ErrorResponse("Failed to delete task: " + e.getMessage()));
            }
        });

        // GET /api/tasks/stats - Get task statistics
        get("/api/tasks/stats", (request, response) -> {
            try {
                TaskStats stats = new TaskStats(
                    taskService.getTaskCount(),
                    taskService.getTasksByStatus(Task.TaskStatus.PENDING).size(),
                    taskService.getTasksByStatus(Task.TaskStatus.DONE).size()
                );
                return gson.toJson(stats);
            } catch (Exception e) {
                response.status(500);
                return gson.toJson(new ErrorResponse("Failed to retrieve statistics: " + e.getMessage()));
            }
        });
    }

    /**
     * Error response wrapper class
     */
    private static class ErrorResponse {
        private final String error;
        private final long timestamp;

        public ErrorResponse(String error) {
            this.error = error;
            this.timestamp = System.currentTimeMillis();
        }

        @SuppressWarnings("unused") // Used by Gson for JSON serialization
        public String getError() { return error; }

        @SuppressWarnings("unused") // Used by Gson for JSON serialization
        public long getTimestamp() { return timestamp; }
    }

    /**
     * Task statistics wrapper class
     */
    private static class TaskStats {
        private final int total;
        private final int pending;
        private final int completed;

        public TaskStats(int total, int pending, int completed) {
            this.total = total;
            this.pending = pending;
            this.completed = completed;
        }

        @SuppressWarnings("unused") // Used by Gson for JSON serialization
        public int getTotal() { return total; }

        @SuppressWarnings("unused") // Used by Gson for JSON serialization
        public int getPending() { return pending; }

        @SuppressWarnings("unused") // Used by Gson for JSON serialization
        public int getCompleted() { return completed; }
    }
}
