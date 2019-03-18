package com.targas.erp.dao;

import com.targas.erp.models.Emploi;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface IEmploiRepo extends CrudRepository<Emploi,Integer> {

    @Query("SELECT COUNT(e) FROM Emploi e WHERE e.dateDebut BETWEEN :dd AND :df OR e.dateFin BETWEEN :dd AND :df")
    Integer existsEmploiByDates(@Param("dd")Date dd,@Param("df")Date df);
}
