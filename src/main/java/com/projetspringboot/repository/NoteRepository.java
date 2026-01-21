package com.projetspringboot.repository;

import com.projetspringboot.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByEtudiantId(Long etudiantId);
    List<Note> findByCoursId(Long coursId);
    Optional<Note> findByEtudiantIdAndCoursId(Long etudiantId, Long coursId);
}
