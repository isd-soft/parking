package com.isd.parking.controller;

import com.isd.parking.exception.ResourceNotFoundException;
import com.isd.parking.model.ParkingLot;
import com.isd.parking.service.ParkingLotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/")
public class ParkingLotController {

    private static final Logger LOG = LoggerFactory.getLogger(ParkingLotController.class);

    @Autowired
    private ParkingLotService parkingLotService;

    @GetMapping()
    public List<ParkingLot> getAllParkingLots() {
        return parkingLotService.listAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<ParkingLot> getEmployeeById(@PathVariable("id") Long parkingLotId)
            throws ResourceNotFoundException {
        ParkingLot parkingLot = parkingLotService.findById(parkingLotId)
                .orElseThrow(() -> new ResourceNotFoundException("Parking Lot not found for this id :: " + parkingLotId));

        return ResponseEntity.ok().body(parkingLot);
    }

}
