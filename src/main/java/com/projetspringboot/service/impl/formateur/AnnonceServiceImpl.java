package com.projetspringboot.service.impl.formateur;

import com.projetspringboot.entity.Cours;
import com.projetspringboot.entity.formateur.Annonce;
import com.projetspringboot.repository.CoursRepository;
import com.projetspringboot.repository.formateur.AnnonceRepository;
import com.projetspringboot.service.formateur.AnnonceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AnnonceServiceImpl implements AnnonceService {

    private final AnnonceRepository annonceRepository;
    private final CoursRepository coursRepository;

    @Autowired
    public AnnonceServiceImpl(AnnonceRepository annonceRepository, CoursRepository coursRepository) {
        this.annonceRepository = annonceRepository;
        this.coursRepository = coursRepository;
    }

    @Override
    public List<Annonce> getByCours(Long formateurId, Long coursId) {
        Cours cours = coursRepository.findById(coursId).orElseThrow(() -> new RuntimeException("Cours non trouvé"));
        if (cours.getFormateur() == null || !cours.getFormateur().getId().equals(formateurId)) {
            throw new SecurityException("Accès refusé : ce cours n'appartient pas au formateur");
        }
        return annonceRepository.findByCoursIdOrderByCreatedAtDesc(coursId);
    }

    @Override
    public Annonce publier(Long formateurId, Long coursId, String message) {
        Cours cours = coursRepository.findById(coursId).orElseThrow(() -> new RuntimeException("Cours non trouvé"));
        if (cours.getFormateur() == null || !cours.getFormateur().getId().equals(formateurId)) {
            throw new SecurityException("Accès refusé : ce cours n'appartient pas au formateur");
        }
        Annonce a = new Annonce();
        a.setCours(cours);
        a.setMessage(message);
        a.setCreatedByFormateurId(formateurId);
        a.setCreatedAt(new Date());
        return annonceRepository.save(a);
    }
}

