package com.self_project.task_management_api.service;

import com.self_project.task_management_api.dto.TaskRequestDto;
import com.self_project.task_management_api.dto.TaskResponseDto;
import com.self_project.task_management_api.entity.Task;
import com.self_project.task_management_api.entity.User;
import com.self_project.task_management_api.exception.ResourceNotFoundException;
import com.self_project.task_management_api.exception.UnauthorizedException;
import com.self_project.task_management_api.repository.TaskRepository;
import com.self_project.task_management_api.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    
    /**
     * Create a new task for the authenticated user
     */
    public TaskResponseDto createTask(TaskRequestDto dto, String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        task.setPriority(dto.getPriority());
        task.setDueDate(dto.getDueDate());
        task.setUser(user);
        
        Task savedTask = taskRepository.save(task);
        return mapToDto(savedTask);
    }
    
    /**
     * Get all tasks for the authenticated user
     */
    public List<TaskResponseDto> getUserTasks(String username) {
        if(username == null || username.isEmpty()) {
            return taskRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        };

        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        return taskRepository.findByUserId(user.getId())
            .stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
    }
    
    /**
     * Get a specific task by ID
     * Only the owner can view their task
     */
    public TaskResponseDto getTaskById(Long id, String username) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        
        // Authorization check: verify task belongs to requesting user
        if (!task.getUser().getUsername().equals(username)) {
            throw new UnauthorizedException("Not authorized to access this task");
        }
        
        return mapToDto(task);
    }
    
    /**
     * Update an existing task
     * Only the owner can update their task
     */
    public TaskResponseDto updateTask(Long id, TaskRequestDto dto, String username) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        
        // Authorization check: verify task belongs to requesting user
        if (!task.getUser().getUsername().equals(username)) {
            throw new UnauthorizedException("Not authorized to update this task");
        }
        
        // Update task fields
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        task.setPriority(dto.getPriority());
        task.setDueDate(dto.getDueDate());
        
        Task updatedTask = taskRepository.save(task);
        return mapToDto(updatedTask);
    }
    
    /**
     * Delete a task by ID
     * Only the owner can delete their task
     */
    public void deleteTask(Long id, String username) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        
        // Authorization check: verify task belongs to requesting user
        if (!task.getUser().getUsername().equals(username)) {
            throw new UnauthorizedException("Not authorized to delete this task");
        }
        
        taskRepository.delete(task);
    }
    
    /**
     * Helper method to convert Task entity to DTO
     */
    private TaskResponseDto mapToDto(Task task) {
        TaskResponseDto dto = new TaskResponseDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setPriority(task.getPriority());
        dto.setDueDate(task.getDueDate());
        dto.setCreatedAt(task.getCreatedAt());
        return dto;
    }
}