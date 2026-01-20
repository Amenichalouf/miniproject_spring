package com.projetspringboot.controller.web;

import com.projetspringboot.entity.Cours;
import com.projetspringboot.service.CoursService;
import com.projetspringboot.service.FormateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cours")
public class CoursController {

    private final CoursService coursService;
    private final FormateurService formateurService;

    @Autowired
    public CoursController(CoursService coursService, FormateurService formateurService) {
        this.coursService = coursService;
        this.formateurService = formateurService;
    }

    @GetMapping
    public String listCours(Model model) {
        model.addAttribute("coursList", coursService.getAllCours());
        return "cours/list";
    }

    @GetMapping("/new")
    public String createCoursForm(Model model) {
        model.addAttribute("cours", new Cours());
        model.addAttribute("formateurs", formateurService.getAllFormateurs());
        return "cours/form";
    }

    @PostMapping
    public String saveCours(@ModelAttribute Cours cours) {
        coursService.saveCours(cours);
        return "redirect:/cours";
    }

    @GetMapping("/edit/{id}")
    public String editCoursForm(@PathVariable Long id, Model model) {
        model.addAttribute("cours", coursService.getCoursById(id).orElseThrow());
        model.addAttribute("formateurs", formateurService.getAllFormateurs());
        return "cours/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteCours(@PathVariable Long id) {
        coursService.deleteCours(id);
        return "redirect:/cours";
    }
}
