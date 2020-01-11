package com.isd.parking.controller;

import com.isd.parking.model.ParkingLot;
import com.isd.parking.model.ParkingLotStatus;
import com.isd.parking.model.StatsRow;
import com.isd.parking.service.ParkingLotService;
import com.isd.parking.service.StatsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.Optional;

@RestController
public class ArduinoController {

    private static final Logger LOG = LoggerFactory.getLogger(ArduinoController.class);

    @Autowired
    private ParkingLotService parkingLotService;

    @Autowired
    private StatsService statsService;


    /**
     * @param parkingLot
     *        JSON object {
     *                       "id": 1,
     *                       "status": 0
     *                    }
     */

    @PutMapping("/arduino")
    //@PostMapping("/arduino/update")           //also works

    @ResponseStatus(HttpStatus.OK)
    public void updateParkingLot(@Valid @RequestBody ParkingLot parkingLot) {

        LOG.info("Controller update parking lot executed...");

        Optional<ParkingLot> parkingLotOptional = parkingLotService.findById(parkingLot.getId());

        parkingLotOptional.ifPresent(updatingParkingLot -> {
            updatingParkingLot.setStatus(parkingLot.getStatus());
            updatingParkingLot.setUpdatedAt(new Date(System.currentTimeMillis()));
            parkingLotService.save(updatingParkingLot);

            //save new statistics to database
            StatsRow statsRow = StatsRow.builder()//.id(UUID.randomUUID())
                    .lotId(parkingLot.getId())
                    .status(parkingLot.getStatus())
                    .updatedAt(new Date(System.currentTimeMillis())).build();

            LOG.info("Controller update statistics executed...");

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

        LOG.info("Controller update parking lot executed...");

        Optional<ParkingLot> parkingLotOptional = parkingLotService.findById(id);

        parkingLotOptional.ifPresent(parkingLot -> {

            parkingLot.setStatus(parkingLotStatus);
            parkingLot.setUpdatedAt(new Date(System.currentTimeMillis()));

            parkingLotService.save(parkingLot);

            //save new statistics to database
            StatsRow statsRow = StatsRow.builder()//.id(UUID.randomUUID())
                    .lotId(parkingLot.getId())
                    .status(parkingLot.getStatus())
                    .updatedAt(new Date(System.currentTimeMillis())).build();

            LOG.info("Controller update statistics executed...");

            statsService.save(statsRow);
        });
    }
}

