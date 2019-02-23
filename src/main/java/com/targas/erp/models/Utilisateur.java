package com.targas.erp.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Random;

@Data
@Entity
public class Utilisateur {

    @Id
    @GeneratedValue
    private int id;
    private String nom;
    private String prenom;
    @Column(unique = true)
    private String identifiant;
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

    public String getUserType(){

        String type = "";
        if(this instanceof Etudiant)
            type = "Etudiant";
        else if (this instanceof Enseignant)
            type = "Enseignant";
        return type;
    }

    public String getPhotoPath(){
        if(this.photo != null && !"".equals(this.photo))
            return  this.photo;
        return  "/assets/global/img/people.png";
    }

    /**
     *FORMAT : premiere lettre nom + premiere lettre prenom + chiffre a 5 caractere + lettre random
     * @return identifiant universitaire tels que defini dans la specification
     */
    public String generateIdentifiant(){
        String identifiant = "";
        String nom = this.nom;
        String prenom = this.prenom;
        String firstNom = nom.substring(0,1);
        String firstPrenom = prenom.substring(0,1);
        Random rand = new Random();
        int randomNum = rand.nextInt((99999 - 10000) + 1) + 10000;
        char c = (char)(rand.nextInt(26)+97);
        identifiant = identifiant.concat(firstNom);
        identifiant = identifiant.concat(firstPrenom);
        identifiant = identifiant.concat(Integer.toString(randomNum));
        identifiant = identifiant.concat(Character.toString(c));
        return identifiant.toLowerCase();
    }

    public String generateName(){
        String name = "";
        String CHAR_LIST = "abcdefghijklmnopqrstuvwxyz";
        StringBuffer randStr = new StringBuffer();
        for(int i=0; i<5; i++){
            Random rand = new Random();
            int number = rand.nextInt((99999 - 10000) + 1) + 10000;
            char ch = CHAR_LIST.charAt(number % CHAR_LIST.length());
            randStr = randStr.append(ch);
            name = randStr.toString();
        }
        return name;
    }

}
