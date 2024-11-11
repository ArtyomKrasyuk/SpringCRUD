package com.example.DatabaseCRUD.dto;

import com.example.DatabaseCRUD.models.Fuel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FuelDTO {
    private String type;
    private String unitOfMeasurement;
    private float price;

    public FuelDTO(Fuel fuel){
        this.type = fuel.getType();
        this.unitOfMeasurement = fuel.getUnitOfMeasurement();
        this.price = fuel.getPrice();
    }
}
