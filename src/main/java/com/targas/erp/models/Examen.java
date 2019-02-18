package com.targas.erp.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Examen {
    @Id
    @GeneratedValue
    private int id;
    private String nom;
    private String description;//description de l'examen (facultatif ?)
    private Date date;//date de l'examen

    @OneToMany(mappedBy = "examen")
    private List<Salle> lieu;//ou aura lieu l'examen



    @ManyToOne
    @JoinColumn(name = "cours")
    private Cours cours;

    @ManyToMany
    @JoinTable(name = "examen_groupEtudiant",
            joinColumns = @JoinColumn(name = "examen"),
            inverseJoinColumns = @JoinColumn(name = "groupeEtudiant"))
    private List<GroupeEtudiant> groupeEtudiantList;//quelles classes passent l'examen ?

    @ManyToMany(mappedBy = "examenList")
    private List<Enseignant> surveillants;//on met un surveillant ?


}
