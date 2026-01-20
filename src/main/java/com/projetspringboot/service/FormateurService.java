package com.projetspringboot.service;

import com.projetspringboot.entity.Formateur;

import java.util.List;
import java.util.Optional;

public interface FormateurService {
    List<Formateur> getAllFormateurs();

    Optional<Formateur> getFormateurById(Long id);

    Formateur saveFormateur(Formateur formateur);

    void deleteFormateur(Long id);
}
