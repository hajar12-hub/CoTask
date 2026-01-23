package com.CoTask.strategy;

import com.CoTask.dto.UserStoryDTO;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ValueEffortStrategy implements PrioritizationStrategy {

    @Override
    public List<UserStoryDTO> prioritize(List<UserStoryDTO> userStories) {
        return userStories.stream()
                .sorted(Comparator.comparing(
                        this::calculateValueEffortRatio,
                        Comparator.nullsLast(Comparator.reverseOrder())
                ))
                .collect(Collectors.toList());
    }

    private Double calculateValueEffortRatio(UserStoryDTO userStory) {
        // Ratio = Valeur métier / Effort (Story Points)
        // Plus le ratio est élevé, plus c'est prioritaire

        Integer storyPoints = userStory.getStoryPoints();
        if (storyPoints == null || storyPoints == 0) {
            return null;
        }

        // La valeur métier est basée sur la priorité MoSCoW
        double businessValue = getBusinessValueFromPriority(userStory.getPriority());

        return businessValue / storyPoints;
    }

    private double getBusinessValueFromPriority(com.CoTask.entity.emuns.PriorityMoSCoW priority) {
        if (priority == null) {
            return 1.0;
        }
        return switch (priority) {
            case MUST -> 10.0;
            case SHOULD -> 7.0;
            case COULD -> 4.0;
            case WONT -> 1.0;
        };
    }

    @Override
    public String getStrategyName() {
        return "Value vs Effort";
    }
}