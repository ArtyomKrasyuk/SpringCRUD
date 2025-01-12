# CRUD приложение на Spring boot

Данный проект является учебным проектом для взаимодействия с базой данных сети автозаправок. Проект реализует возможности создания, чтения, обновления и удаления данных в таблицах.

Также в проекте реализованы различные виды связей таблиц: many to many, one to many, many to one.

Взаимодействие с данными происходит через хендлеры контроллера, выполненные в соответствии с REST API.

Проект содержит 5 моделей таблиц: Client, Firm, Fuel, GasStation, Sale. Также в базе данных создаётся ещё таблица firm_and_fuel, образованная с помощью связи many to many.

# Технологии
- Java
- Spring boot
- Spring Data JPA
- PostgreSQL
- Lombok

Пример класса сущности базы данных:
```java
@Data
@Entity
@Table(name = "firm")
@NoArgsConstructor
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
    @EqualsAndHashCode.Exclude
    private Set<Fuel> fuels;
    @OneToMany(mappedBy = "firm", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private Set<GasStation> gasStations;
    @OneToMany(mappedBy = "firm", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
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
```

Хендлеры для взаимодействия с сущностью Firm:
```java
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
```
