package com.targas.erp.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class ContenuCours {
    @Id @GeneratedValue
    private int id;

    private String nomContenu;

    private String type;

    private String contenu;

    @ManyToOne
    @JoinColumn(name = "cours")
    private Cours cours;
}
