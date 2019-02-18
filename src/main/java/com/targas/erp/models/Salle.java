package com.targas.erp.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Salle {
    @Id
    @GeneratedValue
    private int id;
    private String nom; //nom de la salle , genre B010
    private int capacite;//genre 50 places max;
    private int nbrPC;//nombre de PC de la salle

    @OneToMany(mappedBy = "salle")
    private List<Seance> seanceList;

    @ManyToOne
    @JoinColumn(name = "etablissement")
    private Etablissement etablissement;

    @ManyToOne
    @JoinColumn(name = "examen")
    private Examen examen;
}
