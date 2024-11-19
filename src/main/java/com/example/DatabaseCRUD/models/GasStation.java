package com.example.DatabaseCRUD.models;

import com.example.DatabaseCRUD.dto.GasStationDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@Entity
@Table(name = "gas_station")
@NoArgsConstructor
public class GasStation {
    @Id
    @GeneratedValue
    @Column(name = "gas_station_id")
    private int gasStationId;
    private String address;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "firm_id", referencedColumnName = "firm_id")
    private Firm firm;
    @OneToMany(mappedBy = "gasStation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private Set<Sale> sales;

    public GasStation(String address, Firm firm){
        this.address = address;
        this.firm = firm;
    }

    public void updateFirm(Firm firm){
        this.firm.getGasStations().remove(this);
        this.firm = firm;
        this.firm.getGasStations().add(this);
    }
}
