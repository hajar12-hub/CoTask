package com.CoTask.controller;

import com.CoTask.dto.SprintDTO;
import com.CoTask.service.SprintService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sprints")
@RequiredArgsConstructor
public class SprintController {

    private final SprintService sprintService;


    @PostMapping
    public ResponseEntity<SprintDTO> createSprint(@RequestBody SprintDTO sprintDTO) {
        SprintDTO createdSprint = sprintService.createSprint(sprintDTO);
        return new ResponseEntity<>(createdSprint, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<SprintDTO> updateSprint(@PathVariable Long id, @RequestBody SprintDTO sprintDTO) {
        SprintDTO updatedSprint = sprintService.updateSprint(id, sprintDTO);
        return ResponseEntity.ok(updatedSprint);
    }


    @GetMapping("/{id}")
    public ResponseEntity<SprintDTO> getSprintById(@PathVariable Long id) {
        SprintDTO sprintDTO = sprintService.getSprintById(id);
        return ResponseEntity.ok(sprintDTO);
    }


    @GetMapping
    public ResponseEntity<List<SprintDTO>> getAllSprints() {
        List<SprintDTO> sprints = sprintService.getAllSprints();
        return ResponseEntity.ok(sprints);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSprint(@PathVariable Long id) {
        sprintService.deleteSprint(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{sprintId}/tasks/{taskId}")
    public ResponseEntity<Void> addTaskToSprint(@PathVariable Long sprintId, @PathVariable Long taskId) {
        sprintService.addTaskToSprint(sprintId, taskId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{sprintId}/user-stories/{userStoryId}")
    public ResponseEntity<Void> addUserStoryToSprint(
            @PathVariable Long sprintId,
            @PathVariable Long userStoryId) {
        sprintService.addUserStoryToSprint(sprintId, userStoryId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{sprintId}/user-stories/{userStoryId}")
    public ResponseEntity<Void> removeUserStoryFromSprint(
            @PathVariable Long sprintId,
            @PathVariable Long userStoryId) {
        sprintService.removeUserStoryFromSprint(sprintId, userStoryId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{sprintId}/user-stories")
    public ResponseEntity<List<com.CoTask.dto.UserStoryDTO>> getUserStoriesBySprint(
            @PathVariable Long sprintId) {
        List<com.CoTask.dto.UserStoryDTO> userStories = sprintService.getUserStoriesBySprint(sprintId);
        return ResponseEntity.ok(userStories);
    }

    @PostMapping("/{sprintId}/user-stories/select")
    public ResponseEntity<List<com.CoTask.dto.UserStoryDTO>> selectUserStoriesFromProductBacklog(
            @PathVariable Long sprintId,
            @RequestParam Long productBacklogId,
            @RequestBody List<Long> userStoryIds) {
        List<com.CoTask.dto.UserStoryDTO> userStories = sprintService.selectUserStoriesFromProductBacklog(
                sprintId, productBacklogId, userStoryIds);
        return ResponseEntity.ok(userStories);
    }
}