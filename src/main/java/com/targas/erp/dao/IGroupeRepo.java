package com.targas.erp.dao;

import com.targas.erp.models.Groupe;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IGroupeRepo extends CrudRepository<Groupe,Integer> {

    @Query("SELECT g FROM Groupe g JOIN Filiere f ON g.filiere=f LEFT JOIN g.cours WHERE  g.deleted=false")
    List<Groupe> listAll();
}
