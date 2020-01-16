package com.isd.parking.controller;

import com.isd.parking.model.ParkingLot;
import com.isd.parking.model.StatisticsRecord;
import com.isd.parking.model.enums.ParkingLotStatus;
import com.isd.parking.service.ParkingLotLocalService;
import com.isd.parking.service.ParkingLotService;
import com.isd.parking.service.StatisticsService;
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

    //For using local repository uncomment this and comment parkingLotService above
    /*@Autowired
    private ParkingLotLocalService parkingLotService;*/

    @Autowired
    private StatisticsService statisticsService;

    @PutMapping("/arduino")
    @ResponseStatus(HttpStatus.OK)
    public void updateParkingLot(@Valid @RequestBody ParkingLot parkingLot) {

        log.info("Controller update parking lot executed...");

        Optional<ParkingLot> parkingLotOptional = parkingLotService.findById(parkingLot.getId());

        parkingLotOptional.ifPresent(updatingParkingLot -> {

            log.info("Parking lot found in database: " + updatingParkingLot);

            updatingParkingLot.setStatus(parkingLot.getStatus());
            updatingParkingLot.setUpdatedNow();

            log.info("Updated parking lot: " + updatingParkingLot);

            parkingLotService.save(updatingParkingLot);

            //save new statistics to database
            StatisticsRecord statisticsRecord = StatisticsRecord.builder()//.id(UUID.randomUUID())
                    .lotNumber(updatingParkingLot.getNumber())
                    .status(updatingParkingLot.getStatus())
                    .updatedAt(new Date(System.currentTimeMillis())).build();

            log.info("Statistics record: " + statisticsRecord);

            log.info("Controller update statistics executed...");

            statisticsService.save(statisticsRecord);
        });
    }

    /*
    alternative fallback method for updating data
    not working via Postman (required parameter not present) (fixed)
     */

    @PutMapping("/arduino/update")
    @ResponseStatus(HttpStatus.OK)
    public void updateParkingLotById(@RequestParam(value = "id") Long id,
                                     @RequestParam(value = "status") ParkingLotStatus parkingLotStatus) {

        log.info("Controller update parking lot executed...");

        Optional<ParkingLot> parkingLotOptional = parkingLotService.findById(id);

        parkingLotOptional.ifPresent(parkingLot -> {

            log.info("Parking lot found in database: " + parkingLot);

            parkingLot.setStatus(parkingLotStatus);
            parkingLot.setUpdatedNow();

            log.info("Updated parking lot: " + parkingLotStatus);

            parkingLotService.save(parkingLot);

            //save new statistics to database
            StatisticsRecord statisticsRecord = StatisticsRecord.builder()//.id(UUID.randomUUID())
                    .lotNumber(parkingLot.getNumber())
                    .status(parkingLot.getStatus())
                    .updatedAt(new Date(System.currentTimeMillis())).build();

            log.info("Statistics record: " + statisticsRecord);

            log.info("Controller update statistics executed...");

            statisticsService.save(statisticsRecord);
        });
    }
}

