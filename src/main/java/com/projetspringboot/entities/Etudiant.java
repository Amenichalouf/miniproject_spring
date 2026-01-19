package com.projetspringboot.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "etudiants")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Etudiant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "matricule", nullable = false, unique = true,length = 20)
    private String matricule;

    @Column(name = "nom", nullable = false, unique = true,length = 20)
    private String nom;
    @Column(name = "prenom", nullable = false, unique = true,length = 20)
    private String prenom;
    @Column(name = "email", nullable = false, unique = true,length = 20)
    private String email;

    @Column(name = "date_inscrit")
    private Date date_inscription;
}
