package com.CoTask.service.impl;

import com.CoTask.dto.TaskDTO;
import com.CoTask.entity.Sprint;
import com.CoTask.entity.Task;
import com.CoTask.entity.emuns.TaskStatus;
import com.CoTask.mapper.TaskMapper;
import com.CoTask.repository.SprintRepository;
import com.CoTask.repository.TaskRepository;
import com.CoTask.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final SprintRepository sprintRepository;
    private final TaskMapper taskMapper; // <--- C'est cette variable qu'on doit utiliser !

    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {

        Task task = taskMapper.toEntity(taskDTO);

        if (taskDTO.getSprintId() != null) {
            Sprint sprint = sprintRepository.findById(taskDTO.getSprintId())
                    .orElseThrow(() -> new RuntimeException("Sprint non trouvé"));
            task.setSprint(sprint);
        }
        if (task.getStatus() == null) {
            task.setStatus(TaskStatus.TODO);}
        Task savedTask = taskRepository.save(task);
        return taskMapper.toDTO(savedTask);
    }

    @Override
    public TaskDTO updateTaskStatus(Long id, String statusString) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tâche introuvable"));

        try {
            TaskStatus newStatus = TaskStatus.valueOf(statusString);
            task.setStatus(newStatus);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Statut invalide. Valeurs : TO_DO, IN_PROGRESS, DONE");
        }

        Task updatedTask = taskRepository.save(task);
        return taskMapper.toDTO(updatedTask);
    }

    @Override
    public List<TaskDTO> getTasksBySprint(Long sprintId) {
        return taskRepository.findBySprintId(sprintId).stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Tâche introuvable");
        }
        taskRepository.deleteById(id);
    }
    @Override
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(taskMapper::toDTO)
                .toList();
    }

    }

