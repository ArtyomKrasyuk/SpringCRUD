package com.example.DatabaseCRUD.dto;

import com.example.DatabaseCRUD.models.Sale;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaleDTO {
    private int cardId;
    private int gasStationId;
    private int firmId;
    private int fuelId;
    private String saleDate;
    private float liters;

    public SaleDTO(Sale sale){
        this.cardId = sale.getClient().getCardId();
        this.gasStationId = sale.getGasStation().getGasStationId();
        this.firmId = sale.getFirm().getFirmId();
        this.fuelId = sale.getFuel().getFuelId();
        this.saleDate = sale.getSaleDate();
        this.liters = sale.getLiters();
    }
}
