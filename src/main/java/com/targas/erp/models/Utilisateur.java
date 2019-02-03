package com.targas.erp.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Utilisateur {

    @Id
    @GeneratedValue
    private int id;
    private String nom;
    private String prenom;
    private String adresse;
    @Column(unique = true)
    private String email;
    private String mdp;
    private String photo;
    private String description;

    @ManyToMany
    @JoinTable(name = "recepteur_message",
            joinColumns = @JoinColumn(name = "recepteur"),
            inverseJoinColumns = @JoinColumn(name = "recus"))
    private List<Message> recus;//liste des message recu

    @OneToMany(mappedBy = "emetteur")
    private List<Message> envoyes;//liste des message envoyes

}
