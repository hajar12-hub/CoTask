package com.CoTask.mapper;

import com.CoTask.dto.ProductBacklogDTO;
import com.CoTask.entity.Epic;
import com.CoTask.entity.ProductBacklog;
import com.CoTask.entity.UserStory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static com.CoTask.dto.ProductBacklogDTO.*;

@Component
public class ProductBacklogMapper {

    public ProductBacklogDTO toDto(ProductBacklog backlog) {
        if (backlog == null) {
            return null;
        }

        List<Long> epicIds = (backlog.getEpics() == null)
                ? Collections.emptyList()
                : backlog.getEpics()
                .stream()
                .map(Epic::getId)
                .toList();

        List<Long> userStoryIds = (backlog.getUserStories() == null)
                ? Collections.emptyList()
                : backlog.getUserStories()
                .stream()
                .map(UserStory::getId)
                .toList();

        return builder()
                .id(backlog.getId())
                .name(backlog.getName())
                .epicIds(epicIds)
                .userStoryIds(userStoryIds)
                .build();
    }


    public ProductBacklog toEntity(ProductBacklogDTO dto) {
        if (dto == null) {
            return null;
        }

        return ProductBacklog.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }
}