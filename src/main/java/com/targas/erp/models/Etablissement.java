package com.targas.erp.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Etablissement {
    @Id
    @GeneratedValue
    private int id;
    private String nom;
    private String adresse;

    @OneToMany(mappedBy = "etablissement")
    private List<Salle> salleList;

    @ManyToMany
    @JoinTable(name = "etablissement_filiere",
            joinColumns = @JoinColumn(name = "etablissementList"),
            inverseJoinColumns = @JoinColumn(name = "filieres"))
    private List<Filiere> filieres;

    private boolean deleted = false;
}
