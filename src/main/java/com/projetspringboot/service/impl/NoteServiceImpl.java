package com.projetspringboot.service.impl;

import com.projetspringboot.entity.Cours;
import com.projetspringboot.entity.Etudiant;
import com.projetspringboot.entity.Note;
import com.projetspringboot.repository.CoursRepository;
import com.projetspringboot.repository.EtudiantRepository;
import com.projetspringboot.repository.NoteRepository;
import com.projetspringboot.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final EtudiantRepository etudiantRepository;
    private final CoursRepository coursRepository;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository, EtudiantRepository etudiantRepository,
            CoursRepository coursRepository) {
        this.noteRepository = noteRepository;
        this.etudiantRepository = etudiantRepository;
        this.coursRepository = coursRepository;
    }

    @Override
    public Note addNote(Long etudiantId, Long coursId, Double valeur, String commentaire) {
        Etudiant etudiant = etudiantRepository.findById(etudiantId)
                .orElseThrow(() -> new RuntimeException("Etudiant non trouvé"));
        Cours cours = coursRepository.findById(coursId)
                .orElseThrow(() -> new RuntimeException("Cours non trouvé"));

        // Mettre à jour la note existante si présente, sinon créer
        Note note = noteRepository.findByEtudiantIdAndCoursId(etudiantId, coursId)
                .map(existing -> {
                    existing.setValeur(valeur);
                    existing.setCommentaire(commentaire);
                    return existing;
                })
                .orElseGet(() -> {
                    Note n = new Note();
                    n.setEtudiant(etudiant);
                    n.setCours(cours);
                    n.setValeur(valeur);
                    n.setCommentaire(commentaire);
                    return n;
                });

        return noteRepository.save(note);
    }

    @Override
    public List<Note> getNotesByEtudiant(Long etudiantId) {
        return noteRepository.findByEtudiantId(etudiantId);
    }

    @Override
    public List<Note> getNotesByCours(Long coursId) {
        return noteRepository.findByCoursId(coursId);
    }
}
