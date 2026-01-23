package com.CoTask.service.impl;

import com.CoTask.dto.EpicDTO;
import com.CoTask.entity.Epic;
import com.CoTask.entity.ProductBacklog;
import com.CoTask.mapper.EpicMapper;
import com.CoTask.repository.EpicRepository;
import com.CoTask.repository.ProductBacklogRepository;
import com.CoTask.service.EpicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EpicServiceImpl implements EpicService {

    private final EpicRepository epicRepository;
    private final ProductBacklogRepository productBacklogRepository;
    private final EpicMapper epicMapper;

    @Override
    public EpicDTO createEpic(EpicDTO epicDTO) {
        validateEpic(epicDTO);
        Epic epic = epicMapper.toEntity(epicDTO);

        if (epicDTO.getProductBacklogId() != null) {
            ProductBacklog productBacklog = productBacklogRepository.findById(epicDTO.getProductBacklogId())
                    .orElseThrow(() -> new RuntimeException("ProductBacklog non trouvé avec l'ID : " + epicDTO.getProductBacklogId()));
            epic.setProductBacklog(productBacklog);
        }

        Epic savedEpic = epicRepository.save(epic);
        return epicMapper.toDto(savedEpic);
    }

    @Override
    public EpicDTO updateEpic(Long id, EpicDTO epicDTO) {
        Epic existingEpic = epicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Epic non trouvé avec l'ID : " + id));

        validateEpic(epicDTO);

        existingEpic.setTitle(epicDTO.getTitle());
        existingEpic.setDescription(epicDTO.getDescription());

        if (epicDTO.getProductBacklogId() != null) {
            ProductBacklog productBacklog = productBacklogRepository.findById(epicDTO.getProductBacklogId())
                    .orElseThrow(() -> new RuntimeException("ProductBacklog non trouvé avec l'ID : " + epicDTO.getProductBacklogId()));
            existingEpic.setProductBacklog(productBacklog);
        } else {
            existingEpic.setProductBacklog(null);
        }

        Epic updatedEpic = epicRepository.save(existingEpic);
        return epicMapper.toDto(updatedEpic);
    }

    @Override
    public List<EpicDTO> getAllEpics() {
        return epicRepository.findAll().stream()
                .map(epicMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public EpicDTO getEpicById(Long id) {
        return epicRepository.findById(id)
                .map(epicMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Epic non trouvé"));
    }

    @Override
    public void deleteEpic(Long id) {
        if (!epicRepository.existsById(id)) {
            throw new RuntimeException("Impossible de supprimer : ID introuvable");
        }
        epicRepository.deleteById(id);
    }

    private void validateEpic(EpicDTO dto) {
        if (dto.getTitle() == null || dto.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Le titre de l'Epic ne peut pas être vide !");
        }
    }
}