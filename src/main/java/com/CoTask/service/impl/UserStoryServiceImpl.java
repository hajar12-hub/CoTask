package com.CoTask.service.impl;

import com.CoTask.dto.UserStoryDTO;
import com.CoTask.entity.Epic;
import com.CoTask.entity.ProductBacklog;
import com.CoTask.entity.Sprint;
import com.CoTask.entity.User;
import com.CoTask.entity.UserStory;
import com.CoTask.entity.emuns.UserStoryStatus;
import com.CoTask.exception.ResourceNotFoundException;
import com.CoTask.mapper.UserStoryMapper;
import com.CoTask.repository.EpicRepository;
import com.CoTask.repository.ProductBacklogRepository;
import com.CoTask.repository.SprintRepository;
import com.CoTask.repository.UserRepository;
import com.CoTask.repository.UserStoryRepository;
import com.CoTask.service.UserStoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserStoryServiceImpl implements UserStoryService {

    private final UserStoryRepository userStoryRepository;
    private final UserStoryMapper userStoryMapper;
    private final UserRepository userRepository;
    private final ProductBacklogRepository productBacklogRepository;
    private final SprintRepository sprintRepository;
    private final EpicRepository epicRepository;

    @Override
    public UserStoryDTO createUserStory(UserStoryDTO userStoryDTO) {
        validateUserStory(userStoryDTO);
        UserStory userStory = userStoryMapper.toEntity(userStoryDTO);

        if (userStoryDTO.getEpicId() != null) {
            Epic epic = epicRepository.findById(userStoryDTO.getEpicId())
                    .orElseThrow(() -> new ResourceNotFoundException("Epic non trouvé avec l'ID : " + userStoryDTO.getEpicId()));
            userStory.setEpic(epic);
        }

        if (userStoryDTO.getProductBacklogId() != null) {
            ProductBacklog productBacklog = productBacklogRepository.findById(userStoryDTO.getProductBacklogId())
                    .orElseThrow(() -> new ResourceNotFoundException("ProductBacklog non trouvé avec l'ID : " + userStoryDTO.getProductBacklogId()));
            userStory.setProductBacklog(productBacklog);
        }

        if (userStoryDTO.getSprintId() != null) {
            Sprint sprint = sprintRepository.findById(userStoryDTO.getSprintId())
                    .orElseThrow(() -> new ResourceNotFoundException("Sprint non trouvé avec l'ID : " + userStoryDTO.getSprintId()));
            userStory.setSprint(sprint);
        }

        if (userStoryDTO.getAssignedUserId() != null) {
            User user = userRepository.findById(userStoryDTO.getAssignedUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User non trouvé avec l'ID : " + userStoryDTO.getAssignedUserId()));
            userStory.setAssignedUser(user);
        }

        UserStory savedUserStory = userStoryRepository.save(userStory);
        return userStoryMapper.toDto(savedUserStory);
    }

    @Override
    public UserStoryDTO updateUserStory(Long id, UserStoryDTO userStoryDTO) {
        UserStory existingUserStory = userStoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserStory non trouvé avec l'ID : " + id));

        validateUserStory(userStoryDTO);

        existingUserStory.setTitle(userStoryDTO.getTitle());
        existingUserStory.setDescription(userStoryDTO.getDescription());
        existingUserStory.setStatus(userStoryDTO.getStatus());
        existingUserStory.setPriority(userStoryDTO.getPriority());
        existingUserStory.setAcceptanceCriteria(userStoryDTO.getAcceptanceCriteria());
        existingUserStory.setStoryPoints(userStoryDTO.getStoryPoints());

        if (userStoryDTO.getEpicId() != null) {
            Epic epic = epicRepository.findById(userStoryDTO.getEpicId())
                    .orElseThrow(() -> new ResourceNotFoundException("Epic non trouvé avec l'ID : " + userStoryDTO.getEpicId()));
            existingUserStory.setEpic(epic);
        } else if (userStoryDTO.getEpicId() == null && existingUserStory.getEpic() != null) {
            existingUserStory.setEpic(null);
        }

        if (userStoryDTO.getProductBacklogId() != null) {
            ProductBacklog productBacklog = productBacklogRepository.findById(userStoryDTO.getProductBacklogId())
                    .orElseThrow(() -> new ResourceNotFoundException("ProductBacklog non trouvé avec l'ID : " + userStoryDTO.getProductBacklogId()));
            existingUserStory.setProductBacklog(productBacklog);
        }

        if (userStoryDTO.getSprintId() != null) {
            Sprint sprint = sprintRepository.findById(userStoryDTO.getSprintId())
                    .orElseThrow(() -> new ResourceNotFoundException("Sprint non trouvé avec l'ID : " + userStoryDTO.getSprintId()));
            existingUserStory.setSprint(sprint);
        } else if (userStoryDTO.getSprintId() == null && existingUserStory.getSprint() != null) {
            existingUserStory.setSprint(null);
        }

        if (userStoryDTO.getAssignedUserId() != null) {
            User user = userRepository.findById(userStoryDTO.getAssignedUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User non trouvé avec l'ID : " + userStoryDTO.getAssignedUserId()));
            existingUserStory.setAssignedUser(user);
        } else if (userStoryDTO.getAssignedUserId() == null && existingUserStory.getAssignedUser() != null) {
            existingUserStory.setAssignedUser(null);
        }

        UserStory updatedUserStory = userStoryRepository.save(existingUserStory);
        return userStoryMapper.toDto(updatedUserStory);
    }

    @Override
    public List<UserStoryDTO> getAllUserStories() {
        return userStoryRepository.findAll().stream()
                .map(userStoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserStoryDTO getUserStoryById(Long id) {
        return userStoryRepository.findById(id)
                .map(userStoryMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("UserStory non trouvé avec l'ID : " + id));
    }

    @Override
    public void deleteUserStory(Long id) {
        if (!userStoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("UserStory non trouvé avec l'ID : " + id);
        }
        userStoryRepository.deleteById(id);
    }

    private void validateUserStory(UserStoryDTO dto) {
        if (dto.getTitle() == null || dto.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Le titre de la UserStory ne peut pas être vide !");
        }
    }

    @Override
    public UserStoryDTO assignUserToUserStory(Long userStoryId, Long userId) {
        UserStory userStory = userStoryRepository.findById(userStoryId)
                .orElseThrow(() -> new ResourceNotFoundException("UserStory non trouvé avec l'ID : " + userStoryId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User non trouvé avec l'ID : " + userId));

        userStory.setAssignedUser(user);
        UserStory updatedUserStory = userStoryRepository.save(userStory);
        return userStoryMapper.toDto(updatedUserStory);
    }

    @Override
    public List<UserStoryDTO> getUserStoriesByAssignedUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User non trouvé avec l'ID : " + userId);
        }

        return userStoryRepository.findByAssignedUserId(userId).stream()
                .map(userStoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserStoryDTO addUserStoryToProductBacklog(Long userStoryId, Long productBacklogId) {
        UserStory userStory = userStoryRepository.findById(userStoryId)
                .orElseThrow(() -> new ResourceNotFoundException("UserStory non trouvé avec l'ID : " + userStoryId));

        ProductBacklog productBacklog = productBacklogRepository.findById(productBacklogId)
                .orElseThrow(() -> new ResourceNotFoundException("ProductBacklog non trouvé avec l'ID : " + productBacklogId));

        userStory.setProductBacklog(productBacklog);
        UserStory savedUserStory = userStoryRepository.save(userStory);
        return userStoryMapper.toDto(savedUserStory);
    }

    @Override
    public UserStoryDTO addUserStoryToSprint(Long userStoryId, Long sprintId) {
        UserStory userStory = userStoryRepository.findById(userStoryId)
                .orElseThrow(() -> new ResourceNotFoundException("UserStory non trouvé avec l'ID : " + userStoryId));

        Sprint sprint = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new ResourceNotFoundException("Sprint non trouvé avec l'ID : " + sprintId));

        userStory.setSprint(sprint);
        UserStory savedUserStory = userStoryRepository.save(userStory);
        return userStoryMapper.toDto(savedUserStory);
    }

    @Override
    public UserStoryDTO removeUserStoryFromSprint(Long userStoryId) {
        UserStory userStory = userStoryRepository.findById(userStoryId)
                .orElseThrow(() -> new ResourceNotFoundException("UserStory non trouvé avec l'ID : " + userStoryId));

        userStory.setSprint(null);
        UserStory savedUserStory = userStoryRepository.save(userStory);
        return userStoryMapper.toDto(savedUserStory);
    }

    @Override
    public List<UserStoryDTO> getUserStoriesByEpic(Long epicId) {
        return userStoryRepository.findByEpic_Id(epicId).stream()
                .map(userStoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserStoryDTO> getUserStoriesByStatus(String status) {
        try {
            UserStoryStatus userStoryStatus = UserStoryStatus.valueOf(status.toUpperCase());
            return userStoryRepository.findByStatus(userStoryStatus).stream()
                    .map(userStoryMapper::toDto)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Statut invalide : " + status);
        }
    }
}