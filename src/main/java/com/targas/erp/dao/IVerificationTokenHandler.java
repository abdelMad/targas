package com.targas.erp.dao;

import com.targas.erp.models.VerificationToken;
import org.springframework.data.repository.CrudRepository;


public interface IVerificationTokenHandler extends CrudRepository<VerificationToken,Integer> {


}