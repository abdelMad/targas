package com.targas.erp.dao;

import com.targas.erp.models.Message;
import org.springframework.data.repository.CrudRepository;

public interface IMessageRepo extends CrudRepository<Message,Integer> {
}
