package com.targas.erp.dao;

import com.targas.erp.models.Emploi;
import org.springframework.data.repository.CrudRepository;

public interface IEmploiRepo extends CrudRepository<Emploi,Integer> {
}
