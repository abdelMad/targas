package com.targas.erp.dao;

import com.targas.erp.models.AnneeScolaire;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IAnneeScolaireRepo extends CrudRepository<AnneeScolaire, Integer> {

    @Query("FROM AnneeScolaire WHERE nomAnneeScolaire=:annes")
    AnneeScolaire findByNomAnneeScolaire(@Param("annes") String annes);
}
