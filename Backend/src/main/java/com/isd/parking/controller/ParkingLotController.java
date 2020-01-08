package com.isd.parking.controller;

import com.isd.parking.config.EndpointsAPI;
import com.isd.parking.exception.ResourceNotFoundException;
import com.isd.parking.model.ParkingLot;
import com.isd.parking.model.ParkingLotStatus;
import com.isd.parking.service.ParkingLotService;
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

    @Autowired
    private ParkingLotService parkingLotService;

    /**
     * Get all parking lots list.
     *
     * @return the list
     */
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
}
