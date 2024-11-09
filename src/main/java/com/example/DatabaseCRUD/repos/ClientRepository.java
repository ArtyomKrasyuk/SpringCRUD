package com.example.DatabaseCRUD.repos;

import com.example.DatabaseCRUD.models.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Integer> {

}
