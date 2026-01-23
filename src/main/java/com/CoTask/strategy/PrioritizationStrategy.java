package com.CoTask.strategy;

import com.CoTask.dto.UserStoryDTO;
import java.util.List;

public interface PrioritizationStrategy {
    List<UserStoryDTO> prioritize(List<UserStoryDTO> userStories);
    String getStrategyName();
}