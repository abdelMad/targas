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

    private boolean deleted = false;

    @OneToMany(mappedBy = "cours")
    private List<Seance> seanceList;


    @OneToMany(mappedBy = "cours", fetch = FetchType.EAGER)
    private List<ContenuCours> contenuCours;

    @OneToMany(mappedBy = "cours")
    private List<Examen> examenList;

    @ManyToMany(mappedBy = "cours")
    private List<Groupe> eleves;//quels eleves ont les droits dessus ?

    @OneToMany(mappedBy = "cours")
    private List<Affectation> affectations;


}
