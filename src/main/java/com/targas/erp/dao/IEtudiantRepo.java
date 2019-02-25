package com.targas.erp.dao;

import com.targas.erp.models.Etudiant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IEtudiantRepo extends CrudRepository<Etudiant,Integer> {

}
