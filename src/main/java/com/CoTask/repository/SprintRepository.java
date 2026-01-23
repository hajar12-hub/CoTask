package com.CoTask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.CoTask.entity.Sprint;


public interface SprintRepository extends JpaRepository<Sprint, Long> {
}
