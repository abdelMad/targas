package com.targas.erp.dao;

import com.targas.erp.models.Utilisateur;
import org.springframework.data.repository.CrudRepository;

public interface IUtilisateurRepo extends CrudRepository<Utilisateur,Integer> {
}
