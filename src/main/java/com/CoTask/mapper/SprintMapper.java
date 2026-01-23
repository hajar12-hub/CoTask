package com.CoTask.mapper;

import com.CoTask.dto.SprintDTO;
import com.CoTask.dto.TaskDTO;
import com.CoTask.dto.UserStoryDTO;
import com.CoTask.entity.Sprint;
import com.CoTask.entity.Task;
import com.CoTask.entity.UserStory;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.stream.Collectors;


@Component
public class SprintMapper {

    private final UserStoryMapper userStoryMapper;

    public SprintMapper(UserStoryMapper userStoryMapper) {
        this.userStoryMapper = userStoryMapper;
    }

    public SprintDTO toDto(Sprint sprint) {
        if (sprint == null) {
            return null;
        }

        List<TaskDTO> taskDTOs = (sprint.getTasks() == null) ? Collections.emptyList() :
                sprint.getTasks().stream()
                        .map(this::mapTaskToDto)
                        .collect(Collectors.toList());

        List<UserStoryDTO> userStoryDTOs = (sprint.getUserStories() == null) ? Collections.emptyList() :
                sprint.getUserStories().stream()
                        .map(userStoryMapper::toDto)
                        .collect(Collectors.toList());

        return SprintDTO.builder()
                .id(sprint.getId())
                .name(sprint.getName())
                .startDate(sprint.getStartDate())
                .endDate(sprint.getEndDate())
                .status(sprint.getStatus())
                .tasks(taskDTOs)
                .userStories(userStoryDTOs)
                .build();
    }


    public Sprint toEntity(SprintDTO dto) {
        if (dto == null) {
            return null;
        }

        return Sprint.builder()
                .id(dto.getId())
                .name(dto.getName())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .status(dto.getStatus())
                .build();
    }

    private TaskDTO mapTaskToDto(Task task) {
        return TaskDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .build();
    }
}