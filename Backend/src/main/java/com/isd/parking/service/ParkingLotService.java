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

        //Template mockup datasource for testing API
        //it will be removed after DB connect

        List<ParkingLot> p = new ArrayList<>();
        Date date = new Date(System.currentTimeMillis());

        ParkingLot parkingLot = new ParkingLot();
        UUID uuid = UUID.randomUUID();
        parkingLot.setId(uuid);
        parkingLot.setNumber(1);
        parkingLot.setStatus(ParkingLotStatus.FREE);
        parkingLot.setDate(date);

        ParkingLot parkingLot1 = new ParkingLot();
        UUID uuid1 = UUID.randomUUID();
        parkingLot1.setId(uuid1);
        parkingLot1.setNumber(2);
        parkingLot1.setStatus(ParkingLotStatus.FREE);
        parkingLot1.setDate(date);

        ParkingLot parkingLot2 = new ParkingLot();
        UUID uuid2 = UUID.randomUUID();
        parkingLot2.setId(uuid2);
        parkingLot2.setNumber(3);
        parkingLot2.setStatus(ParkingLotStatus.FREE);
        parkingLot2.setDate(date);

        ParkingLot parkingLot3 = new ParkingLot();
        UUID uuid3 = UUID.randomUUID();
        parkingLot3.setId(uuid3);
        parkingLot3.setNumber(4);
        parkingLot3.setStatus(ParkingLotStatus.OCCUPIED);
        parkingLot3.setDate(date);

        ParkingLot parkingLot4 = new ParkingLot();
        UUID uuid4 = UUID.randomUUID();
        parkingLot4.setId(uuid4);
        parkingLot4.setNumber(5);
        parkingLot4.setStatus(ParkingLotStatus.OCCUPIED);
        parkingLot4.setDate(date);

        ParkingLot parkingLot5 = new ParkingLot();
        UUID uuid5 = UUID.randomUUID();
        parkingLot5.setId(uuid5);
        parkingLot5.setNumber(6);
        parkingLot5.setStatus(ParkingLotStatus.FREE);
        parkingLot5.setDate(date);

        ParkingLot parkingLot6 = new ParkingLot();
        UUID uuid6 = UUID.randomUUID();
        parkingLot6.setId(uuid6);
        parkingLot6.setNumber(7);
        parkingLot6.setStatus(ParkingLotStatus.OCCUPIED);
        parkingLot6.setDate(date);

        ParkingLot parkingLot7 = new ParkingLot();
        UUID uuid7 = UUID.randomUUID();
        parkingLot7.setId(uuid7);
        parkingLot7.setNumber(8);
        parkingLot7.setStatus(ParkingLotStatus.FREE);
        parkingLot7.setDate(date);

        ParkingLot parkingLot8 = new ParkingLot();
        UUID uuid8 = UUID.randomUUID();
        parkingLot8.setId(uuid8);
        parkingLot8.setNumber(9);
        parkingLot8.setStatus(ParkingLotStatus.FREE);
        parkingLot8.setDate(date);

        ParkingLot parkingLot9 = new ParkingLot();
        UUID uuid9 = UUID.randomUUID();
        parkingLot9.setId(uuid9);
        parkingLot9.setNumber(10);
        parkingLot9.setStatus(ParkingLotStatus.OCCUPIED);
        parkingLot9.setDate(date);        

        p.add(parkingLot);
        p.add(parkingLot1);
        p.add(parkingLot2);
        p.add(parkingLot3);
        p.add(parkingLot4);
        p.add(parkingLot5);
        p.add(parkingLot6);
        p.add(parkingLot7);
        p.add(parkingLot8);
        p.add(parkingLot9);
        return p;

        // return parkingLotRepo.findAll();
    }
}
