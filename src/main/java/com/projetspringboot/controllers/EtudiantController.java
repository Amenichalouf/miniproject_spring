package com.projetspringboot.controllers;


import com.projetspringboot.entities.Etudiant;
import com.projetspringboot.services.impl.EtudiantService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/etudiant")
public class EtudiantController {


    EtudiantService etudiantService;

    @GetMapping("/list")

    public List<Etudiant> getAllEtudiant() {

        return etudiantService.getAllEtudiant();

    }
}
