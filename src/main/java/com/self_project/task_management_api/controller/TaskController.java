package com.self_project.task_management_api.controller;

import com.self_project.task_management_api.dto.TaskRequestDto;
import com.self_project.task_management_api.dto.TaskResponseDto;
import com.self_project.task_management_api.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Validated
@Tag(name = "Tasks", description = "Endpoints for managing tasks")
@SecurityRequirement(name = "bearerAuth")
public class TaskController {
    
    private final TaskService taskService;
    
    @PostMapping("/createTask")
    @Operation(summary = "Create new task", description = "Create a new task for the authenticated user")
    @ApiResponse(responseCode = "201", description = "Task created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing token")
    public ResponseEntity<TaskResponseDto> createTask(
            @Valid @RequestBody TaskRequestDto dto,
            @AuthenticationPrincipal UserDetails userDetails) {
        TaskResponseDto created = taskService.createTask(dto, userDetails.getUsername());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    
    @GetMapping("/getUserTasks")
    @Operation(summary = "Get tasks by user login", description = "Get all tasks for the authenticated user")
    @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully")
    @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing token")
    public ResponseEntity<List<TaskResponseDto>> getUserTasks(
            @Parameter(description = "Optional username to filter tasks")
            @RequestParam(name= "username", required = false) 
            String username) {
        List<TaskResponseDto> tasks = taskService.getUserTasks(username);
        return ResponseEntity.ok(tasks);
    }
    
    @GetMapping("getTaskData/{id}")
    @Operation(summary = "Get task by ID", description = "Get a specific task by ID")
    @ApiResponse(responseCode = "200", description = "Task retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Task not found")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    public ResponseEntity<TaskResponseDto> getTaskById(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        TaskResponseDto task = taskService.getTaskById(id, userDetails.getUsername());
        return ResponseEntity.ok(task);
    }
    
    @PutMapping("/updateTask/{id}")
    @Operation(summary = "Update task", description = "Update an existing task")
    @ApiResponse(responseCode = "200", description = "Task updated successfully")
    @ApiResponse(responseCode = "404", description = "Task not found")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    public ResponseEntity<TaskResponseDto> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequestDto dto,
            @AuthenticationPrincipal UserDetails userDetails) {
        TaskResponseDto updated = taskService.updateTask(id, dto, userDetails.getUsername());
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/deleteTask/{id}")
    @Operation(summary = "Delete task", description = "Delete a task by ID")
    @ApiResponse(responseCode = "204", description = "Task deleted successfully")
    @ApiResponse(responseCode = "404", description = "Task not found")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        taskService.deleteTask(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}