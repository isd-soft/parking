package com.isd.parking.controller;

import com.isd.parking.api.EndpointsAPI;
import com.isd.parking.exception.ResourceNotFoundException;
import com.isd.parking.model.ParkingLot;
import com.isd.parking.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(EndpointsAPI.API)
public class ParkingLotController {

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

}
