package com.CoTask.controller;

import com.CoTask.dto.UserStoryDTO;
import com.CoTask.service.UserStoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userstories")
@RequiredArgsConstructor
public class UserStoryController {

    private final UserStoryService userStoryService;

    @PostMapping
    public ResponseEntity<UserStoryDTO> createUserStory(@RequestBody UserStoryDTO userStoryDTO) {
        UserStoryDTO createdUserStory = userStoryService.createUserStory(userStoryDTO);
        return new ResponseEntity<>(createdUserStory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserStoryDTO> updateUserStory(@PathVariable Long id, @RequestBody UserStoryDTO userStoryDTO) {
        UserStoryDTO updatedUserStory = userStoryService.updateUserStory(id, userStoryDTO);
        return ResponseEntity.ok(updatedUserStory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserStoryDTO> getUserStoryById(@PathVariable Long id) {
        UserStoryDTO userStoryDTO = userStoryService.getUserStoryById(id);
        return ResponseEntity.ok(userStoryDTO);
    }

    @GetMapping
    public ResponseEntity<List<UserStoryDTO>> getAllUserStories() {
        List<UserStoryDTO> userStories = userStoryService.getAllUserStories();
        return ResponseEntity.ok(userStories);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserStory(@PathVariable Long id) {
        userStoryService.deleteUserStory(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/product-backlog/{productBacklogId}")
    public ResponseEntity<UserStoryDTO> addUserStoryToProductBacklog(
            @PathVariable Long id,
            @PathVariable Long productBacklogId) {
        UserStoryDTO userStory = userStoryService.addUserStoryToProductBacklog(id, productBacklogId);
        return ResponseEntity.ok(userStory);
    }

    @PostMapping("/{id}/sprint/{sprintId}")
    public ResponseEntity<UserStoryDTO> addUserStoryToSprint(
            @PathVariable Long id,
            @PathVariable Long sprintId) {
        UserStoryDTO userStory = userStoryService.addUserStoryToSprint(id, sprintId);
        return ResponseEntity.ok(userStory);
    }

    @DeleteMapping("/{id}/sprint")
    public ResponseEntity<UserStoryDTO> removeUserStoryFromSprint(@PathVariable Long id) {
        UserStoryDTO userStory = userStoryService.removeUserStoryFromSprint(id);
        return ResponseEntity.ok(userStory);
    }

    @GetMapping("/epic/{epicId}")
    public ResponseEntity<List<UserStoryDTO>> getUserStoriesByEpic(@PathVariable Long epicId) {
        List<UserStoryDTO> userStories = userStoryService.getUserStoriesByEpic(epicId);
        return ResponseEntity.ok(userStories);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<UserStoryDTO>> getUserStoriesByStatus(@PathVariable String status) {
        List<UserStoryDTO> userStories = userStoryService.getUserStoriesByStatus(status);
        return ResponseEntity.ok(userStories);
    }
}