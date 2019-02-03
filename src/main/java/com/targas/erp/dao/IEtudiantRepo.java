package com.targas.erp.dao;

import com.targas.erp.models.Etudiant;
import org.springframework.data.repository.CrudRepository;

public interface IEtudiantRepo extends CrudRepository<Etudiant,Integer> {
}
