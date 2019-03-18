package com.targas.erp.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@Entity
public class AnneeScolaire {
    @Id
    @GeneratedValue
    private int id;
    @Pattern(regexp = "^[0-9]{4}/[0-9]{4}$")
    private String nomAnneeScolaire;

    @OneToMany(mappedBy = "anneeScolaire")
    private List<GroupeEtudiant> groupeEtudiantList;

    @OneToMany(mappedBy = "anneeScolaire")
    private List<Affectation> affectations;
}
