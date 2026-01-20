package com.projetspringboot.service.impl;

import com.projetspringboot.entity.Cours;
import com.projetspringboot.entity.Etudiant;
import com.projetspringboot.entity.Inscription;
import com.projetspringboot.repository.CoursRepository;
import com.projetspringboot.repository.EtudiantRepository;
import com.projetspringboot.repository.InscriptionRepository;
import com.projetspringboot.service.InscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class InscriptionServiceImpl implements InscriptionService {

    private final InscriptionRepository inscriptionRepository;
    private final EtudiantRepository etudiantRepository;
    private final CoursRepository coursRepository;

    @Autowired
    public InscriptionServiceImpl(InscriptionRepository inscriptionRepository, EtudiantRepository etudiantRepository,
            CoursRepository coursRepository) {
        this.inscriptionRepository = inscriptionRepository;
        this.etudiantRepository = etudiantRepository;
        this.coursRepository = coursRepository;
    }

    @Override
    public Inscription inscrireEtudiant(Long etudiantId, Long coursId) {
        Etudiant etudiant = etudiantRepository.findById(etudiantId)
                .orElseThrow(() -> new RuntimeException("Etudiant non trouvé"));
        Cours cours = coursRepository.findById(coursId)
                .orElseThrow(() -> new RuntimeException("Cours non trouvé"));

        // TODO: Check for existing inscription to avoid duplicates?

        Inscription inscription = new Inscription();
        inscription.setEtudiant(etudiant);
        inscription.setCours(cours);
        inscription.setDateInscription(new Date());

        return inscriptionRepository.save(inscription);
    }

    @Override
    public void desinscrireEtudiant(Long inscriptionId) {
        inscriptionRepository.deleteById(inscriptionId);
    }

    @Override
    public List<Inscription> getInscriptionsByEtudiant(Long etudiantId) {
        return inscriptionRepository.findByEtudiantId(etudiantId);
    }

    @Override
    public List<Inscription> getInscriptionsByCours(Long coursId) {
        return inscriptionRepository.findByCoursId(coursId);
    }
}
