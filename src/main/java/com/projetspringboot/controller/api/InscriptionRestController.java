package com.projetspringboot.controller.api;

import com.projetspringboot.entity.Inscription;
import com.projetspringboot.service.InscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inscriptions")
public class InscriptionRestController {

    private final InscriptionService inscriptionService;

    @Autowired
    public InscriptionRestController(InscriptionService inscriptionService) {
        this.inscriptionService = inscriptionService;
    }

    @GetMapping("/etudiant/{etudiantId}")
    public List<Inscription> getInscriptionsByEtudiant(@PathVariable Long etudiantId) {
        return inscriptionService.getInscriptionsByEtudiant(etudiantId);
    }

    @PostMapping
    public Inscription inscrire(@RequestParam Long etudiantId, @RequestParam Long coursId) {
        // Simplest param binding for demo
        return inscriptionService.inscrireEtudiant(etudiantId, coursId);
    }
}
