package com.CoTask.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SprintStatsDTO {
    private Long sprintId;
    private String sprintName;
    private int totalUserStories;
    private int done;
    private int inProgress;
    private int todo;
    private double completionRate;
    private int totalTasks;
    private int tasksDone;
    private int tasksInProgress;
    private int tasksTodo;
    private double tasksCompletionRate;
    private Integer totalStoryPoints;
    private Integer completedStoryPoints;
}