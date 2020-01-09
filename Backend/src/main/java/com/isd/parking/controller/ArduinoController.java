package com.isd.parking.controller;

import com.isd.parking.api.EndpointsAPI;
import com.isd.parking.exception.ResourceNotFoundException;
import com.isd.parking.model.ParkingLot;
import com.isd.parking.model.ParkingLotStatus;
import com.isd.parking.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    /*@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ParkingLot> createParkingLot(@RequestBody ParkingLot parkingLot){
        return new ResponseEntity<ParkingLot>(parkingLotService.save(parkingLot), HttpStatus.CREATED);
    }*/


    //working update
    @RequestMapping(value = "/{status}", method = RequestMethod.PUT)
    public void modifyParkingById(@PathVariable("status") ParkingLotStatus status, @Valid @RequestBody ParkingLot parkingLot) {
        parkingLot.setStatus(status);
        parkingLotService.save(parkingLot);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void modifyParkingById(@Valid @RequestBody ParkingLot parkingLot) {
        parkingLotService.save(parkingLot);
    }


    @PostMapping(value = EndpointsAPI.PARKING_LIST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ParkingLot> updateParkingLotPost(@RequestParam(value = "id") int parkingLotId,
                                                           @RequestParam(value = "status") ParkingLotStatus status) throws ResourceNotFoundException {

        ParkingLot parkingLot = parkingLotService.findById(Long.valueOf(parkingLotId))
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

    @GetMapping(value = "/arduino_get", produces = MediaType.TEXT_PLAIN_VALUE)
    public String testArduinoGet() {
        return "hello from back";
    }

    @GetMapping(value = "/arduino_get_p{id}{status}")
    public void testArduinoGetParams(@PathVariable(value = "id") Long parkingLotId, @PathVariable(value = "status") ParkingLotStatus status) {
        System.out.println("hello from arduino post, ID: " + parkingLotId + ", status: " + status);
    }

    //TODO: edit this methods - not working

    @PostMapping(value = "/arduino_post")
    public void testArduinoPost(@RequestParam(value = "id") int parkingLotId, @RequestParam(value = "status") ParkingLotStatus status) {
        System.out.println("hello from arduino post, ID: " + parkingLotId + ", status: " + status);
    }

    @PutMapping(value = "/arduino_put{id}{status}")
    public void testArduinoPut(@PathVariable(value = "id") Long parkingLotId, @PathVariable(value = "status") ParkingLotStatus status) {
        System.out.println("hello from arduino put, ID: " + parkingLotId + ", status: " + status);
    }
}
