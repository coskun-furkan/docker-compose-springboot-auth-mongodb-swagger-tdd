package com.furkan.tutorials.service;
import com.furkan.tutorials.model.Tutorial;



import com.furkan.tutorials.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TutorialServiceImpl implements TutorialService{

    private TutorialRepository tutorialRepository;

    @Autowired
    public TutorialServiceImpl(TutorialRepository tutorialRepository) {
        this.tutorialRepository = tutorialRepository;
    }

    @Override
    public List<Tutorial> getAll() {
        return tutorialRepository.findAll();
    }

    @Override
    public Optional<Tutorial> getById(String id) {
        return tutorialRepository.findById(id);
    }

    @Override
    public Tutorial create(Tutorial tutorial) {
        return tutorialRepository.save(tutorial);
    }


    @Override
    public Tutorial update(String id, Tutorial tutorial) {
        Optional<Tutorial> t = tutorialRepository.findById(id);
        if(t.isPresent()){
            Tutorial existing = t.get();
            existing.setTitle(tutorial.getTitle());
            existing.setDescription(tutorial.getDescription());
            existing.setPublished(tutorial.isPublished());
            return tutorialRepository.save(existing);
        }
        return null;
    }

    @Override
    public void delete(String id) {
        tutorialRepository.deleteById(id);

    }

    @Override
    public List<Tutorial> getByPublished() {
        return tutorialRepository.findByPublished(true);
    }


}
