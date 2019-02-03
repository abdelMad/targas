package com.targas.erp.dao;

import com.targas.erp.models.Seance;
import org.springframework.data.repository.CrudRepository;

public interface ISeanceRepo extends CrudRepository<Seance,Integer> {
}
