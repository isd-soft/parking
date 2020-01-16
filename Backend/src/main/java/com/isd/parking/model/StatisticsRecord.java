package com.isd.parking.model;

import com.isd.parking.model.enums.ParkingLotStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "statistics")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsRecord {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lot_number")
    private Integer lotNumber;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "parking_lot_status")
    private ParkingLotStatus status;
}
