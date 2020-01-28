package com.isd.parking.data;

import com.isd.parking.model.ParkingLot;
import com.isd.parking.model.ParkingNumber;
import com.isd.parking.model.enums.ParkingLotStatus;
import com.isd.parking.service.ParkingLotLocalService;
import com.isd.parking.service.ParkingLotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.Optional;


@Component
@Slf4j
public class DataLoader implements ApplicationRunner {

    private final ParkingLotService parkingLotService;

    private final ParkingLotLocalService parkingLotLocalService;

    @Autowired
    public DataLoader(ParkingLotService parkingLotService, ParkingLotLocalService parkingLotLocalService) {
        this.parkingLotService = parkingLotService;
        this.parkingLotLocalService = parkingLotLocalService;
    }

    //another fallback method for initializing database
    @Override
    public void run(ApplicationArguments args) {
        Date date = new Date(System.currentTimeMillis());

        //initiate parking lots in database
        /*for (int i = 1; i <= ParkingNumber.totalParkingLotsNumber; i++) {
            //an fallback method to load initial data
            //parkingLotService.save(new ParkingLot((long) i, i, date, ParkingLotStatus.FREE));
            //parkingLotLocalService.save(new ParkingLot((long) i, i, date, ParkingLotStatus.FREE));
        }*/
    }

    @Bean
    public CommandLineRunner loadData(ParkingLotService parkingLotService) {
        return (args) -> {

            // save parking lots
            Date date = new Date(System.currentTimeMillis());

            for (int i = 1; i <= ParkingNumber.totalParkingLotsNumber; i++) {

                //initial saving parking lots to database
                parkingLotService.save(new ParkingLot((10L + (long) i), i, date, ParkingLotStatus.FREE));

                //initial saving parking lots to local Java memory
                parkingLotLocalService.save(new ParkingLot((10L + (long) i), i, date, ParkingLotStatus.FREE));
            }

            // fetch all parking lots from database
            log.info("ParkingLot found with findAll() from DATABASE:");
            log.info("-------------------------------");
            for (ParkingLot parkingLot : parkingLotService.listAll()) {
                log.info(parkingLot.toString());
            }
            log.info("");

            // fetch an individual parking lot by ID
            Optional<ParkingLot> parkingLot = parkingLotService.findById(1L);

            log.info("Parking Lot found with findById(1L):");
            log.info("--------------------------------");
            log.info(parkingLot.toString());
            log.info("");

            // fetch all parking lots from local Java memory
            log.info("ParkingLot found with findAll() from LOCAL Java memory:");
            log.info("-------------------------------");
            for (ParkingLot parkingLotLocal : parkingLotLocalService.listAll()) {
                log.info(parkingLotLocal.toString());
            }
            log.info("");

            // fetch an individual parking lot by ID
            Optional<ParkingLot> parkingLotLocal = parkingLotLocalService.findById(1L);

            log.info("Parking Lot found with findById(1L):");
            log.info("--------------------------------");
            log.info(parkingLotLocal.toString());
            log.info("");
        };
    }
}
