package com.projetspringboot.service;

import com.projetspringboot.entity.Inscription;

import java.util.List;

public interface InscriptionService {
    Inscription inscrireEtudiant(Long etudiantId, Long coursId);

    void desinscrireEtudiant(Long inscriptionId);

    List<Inscription> getInscriptionsByEtudiant(Long etudiantId);

    List<Inscription> getInscriptionsByCours(Long coursId);
}
