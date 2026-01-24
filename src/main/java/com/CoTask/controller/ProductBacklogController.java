package com.CoTask.controller;

import com.CoTask.dto.ProductBacklogDTO;
import com.CoTask.service.ProductBacklogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-backlogs")
@RequiredArgsConstructor
public class ProductBacklogController {

    private final ProductBacklogService backlogService;

    @PostMapping
    public ResponseEntity<ProductBacklogDTO> createBacklog(@Valid @RequestBody ProductBacklogDTO dto) {
        ProductBacklogDTO created = backlogService.createProductBacklog(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductBacklogDTO> updateBacklog(@PathVariable Long id, @RequestBody ProductBacklogDTO dto) {
        ProductBacklogDTO updated = backlogService.updateProductBacklog(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductBacklogDTO> getBacklogById(@PathVariable Long id) {
        ProductBacklogDTO dto = backlogService.getProductBacklogById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<ProductBacklogDTO>> getAllBacklogs() {
        List<ProductBacklogDTO> list = backlogService.getAllProductBacklogs();
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBacklog(@PathVariable Long id) {
        backlogService.deleteProductBacklog(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/user-stories/{userStoryId}")
    public ResponseEntity<Void> addUserStoryToProductBacklog(
            @PathVariable Long id,
            @PathVariable Long userStoryId) {
        backlogService.addUserStoryToProductBacklog(id, userStoryId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/user-stories/{userStoryId}")
    public ResponseEntity<Void> removeUserStoryFromProductBacklog(
            @PathVariable Long id,
            @PathVariable Long userStoryId) {
        backlogService.removeUserStoryFromProductBacklog(id, userStoryId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/user-stories")
    public ResponseEntity<List<com.CoTask.dto.UserStoryDTO>> getUserStoriesByProductBacklog(
            @PathVariable Long id) {
        List<com.CoTask.dto.UserStoryDTO> userStories = backlogService.getUserStoriesByProductBacklog(id);
        return ResponseEntity.ok(userStories);
    }

    @GetMapping("/{id}/user-stories/prioritized")
    public ResponseEntity<List<com.CoTask.dto.UserStoryDTO>> getUserStoriesByProductBacklogOrderedByPriority(
            @PathVariable Long id) {
        List<com.CoTask.dto.UserStoryDTO> userStories = backlogService.getUserStoriesByProductBacklogOrderedByPriority(id);
        return ResponseEntity.ok(userStories);
    }
}