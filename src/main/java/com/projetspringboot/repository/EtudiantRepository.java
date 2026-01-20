package com.projetspringboot.repository;

import com.projetspringboot.entity.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    // Methods specific to Etudiant can be added here
}
