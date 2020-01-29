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

    private final ParkingLotService parkingLotService;

    private final ParkingLotLocalService parkingLotLocalService;

    private final StatisticsService statisticsService;

    @Autowired
    public ArduinoController(ParkingLotService parkingLotService, ParkingLotLocalService parkingLotLocalService, StatisticsService statisticsService) {
        this.parkingLotService = parkingLotService;
        this.parkingLotLocalService = parkingLotLocalService;
        this.statisticsService = statisticsService;
    }

    @PutMapping("/arduino")
    @ResponseStatus(HttpStatus.OK)
    public void updateParkingLot(@Valid @RequestBody ParkingLot parkingLot) {

        Optional<ParkingLot> parkingLotOptional = parkingLotService.findById(parkingLot.getId());

        parkingLotOptional.ifPresent(updatingParkingLot -> {

            updatingParkingLot.setStatus(parkingLot.getStatus());
            updatingParkingLot.setUpdatedNow();

            parkingLotService.save(updatingParkingLot);
            parkingLotLocalService.save(updatingParkingLot);

            StatisticsRecord statisticsRecord = StatisticsRecord.builder()
                    .lotNumber(updatingParkingLot.getNumber())
                    .status(updatingParkingLot.getStatus())
                    .updatedAt(new Date(System.currentTimeMillis())).build();

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

        Optional<ParkingLot> parkingLotOptional = parkingLotService.findById(id);

        parkingLotOptional.ifPresent(parkingLot -> {

            parkingLot.setStatus(parkingLotStatus);
            parkingLot.setUpdatedNow();

            parkingLotService.save(parkingLot);
            parkingLotLocalService.save(parkingLot);

            StatisticsRecord statisticsRecord = StatisticsRecord.builder()
                    .lotNumber(parkingLot.getNumber())
                    .status(parkingLot.getStatus())
                    .updatedAt(new Date(System.currentTimeMillis())).build();

            log.info("Controller update statistics executed...");

            statisticsService.save(statisticsRecord);
        });
    }
}

