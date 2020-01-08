package com.isd.parking.service;

import com.isd.parking.model.ParkingLot;
import com.isd.parking.model.ParkingLotStatus;
import com.isd.parking.model.StatsRow;
import com.isd.parking.repository.StatsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class StatsService {

    //Template mockup datasource for testing API
    //it will be removed after DB connect
    private List<StatsRow> s = new ArrayList<>();

    @Autowired
    private StatsRepo statsRepo;

    public List<StatsRow> listAll() {

        UUID uuid = UUID.randomUUID();

        StatsRow statsRow = new StatsRow();
        statsRow.setId(uuid);

        ParkingLot parkingLot = new ParkingLot();
        Date date = new Date(System.currentTimeMillis());
        parkingLot.setId(1L);
        parkingLot.setStatus(ParkingLotStatus.FREE);
        parkingLot.setUpdatedAt(date);

        statsRow.setLot(parkingLot);

        s.add(statsRow);
        s.add(statsRow);
        s.add(statsRow);

        return s;

        // return parkingLotRepo.findAll();
    }
}
