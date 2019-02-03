package com.targas.erp.dao;

import com.targas.erp.models.Examen;
import org.springframework.data.repository.CrudRepository;

public interface IExamenRepo extends CrudRepository<Examen, Integer> {
}
