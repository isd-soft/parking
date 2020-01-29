package com.isd.parking.repository;

import com.isd.parking.model.ParkingLot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class ParkingLotLocalRepository {

    //Local in-memory storage of parking lots
    private HashMap<Long, ParkingLot> parkingMap = new HashMap<>();

    public ParkingLotLocalRepository() {}

    public synchronized List<ParkingLot> findAll() {

        return new ArrayList<>(parkingMap.values());
    }

    public synchronized Optional<ParkingLot> findById(Long parkingLotId) {
        ParkingLot parkingLot = parkingMap.get(parkingLotId);

        return Optional.ofNullable(parkingLot);
    }

    public synchronized ParkingLot save(ParkingLot parkingLot) {

        if (parkingMap.containsValue(parkingLot)) {

            ParkingLot updatedParkingLot = parkingMap.get(parkingLot.getId());
            updatedParkingLot.setUpdatedNow();

            parkingMap.put(updatedParkingLot.getId(), updatedParkingLot);

            return updatedParkingLot;

        } else {
            parkingMap.put(parkingLot.getId(), parkingLot);

            return parkingLot;
        }
    }
}
