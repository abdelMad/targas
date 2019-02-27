package com.targas.erp.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Groupe {
    @Id
    @GeneratedValue
    private int id;
    private String nomGroupe;
    private String abbreviation;

    private boolean deleted = false;
    @ManyToOne
    @JoinColumn(name = "filiere")
    private Filiere filiere;

    @OneToMany(mappedBy = "groupe")
    private List<GroupeEtudiant> groupeEtudiantList;

    @ManyToMany
    @JoinTable(name = "cours_eleves",
            joinColumns = @JoinColumn(name = "eleves"),
            inverseJoinColumns = @JoinColumn(name = "cours"))
    private List<Cours> cours;

}
