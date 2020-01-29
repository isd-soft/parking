package com.isd.parking.repository;

import com.isd.parking.model.ParkingLot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


/**
 * Local Java memory parking lots repository class
 */
@Repository
@Slf4j
public class ParkingLotLocalRepository {

    @Value("${parking.lots.number}")
    private String totalParkingLotsNumber;

    //Local in-memory storage of parking lots
    private HashMap<Long, ParkingLot> parkingMap = new HashMap<>();

    public ParkingLotLocalRepository() {}

    /**
     * Get all parking lots method
     *
     * @return - Parking lots list
     */
    public synchronized List<ParkingLot> findAll() {
        return new ArrayList<>(parkingMap.values());
    }

    /**
     * Get parking lot by id method
     *
     * @return - specified parking lot
     */
    public synchronized Optional<ParkingLot> findById(Long parkingLotId) {
        ParkingLot parkingLot = parkingMap.get(parkingLotId);
        return Optional.ofNullable(parkingLot);
    }

    /**
     * Save parking lot in local memory method
     * Used for update status of parking lot
     *
     * @return - Parking lot which was saved in database
     */
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
