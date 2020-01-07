package com.isd.parking.service;

import com.isd.parking.model.ParkingLot;
import com.isd.parking.model.ParkingLotStatus;
import com.isd.parking.repository.ParkingLotRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ParkingLotService {
    @Autowired
    private ParkingLotRepo parkingLotRepo;

    public List<ParkingLot> listAll() {

        List<ParkingLot> p = new ArrayList<>();
        Date date = new Date(System.currentTimeMillis());
        UUID uuid = UUID.randomUUID();

        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setId(uuid);
        parkingLot.setNumber(1);
        parkingLot.setStatus(ParkingLotStatus.FREE);
        parkingLot.setDate(date);

        ParkingLot parkingLot1 = new ParkingLot();
        parkingLot1.setId(uuid);
        parkingLot1.setNumber(2);
        parkingLot1.setStatus(ParkingLotStatus.OCCUPIED);
        parkingLot1.setDate(date);

        ParkingLot parkingLot2 = new ParkingLot();
        parkingLot2.setId(uuid);
        parkingLot2.setNumber(3);
        parkingLot2.setStatus(ParkingLotStatus.UNKNOWN);
        parkingLot2.setDate(date);

        p.add(parkingLot);
        p.add(parkingLot1);
        p.add(parkingLot2);
        return p;

        // return parkingLotRepo.findAll();
    }
}
