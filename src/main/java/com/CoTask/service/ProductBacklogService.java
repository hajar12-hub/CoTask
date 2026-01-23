package com.CoTask.service;

import com.CoTask.dto.ProductBacklogDTO;
import com.CoTask.dto.UserStoryDTO;
import java.util.List;

public interface ProductBacklogService {
    ProductBacklogDTO createProductBacklog(ProductBacklogDTO dto);
    ProductBacklogDTO updateProductBacklog(Long id, ProductBacklogDTO dto);
    List<ProductBacklogDTO> getAllProductBacklogs();
    ProductBacklogDTO getProductBacklogById(Long id);
    void deleteProductBacklog(Long id);

    // Gestion des UserStories dans le ProductBacklog
    void addUserStoryToProductBacklog(Long productBacklogId, Long userStoryId);
    void removeUserStoryFromProductBacklog(Long productBacklogId, Long userStoryId);
    List<UserStoryDTO> getUserStoriesByProductBacklog(Long productBacklogId);
    List<UserStoryDTO> getUserStoriesByProductBacklogOrderedByPriority(Long productBacklogId);

    // Priorisation
    void prioritizeUserStory(Long userStoryId, String priorityStrategy);
}