package com.CoTask.dto;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EpicDTO {
    private Long id;
    private String title;
    private String description;
    private Long productBacklogId; // id pour éviter d’envoyer tout le ProductBacklog
}

