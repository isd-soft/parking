package com.staxrt.tutorial.model;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "stats")
public class StatisticRow {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @ManyToOne(optional = false)
//    @JoinColumn(name = "lot_id",
//            referencedColumnName = "product_id")
    ParkingLot lot;
    Status status;

    public StatisticRow() {
    }

    public StatisticRow(ParkingLot lot, Status status) {
        this.status = status;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
