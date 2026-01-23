package com.CoTask.service.impl;

import com.CoTask.dto.SprintDTO;
import com.CoTask.dto.UserStoryDTO;
import com.CoTask.entity.ProductBacklog;
import com.CoTask.entity.Sprint;
import com.CoTask.entity.Task;
import com.CoTask.entity.UserStory;
import com.CoTask.exception.ResourceNotFoundException;
import com.CoTask.mapper.SprintMapper;
import com.CoTask.mapper.UserStoryMapper;
import com.CoTask.repository.ProductBacklogRepository;
import com.CoTask.repository.SprintRepository;
import com.CoTask.repository.TaskRepository;
import com.CoTask.repository.UserStoryRepository;
import com.CoTask.service.SprintService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SprintServiceImpl implements SprintService {

    private final SprintRepository sprintRepository;
    private final SprintMapper sprintMapper;
    private final TaskRepository taskRepository;
    private final UserStoryRepository userStoryRepository;
    private final UserStoryMapper userStoryMapper;
    private final ProductBacklogRepository productBacklogRepository;

    @Override
    public SprintDTO createSprint(SprintDTO sprintDTO) {
        validateDates(sprintDTO);
        Sprint sprint = sprintMapper.toEntity(sprintDTO);
        Sprint savedSprint = sprintRepository.save(sprint);
        return sprintMapper.toDto(savedSprint);
    }

    @Override
    public SprintDTO updateSprint(Long id, SprintDTO sprintDTO) {
        Sprint existingSprint = sprintRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sprint non trouvé avec l'ID : " + id));

        validateDates(sprintDTO);

        existingSprint.setName(sprintDTO.getName());
        existingSprint.setStartDate(sprintDTO.getStartDate());
        existingSprint.setEndDate(sprintDTO.getEndDate());
        existingSprint.setStatus(sprintDTO.getStatus());

        Sprint updatedSprint = sprintRepository.save(existingSprint);
        return sprintMapper.toDto(updatedSprint);
    }

    @Override
    public List<SprintDTO> getAllSprints() {
        return sprintRepository.findAll().stream()
                .map(sprintMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SprintDTO getSprintById(Long id) {
        return sprintRepository.findById(id)
                .map(sprintMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Sprint non trouvé avec l'ID : " + id));
    }

    @Override
    public void deleteSprint(Long id) {
        if (!sprintRepository.existsById(id)) {
            throw new ResourceNotFoundException("Sprint non trouvé avec l'ID : " + id);
        }
        sprintRepository.deleteById(id);
    }


    private void validateDates(SprintDTO dto) {
        if (dto.getStartDate() != null && dto.getEndDate() != null) {
            if (dto.getEndDate().isBefore(dto.getStartDate())) {
                throw new IllegalArgumentException("La date de fin doit être après la date de début !");
            }
        }
    }
    @Override
    public void addTaskToSprint(Long sprintId, Long taskId) {
        Sprint sprint = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new ResourceNotFoundException("Sprint non trouvé avec l'ID : " + sprintId));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task non trouvé avec l'ID : " + taskId));

        task.setSprint(sprint);

        taskRepository.save(task);
    }

    @Override
    public void addUserStoryToSprint(Long sprintId, Long userStoryId) {
        Sprint sprint = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new ResourceNotFoundException("Sprint non trouvé avec l'ID : " + sprintId));

        UserStory userStory = userStoryRepository.findById(userStoryId)
                .orElseThrow(() -> new ResourceNotFoundException("UserStory non trouvé avec l'ID : " + userStoryId));

        userStory.setSprint(sprint);
        userStoryRepository.save(userStory);
    }

    @Override
    public void removeUserStoryFromSprint(Long sprintId, Long userStoryId) {
        UserStory userStory = userStoryRepository.findById(userStoryId)
                .orElseThrow(() -> new ResourceNotFoundException("UserStory non trouvé avec l'ID : " + userStoryId));

        if (userStory.getSprint() == null || !userStory.getSprint().getId().equals(sprintId)) {
            throw new ResourceNotFoundException("La UserStory n'est pas associée à ce Sprint");
        }

        userStory.setSprint(null);
        userStoryRepository.save(userStory);
    }

    @Override
    public List<UserStoryDTO> getUserStoriesBySprint(Long sprintId) {
        if (!sprintRepository.existsById(sprintId)) {
            throw new ResourceNotFoundException("Sprint non trouvé avec l'ID : " + sprintId);
        }

        return userStoryRepository.findBySprint_Id(sprintId).stream()
                .map(userStoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserStoryDTO> selectUserStoriesFromProductBacklog(Long sprintId, Long productBacklogId, List<Long> userStoryIds) {
        Sprint sprint = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new ResourceNotFoundException("Sprint non trouvé avec l'ID : " + sprintId));

        ProductBacklog productBacklog = productBacklogRepository.findById(productBacklogId)
                .orElseThrow(() -> new ResourceNotFoundException("ProductBacklog non trouvé avec l'ID : " + productBacklogId));

        List<UserStory> userStories = userStoryRepository.findAllById(userStoryIds);

        for (UserStory userStory : userStories) {
            if (userStory.getProductBacklog() == null || !userStory.getProductBacklog().getId().equals(productBacklogId)) {
                throw new ResourceNotFoundException("La UserStory avec l'ID " + userStory.getId() + " n'appartient pas au ProductBacklog");
            }
            userStory.setSprint(sprint);
        }

        userStoryRepository.saveAll(userStories);

        return userStories.stream()
                .map(userStoryMapper::toDto)
                .collect(Collectors.toList());
    }
}