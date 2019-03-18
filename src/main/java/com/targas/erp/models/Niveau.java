package com.targas.erp.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class Niveau {

    @Id
    @GeneratedValue
    private int id;
    private String nom;
    private boolean deleted = false;
    @OneToMany(mappedBy = "niveau")
    private List<GroupeEtudiant> groupeEtudiants;
}
