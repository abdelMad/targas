package com.targas.erp.models;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class MbrScolarite extends Utilisateur {
    @Id @GeneratedValue
    private int id;
//    private List<Demarche> demarche;//liste des demarches que l'utilisateur traite

}
