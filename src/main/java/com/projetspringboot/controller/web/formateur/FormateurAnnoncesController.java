package com.projetspringboot.controller.web.formateur;

import com.projetspringboot.entity.Utilisateur;
import com.projetspringboot.repository.UtilisateurRepository;
import com.projetspringboot.service.formateur.AnnonceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/formateur")
public class FormateurAnnoncesController {

    private final AnnonceService annonceService;
    private final UtilisateurRepository utilisateurRepository;

    @Autowired
    public FormateurAnnoncesController(AnnonceService annonceService, UtilisateurRepository utilisateurRepository) {
        this.annonceService = annonceService;
        this.utilisateurRepository = utilisateurRepository;
    }

    @GetMapping("/cours/{coursId}/annonces")
    public String annonces(@PathVariable Long coursId, Model model) {
        Long formateurId = getCurrentUtilisateurId();
        var annonces = annonceService.getByCours(formateurId, coursId);
        model.addAttribute("annonces", annonces);
        model.addAttribute("coursId", coursId);
        return "formateur/annoncesCours";
    }

    @PostMapping("/cours/{coursId}/annonces")
    public String publier(@PathVariable Long coursId, @RequestParam String message) {
        Long formateurId = getCurrentUtilisateurId();
        annonceService.publier(formateurId, coursId, message);
        return "redirect:/formateur/cours/" + coursId + "/annonces";
    }

    private Long getCurrentUtilisateurId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return utilisateur.getId();
    }
}

