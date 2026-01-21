package com.projetspringboot.controller.web.etudiant;

import com.projetspringboot.entity.Etudiant;
import com.projetspringboot.service.EtudiantService;
// import com.projetspringboot.service.GroupeService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/etudiants")
public class EtudiantController {

    private final EtudiantService etudiantService;
    // private final GroupeService groupeService;

    @Autowired
    public EtudiantController(EtudiantService etudiantService) {
        this.etudiantService = etudiantService;
    }

    @GetMapping
    public String listEtudiants(Model model) {
        model.addAttribute("etudiants", etudiantService.getAllEtudiants());
        return "etudiants/listEtudiants";
    }

    @GetMapping("/new")
    public String createEtudiantForm(Model model) {
        model.addAttribute("etudiant", new Etudiant());
        // model.addAttribute("groupes", groupeService.getAllGroupes());
        return "etudiants/addEtudiant";
    }

    @PostMapping
    public String saveEtudiant(@ModelAttribute Etudiant etudiant) {
        // Basic naive save, needs proper Role handling and password encoding if new
        etudiantService.saveEtudiant(etudiant);
        return "redirect:/etudiants";
    }

    @GetMapping("/edit/{id}")
    public String editEtudiantForm(@PathVariable Long id, Model model) {
        model.addAttribute("etudiant", etudiantService.getEtudiantById(id).orElseThrow());
        return "etudiants/addEtudiant";
    }

    @GetMapping("/delete/{id}")
    public String deleteEtudiant(@PathVariable Long id) {
        etudiantService.deleteEtudiant(id);
        return "redirect:/etudiants";
    }
}
