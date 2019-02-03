package com.targas.erp.dao;

import com.targas.erp.models.Etablissement;
import org.springframework.data.repository.CrudRepository;

public interface IEtablissementRepo extends CrudRepository<Etablissement,Integer> {
}
