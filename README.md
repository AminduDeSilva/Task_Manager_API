# Task Manager API

A simple and elegant Task Manager REST API built with Java, demonstrating object-oriented programming principles and RESTful web service design. This project showcases clean code architecture, proper separation of concerns, and a functional web interface.

## Features

- **Complete CRUD Operations**: Create, Read, Update, and Delete tasks
- **RESTful API Design**: Clean, intuitive endpoints following REST conventions
- **Object-Oriented Architecture**: Proper encapsulation and separation of concerns
- **Interactive Web UI**: Modern, responsive front-end interface
- **In-Memory Storage**: Fast, lightweight data persistence for demonstration
- **Thread-Safe Operations**: Concurrent request handling with thread-safe collections
- **Error Handling**: Comprehensive error responses and validation
- **API Statistics**: Task completion metrics and analytics

## Technology Stack

- **Java 17**: Modern Java with latest features
- **Spark Java**: Lightweight web framework for REST API
- **Gson**: JSON serialization and deserialization
- **Maven**: Build automation and dependency management
- **HTML5 & JavaScript**: Clean, modern front-end interface
- **CSS3**: Responsive design with modern styling

## API Endpoints

### Tasks Management
- `GET /api/tasks` - Retrieve all tasks
- `GET /api/tasks/{id}` - Get a specific task by ID
- `POST /api/tasks` - Create a new task
- `PUT /api/tasks/{id}` - Update an existing task
- `PATCH /api/tasks/{id}/done` - Mark task as completed
- `PATCH /api/tasks/{id}/pending` - Mark task as pending
- `DELETE /api/tasks/{id}` - Delete a task

### Statistics & Health
- `GET /api/tasks/stats` - Get task statistics (total, pending, completed)
- `GET /health` - Health check endpoint
- `GET /` - API documentation page

## Project Structure

```
src/
├── main/
│   ├── java/com/taskmanager/
│   │   ├── TaskManagerApp.java          # Main application entry point
│   │   ├── model/
│   │   │   └── Task.java                # Task data model with encapsulation
│   │   ├── service/
│   │   │   └── TaskService.java         # Business logic layer
│   │   └── controller/
│   │       └── TaskController.java      # REST API endpoints
│   └── resources/
│       └── public/
│           └── index.html               # Web UI interface
```

## Object-Oriented Design Principles

### 1. **Encapsulation**
- `Task` class with private fields and public getters/setters
- Controlled access to task properties and state

### 2. **Separation of Concerns**
- **Model Layer**: `Task` class handles data representation
- **Service Layer**: `TaskService` manages business logic
- **Controller Layer**: `TaskController` handles HTTP requests/responses

### 3. **Single Responsibility**
- Each class has a clear, focused purpose
- Methods are cohesive and focused on specific functionality

### 4. **Data Integrity**
- Thread-safe collections for concurrent access
- Input validation and error handling
- Atomic operations for ID generation

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Installation & Running

1. **Clone or download the project**
   ```bash
   cd task-manager-api
   ```

2. **Build the project**
   ```bash
   mvn clean compile
   ```

3. **Run the application**
   ```bash
   mvn exec:java -Dexec.mainClass="com.taskmanager.TaskManagerApp"
   ```

4. **Access the application**
   - **Web UI**: http://localhost:8080/index.html
   - **API Documentation**: http://localhost:8080/
   - **Health Check**: http://localhost:8080/health

## Sample API Usage

### Creating a Task
```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Learn Java OOP",
    "description": "Study object-oriented programming concepts"
  }'
```

### Getting All Tasks
```bash
curl http://localhost:8080/api/tasks
```

### Marking a Task as Done
```bash
curl -X PATCH http://localhost:8080/api/tasks/1/done
```

### Getting Task Statistics
```bash
curl http://localhost:8080/api/tasks/stats
```

## Web Interface Features

The included web interface provides:
- **Task Creation**: Simple form to add new tasks
- **Task Management**: Mark tasks as done/pending, delete tasks
- **Real-time Statistics**: Live updates of task counts and completion rates
- **Responsive Design**: Works on desktop and mobile devices
- **Error Handling**: User-friendly error messages and validation

----------------------
<img  alt="Web Interface" src="https://github.com/user-attachments/assets/fbfd4d31-5594-477c-aa45-483428dfa0b6" />

----------------------

<img  alt="Web Interface" src="https://github.com/user-attachments/assets/20530695-4fbd-4288-93f6-1b4aa2e82828" />



---


## Configuration

The application can be configured through environment variables:
- `PORT`: Set the server port (default: 8080)

Example:
```bash
export PORT=9000
mvn exec:java -Dexec.mainClass="com.taskmanager.TaskManagerApp"
```

## Testing the API

You can test the API using:
1. **Web Interface**: Use the included HTML interface at `/index.html`
2. **Curl Commands**: Use the command-line examples above
3. **Postman**: Import the endpoints for interactive testing
4. **Browser**: GET endpoints can be tested directly in the browser


## Future Enhancements

This project can be extended with:
- Database persistence (H2, PostgreSQL, MongoDB)
- User authentication and authorization
- Task categories and tags
- Due dates and reminders
- Task priority levels
- File attachments
- RESTful pagination for large datasets
- Unit and integration tests
- Docker containerization
- API versioning


