package com.example.DatabaseCRUD.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "sale")
public class Sale {
    @Id
    @GeneratedValue
    @Column(name = "sale_id")
    private int saleId;
    private float liters;
    @Column(name = "sale_date")
    private String saleDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "firm_id", referencedColumnName = "firm_id")
    private Firm firm;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fuel_id", referencedColumnName = "fuel_id")
    private Fuel fuel;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gas_station_id", referencedColumnName = "gas_station_id")
    private GasStation gasStation;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", referencedColumnName = "card_id")
    private Client client;

    public Sale(float liters, String saleDate, Firm firm, Fuel fuel, GasStation gasStation, Client client){
        this.liters = liters;
        this.saleDate = saleDate;
        this.firm = firm;
        this.fuel = fuel;
        this.gasStation = gasStation;
        this.client = client;
    }

    public void updateFirm(Firm firm){
        this.firm.getSales().remove(this);
        this.firm = firm;
        this.firm.getSales().add(this);
    }

    public void updateFuel(Fuel fuel){
        this.fuel.getSales().remove(this);
        this.fuel = fuel;
        this.fuel.getSales().add(this);
    }

    public void updateGasStation(GasStation gasStation){
        this.gasStation.getSales().remove(this);
        this.gasStation = gasStation;
        this.gasStation.getSales().add(this);
    }

    public void updateClient(Client client){
        this.client.getSales().remove(this);
        this.client = client;
        this.client.getSales().add(this);
    }
}
