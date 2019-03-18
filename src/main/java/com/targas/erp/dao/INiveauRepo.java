package com.targas.erp.dao;

import com.targas.erp.models.Niveau;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface INiveauRepo extends CrudRepository<Niveau,Integer> {

    @Query("FROM Niveau  WHERE deleted = false ")
    List<Niveau> listAll();
}
