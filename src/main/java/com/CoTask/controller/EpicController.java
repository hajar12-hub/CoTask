package com.CoTask.controller;

import com.CoTask.dto.EpicDTO;
import com.CoTask.service.EpicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/epics")
@RequiredArgsConstructor
public class EpicController {

    private final EpicService epicService;
    @PostMapping
    public ResponseEntity<EpicDTO> createEpic(@Valid @RequestBody EpicDTO epicDTO) {
        EpicDTO createdEpic = epicService.createEpic(epicDTO);
        return new ResponseEntity<>(createdEpic, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EpicDTO> updateEpic(@PathVariable Long id, @RequestBody EpicDTO epicDTO) {
        EpicDTO updatedEpic = epicService.updateEpic(id, epicDTO);
        return ResponseEntity.ok(updatedEpic);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EpicDTO> getEpicById(@PathVariable Long id) {
        EpicDTO epicDTO = epicService.getEpicById(id);
        return ResponseEntity.ok(epicDTO);
    }

    @GetMapping
    public ResponseEntity<List<EpicDTO>> getAllEpics() {
        List<EpicDTO> epics = epicService.getAllEpics();
        return ResponseEntity.ok(epics);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEpic(@PathVariable Long id) {
        epicService.deleteEpic(id);
        return ResponseEntity.noContent().build();
    }
}