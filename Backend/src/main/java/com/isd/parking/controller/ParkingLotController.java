package com.isd.parking.controller;

import com.isd.parking.api.EndpointsAPI;
import com.isd.parking.exception.ResourceNotFoundException;
import com.isd.parking.model.ParkingLot;
import com.isd.parking.model.ParkingLotStatus;
import com.isd.parking.service.ParkingLotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * The type ParkingLot controller.
 *
 * @author ISD Inthership Team
 */

@RestController
@RequestMapping(EndpointsAPI.API)
public class ParkingLotController {

    private static final Logger LOG = LoggerFactory.getLogger(ParkingLotController.class);

    @Autowired
    private ParkingLotService parkingLotService;

    @GetMapping(EndpointsAPI.PARKING_LIST)
    public List<ParkingLot> getAllParkingLots() {
        return parkingLotService.listAll();
    }

    @GetMapping(EndpointsAPI.PARKING_LIST + "/{id}")
    public ResponseEntity<ParkingLot> getEmployeeById(@PathVariable(value = "id") Long parkingLotId)
            throws ResourceNotFoundException {
        ParkingLot parkingLot = parkingLotService.findById(parkingLotId)
                .orElseThrow(() -> new ResourceNotFoundException("Parking Lot not found for this id :: " + parkingLotId));

        return ResponseEntity.ok().body(parkingLot);
    }

    //TODO: endpoint for creating and deleting parking lots
    //branch test
}
