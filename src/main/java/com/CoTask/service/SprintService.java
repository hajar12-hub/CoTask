package com.CoTask.service;

import com.CoTask.dto.SprintDTO;
import com.CoTask.dto.UserStoryDTO;

import java.util.List;

public interface SprintService {
    SprintDTO createSprint(SprintDTO sprintDTO);
    SprintDTO updateSprint(Long id, SprintDTO springDTO);
    SprintDTO getSprintById(Long id);
    List<SprintDTO> getAllSprints();
    void deleteSprint(Long id);

    void addTaskToSprint(Long sprintId, Long taskId);

    // Gestion des UserStories dans le Sprint
    void addUserStoryToSprint(Long sprintId, Long userStoryId);
    void removeUserStoryFromSprint(Long sprintId, Long userStoryId);
    List<UserStoryDTO> getUserStoriesBySprint(Long sprintId);
    List<UserStoryDTO> selectUserStoriesFromProductBacklog(Long sprintId, Long productBacklogId, List<Long> userStoryIds);
}
