package com.CoTask.dto;

import com.CoTask.entity.emuns.UserStoryStatus;
import com.CoTask.entity.emuns.PriorityMoSCoW;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserStoryDTO {
    private Long id;
    private String title;
    private String description;
    private UserStoryStatus status;
    private PriorityMoSCoW priority;
    private String acceptanceCriteria;
    private Integer storyPoints;
    private Long epicId;
    private Long productBacklogId;
    private Long sprintId;
    private Long assignedUserId;
}