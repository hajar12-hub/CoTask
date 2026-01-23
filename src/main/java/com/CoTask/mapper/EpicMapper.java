package com.CoTask.mapper;

import com.CoTask.dto.EpicDTO;
import com.CoTask.entity.Epic;
import org.springframework.stereotype.Component;

@Component
public class EpicMapper {

    public EpicDTO toDto(Epic epic) {
        if (epic == null) {
            return null;
        }

        Long productBacklogId = (epic.getProductBacklog() != null) ?
                epic.getProductBacklog().getId() : null;

        return EpicDTO.builder()
                .id(epic.getId())
                .title(epic.getTitle())
                .description(epic.getDescription())
                .productBacklogId(productBacklogId)
                .build();
    }

    public Epic toEntity(EpicDTO dto) {
        if (dto == null) {
            return null;
        }

        return Epic.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .build();
    }
}