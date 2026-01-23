package com.CoTask.strategy;

import com.CoTask.dto.UserStoryDTO;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WsjfStrategy implements PrioritizationStrategy {

    @Override
    public List<UserStoryDTO> prioritize(List<UserStoryDTO> userStories) {
        return userStories.stream()
                .sorted(Comparator.comparing(
                        this::calculateWSJF,
                        Comparator.nullsLast(Comparator.reverseOrder())
                ))
                .collect(Collectors.toList());
    }

    /**
     * WSJF = (Valeur Métier + Urgence + Réduction de Risque) / Durée
     * Pour simplifier, on utilise:
     * - Valeur Métier basée sur la priorité MoSCoW
     * - Urgence basée sur le statut (TODO = moins urgent, IN_PROGRESS = urgent)
     * - Réduction de Risque = constante pour l'instant
     * - Durée = Story Points
     */
    private Double calculateWSJF(UserStoryDTO userStory) {
        Integer storyPoints = userStory.getStoryPoints();
        if (storyPoints == null || storyPoints == 0) {
            return null;
        }

        double businessValue = getBusinessValueFromPriority(userStory.getPriority());
        double urgency = getUrgencyFromStatus(userStory.getStatus());
        double riskReduction = 1.0; // Constante pour simplifier

        double numerator = businessValue + urgency + riskReduction;

        return numerator / storyPoints;
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

    private double getUrgencyFromStatus(com.CoTask.entity.emuns.UserStoryStatus status) {
        if (status == null) {
            return 1.0;
        }
        return switch (status) {
            case TODO -> 1.0;
            case IN_PROGRESS -> 5.0;
            case DONE -> 0.0;
        };
    }

    @Override
    public String getStrategyName() {
        return "WSJF (Weighted Shortest Job First)";
    }
}