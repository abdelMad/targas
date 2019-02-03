package com.targas.erp.dao;

import com.targas.erp.models.Cours;
import org.springframework.data.repository.CrudRepository;

public interface ICoursRepo extends CrudRepository<Cours,Integer> {
}
