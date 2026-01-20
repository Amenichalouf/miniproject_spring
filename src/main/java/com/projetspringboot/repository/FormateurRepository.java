package com.projetspringboot.repository;

import com.projetspringboot.entity.Formateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormateurRepository extends JpaRepository<Formateur, Long> {
}
