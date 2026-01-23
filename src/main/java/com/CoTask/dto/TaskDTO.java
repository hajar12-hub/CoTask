package com.CoTask.dto;

import com.CoTask.entity.emuns.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private String assignee;
    private Long userStoryId;
    private Long sprintId;
}
