package com.CoTask.service.impl;

import com.CoTask.dto.ProductBacklogDTO;
import com.CoTask.dto.UserStoryDTO;
import com.CoTask.entity.ProductBacklog;
import com.CoTask.entity.UserStory;
import com.CoTask.exception.ResourceNotFoundException;
import com.CoTask.mapper.ProductBacklogMapper;
import com.CoTask.mapper.UserStoryMapper;
import com.CoTask.repository.ProductBacklogRepository;
import com.CoTask.repository.UserStoryRepository;
import com.CoTask.service.ProductBacklogService;
import com.CoTask.strategy.MoscowStrategy;
import com.CoTask.strategy.PrioritizationStrategy;
import com.CoTask.strategy.ValueEffortStrategy;
import com.CoTask.strategy.WsjfStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductBacklogServiceImpl implements ProductBacklogService {

    private final ProductBacklogRepository repository;
    private final ProductBacklogMapper mapper;
    private final UserStoryRepository userStoryRepository;
    private final UserStoryMapper userStoryMapper;
    private final MoscowStrategy moscowStrategy;
    private final ValueEffortStrategy valueEffortStrategy;
    private final WsjfStrategy wsjfStrategy;

    private Map<String, PrioritizationStrategy> strategies;

    private Map<String, PrioritizationStrategy> getStrategies() {
        if (strategies == null) {
            strategies = new HashMap<>();
            strategies.put("MOSCOW", moscowStrategy);
            strategies.put("VALUE_EFFORT", valueEffortStrategy);
            strategies.put("WSJF", wsjfStrategy);
        }
        return strategies;
    }

    @Override
    public ProductBacklogDTO createProductBacklog(ProductBacklogDTO dto) {
        validateProductBacklog(dto);
        ProductBacklog entity = mapper.toEntity(dto);
        ProductBacklog saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public ProductBacklogDTO updateProductBacklog(Long id, ProductBacklogDTO dto) {
        ProductBacklog existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductBacklog non trouvé avec l'ID : " + id));

        validateProductBacklog(dto);

        existing.setName(dto.getName());

        ProductBacklog updated = repository.save(existing);
        return mapper.toDto(updated);
    }

    @Override
    public List<ProductBacklogDTO> getAllProductBacklogs() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductBacklogDTO getProductBacklogById(Long id) {
        ProductBacklog entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductBacklog non trouvé avec l'ID : " + id));
        return mapper.toDto(entity);
    }

    @Override
    public void deleteProductBacklog(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("ProductBacklog non trouvé avec l'ID : " + id);
        }
        repository.deleteById(id);
    }

    private void validateProductBacklog(ProductBacklogDTO dto) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom du ProductBacklog ne peut pas être vide !");
        }
    }

    @Override
    public void addUserStoryToProductBacklog(Long productBacklogId, Long userStoryId) {
        ProductBacklog productBacklog = repository.findById(productBacklogId)
                .orElseThrow(() -> new ResourceNotFoundException("ProductBacklog non trouvé avec l'ID : " + productBacklogId));

        UserStory userStory = userStoryRepository.findById(userStoryId)
                .orElseThrow(() -> new ResourceNotFoundException("UserStory non trouvé avec l'ID : " + userStoryId));

        userStory.setProductBacklog(productBacklog);
        userStoryRepository.save(userStory);
    }

    @Override
    public void removeUserStoryFromProductBacklog(Long productBacklogId, Long userStoryId) {
        UserStory userStory = userStoryRepository.findById(userStoryId)
                .orElseThrow(() -> new ResourceNotFoundException("UserStory non trouvé avec l'ID : " + userStoryId));

        if (userStory.getProductBacklog() == null || !userStory.getProductBacklog().getId().equals(productBacklogId)) {
            throw new ResourceNotFoundException("La UserStory n'est pas associée à ce ProductBacklog");
        }

        userStory.setProductBacklog(null);
        userStoryRepository.save(userStory);
    }

    @Override
    public List<UserStoryDTO> getUserStoriesByProductBacklog(Long productBacklogId) {
        if (!repository.existsById(productBacklogId)) {
            throw new ResourceNotFoundException("ProductBacklog non trouvé avec l'ID : " + productBacklogId);
        }

        return userStoryRepository.findByProductBacklog_Id(productBacklogId).stream()
                .map(userStoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserStoryDTO> getUserStoriesByProductBacklogOrderedByPriority(Long productBacklogId) {
        if (!repository.existsById(productBacklogId)) {
            throw new ResourceNotFoundException("ProductBacklog non trouvé avec l'ID : " + productBacklogId);
        }

        return userStoryRepository.findByProductBacklog_IdOrderByPriorityAsc(productBacklogId).stream()
                .map(userStoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void prioritizeUserStory(Long userStoryId, String priorityStrategy) {
        // Cette méthode peut être utilisée pour appliquer une stratégie de priorisation
        // La priorisation se fait principalement via l'update de la UserStory avec une nouvelle priorité
        // Les stratégies sont utilisées pour trier les UserStories
    }

    public List<UserStoryDTO> prioritizeUserStories(List<UserStoryDTO> userStories, String strategyName) {
        PrioritizationStrategy strategy = getStrategies().get(strategyName.toUpperCase());
        if (strategy == null) {
            throw new IllegalArgumentException("Stratégie de priorisation inconnue : " + strategyName);
        }
        return strategy.prioritize(userStories);
    }
}