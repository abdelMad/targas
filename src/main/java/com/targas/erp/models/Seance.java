package com.targas.erp.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Seance {
    @Id
    @GeneratedValue
    private int id;

    private Date debut;//quand sa commence
    private Date fin;//quand sa finit

    @ManyToOne
    @JoinColumn(name = "cours")
    private Cours cours;//le cours

    @ManyToOne
    @JoinColumn(name = "enseignant")
    private Enseignant enseignant;//peut y avoir plusieur enseignant ?


    @ManyToOne
    @JoinColumn(name = "salle")
    private Salle salle; //dans quelle salle ?

    @ManyToOne
    @JoinColumn(name = "emploi")
    private Emploi emploi;

    @ManyToMany
    @JoinTable(name = "seance_classe",
            joinColumns = @JoinColumn(name = "seance"),
            inverseJoinColumns = @JoinColumn(name = "classes"))
    private List<GroupeEtudiant> classes;//les classes concernées par la seance


}