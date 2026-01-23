package com.CoTask.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTaskStatusDTO {

    @NotBlank(message = "Task status is required")
    private String status;

}
