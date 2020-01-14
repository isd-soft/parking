package com.isd.parking.data;

import com.isd.parking.model.ParkingLot;
import com.isd.parking.model.ParkingNumber;
import com.isd.parking.model.enums.ParkingLotStatus;
import com.isd.parking.repository.ParkingLotRepository;
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

    private ParkingLotRepository parkingLotRepository;

    @Autowired
    public DataLoader(ParkingLotRepository parkingLotRepository) {
        this.parkingLotRepository = parkingLotRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Date date = new Date(System.currentTimeMillis());

        //initiate parking lots in database
        for (int i = 1; i <= ParkingNumber.totalParkingLotsNumber; i++) {
            //an fallback method to load initial data
            //parkingLotRepository.save(new ParkingLot((long) i, i, date, ParkingLotStatus.UNKNOWN));
        }
    }

    @Bean
    public CommandLineRunner loadData(ParkingLotRepository parkingLotRepository) {
        return (args) -> {

            // save parking lots
            Date date = new Date(System.currentTimeMillis());

            for (int i = 1; i <= ParkingNumber.totalParkingLotsNumber; i++) {
                parkingLotRepository.save(new ParkingLot((long) i, i, date, ParkingLotStatus.FREE));
            }

            // fetch all parking lots
            log.info("ParkingLot found with findAll():");
            log.info("-------------------------------");
            for (ParkingLot parkingLot : parkingLotRepository.findAll()) {
                log.info(parkingLot.toString());
            }
            log.info("");

            // fetch an individual parking lot by ID
            Optional<ParkingLot> parkingLot = parkingLotRepository.findById(1L);

            log.info("Parking Lot found with findById(1L):");
            log.info("--------------------------------");
            log.info(parkingLot.toString());
            log.info("");
        };
    }
}
