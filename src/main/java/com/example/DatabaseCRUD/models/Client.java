package com.example.DatabaseCRUD.models;

import com.example.DatabaseCRUD.dto.ClientDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@Table(name = "client")
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue
    @Column(name = "card_id")
    private int cardId;
    private String name;
    private String address;
    private String phone;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Sale> sales;

    public Client(ClientDTO client) {
        this.name = client.getName();
        this.address = client.getAddress();
        this.phone = client.getPhone();
    }
}
