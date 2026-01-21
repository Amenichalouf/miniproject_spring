package com.projetspringboot.service.impl.formateur;

import com.projetspringboot.entity.Cours;
import com.projetspringboot.entity.Inscription;
import com.projetspringboot.entity.Note;
import com.projetspringboot.entity.formateur.NoteHistorique;
import com.projetspringboot.entity.formateur.NoteOperation;
import com.projetspringboot.repository.CoursRepository;
import com.projetspringboot.repository.InscriptionRepository;
import com.projetspringboot.repository.NoteRepository;
import com.projetspringboot.repository.formateur.NoteHistoriqueRepository;
import com.projetspringboot.service.formateur.FormateurNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class FormateurNoteServiceImpl implements FormateurNoteService {

    private final NoteRepository noteRepository;
    private final CoursRepository coursRepository;
    private final InscriptionRepository inscriptionRepository;
    private final NoteHistoriqueRepository noteHistoriqueRepository;

    @Autowired
    public FormateurNoteServiceImpl(NoteRepository noteRepository,
                                    CoursRepository coursRepository,
                                    InscriptionRepository inscriptionRepository,
                                    NoteHistoriqueRepository noteHistoriqueRepository) {
        this.noteRepository = noteRepository;
        this.coursRepository = coursRepository;
        this.inscriptionRepository = inscriptionRepository;
        this.noteHistoriqueRepository = noteHistoriqueRepository;
    }

    @Override
    public Note addOrUpdateNote(Long formateurId, Long etudiantId, Long coursId, Double valeur, String commentaire) {
        Cours cours = coursRepository.findById(coursId)
                .orElseThrow(() -> new RuntimeException("Cours non trouvé"));
        if (cours.getFormateur() == null || !cours.getFormateur().getId().equals(formateurId)) {
            throw new SecurityException("Accès refusé : ce cours n'appartient pas au formateur");
        }
        List<Inscription> inscriptions = inscriptionRepository.findByCoursId(coursId);
        boolean inscrit = inscriptions.stream().anyMatch(i -> i.getEtudiant().getId().equals(etudiantId));
        if (!inscrit) {
            throw new IllegalStateException("L'étudiant n'est pas inscrit à ce cours");
        }

        Note note = noteRepository.findByEtudiantIdAndCoursId(etudiantId, coursId)
                .map(existing -> {
                    existing.setValeur(valeur);
                    existing.setCommentaire(commentaire);
                    enregistrerHistorique(existing.getId(), etudiantId, coursId, valeur, commentaire, formateurId, NoteOperation.UPDATED);
                    return existing;
                })
                .orElseGet(() -> {
                    Note n = new Note();
                    n.setEtudiant(inscriptions.stream().filter(i -> i.getEtudiant().getId().equals(etudiantId)).findFirst()
                            .orElseThrow().getEtudiant());
                    n.setCours(cours);
                    n.setValeur(valeur);
                    n.setCommentaire(commentaire);
                    // Historique sera créé après save pour obtenir l'id
                    return n;
                });

        Note saved = noteRepository.save(note);
        if (note.getId() == null) {
            enregistrerHistorique(saved.getId(), etudiantId, coursId, valeur, commentaire, formateurId, NoteOperation.CREATED);
        }
        return saved;
    }

    @Override
    public void deleteNote(Long formateurId, Long noteId) {
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new RuntimeException("Note non trouvée"));
        Cours cours = note.getCours();
        if (cours.getFormateur() == null || !cours.getFormateur().getId().equals(formateurId)) {
            throw new SecurityException("Accès refusé : ce cours n'appartient pas au formateur");
        }
        enregistrerHistorique(note.getId(),
                note.getEtudiant().getId(),
                note.getCours().getId(),
                note.getValeur(),
                note.getCommentaire(),
                formateurId,
                NoteOperation.DELETED);
        noteRepository.deleteById(noteId);
    }

    @Override
    public List<Note> getNotesByCoursForFormateur(Long formateurId, Long coursId) {
        Cours cours = coursRepository.findById(coursId)
                .orElseThrow(() -> new RuntimeException("Cours non trouvé"));
        if (cours.getFormateur() == null || !cours.getFormateur().getId().equals(formateurId)) {
            throw new SecurityException("Accès refusé : ce cours n'appartient pas au formateur");
        }
        return noteRepository.findByCoursId(coursId);
    }

    @Override
    public double calculerMoyenneCours(Long formateurId, Long coursId) {
        List<Note> notes = getNotesByCoursForFormateur(formateurId, coursId);
        return notes.stream()
                .filter(n -> n.getValeur() != null)
                .mapToDouble(Note::getValeur)
                .average()
                .orElse(0.0);
    }

    @Override
    public Map<String, Long> distributionNotes(Long formateurId, Long coursId) {
        List<Note> notes = getNotesByCoursForFormateur(formateurId, coursId);
        long moinsDe10 = notes.stream().filter(n -> n.getValeur() != null && n.getValeur() < 10).count();
        long de10a14 = notes.stream().filter(n -> n.getValeur() != null && n.getValeur() >= 10 && n.getValeur() < 15).count();
        long auMoins15 = notes.stream().filter(n -> n.getValeur() != null && n.getValeur() >= 15).count();
        Map<String, Long> dist = new HashMap<>();
        dist.put("<10", moinsDe10);
        dist.put("10–14", de10a14);
        dist.put("≥15", auMoins15);
        return dist;
    }

    @Override
    public double tauxReussite(Long formateurId, Long coursId) {
        List<Note> notes = getNotesByCoursForFormateur(formateurId, coursId);
        long total = notes.stream().filter(n -> n.getValeur() != null).count();
        if (total == 0) return 0.0;
        long reussites = notes.stream().filter(n -> n.getValeur() != null && n.getValeur() >= 10).count();
        return (reussites * 100.0) / total;
    }

    private void enregistrerHistorique(Long noteId,
                                       Long etudiantId,
                                       Long coursId,
                                       Double valeur,
                                       String commentaire,
                                       Long formateurId,
                                       NoteOperation operation) {
        NoteHistorique h = new NoteHistorique();
        h.setNoteId(noteId);
        h.setEtudiantId(etudiantId);
        h.setCoursId(coursId);
        h.setValeur(valeur);
        h.setCommentaire(commentaire);
        h.setOperation(operation);
        h.setPerformedByFormateurId(formateurId);
        h.setPerformedAt(new Date());
        noteHistoriqueRepository.save(h);
    }
}
