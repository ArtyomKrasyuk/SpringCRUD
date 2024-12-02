package com.example.DatabaseCRUD.controllers;

import com.example.DatabaseCRUD.dto.*;
import com.example.DatabaseCRUD.models.*;
import com.example.DatabaseCRUD.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@Controller
@CrossOrigin
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
    @GetMapping("/tables/client/{clientId}")
    @ResponseBody
    public ClientDTO getClient(@PathVariable int clientId){
        Client client = clientRep.findById(clientId).orElseThrow();
        return new ClientDTO(client);
    }

    @PostMapping("/tables/client")
    public ResponseEntity<?> insertClient(@RequestBody ClientDTO client){
        clientRep.save(new Client(client));
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/tables/client/{clientId}")
    public ResponseEntity<?>  updateClient(@PathVariable int clientId, @RequestBody ClientDTO clientDTO){
        Client client = clientRep.findById(clientId).orElseThrow();
        client.setName(clientDTO.getName());
        client.setAddress(clientDTO.getAddress());
        client.setPhone(clientDTO.getPhone());
        clientRep.save(client);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/tables/client/{clientId}")
    public ResponseEntity<?>  deleteClient(@PathVariable int clientId){
        clientRep.deleteById(clientId);
        return ResponseEntity.ok().build();
    }
    //-----------------------------------------------Firm------------------------------------------------------
    @GetMapping("/tables/firm/{firmId}")
    @ResponseBody
    public FirmDTO getFirm(@PathVariable int firmId){
        Firm firm = firmRep.findById(firmId).orElseThrow();
        return new FirmDTO(firm);
    }

    @PostMapping("/tables/firm")
    public ResponseEntity<?>  insertFirm(@RequestBody FirmDTO firm){
        firmRep.save(new Firm(firm));
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/tables/firm/{firmId}")
    public ResponseEntity<?>  updateFirm(@PathVariable int firmId, @RequestBody FirmDTO firmDTO){
        Firm firm = firmRep.findById(firmId).orElseThrow();
        firm.setAddress(firmDTO.getAddress());
        firm.setPhone(firmDTO.getPhone());
        firm.setName(firmDTO.getName());
        firmRep.save(firm);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/tables/firm/{firmId}")
    public ResponseEntity<?>  deleteFirm(@PathVariable int firmId){
        firmRep.deleteById(firmId);
        return ResponseEntity.ok().build();
    }
    //-----------------------------------------------Fuel------------------------------------------------------
    @GetMapping("/tables/fuel/{fuelId}")
    @ResponseBody
    public FuelDTO getFuel(@PathVariable int fuelId){
        Fuel fuel = fuelRep.findById(fuelId).orElseThrow();
        return new FuelDTO(fuel);
    }

    @PostMapping("/tables/fuel")
    public ResponseEntity<?>  insertFuel(@RequestBody FuelDTO fuel){
        fuelRep.save(new Fuel(fuel));
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/tables/fuel/{fuelId}")
    public ResponseEntity<?>  updateFuel(@PathVariable int fuelId, @RequestBody FuelDTO fuelDTO){
        Fuel fuel = fuelRep.findById(fuelId).orElseThrow();
        fuel.setType(fuelDTO.getType());
        fuel.setUnitOfMeasurement(fuelDTO.getUnitOfMeasurement());
        fuel.setPrice(fuelDTO.getPrice());
        fuelRep.save(fuel);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/tables/fuel/{fuelId}")
    public ResponseEntity<?>  deleteFuel(@PathVariable int fuelId){
        fuelRep.deleteById(fuelId);
        return ResponseEntity.ok().build();
    }
    //--------------------------------------------Firm has Fuel-------------------------------------------------
    @GetMapping("/tables/firmHasFuel/{firmId}")
    @ResponseBody
    public ArrayList<FuelDTO> readFirmHasFuel(@PathVariable int firmId){
        Firm firm = firmRep.findById(firmId).orElseThrow();
        var fuels = new ArrayList<FuelDTO>();
        firm.getFuels().forEach(elem -> fuels.add(new FuelDTO(elem)));
        return fuels;
    }

    @PostMapping("/tables/firmHasFuel")
    public ResponseEntity<?>  insertFirmHasFuel(@RequestBody FirmHasFuelDTO dto){
        Firm firm = firmRep.findById(dto.getFirmId()).orElseThrow();
        Fuel fuel = fuelRep.findById(dto.getFuelId()).orElseThrow();
        firm.addFuel(fuel);
        firmRep.save(firm);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/tables/firmHasFuel")
    public ResponseEntity<?>  updateFirmHasFuel(@RequestParam int firmId, @RequestParam int fuelId, @RequestBody FirmHasFuelDTO dto){
        Fuel oldFuel = fuelRep.findById(fuelId).orElseThrow();
        Firm oldFirm = firmRep.findById(firmId).orElseThrow();
        if(dto.getFirmId() != firmId && dto.getFuelId() == fuelId){
            Firm newFirm = firmRep.findById(dto.getFirmId()).orElseThrow();
            oldFuel.updateFirm(oldFirm, newFirm);
            firmRep.save(newFirm);
        }
        else if(dto.getFirmId() == firmId && dto.getFuelId() != fuelId){
            Fuel newFuel = fuelRep.findById(dto.getFuelId()).orElseThrow();
            oldFirm.updateFuel(oldFuel, newFuel);
            firmRep.save(oldFirm);
        }
        else if(dto.getFirmId() != firmId && dto.getFuelId() != fuelId){
            Firm newFirm = firmRep.findById(dto.getFirmId()).orElseThrow();
            Fuel newFuel = fuelRep.findById(dto.getFuelId()).orElseThrow();
            oldFirm.removeFuel(oldFuel);
            newFirm.addFuel(newFuel);
            firmRep.save(newFirm);
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/tables/firmHasFuel")
    public ResponseEntity<?>  deleteFirmHasFuel(@RequestParam int firmId, @RequestParam int fuelId){
        Firm firm = firmRep.findById(firmId).orElseThrow();
        Fuel fuel = fuelRep.findById(fuelId).orElseThrow();
        firm.removeFuel(fuel);
        firmRep.save(firm);
        return ResponseEntity.ok().build();
    }
    //-----------------------------------------------Gas Station-------------------------------------------------
    @GetMapping("/tables/gasStation/{gasStationId}")
    @ResponseBody
    public GasStationDTO getGasStation(@PathVariable int gasStationId){
        GasStation gasStation = gasStationRep.findById(gasStationId).orElseThrow();
        return new GasStationDTO(gasStation);
    }

    @PostMapping("/tables/gasStation")
    public ResponseEntity<?>  insertGasStation(@RequestBody GasStationDTO dto){
        Firm firm = firmRep.findById(dto.getFirmId()).orElseThrow();
        GasStation station = new GasStation(dto.getAddress(), firm);
        gasStationRep.save(station);
        firm.getGasStations().add(station);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/tables/gasStation/{gasStationId}")
    public ResponseEntity<?>  updateGasStation(@PathVariable int gasStationId, @RequestBody GasStationDTO dto){
        Firm firm = firmRep.findById(dto.getFirmId()).orElseThrow();
        GasStation station = gasStationRep.findById(gasStationId).orElseThrow();
        station.setAddress(dto.getAddress());
        if(!station.getFirm().equals(firm)) station.updateFirm(firm);
        gasStationRep.save(station);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/tables/gasStation/{gasStationId}")
    public ResponseEntity<?>  deleteGasStation(@PathVariable int gasStationId){
        GasStation station = gasStationRep.findById(gasStationId).orElseThrow();
        station.getFirm().getGasStations().remove(station);
        gasStationRep.delete(station);
        return ResponseEntity.ok().build();
    }
    //-----------------------------------------------Sale-------------------------------------------------------
    @GetMapping("/tables/sale/{saleId}")
    @ResponseBody
    public SaleDTO getSale(@PathVariable int saleId){
        Sale sale = saleRep.findById(saleId).orElseThrow();
        return new SaleDTO(sale);
    }

    @PostMapping("/tables/sale")
    public ResponseEntity<?>  insertSale(@RequestBody SaleDTO dto){
        Firm firm = firmRep.findById(dto.getFirmId()).orElseThrow();
        Fuel fuel = fuelRep.findById(dto.getFuelId()).orElseThrow();
        GasStation gasStation = gasStationRep.findById(dto.getGasStationId()).orElseThrow();
        Client client = clientRep.findById(dto.getCardId()).orElseThrow();
        Sale sale = new Sale(dto.getLiters(), dto.getSaleDate(), firm, fuel, gasStation, client);
        saleRep.save(sale);
        firm.getSales().add(sale);
        fuel.getSales().add(sale);
        gasStation.getSales().add(sale);
        client.getSales().add(sale);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/tables/sale/{saleId}")
    public ResponseEntity<?>  updateSale(@PathVariable int saleId, @RequestBody SaleDTO dto){
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
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/tables/sale/{saleId}")
    public ResponseEntity<?>  deleteSale(@PathVariable int saleId){
        Sale sale = saleRep.findById(saleId).orElseThrow();
        sale.getFirm().getSales().remove(sale);
        sale.getFuel().getSales().remove(sale);
        sale.getGasStation().getSales().remove(sale);
        sale.getClient().getSales().remove(sale);
        saleRep.delete(sale);
        return ResponseEntity.ok().build();
    }
}
