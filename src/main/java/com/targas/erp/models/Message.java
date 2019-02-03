package com.targas.erp.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Message {
    @Id @GeneratedValue
    private int id;
    private String objet;//objet du message
    private String message;//le message
    private Date date;//date d'envoi

    @ManyToOne
    @JoinColumn(name = "emetteur")
    private Utilisateur emetteur;//qui envoie ?

    @ManyToMany(mappedBy = "recus")
    private List<Utilisateur> recepteurs;//qui recoit le message ?
}

