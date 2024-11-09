package com.example.DatabaseCRUD.dto;

import lombok.Data;

@Data
public class SaleDTO {
    private int cardId;
    private int gasStationId;
    private int firmId;
    private int fuelId;
    private String saleDate;
    private float liters;
}
