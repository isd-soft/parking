package com.isd.parking.model;

import com.isd.parking.model.enums.ParkingLotStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "parking_lots")
@DynamicUpdate
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLot {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lot_number")
    private Integer number;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "status")
    private ParkingLotStatus status;

    public void setUpdatedNow() {
        this.setUpdatedAt(new java.sql.Date(System.currentTimeMillis()));
    }

}
