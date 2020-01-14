package com.isd.parking.service;

import com.isd.parking.model.ParkingLot;
import com.isd.parking.repository.ParkingLotLocalRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ParkingLotLocalService {

    @Autowired
    private ParkingLotLocalRepo parkingLotLocalRepo;

    public List<ParkingLot> listAll() {

        log.info("Local parking lot service get all parking lots list executed...");

        return parkingLotLocalRepo.findAll();
    }

    public Optional<ParkingLot> findById(Long parkingLotId) {

        log.info("Local parking lot service get parking lot by id executed...");

        return parkingLotLocalRepo.findById(parkingLotId);
    }

    public ParkingLot save(ParkingLot parkingLot) {

        log.info("Local parking lot service save parking lot executed...");

        return parkingLotLocalRepo.save(parkingLot);
    }
}
