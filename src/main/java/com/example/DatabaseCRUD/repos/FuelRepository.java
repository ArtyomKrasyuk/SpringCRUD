package com.example.DatabaseCRUD.repos;

import com.example.DatabaseCRUD.models.Fuel;
import org.springframework.data.repository.CrudRepository;

public interface FuelRepository extends CrudRepository<Fuel, Integer> {
}
