package com.projetspringboot.repository.formateur;

import com.projetspringboot.entity.formateur.NoteHistorique;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NoteHistoriqueRepository extends JpaRepository<NoteHistorique, Long> {
    List<NoteHistorique> findByCoursId(Long coursId);
    List<NoteHistorique> findByEtudiantId(Long etudiantId);
}

