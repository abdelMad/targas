package com.targas.erp.models;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Emploi {
    @Id @GeneratedValue
    private int id;
    private String nom;
    private Date dateDebut;
    private Date dateFin;

    @OneToMany(mappedBy = "emploi")
    private List<Seance> listeSeance;
}
