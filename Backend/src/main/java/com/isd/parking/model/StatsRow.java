package com.isd.parking.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity(name = "stats")
@Data
public class StatsRow {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "lot_id")
    private ParkingLot lot;

    private Date updatedAt;
    private ParkingLotStatus parkingLotStatus;
}
