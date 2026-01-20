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

    @Autowired
    public FormateurServiceImpl(FormateurRepository formateurRepository) {
        this.formateurRepository = formateurRepository;
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
        return formateurRepository.save(formateur);
    }

    @Override
    public void deleteFormateur(Long id) {
        formateurRepository.deleteById(id);
    }
}
