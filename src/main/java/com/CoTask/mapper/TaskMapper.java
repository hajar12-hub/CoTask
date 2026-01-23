package com.CoTask.mapper;

import com.CoTask.dto.TaskDTO;
import com.CoTask.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public TaskDTO toDTO(Task task) {
        if (task == null) {
            return null;
        }
        return TaskDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .userStoryId(task.getUserStory() != null ? task.getUserStory().getId() : null)
                .sprintId(task.getSprint() != null ? task.getSprint().getId() : null)
                .build();
    }


    public Task toEntity(TaskDTO dto) {
        if (dto == null) {
            return null;
        }
        return Task.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .build();
    }
}