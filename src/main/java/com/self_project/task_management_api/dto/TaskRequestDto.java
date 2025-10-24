package com.self_project.task_management_api.dto;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

import com.self_project.task_management_api.enumeration.Priority;
import com.self_project.task_management_api.enumeration.TaskStatus;

@Data
public class TaskRequestDto {
    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Status is required")
    private TaskStatus status;

    private Priority priority;

    @Future(message = "Due date must be in the future")
    private LocalDate dueDate;
}
