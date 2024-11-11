package com.example.DatabaseCRUD.dto;

import com.example.DatabaseCRUD.models.GasStation;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GasStationDTO {
    private int firmId;
    private String address;

    public GasStationDTO(GasStation gasStation){
        this.firmId = gasStation.getFirm().getFirmId();
        this.address = gasStation.getAddress();
    }
}
