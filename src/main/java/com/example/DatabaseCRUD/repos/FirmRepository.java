package com.example.DatabaseCRUD.repos;


import com.example.DatabaseCRUD.models.Firm;
import org.springframework.data.repository.CrudRepository;

public interface FirmRepository extends CrudRepository<Firm, Integer> {
}
