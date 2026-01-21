package com.projetspringboot.service.formateur;

import com.projetspringboot.entity.formateur.Annonce;

import java.util.List;

public interface AnnonceService {
    List<Annonce> getByCours(Long formateurId, Long coursId);
    Annonce publier(Long formateurId, Long coursId, String message);
}

