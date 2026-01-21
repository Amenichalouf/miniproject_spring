package com.projetspringboot.service.formateur;

import com.projetspringboot.entity.Note;

import java.util.List;
import java.util.Map;

public interface FormateurNoteService {

    Note addOrUpdateNote(Long formateurId, Long etudiantId, Long coursId, Double valeur, String commentaire);

    void deleteNote(Long formateurId, Long noteId);

    List<Note> getNotesByCoursForFormateur(Long formateurId, Long coursId);

    double calculerMoyenneCours(Long formateurId, Long coursId);

    Map<String, Long> distributionNotes(Long formateurId, Long coursId);

    double tauxReussite(Long formateurId, Long coursId);
}
