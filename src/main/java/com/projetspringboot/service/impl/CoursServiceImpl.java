package com.projetspringboot.service.impl;

import com.projetspringboot.entity.Cours;
import com.projetspringboot.repository.CoursRepository;
import com.projetspringboot.service.CoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CoursServiceImpl implements CoursService {

    private final CoursRepository coursRepository;

    @Autowired
    public CoursServiceImpl(CoursRepository coursRepository) {
        this.coursRepository = coursRepository;
    }

    @Override
    public List<Cours> getAllCours() {
        return coursRepository.findAll();
    }

    @Override
    public Optional<Cours> getCoursById(Long id) {
        return coursRepository.findById(id);
    }

    @Override
    public Cours saveCours(Cours cours) {
        return coursRepository.save(cours);
    }

    @Override
    public void deleteCours(Long id) {
        coursRepository.deleteById(id);
    }
}
