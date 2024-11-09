package com.example.DatabaseCRUD.repos;

import com.example.DatabaseCRUD.models.Sale;
import org.springframework.data.repository.CrudRepository;

public interface SaleRepository extends CrudRepository<Sale, Integer> {
}
