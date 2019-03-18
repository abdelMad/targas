package com.targas.erp.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Affectation {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "anneeScolaire")
    private AnneeScolaire anneeScolaire;

    @ManyToOne
    @JoinColumn(name = "cours")
    private Cours cours;

    @ManyToOne
    @JoinColumn(name = "enseignant")
    private Enseignant enseignant;

}
