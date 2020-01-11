package com.isd.parking.controller;

import com.isd.parking.model.ParkingLot;
import com.isd.parking.model.StatsRow;
import com.isd.parking.service.ParkingLotService;
import com.isd.parking.service.StatsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;

@RestController
public class ArduinoController {

    private static final Logger LOG = LoggerFactory.getLogger(ArduinoController.class);

    @Autowired
    private ParkingLotService parkingLotService;

    @Autowired
    private StatsService statsService;

    @PutMapping("/arduino")
    @ResponseStatus(HttpStatus.OK)
    public void updateParkingLot(@RequestBody ParkingLot parkingLot) {

        LOG.info("Controller update parking lot executed...");

        parkingLotService.save(parkingLot);


        StatsRow statsRow = StatsRow.builder()//.id(UUID.randomUUID())
                .lotId(parkingLot.getId())
                .status(parkingLot.getStatus())
                .updatedAt(new Date(System.currentTimeMillis())).build();

        LOG.info("Controller update statistics executed...");

        statsService.save(statsRow);
    }
}
