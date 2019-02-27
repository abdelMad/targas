package com.targas.erp.dao;

import com.targas.erp.models.Filiere;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IFiliereRepo extends CrudRepository<Filiere, Integer> {

    @Query("SELECT f FROM Filiere f  WHERE f.deleted=false")
    List<Filiere> listAll();
}
