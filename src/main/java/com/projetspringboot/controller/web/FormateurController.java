package com.projetspringboot.controller.web;

import com.projetspringboot.entity.Formateur;
import com.projetspringboot.service.FormateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/formateurs")
public class FormateurController {

    private final FormateurService formateurService;

    @Autowired
    public FormateurController(FormateurService formateurService) {
        this.formateurService = formateurService;
    }

    @GetMapping
    public String listFormateurs(Model model) {
        model.addAttribute("formateurs", formateurService.getAllFormateurs());
        return "formateurs/listFormateurs";
    }

    @GetMapping("/new")
    public String createFormateurForm(Model model) {
        model.addAttribute("formateur", new Formateur());
        return "formateurs/addFormateur";
    }

    @PostMapping
    public String saveFormateur(@ModelAttribute Formateur formateur) {
        formateurService.saveFormateur(formateur);
        return "redirect:/formateurs";
    }

    @GetMapping("/edit/{id}")
    public String editFormateurForm(@PathVariable Long id, Model model) {
        model.addAttribute("formateur", formateurService.getFormateurById(id).orElseThrow());
        return "formateurs/addFormateur";
    }

    @GetMapping("/delete/{id}")
    public String deleteFormateur(@PathVariable Long id) {
        formateurService.deleteFormateur(id);
        return "redirect:/formateurs";
    }
}