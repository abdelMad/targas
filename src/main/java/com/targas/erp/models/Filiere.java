package com.targas.erp.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity

public class Filiere {

    @Id @GeneratedValue
    private int id;

    private String nomFiliere;

    private boolean deleted = false;

    @OneToMany(mappedBy = "filiere")
    private List<Groupe> groupeList;

    @ManyToMany
    @JoinTable(name = "etablissement_filiere",
            joinColumns = @JoinColumn(name = "filieres"),
            inverseJoinColumns = @JoinColumn(name = "etablissementList"))
    private List<Etablissement> etablissementList;
}
