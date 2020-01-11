package com.isd.parking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity(name = "statistics")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatsRow {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lot_id")
    private Long lotId;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "parking_lot_status")
    private ParkingLotStatus status;
}
