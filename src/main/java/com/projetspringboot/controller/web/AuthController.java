package com.projetspringboot.controller.web;

import com.projetspringboot.entity.Etudiant;
import com.projetspringboot.entity.Role;
import com.projetspringboot.repository.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final EtudiantRepository etudiantRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(EtudiantRepository etudiantRepository, PasswordEncoder passwordEncoder) {
        this.etudiantRepository = etudiantRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("etudiant", new Etudiant());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("etudiant") Etudiant etudiant) {
        etudiant.setPassword(passwordEncoder.encode(etudiant.getPassword()));
        etudiant.setRole(Role.ETUDIANT);

        // Generate dummy matricule if needed or keep blank
        if (etudiant.getMatricule() == null || etudiant.getMatricule().isEmpty()) {
            etudiant.setMatricule("ETU-" + System.currentTimeMillis());
        }

        etudiantRepository.save(etudiant);
        return "redirect:/login?registered";
    }
}
