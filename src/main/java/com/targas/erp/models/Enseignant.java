package com.targas.erp.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Enseignant extends Utilisateur {
    @Id
    @GeneratedValue
    private int id;
//
//    @Column(unique = true)
//    private String matricule;

    @OneToMany(mappedBy = "enseignant")
    private List<Seance> seanceList;// list de seance

    @ManyToMany
    @JoinTable(name = "examen_enseignant",
            joinColumns = @JoinColumn(name = "surveillants"),
            inverseJoinColumns = @JoinColumn(name = "examenList"))
    private List<Examen> examenList;

    @OneToMany(mappedBy = "enseignant")
    private List<Affectation> affectations;

//    public String getJsonCours() {
//        JSONArray jsonArray = new JSONArray();
//        for (Cours cour : cours) {
//            jsonArray.put(cour.getId());
//        }
//        return jsonArray.toString();
//    }
}
