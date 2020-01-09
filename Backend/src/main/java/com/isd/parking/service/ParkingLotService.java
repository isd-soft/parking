package com.isd.parking.service;

import com.isd.parking.model.ParkingLot;
import com.isd.parking.repository.ParkingLotRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingLotService {

    @Autowired
    private ParkingLotRepo parkingLotRepo;

    public List<ParkingLot> listAll() {
        return parkingLotRepo.findAll();
    }

    public Optional<ParkingLot> findById(Long parkingLotId) {
        return parkingLotRepo.findById(parkingLotId);
    }

    public ParkingLot save(ParkingLot parkingLot) {
        return parkingLotRepo.save(parkingLot);
    }
}

