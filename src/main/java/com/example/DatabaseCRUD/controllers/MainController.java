package com.example.DatabaseCRUD.controllers;

import com.example.DatabaseCRUD.dto.*;
import com.example.DatabaseCRUD.models.*;
import com.example.DatabaseCRUD.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class MainController {
    @Autowired
    private ClientRepository clientRep;
    @Autowired
    private FirmRepository firmRep;
    @Autowired
    private FuelRepository fuelRep;
    @Autowired
    private GasStationRepository gasStationRep;
    @Autowired
    private SaleRepository saleRep;

    //-----------------------------------------------Client----------------------------------------------------
    @PostMapping("/tables/client")
    public void insertClient(@RequestBody ClientDTO client){
        clientRep.save(new Client(client));
    }

    @PatchMapping("/tables/client/{clientId}")
    public void updateClient(@PathVariable int clientId, @RequestBody ClientDTO clientDTO){
        Client client = clientRep.findById(clientId).orElseThrow();
        client.setName(clientDTO.getName());
        client.setAddress(clientDTO.getAddress());
        client.setPhone(clientDTO.getPhone());
        clientRep.save(client);
    }
    @DeleteMapping("/tables/client/{clientId}")
    public void deleteClient(@PathVariable int clientId){
        clientRep.deleteById(clientId);
    }
    //-----------------------------------------------Firm------------------------------------------------------
    @PostMapping("/tables/firm")
    public void insertFirm(@RequestBody FirmDTO firm){
        firmRep.save(new Firm(firm));
    }

    @PatchMapping("/tables/firm/{firmId}")
    public void updateFirm(@PathVariable int firmId, @RequestBody FirmDTO firmDTO){
        Firm firm = firmRep.findById(firmId).orElseThrow();
        firm.setAddress(firmDTO.getAddress());
        firm.setPhone(firmDTO.getPhone());
        firm.setName(firmDTO.getName());
        firmRep.save(firm);
    }
    @DeleteMapping("/tables/firm/{firmId}")
    public void deleteFirm(@PathVariable int firmId){
        firmRep.deleteById(firmId);
    }
    //-----------------------------------------------Fuel------------------------------------------------------
    @PostMapping("/tables/fuel")
    public void insertFuel(@RequestBody FuelDTO fuel){
        fuelRep.save(new Fuel(fuel));
    }

    @PatchMapping("/tables/fuel/{fuelId}")
    public void updateFuel(@PathVariable int fuelId, @RequestBody FuelDTO fuelDTO){
        Fuel fuel = fuelRep.findById(fuelId).orElseThrow();
        fuel.setType(fuelDTO.getType());
        fuel.setUnitOfMeasurement(fuelDTO.getUnitOfMeasurement());
        fuel.setPrice(fuelDTO.getPrice());
        fuelRep.save(fuel);
    }
    @DeleteMapping("/tables/fuel/{fuelId}")
    public void deleteFuel(@PathVariable int fuelId){
        fuelRep.deleteById(fuelId);
    }
    //--------------------------------------------Firm has Fuel-------------------------------------------------
    @PostMapping("/tables/firmHasFuel")
    public void insertFirmHasFuel(@RequestBody FirmHasFuelDTO dto){
        Firm firm = firmRep.findById(dto.getFirmId()).orElseThrow();
        Fuel fuel = fuelRep.findById(dto.getFuelId()).orElseThrow();
        firm.addFuel(fuel);
    }

    @PatchMapping("/tables/firmHasFuel")
    public void updateFirmHasFuel(@RequestParam int firmId, @RequestParam int fuelId, @RequestBody FirmHasFuelDTO dto){
        Fuel oldFuel = fuelRep.findById(fuelId).orElseThrow();
        Firm oldFirm = firmRep.findById(firmId).orElseThrow();
        if(dto.getFirmId() != firmId && dto.getFuelId() == fuelId){
            Firm newFirm = firmRep.findById(dto.getFirmId()).orElseThrow();
            oldFuel.updateFirm(oldFirm, newFirm);
        }
        else if(dto.getFirmId() == firmId && dto.getFuelId() != fuelId){
            Fuel newFuel = fuelRep.findById(dto.getFuelId()).orElseThrow();
            oldFirm.updateFuel(oldFuel, newFuel);
        }
        else if(dto.getFirmId() != firmId && dto.getFuelId() != fuelId){
            Firm newFirm = firmRep.findById(dto.getFirmId()).orElseThrow();
            Fuel newFuel = fuelRep.findById(dto.getFuelId()).orElseThrow();
            oldFirm.removeFuel(oldFuel);
            newFirm.addFuel(newFuel);
        }
    }

    @DeleteMapping("/tables/firmHasFuel")
    public void deleteFirmHasFuel(@RequestParam int firmId, @RequestParam int fuelId){
        Firm firm = firmRep.findById(firmId).orElseThrow();
        Fuel fuel = fuelRep.findById(fuelId).orElseThrow();
        firm.removeFuel(fuel);
    }
    //-----------------------------------------------Gas Station-------------------------------------------------
    @PostMapping("/tables/gasStation")
    public void insertGasStation(@RequestBody GasStationDTO dto){
        Firm firm = firmRep.findById(dto.getFirmId()).orElseThrow();
        GasStation station = new GasStation(dto.getAddress(), firm);
        firm.getGasStations().add(station);
        gasStationRep.save(station);
    }

    @PatchMapping("/tables/gasStation/{gasStationId}")
    public void updateGasStation(@RequestParam int gasStationId, @RequestBody GasStationDTO dto){
        Firm firm = firmRep.findById(dto.getFirmId()).orElseThrow();
        GasStation station = gasStationRep.findById(gasStationId).orElseThrow();
        station.setAddress(dto.getAddress());
        if(!station.getFirm().equals(firm)) station.updateFirm(firm);
        gasStationRep.save(station);
    }
    @DeleteMapping("/tables/gasStation/{gasStationId}")
    public void deleteGasStation(@RequestParam int gasStationId){
        GasStation station = gasStationRep.findById(gasStationId).orElseThrow();
        station.getFirm().getGasStations().remove(station);
        gasStationRep.delete(station);
    }
    //-----------------------------------------------Sale-------------------------------------------------------
    @PostMapping("/tables/sale")
    public void insertSale(@RequestBody SaleDTO dto){
        Firm firm = firmRep.findById(dto.getFirmId()).orElseThrow();
        Fuel fuel = fuelRep.findById(dto.getFuelId()).orElseThrow();
        GasStation gasStation = gasStationRep.findById(dto.getGasStationId()).orElseThrow();
        Client client = clientRep.findById(dto.getCardId()).orElseThrow();
        saleRep.save(new Sale(dto.getLiters(), dto.getSaleDate(), firm, fuel, gasStation, client));
    }

    @PatchMapping("/tables/sale/{saleId}")
    public void updateSale(@PathVariable int saleId, @RequestBody SaleDTO dto){
        Firm firm = firmRep.findById(dto.getFirmId()).orElseThrow();
        Fuel fuel = fuelRep.findById(dto.getFuelId()).orElseThrow();
        GasStation gasStation = gasStationRep.findById(dto.getGasStationId()).orElseThrow();
        Client client = clientRep.findById(dto.getCardId()).orElseThrow();
        Sale sale = saleRep.findById(saleId).orElseThrow();
        sale.setLiters(dto.getLiters());
        sale.setSaleDate(dto.getSaleDate());
        if(!sale.getFirm().equals(firm)) sale.updateFirm(firm);
        if(!sale.getFuel().equals(fuel)) sale.updateFuel(fuel);
        if(!sale.getGasStation().equals(gasStation)) sale.updateGasStation(gasStation);
        if(!sale.getClient().equals(client)) sale.updateClient(client);
        saleRep.save(sale);
    }

    @DeleteMapping("/tables/sale/{saleId}")
    public void deleteSale(@PathVariable int saleId){
        Sale sale = saleRep.findById(saleId).orElseThrow();
        sale.getFirm().getSales().remove(sale);
        sale.getFuel().getSales().remove(sale);
        sale.getGasStation().getSales().remove(sale);
        sale.getClient().getSales().remove(sale);
        saleRep.delete(sale);
    }
}
