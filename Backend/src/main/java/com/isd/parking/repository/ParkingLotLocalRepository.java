package com.isd.parking.repository;

import com.isd.parking.model.ParkingLot;
import lombok.extern.slf4j.Slf4j;
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

    //Local in-memory storage of parking lots
    private HashMap<Long, ParkingLot> parkingMap = new HashMap<>();

    public ParkingLotLocalRepository() {
    }

    /**
     * Get all parking lots method
     *
     * @return - Parking lots list
     */
    public synchronized List<ParkingLot> findAll() {
        List<ParkingLot> parkingLots = new ArrayList<>(parkingMap.values());
        log.info("Parking lots" + parkingLots);
        return parkingLots;
    }

    /**
     * Get parking lot by id method
     *
     * @return - specified parking lot
     */
    public synchronized Optional<ParkingLot> findById(Long parkingLotId) {
        ParkingLot parkingLot = parkingMap.get(parkingLotId);
        log.info("Get parking lot: " + parkingLot);
        return Optional.ofNullable(parkingLot);
    }

    /**
     * Save parking lot in local memory method
     * Used for update status of parking lot
     *
     * @return - Parking lot which was saved in database
     */
    public synchronized ParkingLot save(ParkingLot parkingLot) {

        // if this parking lot exists in local memory storage
        if (parkingMap.containsValue(parkingLot)) {

            ParkingLot updatedParkingLot = parkingMap.get(parkingLot.getId());
            updatedParkingLot.setUpdatedNow();

            parkingMap.put(updatedParkingLot.getId(), updatedParkingLot);
            log.info("Updated parking lot: " + updatedParkingLot);

            return updatedParkingLot;

        } else {
            // add parking lot
            parkingMap.put(parkingLot.getId(), parkingLot);
            log.info("Added parking lot: " + parkingLot);

            return parkingLot;
        }
    }

    //alternative fallback initialization method
    /*private void initParkingMap() {


        for (int i = 1; i <= ParkingNumber.totalParkingLotsNumber; i++) {
            ParkingLot parkingLot = ParkingLot.builder()
                    .id((long) i)
                    .number(i)
                    .status(ParkingLotStatus.FREE)
                    .updatedAt(new Date(System.currentTimeMillis())).build();

            parkingMap.put(parkingLot.getId(), parkingLot);
        }
    }*/

    //alternate methods using list

    //private List<ParkingLot> parkingLotList = new ArrayList<>();

    /* private void initParkingList() {
        Date date = new Date(System.currentTimeMillis());

        for (int i = 0; i < ParkingNumber.totalParkingLotsNumber; i++) {
            ParkingLot parkingLot = ParkingLot.builder()
                    .id((long) i)
                    .number(i)
                    .status(ParkingLotStatus.FREE)
                    .updatedAt(new Date(System.currentTimeMillis())).build();

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
