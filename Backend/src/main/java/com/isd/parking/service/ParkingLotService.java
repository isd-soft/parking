package com.isd.parking.service;

import com.isd.parking.model.ParkingLot;
import com.isd.parking.repository.ParkingLotRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ParkingLotService {

    @Autowired
    private ParkingLotRepo parkingLotRepo;

    @Transactional
    public List<ParkingLot> listAll() {
        log.info("Service get all parking lots list executed...");
        return parkingLotRepo.findAll();
    }

    @Transactional
    public Optional<ParkingLot> findById(Long parkingLotId) {
        log.info("Service get parking lot by id executed...");
        return parkingLotRepo.findById(parkingLotId);
    }

    @Transactional
    public ParkingLot save(ParkingLot parkingLot) {
        log.info("Service save parking lot executed...");
        return parkingLotRepo.save(parkingLot);
    }
}

