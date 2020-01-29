package com.isd.parking.controller;

import com.isd.parking.exception.ResourceNotFoundException;
import com.isd.parking.model.ParkingLot;
import com.isd.parking.service.ParkingLotLocalService;
import com.isd.parking.service.ParkingLotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/")
@Slf4j
public class ParkingLotController {

    //For using database uncomment this and comment parkingLotService below
    /*@Autowired
    private ParkingLotService parkingLotService;*/

    private final ParkingLotLocalService parkingLotService;

    @Autowired
    public ParkingLotController(ParkingLotLocalService parkingLotService) {
        this.parkingLotService = parkingLotService;
    }

    @GetMapping("parking")
    public List<ParkingLot> getAllParkingLots() {

        log.info("Controller update parking lot executed...");

        return parkingLotService.listAll();
    }

    @GetMapping("parking/{id}")
    public ResponseEntity<ParkingLot> getParkingLotById(@PathVariable("id") Long parkingLotId)
            throws ResourceNotFoundException {

        log.info("Controller get parking lot by id executed...");

        ParkingLot parkingLot = parkingLotService.findById(parkingLotId)
                .orElseThrow(() -> new ResourceNotFoundException("Parking Lot not found for this id :: " + parkingLotId));

        return ResponseEntity.ok().body(parkingLot);
    }

}
