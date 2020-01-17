package com.isd.parking.model;

import lombok.Data;
import org.apache.catalina.User;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "parking_lots")
@DynamicUpdate
@Data
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

//    private boolean reserved;

//    private User user;

}
