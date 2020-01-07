package com.isd.parking.controller;

import com.isd.parking.model.ParkingLot;
import com.isd.parking.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type ParkingLot controller.
 *
 * @author ISD Inthership Team
 */

@RestController
@RequestMapping("/api/v1")
public class ParkingLotController {

    @Autowired
    private ParkingLotService parkingLotService;

    /**
     * Get all parking lots list.
     *
     * @return the list
     */
    @GetMapping("/parking")
    public List<ParkingLot> getAllParkingLots() {
        return parkingLotService.listAll();
    }
}
