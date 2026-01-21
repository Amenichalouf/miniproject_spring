package com.projetspringboot.controller.web.etudiant;

import com.projetspringboot.entity.Utilisateur;
import com.projetspringboot.entity.Inscription;
import com.projetspringboot.entity.Cours;
import com.projetspringboot.repository.UtilisateurRepository;
import com.projetspringboot.service.CoursService;
import com.projetspringboot.service.InscriptionService;
import com.projetspringboot.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/etudiant")
public class EtudiantDashboardController {

    private final CoursService coursService;
    private final InscriptionService inscriptionService;
    private final NoteService noteService;
    private final UtilisateurRepository utilisateurRepository;

    @Autowired
    public EtudiantDashboardController(CoursService coursService,
                                       InscriptionService inscriptionService,
                                       NoteService noteService,
                                       UtilisateurRepository utilisateurRepository) {
        this.coursService = coursService;
        this.inscriptionService = inscriptionService;
        this.noteService = noteService;
        this.utilisateurRepository = utilisateurRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, @RequestParam(value = "message", required = false) String message,
                            @RequestParam(value = "error", required = false) String error) {
        Long etudiantId = getCurrentUtilisateurId();
        List<Cours> coursList = coursService.getAllCours();
        List<Inscription> mesInscriptions = inscriptionService.getInscriptionsByEtudiant(etudiantId);

        model.addAttribute("coursList", coursList);
        model.addAttribute("mesInscriptions", mesInscriptions);
        model.addAttribute("message", message);
        model.addAttribute("error", error);

        return "etudiant/dashboard";
    }

    @PostMapping("/inscrire/{coursId}")
    public String inscrire(@PathVariable Long coursId) {
        Long etudiantId = getCurrentUtilisateurId();
        try {
            inscriptionService.inscrireEtudiant(etudiantId, coursId);
            return "redirect:/etudiant/dashboard?message=Inscription%20réussie";
        } catch (IllegalStateException ise) {
            return "redirect:/etudiant/dashboard?error=" + encode("Déjà inscrit à ce cours");
        } catch (RuntimeException re) {
            return "redirect:/etudiant/dashboard?error=" + encode(re.getMessage());
        }
    }

    @GetMapping("/notes")
    public String notes(Model model) {
        Long etudiantId = getCurrentUtilisateurId();
        var notes = noteService.getNotesByEtudiant(etudiantId);
        double moyenne = notes.stream()
                .filter(n -> n.getValeur() != null)
                .mapToDouble(n -> n.getValeur())
                .average().orElse(0.0);
        model.addAttribute("notes", notes);
        model.addAttribute("moyenne", moyenne);
        return "etudiant/notes";
    }

    private Long getCurrentUtilisateurId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return utilisateur.getId();
    }

    private String encode(String s) {
        return s.replace(" ", "%20");
    }
}