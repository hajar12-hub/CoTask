package com.CoTask.service;
import com.CoTask.dto.UserStoryDTO;

import java.util.List;


public interface UserStoryService {
    UserStoryDTO createUserStory(UserStoryDTO userStoryDTO);
    UserStoryDTO updateUserStory(Long id, UserStoryDTO userStoryDTO);
    List<UserStoryDTO> getAllUserStories();
    UserStoryDTO getUserStoryById(Long id);
    void deleteUserStory(Long id);
    UserStoryDTO assignUserToUserStory(Long userStoryId, Long userId);
    List<UserStoryDTO> getUserStoriesByAssignedUser(Long userId);

    // Gestion des relations avec ProductBacklog et Sprint
    UserStoryDTO addUserStoryToProductBacklog(Long userStoryId, Long productBacklogId);
    UserStoryDTO addUserStoryToSprint(Long userStoryId, Long sprintId);
    UserStoryDTO removeUserStoryFromSprint(Long userStoryId);
    List<UserStoryDTO> getUserStoriesByEpic(Long epicId);
    List<UserStoryDTO> getUserStoriesByStatus(String status);
}