package com.targas.erp.dao;

import com.targas.erp.models.Cours;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICoursRepo extends CrudRepository<Cours, Integer> {
    @Query("SELECT c FROM Cours c  WHERE c.deleted=false")
    List<Cours> listAll();

    @Query(value = "SELECT DISTINCT cc.* FROM Cours cc WHERE id <> ALL(SELECT DISTINCT c.id FROM Cours c JOIN affectation af ON c.id=af.cours WHERE af.enseignant = :idEnseignant ) AND deleted = 0"
            , nativeQuery = true)
    List<Cours> coursRestantEnseignant(@Param("idEnseignant") int id);
    @Query(value = "SELECT DISTINCT c.* FROM cours c WHERE c.id <> ALL( SELECT ce.cours FROM cours_eleves ce WHERE ce.eleves = :idGrp) AND c.deleted = 0"
            , nativeQuery = true)
    List<Cours> coursRestantGroupe(@Param("idGrp") int id);

}
