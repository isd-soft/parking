package com.isd.parking.service;

import com.isd.parking.model.ParkingLot;
import com.isd.parking.model.ParkingLotStatus;
import com.isd.parking.repository.ParkingLotRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingLotService {

    //Template mockup datasource for testing API
    //it will be removed after DB connect
    private List<ParkingLot> p = new ArrayList<>();

    @Autowired
    private ParkingLotRepo parkingLotRepo;

    public ParkingLotService() {
        //initParkingList();
    }

    public List<ParkingLot> listAll() {

        // return p;

         return parkingLotRepo.findAll();
    }

    //mockup method
    private void initParkingList() {
        Date date = new Date(System.currentTimeMillis());

        ParkingLot parkingLot = new ParkingLot();

        parkingLot.setId(1L);
        parkingLot.setNumber(1);
        parkingLot.setStatus(ParkingLotStatus.FREE);
        parkingLot.setUpdatedAt(date);

        ParkingLot parkingLot1 = new ParkingLot();

        parkingLot1.setId(2L);
        parkingLot1.setNumber(2);
        parkingLot1.setStatus(ParkingLotStatus.FREE);
        parkingLot1.setUpdatedAt(date);

        ParkingLot parkingLot2 = new ParkingLot();

        parkingLot2.setId(3L);
        parkingLot2.setNumber(3);
        parkingLot2.setStatus(ParkingLotStatus.FREE);
        parkingLot2.setUpdatedAt(date);

        ParkingLot parkingLot3 = new ParkingLot();

        parkingLot3.setId(4L);
        parkingLot3.setNumber(4);
        parkingLot3.setStatus(ParkingLotStatus.OCCUPIED);
        parkingLot3.setUpdatedAt(date);

        ParkingLot parkingLot4 = new ParkingLot();
        parkingLot4.setId(5L);
        parkingLot4.setNumber(5);
        parkingLot4.setStatus(ParkingLotStatus.OCCUPIED);
        parkingLot4.setUpdatedAt(date);

        ParkingLot parkingLot5 = new ParkingLot();

        parkingLot5.setId(6L);
        parkingLot5.setNumber(6);
        parkingLot5.setStatus(ParkingLotStatus.FREE);
        parkingLot5.setUpdatedAt(date);

        ParkingLot parkingLot6 = new ParkingLot();

        parkingLot6.setId(7L);
        parkingLot6.setNumber(7);
        parkingLot6.setStatus(ParkingLotStatus.OCCUPIED);
        parkingLot6.setUpdatedAt(date);

        ParkingLot parkingLot7 = new ParkingLot();

        parkingLot7.setId(8L);
        parkingLot7.setNumber(8);
        parkingLot7.setStatus(ParkingLotStatus.FREE);
        parkingLot7.setUpdatedAt(date);

        ParkingLot parkingLot8 = new ParkingLot();

        parkingLot8.setId(9L);
        parkingLot8.setNumber(9);
        parkingLot8.setStatus(ParkingLotStatus.FREE);
        parkingLot8.setUpdatedAt(date);

        ParkingLot parkingLot9 = new ParkingLot();

        parkingLot9.setId(10L);
        parkingLot9.setNumber(10);
        parkingLot9.setStatus(ParkingLotStatus.OCCUPIED);
        parkingLot9.setUpdatedAt(date);

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
    }

    public Optional<ParkingLot> findById(Long parkingLotId) {

//        int id = Math.toIntExact(parkingLotId);
//
//        return Optional.ofNullable(p.get(id));

        return parkingLotRepo.findById(parkingLotId);
    }

    public ParkingLot save(ParkingLot parkingLot) {

        /*p.add(parkingLot);

        parkingLot.setUpdatedAt(new Date(System.currentTimeMillis()));

        if (p.contains(parkingLot))
            return parkingLot;
        else return null;*/

        return parkingLotRepo.save(parkingLot);
    }
}

