package com.targas.erp.dao;

import com.targas.erp.models.Cours;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ICoursRepo extends CrudRepository<Cours, Integer> {
    @Query("SELECT c FROM Cours c  WHERE c.deleted=false")
    List<Cours> listAll();
}
