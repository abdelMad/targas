package com.targas.erp.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Etudiant extends Utilisateur {

    @Id
    @GeneratedValue
    private int id;
    @Column(unique = true)
    private int numEleve;

    @ManyToOne
    @JoinColumn(name = "groupeEtudiant")
    private GroupeEtudiant groupeEtudiant;
    //private List<Demarche> listeDemarche; //pour voir les demarches qu'il a fait

    @ManyToMany
    @JoinTable(name = "etudiant_examen",
            joinColumns = @JoinColumn(name = "surveillants"),
            inverseJoinColumns = @JoinColumn(name = "examenList"))
    private List<Examen> examenList;

}
