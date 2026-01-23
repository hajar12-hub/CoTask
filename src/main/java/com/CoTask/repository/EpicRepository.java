package com.CoTask.repository;
import com.CoTask.entity.Epic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpicRepository extends JpaRepository<Epic, Long> {}
