package com.CoTask.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class ProductBacklogDTO {
    private Long id;
    private String name;
    private List<Long> epicIds;
    private List<Long> userStoryIds;
}
