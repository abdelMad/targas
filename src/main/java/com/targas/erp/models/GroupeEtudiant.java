package com.targas.erp.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

// par exemple le groupe dsc de l annee scolaire 2018/2019
@Data
@Entity
public class GroupeEtudiant {

    @Id
    @GeneratedValue
    private int id;
    private String nom;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "groupe")
    private Groupe groupe;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "niveau")
    private Niveau niveau;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "anneeScolaire")
    private AnneeScolaire anneeScolaire;


    @OneToMany(mappedBy = "groupeEtudiant", fetch = FetchType.EAGER)
    private List<Etudiant> etudiants;

    @ManyToMany(mappedBy = "classes")
    private List<Seance> seances;


    @ManyToMany(mappedBy = "groupeEtudiantList")
    private List<Examen> examenList;


}
