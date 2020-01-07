package com.isd.parking.repository;

import com.isd.parking.model.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface User repository.
 *
 * @author ISD Inthership Team
 */
@Repository
public interface ParkingLotRepo extends JpaRepository<ParkingLot, Long> {
}


