package com.targas.erp.dao;

import com.targas.erp.models.Affectation;
import com.targas.erp.models.Cours;
import com.targas.erp.models.Enseignant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IAffectationRepo extends CrudRepository<Affectation, Integer> {

//    @Query("SELECT e FROM Enseignant e")
//    List<Enseignant> findAllAfffectations();

    @Query("SELECT DISTINCT e FROM Enseignant e JOIN Affectation af ON af.enseignant=e JOIN Cours c ON af.cours=c JOIN AnneeScolaire a ON af.anneeScolaire=a WHERE a.nomAnneeScolaire=:anneeS")
    List<Enseignant> listAllEnseignantsAff(@Param("anneeS") String anneeS);


}
