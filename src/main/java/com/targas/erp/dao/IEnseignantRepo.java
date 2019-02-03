package com.targas.erp.dao;

import com.targas.erp.models.Enseignant;
import org.springframework.data.repository.CrudRepository;

public interface IEnseignantRepo extends CrudRepository<Enseignant,Integer> {
}
