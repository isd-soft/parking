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

    @Autowired
    private StatsRepo statsRepo;

    public List<StatsRow> listAll() {
         return statsRepo.findAll();
    }
}
