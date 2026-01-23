package com.CoTask.controller;

import com.CoTask.dto.SprintStatsDTO;
import com.CoTask.service.ReportingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reporting")
@RequiredArgsConstructor
public class ReportingController {

    private final ReportingService reportingService;


    @GetMapping("/sprints/{sprintId}")
    public ResponseEntity<SprintStatsDTO> getSprintStats(@PathVariable Long sprintId) {
        SprintStatsDTO stats = reportingService.getSprintStats(sprintId);
        return ResponseEntity.ok(stats);
    }
}