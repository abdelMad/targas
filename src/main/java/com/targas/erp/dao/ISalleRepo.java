package com.targas.erp.dao;

import com.targas.erp.models.Salle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ISalleRepo extends CrudRepository<Salle,Integer> {

    @Query("SELECT s FROM Salle s JOIN Etablissement e ON s.etablissement = e WHERE s.deleted=false")
    List<Salle> listAll();
}
