package com.CoTask.entity;

import lombok.*;
import jakarta.persistence.*;
import java.util.List;


@Entity
@Table(name = "epics")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Epic {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_backlog_id")
    private ProductBacklog productBacklog;

    @OneToMany(mappedBy = "epic", cascade = CascadeType.ALL)
    private List<UserStory> userStories;
}
