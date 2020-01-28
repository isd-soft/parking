package com.isd.parking.controller;

import com.isd.parking.exception.ResourceNotFoundException;
import com.isd.parking.model.ParkingLot;
import com.isd.parking.service.ParkingLotLocalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Parking lots controller
 * Provides methods for getting all parking lots stored in local Java memory and database
 */
@RestController("/")
@CrossOrigin(origins = "*")
@Slf4j
public class ParkingLotController {

    /* For using separate SQL-like database uncomment this and comment parkingLotService
       below and import necessary class
       Otherwise data is obtained from local Java memory
    */
    /*@Autowired
    private ParkingLotService parkingLotService;*/

    private final ParkingLotLocalService parkingLotService;

    @Autowired
    public ParkingLotController(ParkingLotLocalService parkingLotService) {
        this.parkingLotService = parkingLotService;
    }


    /**
     * Parking lots get controller
     * Used to get all parking lots from the local Java memory
     *
     * @return Parking lots list
     */
    @GetMapping("parking")
    public List<ParkingLot> getAllParkingLots() {

        log.info("Controller update parking lot executed...");

        return parkingLotService.listAll();
    }


    /**
     * Parking lots get by id controller
     * Used to get parking lot by its id from the local Java memory
     *
     * @return ResponseEntity.OK with body of Parking lot if exists in storage else
     * @throw ResourceNotFoundException
     */
    @GetMapping("parking/{id}")
    public ResponseEntity<ParkingLot> getParkingLotById(@PathVariable("id") Long parkingLotId)
            throws ResourceNotFoundException {

        log.info("Controller get parking lot by id executed...");

        ParkingLot parkingLot = parkingLotService.findById(parkingLotId)
                .orElseThrow(() -> new ResourceNotFoundException("Parking Lot not found for this id :: " + parkingLotId));

        return ResponseEntity.ok().body(parkingLot);
    }

}
