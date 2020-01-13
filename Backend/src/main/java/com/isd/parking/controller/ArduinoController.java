package com.isd.parking.controller;

import com.isd.parking.model.ParkingLot;
import com.isd.parking.model.StatsRow;
import com.isd.parking.model.enums.ParkingLotStatus;
import com.isd.parking.service.ParkingLotService;
import com.isd.parking.service.StatsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.Optional;

@RestController
@Slf4j
public class ArduinoController {

    @Autowired
    private ParkingLotService parkingLotService;

    @Autowired
    private StatsService statsService;

    @PutMapping("/arduino")
    @ResponseStatus(HttpStatus.OK)
    public void updateParkingLot(@Valid @RequestBody ParkingLot parkingLot) {

        log.info("Controller update parking lot executed...");

        Optional<ParkingLot> parkingLotOptional = parkingLotService.findById(parkingLot.getId());

        parkingLotOptional.ifPresent(updatingParkingLot -> {
            updatingParkingLot.setStatus(parkingLot.getStatus());
            updatingParkingLot.setUpdatedAt(new Date(System.currentTimeMillis()));
            parkingLotService.save(updatingParkingLot);

            //save new statistics to database
            StatsRow statsRow = StatsRow.builder()//.id(UUID.randomUUID())
                    .lotNumber(parkingLot.getNumber())
                    .status(parkingLot.getStatus())
                    .updatedAt(new Date(System.currentTimeMillis())).build();

            log.info("Controller update statistics executed...");

            statsService.save(statsRow);
        });
    }




    /*
    not working via Postman (required parameter not present)
     */

    @PutMapping("/arduino/update")
    @ResponseStatus(HttpStatus.OK)
    public void updateParkingLotById(@RequestParam(value = "id") Long id,
                                     @RequestParam(value = "status") ParkingLotStatus parkingLotStatus) {

        log.info("Controller update parking lot executed...");

        Optional<ParkingLot> parkingLotOptional = parkingLotService.findById(id);

        parkingLotOptional.ifPresent(parkingLot -> {

            parkingLot.setStatus(parkingLotStatus);
            parkingLot.setUpdatedAt(new Date(System.currentTimeMillis()));

            parkingLotService.save(parkingLot);

            //save new statistics to database
            StatsRow statsRow = StatsRow.builder()//.id(UUID.randomUUID())
                    .lotNumber(parkingLot.getNumber())
                    .status(parkingLot.getStatus())
                    .updatedAt(new Date(System.currentTimeMillis())).build();

            log.info("Controller update statistics executed...");

            statsService.save(statsRow);
        });
    }
}

