package com.targas.erp.dao;

import com.targas.erp.models.GroupeEtudiant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IGroupeEtudiantRepo extends CrudRepository<GroupeEtudiant, Integer> {

    @Query("FROM GroupeEtudiant ge WHERE ge.anneeScolaire=:anneeS")
    List<GroupeEtudiant> listAllGroupeEtudiant(@Param("anneeS") String anneeS);

}
