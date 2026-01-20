package com.projetspringboot.service;

import com.projetspringboot.entity.Etudiant;

import java.util.List;
import java.util.Optional;

public interface EtudiantService {
    List<Etudiant> getAllEtudiants();
    Optional<Etudiant> getEtudiantById(Long id);
    Etudiant saveEtudiant(Etudiant etudiant);
    void deleteEtudiant(Long id);
}
