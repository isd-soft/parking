package com.isd.parking.util;

import com.isd.parking.model.ParkingLot;
import com.isd.parking.model.enums.ParkingLotStatus;
import com.isd.parking.service.ParkingLotLocalService;
import com.isd.parking.service.ParkingLotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;


/**
 * Utility class
 * Fills the database and local Java memory storage with initial data
 */
@Component
@Slf4j
public class DataLoader implements ApplicationRunner {

    @Value("${parking.lots.number}")
    private String totalParkingLotsNumber;

    private final ParkingLotService parkingLotService;

    private final ParkingLotLocalService parkingLotLocalService;

    @Autowired
    public DataLoader(ParkingLotService parkingLotService, ParkingLotLocalService parkingLotLocalService) {
        this.parkingLotService = parkingLotService;
        this.parkingLotLocalService = parkingLotLocalService;
    }

    /**
     * Method initiates the database and local Java memory storage with necessary data
     * This method runs once at every application start.
     *
     * @param parkingLotService - parking lots service
     * @return - result of provided operation
     */
    @Bean
    public CommandLineRunner loadData(ParkingLotService parkingLotService) {
        return (args) -> {

            // save parking lots
            Date date = new Date(System.currentTimeMillis());

            int totalParkingLotsNumber = Integer.parseInt(this.totalParkingLotsNumber);
            for (int i = 1; i <= totalParkingLotsNumber; i++) {

                //initial saving parking lots to database
<<<<<<< HEAD
                parkingLotService.save(new ParkingLot((long) i + 10L, i, date, ParkingLotStatus.UNKNOWN));

                //initial saving parking lots to local Java memory
                parkingLotLocalService.save(new ParkingLot((long) i + 10L, i, date, ParkingLotStatus.UNKNOWN));
=======
                parkingLotService.save(new ParkingLot((long) i+10, i, date, ParkingLotStatus.UNKNOWN));

                //initial saving parking lots to local Java memory
                parkingLotLocalService.save(new ParkingLot((long) i+10, i, date, ParkingLotStatus.UNKNOWN));
>>>>>>> b0043cb3836d857c948802da4ad4cb531a35e15e
            }

            // fetch all parking lots from database
            log.info("ParkingLot found with findAll() from DATABASE:");
            printSeparator();
            for (ParkingLot parkingLot : parkingLotService.listAll()) {
                log.info(parkingLot.toString());
            }
            log.info("");

            // fetch an individual parking lot by ID
            Optional<ParkingLot> parkingLot = parkingLotService.findById(1L);

            log.info("Parking Lot found with findById(1L):");
            printSeparator();
            log.info(parkingLot.toString());
            log.info("");

            // fetch all parking lots from local Java memory
            log.info("ParkingLot found with findAll() from LOCAL Java memory:");
            printSeparator();
            for (ParkingLot parkingLotLocal : parkingLotLocalService.listAll()) {
                log.info(parkingLotLocal.toString());
            }
            log.info("");

            // fetch an individual parking lot by ID
            Optional<ParkingLot> parkingLotLocal = parkingLotLocalService.findById(1L);

            log.info("Parking Lot found with findById(1L):");
            printSeparator();
            log.info(parkingLotLocal.toString());
            log.info("");
        };
    }

    private void printSeparator() {
        log.info("-------------------------------");
    }

    /**
     * Fallback method for initializing database.
     * Use this if the previous one does not work
     *
     * @param args - application arguments
     */
    @Override
    public void run(ApplicationArguments args) {
        Date date = new Date(System.currentTimeMillis());

        //initiate parking lots in database
        // int totalParkingLotsNumber = Integer.parseInt(this.totalParkingLotsNumber);
        /*for (int i = 1; i <= totalParkingLotsNumber; i++) {
            //an fallback method to load initial data
            //parkingLotService.save(new ParkingLot((long) i, i, date, ParkingLotStatus.FREE));
            //parkingLotLocalService.save(new ParkingLot((long) i, i, date, ParkingLotStatus.FREE));
        }*/
    }
}
