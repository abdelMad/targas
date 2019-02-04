package com.targas.erp.dao;

import com.targas.erp.models.Utilisateur;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IUtilisateurRepo extends CrudRepository<Utilisateur,Integer> {
    @Query("SELECT  u FROM Utilisateur u WHERE u.identifiant = (:identifiant) AND u.mdp = (:mdp)")
    Utilisateur findByIdentifiantAndMdp(@Param("identifiant") String identifiant, @Param("mdp") String mdp);
}
