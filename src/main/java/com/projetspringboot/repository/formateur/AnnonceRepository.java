package com.projetspringboot.repository.formateur;

import com.projetspringboot.entity.formateur.Annonce;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AnnonceRepository extends JpaRepository<Annonce, Long> {
    List<Annonce> findByCoursIdOrderByCreatedAtDesc(Long coursId);
}

