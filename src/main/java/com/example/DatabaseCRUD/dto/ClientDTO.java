package com.example.DatabaseCRUD.dto;

import com.example.DatabaseCRUD.models.Client;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientDTO {
    private String name;
    private String address;
    private String phone;

    public ClientDTO(Client client){
        this.name = client.getName();
        this.address = client.getAddress();
        this.phone = client.getPhone();
    }
}
