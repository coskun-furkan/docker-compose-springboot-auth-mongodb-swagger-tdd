package com.furkan.tutorials.service;

import com.furkan.tutorials.model.Tutorial;

import java.util.List;
import java.util.Optional;

public interface TutorialService {
    List<Tutorial> getAll();
    Optional<Tutorial> getById(String id);
    Tutorial create(Tutorial tutorial);
    Tutorial update(String id, Tutorial tutorial);
    void delete(String id);
    List<Tutorial> getByPublished();
}
