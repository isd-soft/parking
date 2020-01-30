package com.isd.parking.repository;

import com.isd.parking.model.ParkingLot;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;


/**
 * Local Java memory parking lots storage class
 */
public class LocalParkingStorage {

    @Autowired
    private final HashMap<Long, ParkingLot> parkingMap;

    public LocalParkingStorage(HashMap<Long, ParkingLot> parkingMap) {
        this.parkingMap = parkingMap;
    }
}
