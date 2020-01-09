package com.isd.parking.model;

import javax.persistence.*;
import java.util.Date;
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

    Date updatedAt;
    ParkingLotStatus status;

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

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ParkingLotStatus getStatus() {
        return status;
    }

    public void setStatus(ParkingLotStatus status) {
        this.status = status;
    }
}
