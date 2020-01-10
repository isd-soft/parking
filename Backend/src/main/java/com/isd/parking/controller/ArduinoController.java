package com.isd.parking.controller;

import com.isd.parking.model.ParkingLot;
import com.isd.parking.service.ParkingLotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArduinoController {

    private static final Logger LOG = LoggerFactory.getLogger(ArduinoController.class);

    @Autowired
    private ParkingLotService parkingLotService;

    @PutMapping("/arduino")
    public void updateParkingLot(@RequestBody ParkingLot parkingLot) {
        parkingLotService.save(parkingLot);
    }

}
