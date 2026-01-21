package com.projetspringboot.service.impl;

import com.projetspringboot.entity.Etudiant;
import com.projetspringboot.repository.EtudiantRepository;
import com.projetspringboot.service.EtudiantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EtudiantServiceImpl implements EtudiantService {

    private final EtudiantRepository etudiantRepository;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Autowired
    public EtudiantServiceImpl(EtudiantRepository etudiantRepository,
            org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
        this.etudiantRepository = etudiantRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Etudiant> getAllEtudiants() {
        return etudiantRepository.findAll();
    }

    @Override
    public Optional<Etudiant> getEtudiantById(Long id) {
        return etudiantRepository.findById(id);
    }

    @Override
    public Etudiant saveEtudiant(Etudiant etudiant) {
        if (etudiant.getId() != null) {
            // Update mode
            Etudiant existing = etudiantRepository.findById(etudiant.getId())
                    .orElseThrow(() -> new RuntimeException("Etudiant non trouvé"));

            // Preserve password if not changed
            if (etudiant.getPassword() == null || etudiant.getPassword().isEmpty()) {
                etudiant.setPassword(existing.getPassword());
            } else {
                etudiant.setPassword(passwordEncoder.encode(etudiant.getPassword()));
            }
            // Preserve other fields if necessary or ensure they are in the form
            if (etudiant.getRole() == null) {
                etudiant.setRole(existing.getRole());
            }
        } else {
            // Create mode
            if (etudiant.getPassword() != null && !etudiant.getPassword().isEmpty()) {
                etudiant.setPassword(passwordEncoder.encode(etudiant.getPassword()));
            }
            if (etudiant.getRole() == null) {
                etudiant.setRole(com.projetspringboot.entity.Role.ETUDIANT);
            }
            if (etudiant.getDateInscriptionEcole() == null) {
                etudiant.setDateInscriptionEcole(new java.util.Date());
            }
        }
        return etudiantRepository.save(etudiant);
    }

    @Override
    public void deleteEtudiant(Long id) {
        etudiantRepository.deleteById(id);
    }
}
