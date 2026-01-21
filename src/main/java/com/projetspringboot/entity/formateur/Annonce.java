package com.projetspringboot.entity.formateur;

import com.projetspringboot.entity.Cours;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Annonce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cours_id", nullable = false)
    private Cours cours;

    @Column(nullable = false, length = 1000)
    private String message;

    private Long createdByFormateurId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
}

