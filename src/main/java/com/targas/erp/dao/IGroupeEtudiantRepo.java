package com.targas.erp.dao;

import com.targas.erp.models.GroupeEtudiant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IGroupeEtudiantRepo extends CrudRepository<GroupeEtudiant, Integer> {

    @Query("SELECT DISTINCT  ge FROM GroupeEtudiant ge JOIN AnneeScolaire ass ON ge.anneeScolaire=ass  WHERE  ass.nomAnneeScolaire=:anneeS")
    List<GroupeEtudiant> listAllGroupeEtudiant(@Param("anneeS") String anneeS);


    @Query("SELECT COUNT (ge) FROM GroupeEtudiant  ge JOIN Groupe g ON ge.groupe=g JOIN AnneeScolaire a ON ge.anneeScolaire=a JOIN Niveau n ON ge.niveau=n " +
            " WHERE g.id=:grp AND a.nomAnneeScolaire=:ass AND n.id=:niv")
    Integer grpExists(@Param("grp") int grp, @Param("ass") String as, @Param("niv") int niv);
}
