package com.projetspringboot.service;

import com.projetspringboot.entity.Cours;

import java.util.List;
import java.util.Optional;

public interface CoursService {
    List<Cours> getAllCours();

    Optional<Cours> getCoursById(Long id);

    Cours saveCours(Cours cours);

    void deleteCours(Long id);

    List<Cours> getCoursByFormateur(Long formateurId);
}
