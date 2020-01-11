package com.isd.parking.service;

import com.isd.parking.model.ParkingLot;
import com.isd.parking.repository.ParkingLotRepo;
import com.isd.parking.sheduller.ScheduleStatisticsDeleter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingLotService {

    private static final Logger LOG = LoggerFactory.getLogger(ParkingLotService.class);

    @Autowired
    private ParkingLotRepo parkingLotRepo;

    public List<ParkingLot> listAll() {

        LOG.info("Service get all parking lots list executed...");

        return parkingLotRepo.findAll();
    }

    public Optional<ParkingLot> findById(Long parkingLotId) {

        LOG.info("Service get parking lot by id executed...");

        return parkingLotRepo.findById(parkingLotId);
    }

    public ParkingLot save(ParkingLot parkingLot) {

        LOG.info("Service save parking lot executed...");

        return parkingLotRepo.save(parkingLot);
    }

}

