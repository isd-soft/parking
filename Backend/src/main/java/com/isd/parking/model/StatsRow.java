package com.isd.parking.model;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "stats")
public class StatsRow {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @ManyToOne(optional = false)
    //    @JoinColumn(name = "lot_id",
    //            referencedColumnName = "id")
    ParkingLot lot;

    public StatsRow() {
    }

    public StatsRow(ParkingLot lot) {
        this.lot = lot;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ParkingLot getLot() {
        return lot;
    }

    public void setLot(ParkingLot lot) {
        this.lot = lot;
    }

}
