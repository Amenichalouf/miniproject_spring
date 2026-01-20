package com.projetspringboot.repository;

import com.projetspringboot.entity.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupeRepository extends JpaRepository<Groupe, Long> {
}
