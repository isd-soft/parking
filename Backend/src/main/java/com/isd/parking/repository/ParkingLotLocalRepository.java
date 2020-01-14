package com.isd.parking.repository;

import com.isd.parking.model.ParkingLot;
import com.isd.parking.model.ParkingNumber;
import com.isd.parking.model.enums.ParkingLotStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Slf4j
public class ParkingLotLocalRepository {

    //Local in-memory storage of parking lots
    private HashMap<Long, ParkingLot> parkingMap = new HashMap<>();

    public List<ParkingLot> findAll() {
        List<ParkingLot> parkingLots = new ArrayList<>(parkingMap.values());
        log.info("Parking lots" + parkingLots);
        return parkingLots;
    }

    public Optional<ParkingLot> findById(Long parkingLotId) {
        ParkingLot parkingLot = parkingMap.get(parkingLotId);
        log.info("Get parking lot: " + parkingLot);
        return Optional.ofNullable(parkingLot);
    }

    public ParkingLotLocalRepository() {
        initParkingMap();
    }

    private void initParkingMap() {
        Date date = new Date(System.currentTimeMillis());

        for (int i = 1; i <= ParkingNumber.totalParkingLotsNumber; i++) {
            ParkingLot parkingLot = new ParkingLot();
            parkingLot.setId((long) i);
            parkingLot.setNumber(i);
            parkingLot.setStatus(ParkingLotStatus.UNKNOWN);
            parkingLot.setUpdatedAt(date);
            parkingMap.put(parkingLot.getId(), parkingLot);
        }
    }

    public ParkingLot save(ParkingLot parkingLot) {

        if (parkingMap.containsValue(parkingLot)) {

            ParkingLot updatedParkingLot = parkingMap.get(parkingLot.getId());
            updatedParkingLot.setUpdatedAt(new Date(System.currentTimeMillis()));

            parkingMap.put(updatedParkingLot.getId(), updatedParkingLot);

            log.info("Updated parking lot: " + updatedParkingLot);

            return updatedParkingLot;

        } else {
            parkingMap.put(parkingLot.getId(), parkingLot);

            log.info("Added parking lot: " + parkingLot);

            return parkingLot;
        }
    }

    //alternate methods using list

    //private List<ParkingLot> parkingLotList = new ArrayList<>();

    /* private void initParkingList() {
        Date date = new Date(System.currentTimeMillis());

        for (int i = 0; i < ParkingNumber.totalParkingLotsNumber; i++) {
            ParkingLot parkingLot = new ParkingLot();
            parkingLot.setId((long) i);
            parkingLot.setNumber(i);
            parkingLot.setStatus(ParkingLotStatus.UNKNOWN);
            parkingLot.setUpdatedAt(date);

            parkingLotList.add(parkingLot);
        }
    }

    public List<ParkingLot> findAll() {
        return parkingLotList;
    }

    public Optional<ParkingLot> findById(Long parkingLotId) {
        return Optional.ofNullable(parkingLotList.get(Math.toIntExact(parkingLotId)));
    }

    public ParkingLot save(ParkingLot parkingLot) {

        if (parkingLotList.contains(parkingLot)) {
            ParkingLot updatedParkingLot = parkingLotList.get(Math.toIntExact(parkingLot.getId()));
            updatedParkingLot.setUpdatedAt(new Date(System.currentTimeMillis()));

            return updatedParkingLot;

        } else {
            parkingLotList.add(parkingLot);

            return parkingLot;
        }
    }
    */
}
