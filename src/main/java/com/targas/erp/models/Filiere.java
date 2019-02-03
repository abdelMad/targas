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

    @OneToMany(mappedBy = "filiere")
    private List<Groupe> groupeList;

    @ManyToMany(mappedBy = "filieres")
    private List<Etablissement> etablissementList;
}
