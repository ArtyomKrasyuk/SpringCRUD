package com.example.DatabaseCRUD.models;

import com.example.DatabaseCRUD.dto.FirmDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "firm")
public class Firm {
    @Id
    @GeneratedValue
    @Column(name = "firm_id")
    private int firmId;
    private String address;
    private String phone;
    private String name;

    @ManyToMany
    @JoinTable(
            name = "firm_and_fuel",
            joinColumns = @JoinColumn(name = "firm_id"),
            inverseJoinColumns = @JoinColumn(name = "fuel_id")
    )
    private Set<Fuel> fuels;
    @OneToMany(mappedBy = "firm", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<GasStation> gasStations;
    @OneToMany(mappedBy = "firm", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Sale> sales;

    public Firm(FirmDTO firm){
        this.address = firm.getAddress();
        this.phone = firm.getPhone();
        this.name = firm.getName();
    }

    public void addFuel(Fuel fuel){
        fuels.add(fuel);
        fuel.getFirms().add(this);
    }

    public void removeFuel(Fuel fuel){
        fuels.remove(fuel);
        fuel.getFirms().remove(this);
    }

    public void updateFuel(Fuel oldFuel, Fuel newFuel){
        fuels.remove(oldFuel);
        oldFuel.getFirms().remove(this);
        fuels.add(newFuel);
        newFuel.getFirms().add(this);
    }
}
