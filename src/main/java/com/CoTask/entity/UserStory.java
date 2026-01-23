package com.CoTask.entity;

import com.CoTask.entity.emuns.PriorityMoSCoW;
import com.CoTask.entity.emuns.UserStoryStatus;
import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "user_stories")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserStory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private UserStoryStatus status;

    @Enumerated(EnumType.STRING)
    private PriorityMoSCoW priority;

    private String acceptanceCriteria;

    private Integer storyPoints;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "epic_id")
    private Epic epic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_backlog_id")
    private ProductBacklog productBacklog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sprint_id")
    private Sprint sprint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_user_id")
    private User assignedUser;
}