package com.CoTask.service;

import com.CoTask.dto.TaskDTO;

import java.util.List;

public interface TaskService {
    TaskDTO createTask(TaskDTO taskDTO);
    TaskDTO updateTaskStatus(Long id, String newStatus);
    List<TaskDTO> getTasksBySprint(Long sprintId);
    void deleteTask(Long id);
    List<TaskDTO> getAllTasks();
}

