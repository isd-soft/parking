package com.isd.parking.model;

import lombok.Data;
import org.apache.catalina.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity(name = "parking_lots")
@Data
public class ParkingLot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer number;

    private Date updatedAt;

    private ParkingLotStatus status;

//    private boolean reserved;

//    private User user;

}
