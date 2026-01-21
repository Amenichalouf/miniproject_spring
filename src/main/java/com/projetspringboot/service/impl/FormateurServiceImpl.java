package com.projetspringboot.service.impl;

import com.projetspringboot.entity.Formateur;
import com.projetspringboot.repository.FormateurRepository;
import com.projetspringboot.service.FormateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FormateurServiceImpl implements FormateurService {

    private final FormateurRepository formateurRepository;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Autowired
    public FormateurServiceImpl(FormateurRepository formateurRepository,
            org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
        this.formateurRepository = formateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Formateur> getAllFormateurs() {
        return formateurRepository.findAll();
    }

    @Override
    public Optional<Formateur> getFormateurById(Long id) {
        return formateurRepository.findById(id);
    }

    @Override
    public Formateur saveFormateur(Formateur formateur) {
        if (formateur.getId() != null) {
            // Update mode
            Formateur existing = formateurRepository.findById(formateur.getId())
                    .orElseThrow(() -> new RuntimeException("Formateur non trouvé"));

            // Preserve password if not changed
            if (formateur.getPassword() == null || formateur.getPassword().isEmpty()) {
                formateur.setPassword(existing.getPassword());
            } else {
                formateur.setPassword(passwordEncoder.encode(formateur.getPassword()));
            }
            if (formateur.getRole() == null) {
                formateur.setRole(existing.getRole());
            }
        } else {
            // Create mode
            if (formateur.getPassword() != null && !formateur.getPassword().isEmpty()) {
                formateur.setPassword(passwordEncoder.encode(formateur.getPassword()));
            }
            if (formateur.getRole() == null) {
                formateur.setRole(com.projetspringboot.entity.Role.FORMATEUR);
            }
        }
        return formateurRepository.save(formateur);
    }

    @Override
    public void deleteFormateur(Long id) {
        formateurRepository.deleteById(id);
    }
}
