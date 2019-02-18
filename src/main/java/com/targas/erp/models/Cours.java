package com.targas.erp.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Cours {
    @Id
    @GeneratedValue
    private int id;
    private String nom;

    private float masseHoraire;

    @OneToMany(mappedBy = "cours")
    private List<Seance> seanceList;


    @OneToMany(mappedBy = "cours")
    private List<ContenuCours> contenuCours;

    @OneToMany(mappedBy = "cours")
    private List<Examen> examenList;

    @ManyToMany
    @JoinTable(name = "cours_eleves",
            joinColumns = @JoinColumn(name = "cours"),
            inverseJoinColumns = @JoinColumn(name = "eleves"))
    private List<GroupeEtudiant> eleves;//quels eleves ont les droits dessus ?

    @ManyToMany
    @JoinTable(name = "cours_enseignant",
            joinColumns = @JoinColumn(name = "cours"),
            inverseJoinColumns = @JoinColumn(name = "enseignants"))
    private List<Enseignant> enseignants;


}
