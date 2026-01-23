package com.CoTask.service;

import com.CoTask.dto.EpicDTO;
import java.util.List;

public interface EpicService {
    EpicDTO createEpic(EpicDTO epicDTO);
    EpicDTO updateEpic(Long id, EpicDTO epicDTO);
    List<EpicDTO> getAllEpics();
    EpicDTO getEpicById(Long id);
    void deleteEpic(Long id);
}