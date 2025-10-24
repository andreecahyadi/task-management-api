package com.self_project.task_management_api.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.self_project.task_management_api.enumeration.Priority;
import com.self_project.task_management_api.enumeration.TaskStatus;

@Data
public class TaskResponseDto {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private Priority priority;
    private LocalDate dueDate;
    private LocalDateTime createdAt;
}
