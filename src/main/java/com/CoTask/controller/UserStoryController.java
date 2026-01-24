package com.CoTask.controller;

import com.CoTask.dto.UserStoryDTO;
import com.CoTask.service.UserStoryService;
import jakarta.validation.Valid;
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
    public ResponseEntity<UserStoryDTO> createUserStory(@Valid @RequestBody UserStoryDTO userStoryDTO) {
        UserStoryDTO createdUserStory = userStoryService.createUserStory(userStoryDTO);
        return new ResponseEntity<>(createdUserStory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserStoryDTO> updateUserStory(
            @PathVariable Long id,
            @RequestBody UserStoryDTO userStoryDTO) {

        UserStoryDTO updatedUserStory = userStoryService.updateUserStory(id, userStoryDTO);
        return ResponseEntity.ok(updatedUserStory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserStoryDTO> getUserStoryById(@PathVariable Long id) {
        return ResponseEntity.ok(userStoryService.getUserStoryById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserStoryDTO>> getAllUserStories() {
        return ResponseEntity.ok(userStoryService.getAllUserStories());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserStory(@PathVariable Long id) {
        userStoryService.deleteUserStory(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userStoryId}/assign/{userId}")
    public ResponseEntity<UserStoryDTO> assignUserToUserStory(
            @PathVariable Long userStoryId,
            @PathVariable Long userId) {

        UserStoryDTO updated = userStoryService.assignUserToUserStory(userStoryId, userId);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/epic/{epicId}")
    public ResponseEntity<List<UserStoryDTO>> getUserStoriesByEpic(@PathVariable Long epicId) {
        return ResponseEntity.ok(userStoryService.getUserStoriesByEpic(epicId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<UserStoryDTO>> getUserStoriesByStatus(@PathVariable String status) {
        return ResponseEntity.ok(userStoryService.getUserStoriesByStatus(status));
    }
}
