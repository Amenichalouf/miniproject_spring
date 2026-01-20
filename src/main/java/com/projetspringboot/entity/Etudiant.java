package com.projetspringboot.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "utilisateur_id")
public class Etudiant extends Utilisateur {

    @Column(unique = true)
    private String matricule;

    @Temporal(TemporalType.DATE)
    private Date dateInscriptionEcole;

    @ManyToOne
    @JoinColumn(name = "groupe_id")
    private Groupe groupe;

    @OneToMany(mappedBy = "etudiant")
    private List<Inscription> inscriptions;
}
