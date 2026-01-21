package com.projetspringboot.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String titre;

    private String description;

    @Lob
    private String contenu;

    @ManyToOne
    @JoinColumn(name = "formateur_id")
    private Formateur formateur;

    @OneToMany(mappedBy = "cours")
    private List<Inscription> inscriptions;

    // TODO: Add support for assigning to groups if needed explicitly, 
    // or implicitly via inscriptions. Plan mentioned "Affecter des cours à plusieurs groupes".
    // For now, let's keep it simple.
}
