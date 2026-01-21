package com.projetspringboot.repository;

import com.projetspringboot.entity.Cours;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CoursRepository extends JpaRepository<Cours, Long> {
    List<Cours> findByFormateurId(Long formateurId);
}
