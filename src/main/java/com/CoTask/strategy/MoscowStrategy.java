package com.CoTask.strategy;

import com.CoTask.dto.UserStoryDTO;
import com.CoTask.entity.emuns.PriorityMoSCoW;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MoscowStrategy implements PrioritizationStrategy {

    @Override
    public List<UserStoryDTO> prioritize(List<UserStoryDTO> userStories) {
        return userStories.stream()
                .sorted(Comparator.comparing(
                        (UserStoryDTO us) -> getPriorityOrder(us.getPriority()),
                        Comparator.nullsLast(Comparator.naturalOrder())
                ))
                .collect(Collectors.toList());
    }

    private int getPriorityOrder(PriorityMoSCoW priority) {
        if (priority == null) {
            return Integer.MAX_VALUE;
        }
        return switch (priority) {
            case MUST -> 1;
            case SHOULD -> 2;
            case COULD -> 3;
            case WONT -> 4;
        };
    }

    @Override
    public String getStrategyName() {
        return "MoSCoW";
    }
}