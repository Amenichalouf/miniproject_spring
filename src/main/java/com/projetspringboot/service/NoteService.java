package com.projetspringboot.service;

import com.projetspringboot.entity.Note;

import java.util.List;

public interface NoteService {
    Note addNote(Long etudiantId, Long coursId, Double valeur, String commentaire);

    List<Note> getNotesByEtudiant(Long etudiantId);

    List<Note> getNotesByCours(Long coursId);
}
