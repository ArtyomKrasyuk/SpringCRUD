package com.example.DatabaseCRUD.dto;

import com.example.DatabaseCRUD.models.Firm;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FirmDTO {
    private String address;
    private String phone;
    private String name;

    public FirmDTO(Firm firm){
        this.address = firm.getAddress();
        this.phone = firm.getPhone();
        this.name = firm.getName();
    }
}
