package com.projetspringboot.controller.api;

import com.projetspringboot.entity.Etudiant;
import com.projetspringboot.service.EtudiantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/etudiants")
public class EtudiantRestController {

    private final EtudiantService etudiantService;

    @Autowired
    public EtudiantRestController(EtudiantService etudiantService) {
        this.etudiantService = etudiantService;
    }

    @GetMapping
    public List<Etudiant> getAllEtudiants() {
        return etudiantService.getAllEtudiants();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Etudiant> getEtudiantById(@PathVariable Long id) {
        return etudiantService.getEtudiantById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Etudiant createEtudiant(@RequestBody Etudiant etudiant) {
        // Warning: Password should be handled carefully
        return etudiantService.saveEtudiant(etudiant);
    }

    // Additional endpoints for update/delete can be added
}
