package com.projetspringboot.entity.formateur;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteHistorique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long noteId;

    private Long etudiantId;

    private Long coursId;

    private Double valeur;

    private String commentaire;

    @Enumerated(EnumType.STRING)
    private NoteOperation operation;

    private Long performedByFormateurId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date performedAt;
}

