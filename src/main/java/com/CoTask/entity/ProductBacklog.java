package com.CoTask.entity;

import lombok.*;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "product_backlogs")
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductBacklog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "productBacklog", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Epic> epics;

    @OneToMany(mappedBy = "productBacklog", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<UserStory> userStories;
}