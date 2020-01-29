package com.isd.parking.service;

import com.isd.parking.model.ParkingLot;
import com.isd.parking.repository.ParkingLotRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


/**
 * Parking Lot Service class for database repository
 * Contains methods for
 * getting all parking lots,
 * get parking lot by id,
 * saving (in this case updating) parking lot
 */
@Service
@Slf4j
public class ParkingLotService {

    private final ParkingLotRepository parkingLotRepository;

    @Autowired
    public ParkingLotService(ParkingLotRepository parkingLotRepository) {
        this.parkingLotRepository = parkingLotRepository;
    }

    /**
     * Get all parking lots from database method
     *
     * @return - Parking lots list
     */
    @Transactional
    public List<ParkingLot> listAll() {

        log.info("Service get all parking lots list executed...");

        return parkingLotRepository.findAll();
    }

    /**
     * Get parking lot by id from database method
     *
     * @return - specified parking lot
     */
    @Transactional
    public Optional<ParkingLot> findById(Long parkingLotId) {

        log.info("Service get parking lot by id executed...");

        return parkingLotRepository.findById(parkingLotId);
    }

    /**
     * Save parking lot in database method
     * Used for update status of parking lot
     *
     * @return - Parking lot which was saved in database
     */
    @Transactional
    public ParkingLot save(ParkingLot parkingLot) {

        log.info("Service save parking lot in database executed...");

        return parkingLotRepository.save(parkingLot);
    }
}

