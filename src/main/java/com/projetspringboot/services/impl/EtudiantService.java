package com.projetspringboot.services.impl;

import com.projetspringboot.entities.Etudiant;
import com.projetspringboot.repositories.EtudiantRepository;
import com.projetspringboot.services.IEtudiant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtudiantService implements IEtudiant {

    @Autowired
    private EtudiantRepository repo;



    @Override
    public List<Etudiant> getAllEtudiant() {
        return repo.findAll();
    }
}
