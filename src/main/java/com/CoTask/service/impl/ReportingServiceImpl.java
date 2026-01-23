package com.CoTask.service.impl;

import com.CoTask.dto.SprintStatsDTO;
import com.CoTask.entity.Sprint;
import com.CoTask.entity.Task;
import com.CoTask.entity.UserStory;
import com.CoTask.entity.emuns.TaskStatus;
import com.CoTask.entity.emuns.UserStoryStatus;
import com.CoTask.exception.ResourceNotFoundException;
import com.CoTask.repository.SprintRepository;
import com.CoTask.service.ReportingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportingServiceImpl implements ReportingService {

    private final SprintRepository sprintRepository;

    @Override
    public SprintStatsDTO getSprintStats(Long sprintId) {
        Sprint sprint = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new ResourceNotFoundException("Sprint non trouvé avec l'ID : " + sprintId));

        List<UserStory> userStories = sprint.getUserStories() != null ? sprint.getUserStories() : List.of();
        List<Task> tasks = sprint.getTasks() != null ? sprint.getTasks() : List.of();

        // Statistiques UserStories
        int totalUserStories = userStories.size();
        long done = userStories.stream().filter(us -> us.getStatus() == UserStoryStatus.DONE).count();
        long inProgress = userStories.stream().filter(us -> us.getStatus() == UserStoryStatus.IN_PROGRESS).count();
        long todo = userStories.stream().filter(us -> us.getStatus() == UserStoryStatus.TODO).count();
        double completionRate = totalUserStories > 0 ? (double) done / totalUserStories * 100 : 0.0;

        // Statistiques Tasks
        int totalTasks = tasks.size();
        long tasksDone = tasks.stream().filter(t -> t.getStatus() == TaskStatus.DONE).count();
        long tasksInProgress = tasks.stream().filter(t -> t.getStatus() == TaskStatus.DOING).count();
        long tasksTodo = tasks.stream().filter(t -> t.getStatus() == TaskStatus.TODO).count();
        double tasksCompletionRate = totalTasks > 0 ? (double) tasksDone / totalTasks * 100 : 0.0;

        // Story Points
        Integer totalStoryPoints = userStories.stream()
                .map(UserStory::getStoryPoints)
                .filter(sp -> sp != null)
                .reduce(0, Integer::sum);

        Integer completedStoryPoints = userStories.stream()
                .filter(us -> us.getStatus() == UserStoryStatus.DONE)
                .map(UserStory::getStoryPoints)
                .filter(sp -> sp != null)
                .reduce(0, Integer::sum);

        return SprintStatsDTO.builder()
                .sprintId(sprint.getId())
                .sprintName(sprint.getName())
                .totalUserStories(totalUserStories)
                .done((int) done)
                .inProgress((int) inProgress)
                .todo((int) todo)
                .completionRate(completionRate)
                .totalTasks(totalTasks)
                .tasksDone((int) tasksDone)
                .tasksInProgress((int) tasksInProgress)
                .tasksTodo((int) tasksTodo)
                .tasksCompletionRate(tasksCompletionRate)
                .totalStoryPoints(totalStoryPoints)
                .completedStoryPoints(completedStoryPoints)
                .build();
    }
}