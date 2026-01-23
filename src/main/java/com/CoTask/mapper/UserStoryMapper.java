package com.CoTask.mapper;

import com.CoTask.dto.UserStoryDTO;
import com.CoTask.entity.UserStory;
import org.springframework.stereotype.Component;


@Component
public class UserStoryMapper {

    public UserStoryDTO toDto(UserStory userStory) {
        if (userStory == null) {
            return null;
        }

        return UserStoryDTO.builder()
                .id(userStory.getId())
                .title(userStory.getTitle())
                .description(userStory.getDescription())
                .status(userStory.getStatus())
                .priority(userStory.getPriority())
                .acceptanceCriteria(userStory.getAcceptanceCriteria())
                .storyPoints(userStory.getStoryPoints())
                .epicId(userStory.getEpic() != null ? userStory.getEpic().getId() : null)
                .productBacklogId(userStory.getProductBacklog() != null ? userStory.getProductBacklog().getId() : null)
                .sprintId(userStory.getSprint() != null ? userStory.getSprint().getId() : null)
                .assignedUserId(userStory.getAssignedUser() != null ? userStory.getAssignedUser().getId() : null)
                .build();
    }

    public UserStory toEntity(UserStoryDTO dto) {
        if (dto == null) {
            return null;
        }

        return UserStory.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .priority(dto.getPriority())
                .acceptanceCriteria(dto.getAcceptanceCriteria())
                .storyPoints(dto.getStoryPoints())
                .build();
    }


}