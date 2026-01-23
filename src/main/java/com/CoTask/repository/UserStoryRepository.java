package com.CoTask.repository;

import com.CoTask.entity.UserStory;
import com.CoTask.entity.emuns.UserStoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserStoryRepository extends JpaRepository<UserStory, Long> {
    List<UserStory> findByAssignedUserId(Long userId);

    List<UserStory> findByEpic_Id(Long epicId);

    List<UserStory> findByStatus(UserStoryStatus status);

    List<UserStory> findByProductBacklog_Id(Long productBacklogId);

    List<UserStory> findByProductBacklog_IdOrderByPriorityAsc(Long productBacklogId);

    List<UserStory> findBySprint_Id(Long sprintId);
}