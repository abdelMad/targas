package com.targas.erp.dao;

import com.targas.erp.models.Etablissement;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IEtablissementRepo extends CrudRepository<Etablissement,Integer> {

    @Query("SELECT e FROM Etablissement e WHERE e.deleted=false ")
    List<Etablissement> listAll();
}
