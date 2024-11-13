package com.example.DatabaseCRUD.models;

import com.example.DatabaseCRUD.dto.FuelDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@Table(name = "fuel")
@NoArgsConstructor
public class Fuel {
    @Id
    @GeneratedValue
    @Column(name = "fuel_id")
    private int fuelId;
    private String type;
    @Column(name = "unit_of_measurement")
    private String unitOfMeasurement;
    private float price;

    @ManyToMany(mappedBy = "fuels")
    @EqualsAndHashCode.Exclude
    private Set<Firm> firms;
    @OneToMany(mappedBy = "fuel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Sale> sales;

    public Fuel(FuelDTO fuel){
        this.type = fuel.getType();
        this.unitOfMeasurement = fuel.getUnitOfMeasurement();
        this.price = fuel.getPrice();
    }

    public void updateFirm(Firm oldFirm, Firm newFirm){
        firms.remove(oldFirm);
        oldFirm.getFuels().remove(this);
        firms.add(newFirm);
        newFirm.getFuels().add(this);
    }
}
