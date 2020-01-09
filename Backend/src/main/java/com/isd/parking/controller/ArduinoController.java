package com.isd.parking.controller;

import com.isd.parking.api.EndpointsAPI;
import com.isd.parking.exception.ResourceNotFoundException;
import com.isd.parking.model.ParkingLot;
import com.isd.parking.model.ParkingLotStatus;
import com.isd.parking.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


/**
 * The type Arduino controller.
 *
 * @author ISD Inthership Team
 */

@RestController
@RequestMapping(EndpointsAPI.API)
public class ArduinoController {

    @Autowired
    private ParkingLotService parkingLotService;

    @PutMapping(EndpointsAPI.PARKING_LIST + "/{id}{status}")
    public ResponseEntity<ParkingLot> updateEmployee(@PathVariable(value = "id") Long parkingLotId,
                                                     @PathVariable(value = "status") ParkingLotStatus status) throws ResourceNotFoundException {
        ParkingLot parkingLot = parkingLotService.findById(parkingLotId)
                .orElseThrow(() -> new ResourceNotFoundException("Parking Lot found for this id :: " + parkingLotId));

        parkingLot.setStatus(status);

        Date date = new Date(System.currentTimeMillis());
        parkingLot.setUpdatedAt(date);

        final ParkingLot updatedParkingLot = parkingLotService.save(parkingLot);
        return ResponseEntity.ok(updatedParkingLot);
    }

    //TODO: endpoint for creating and deleting parking lots

    //TODO: edit this methods - not working
    //test endpoints for arduino
//    @GetMapping(EndpointsAPI.PARKING_LIST + "/arduino_get")
//    public String testArduinoGet() {
//        return "hello from back get";
//    }
//
//    @PostMapping(EndpointsAPI.PARKING_LIST + "/arduino_post")
//    public String testArduinoPost() {
//        return "hello from back post";
//    }
//
//    @PostMapping(EndpointsAPI.PARKING_LIST + "/arduino_post")
//    public String testArduinoPut() {
//        return "hello from back put";
//    }
}
