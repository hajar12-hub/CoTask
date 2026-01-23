package com.CoTask.service;

import com.CoTask.dto.SprintStatsDTO;

public interface ReportingService {
    SprintStatsDTO getSprintStats(Long sprintId);
}
