package com.targas.erp.dao;

import com.targas.erp.models.Enseignant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IEnseignantRepo extends CrudRepository<Enseignant,Integer> {

//    @Query("SELECT e FROM Enseignant e")
//    List<Enseignant> findAllAfffectations();
}
