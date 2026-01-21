package com.projetspringboot.controller.web.formateur;

import com.projetspringboot.entity.Cours;
import com.projetspringboot.entity.Inscription;
import com.projetspringboot.entity.Utilisateur;
import com.projetspringboot.repository.UtilisateurRepository;
import com.projetspringboot.service.CoursService;
import com.projetspringboot.service.InscriptionService;
import com.projetspringboot.service.NoteService;
import com.projetspringboot.service.formateur.FormateurNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/formateur")
public class FormateurDashboardController {

    private final CoursService coursService;
    private final InscriptionService inscriptionService;
    private final NoteService noteService;
    private final FormateurNoteService formateurNoteService;
    private final UtilisateurRepository utilisateurRepository;

    @Autowired
    public FormateurDashboardController(CoursService coursService,
                                        InscriptionService inscriptionService,
                                        NoteService noteService,
                                        FormateurNoteService formateurNoteService,
                                        UtilisateurRepository utilisateurRepository) {
        this.coursService = coursService;
        this.inscriptionService = inscriptionService;
        this.noteService = noteService;
        this.formateurNoteService = formateurNoteService;
        this.utilisateurRepository = utilisateurRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Long formateurId = getCurrentUtilisateurId();
        List<Cours> mesCours = coursService.getCoursByFormateur(formateurId);
        model.addAttribute("mesCours", mesCours);
        return "formateur/dashboard";
    }

    @GetMapping("/cours/{coursId}")
    public String detailsCours(@PathVariable Long coursId, Model model) {
        Long formateurId = getCurrentUtilisateurId();
        Cours cours = coursService.getCoursById(coursId)
                .orElseThrow(() -> new RuntimeException("Cours non trouvé"));
        if (cours.getFormateur() == null || !cours.getFormateur().getId().equals(formateurId)) {
            throw new SecurityException("Accès refusé");
        }
        model.addAttribute("cours", cours);
        return "formateur/coursDetail";
    }

    @GetMapping("/cours/{coursId}/edit")
    public String editCoursForm(@PathVariable Long coursId, Model model) {
        Long formateurId = getCurrentUtilisateurId();
        Cours cours = coursService.getCoursById(coursId)
                .orElseThrow(() -> new RuntimeException("Cours non trouvé"));
        if (cours.getFormateur() == null || !cours.getFormateur().getId().equals(formateurId)) {
            throw new SecurityException("Accès refusé");
        }
        model.addAttribute("cours", cours);
        return "formateur/coursEdit";
    }

    @PostMapping("/cours/{coursId}/edit")
    public String editCours(@PathVariable Long coursId,
                            @RequestParam String description,
                            @RequestParam(required = false) String contenu) {
        Long formateurId = getCurrentUtilisateurId();
        Cours cours = coursService.getCoursById(coursId)
                .orElseThrow(() -> new RuntimeException("Cours non trouvé"));
        if (cours.getFormateur() == null || !cours.getFormateur().getId().equals(formateurId)) {
            throw new SecurityException("Accès refusé");
        }
        cours.setDescription(description);
        cours.setContenu(contenu);
        coursService.saveCours(cours);
        return "redirect:/formateur/cours/" + coursId;
    }

    @GetMapping("/cours/{coursId}/etudiants")
    public String etudiantsParCours(@PathVariable Long coursId, Model model) {
        List<Inscription> inscriptions = inscriptionService.getInscriptionsByCours(coursId);
        model.addAttribute("inscriptions", inscriptions);
        model.addAttribute("coursId", coursId);
        return "formateur/etudiantsParCours";
    }

    @PostMapping("/cours/{coursId}/notes")
    public String attribuerNote(@PathVariable Long coursId,
                                @RequestParam Long etudiantId,
                                @RequestParam Double valeur,
                                @RequestParam(required = false) String commentaire) {
        Long formateurId = getCurrentUtilisateurId();
        formateurNoteService.addOrUpdateNote(formateurId, etudiantId, coursId, valeur, commentaire);
        return "redirect:/formateur/cours/" + coursId + "/etudiants";
    }

    @GetMapping("/cours/{coursId}/notes")
    public String notesCours(@PathVariable Long coursId, Model model) {
        Long formateurId = getCurrentUtilisateurId();
        var notes = formateurNoteService.getNotesByCoursForFormateur(formateurId, coursId);
        double moyenne = formateurNoteService.calculerMoyenneCours(formateurId, coursId);
        var distribution = formateurNoteService.distributionNotes(formateurId, coursId);
        double tauxReussite = formateurNoteService.tauxReussite(formateurId, coursId);
        model.addAttribute("notes", notes);
        model.addAttribute("moyenne", moyenne);
        model.addAttribute("distribution", distribution);
        model.addAttribute("tauxReussite", tauxReussite);
        model.addAttribute("coursId", coursId);
        return "formateur/notesCours";
    }

    @PostMapping("/notes/{noteId}/delete")
    public String supprimerNote(@PathVariable Long noteId, @RequestParam Long coursId) {
        Long formateurId = getCurrentUtilisateurId();
        formateurNoteService.deleteNote(formateurId, noteId);
        return "redirect:/formateur/cours/" + coursId + "/notes";
    }

    @GetMapping("/cours/{coursId}/bulletin")
    public String bulletin(@PathVariable Long coursId, Model model) {
        Long formateurId = getCurrentUtilisateurId();
        var notes = formateurNoteService.getNotesByCoursForFormateur(formateurId, coursId);
        double moyenne = formateurNoteService.calculerMoyenneCours(formateurId, coursId);
        model.addAttribute("notes", notes);
        model.addAttribute("moyenne", moyenne);
        model.addAttribute("coursId", coursId);
        return "formateur/bulletinCours";
    }

    @GetMapping("/cours/{coursId}/etudiants/{etudiantId}")
    public String profilEtudiant(@PathVariable Long coursId, @PathVariable Long etudiantId, Model model) {
        List<Inscription> inscriptions = inscriptionService.getInscriptionsByCours(coursId);
        boolean inscrit = inscriptions.stream().anyMatch(i -> i.getEtudiant().getId().equals(etudiantId));
        if (!inscrit) {
            throw new SecurityException("Accès refusé : étudiant non inscrit à ce cours");
        }
        var etudiant = inscriptions.stream().filter(i -> i.getEtudiant().getId().equals(etudiantId))
                .findFirst().orElseThrow().getEtudiant();
        model.addAttribute("etudiant", etudiant);
        model.addAttribute("coursId", coursId);
        return "formateur/profilEtudiant";
    }

    private Long getCurrentUtilisateurId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return utilisateur.getId();
    }
}
