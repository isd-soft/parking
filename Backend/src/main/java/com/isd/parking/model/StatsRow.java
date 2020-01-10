package com.isd.parking.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity(name = "stats")
@Data
public class StatsRow {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "lot_id")
    private ParkingLot lot;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "parking_lot_status")
    private ParkingLotStatus status;
}
