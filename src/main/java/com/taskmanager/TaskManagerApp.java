package com.taskmanager;

import com.taskmanager.controller.TaskController;
import com.taskmanager.service.TaskService;

import static spark.Spark.*;

/**
 * Main application class for the Task Manager API
 * Demonstrates Java OOP principles with a RESTful web service
 */
public class TaskManagerApp {

    public static void main(String[] args) {
        // Set the port (default 4567 for Spark, or use environment variable)
        String portStr = System.getenv("PORT");
        if (portStr != null) {
            port(Integer.parseInt(portStr));
        } else {
            port(8080); // Use 8080 for easier access
        }

        // Configure static file serving for the web UI
        staticFileLocation("/public");

        // Initialize service and controller
        TaskService taskService = new TaskService();
        TaskController taskController = new TaskController(taskService);

        // Add some sample data for demo purposes
        initializeSampleData(taskService);

        // Initialize all API routes
        taskController.initializeRoutes();

        // Add a health check endpoint
        get("/health", (req, res) -> {
            res.type("application/json");
            return "{\"status\":\"UP\",\"service\":\"Task Manager API\"}";
        });

        // Root endpoint with API information
        get("/", (req, res) -> {
            res.type("text/html");
            return "<html><body>" +
                   "<h1>Task Manager API</h1>" +
                   "<p>Welcome to the Task Manager REST API</p>" +
                   "<h3>Available Endpoints:</h3>" +
                   "<ul>" +
                   "<li>GET /api/tasks - Get all tasks</li>" +
                   "<li>GET /api/tasks/{id} - Get a specific task</li>" +
                   "<li>POST /api/tasks - Create a new task</li>" +
                   "<li>PUT /api/tasks/{id} - Update a task</li>" +
                   "<li>PATCH /api/tasks/{id}/done - Mark task as done</li>" +
                   "<li>PATCH /api/tasks/{id}/pending - Mark task as pending</li>" +
                   "<li>DELETE /api/tasks/{id} - Delete a task</li>" +
                   "<li>GET /api/tasks/stats - Get task statistics</li>" +
                   "</ul>" +
                   "<p><a href='/index.html'>Go to Task Manager UI</a></p>" +
                   "</body></html>";
        });

        // Setup graceful shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down Task Manager API...");
            stop();
        }));

        System.out.println("🚀 Task Manager API started successfully!");
        System.out.println("📍 Server running on: http://localhost:" + port());
        System.out.println("🌐 Web UI available at: http://localhost:" + port() + "/index.html");
        System.out.println("📋 API documentation at: http://localhost:" + port() + "/");
    }

    /**
     * Initialize some sample tasks for demonstration
     */
    private static void initializeSampleData(TaskService taskService) {
        taskService.addTask(new com.taskmanager.model.Task("Learn Java OOP", "Study object-oriented programming concepts and design patterns"));
        taskService.addTask(new com.taskmanager.model.Task("Build REST API", "Create a RESTful web service using Spark Java framework"));
        taskService.addTask(new com.taskmanager.model.Task("Write Documentation", "Create comprehensive README and API documentation"));

        // Mark one task as completed for demo
        taskService.markTaskDone(2);

        System.out.println("✅ Sample data initialized with 3 tasks");
    }
}
