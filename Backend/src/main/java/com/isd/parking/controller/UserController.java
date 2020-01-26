package com.isd.parking.controller;

import com.isd.parking.model.ParkingLot;
import com.isd.parking.model.ParkingLotStatus;
import com.isd.parking.model.StatsRow;
import com.isd.parking.model.User;
import com.isd.parking.service.ParkingLotService;
import com.isd.parking.service.StatsService;
import com.isd.parking.service.ldap.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.sql.Date;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;


@RestController
@CrossOrigin(origins = "*")
@Slf4j
public class UserController {

    private final UserService userService;

    private final ParkingLotService parkingLotService;

    //    private final ParkingLotLocalService parkingLotLocalService;

    private final StatsService statisticsService;

    @Value("${http.auth.admin.name}")
    private String admin;

    @Value("${http.auth.admin.pass}")
    private String adminPass;

    @Value("${http.auth.user.name}")
    private String userName;

    @Value("${http.auth.user.pass}")
    private String userPass;

    @Value("${ldap.enabled}")
    private String ldapEnabled;

    @Autowired
    public UserController(UserService userService, ParkingLotService parkingLotService, StatsService statisticsService) {
        this.userService = userService;
        this.parkingLotService = parkingLotService;
        this.statisticsService = statisticsService;
    }

    // for test in memory auth
    @RequestMapping("/login")
    public boolean login(@RequestBody User user) {

        final String username = user.getUsername();
        final String password = user.getPassword();

        log.info("Request body: " + user);
        log.info("LDAP enabled: " + Boolean.parseBoolean(ldapEnabled));

        if (Boolean.parseBoolean(ldapEnabled)) {
            // LDAP
            log.info("Request body: login " + username + " " + password);
            log.info(String.valueOf(userService.authenticate(username, password)));
            return userService.authenticate(username, password);
            //   return userLdapService.authenticate(user.getUsername(), user.getPassword());       unusable don't return boolean
        } else {
            return username.equals(admin) && password.equals(adminPass)
                    || username.equals(userName) && password.equals(userPass);
        }
    }

    @RequestMapping("/registration")
    public boolean registration(@RequestBody User user) {

        final String username = user.getUsername();
        final String password = user.getPassword();

        log.info("Request body: registration " + username + " " + password);

        //verify if user exists in db and throw error, else create
        List<String> sameUserNames = userService.search(username);
        log.info(String.valueOf(sameUserNames));

        if (sameUserNames.isEmpty()) {
            userService.create(username, password);
            sameUserNames = userService.search(username);
            log.info(String.valueOf(sameUserNames));
            if (!sameUserNames.isEmpty()) {
                log.info("not created");
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @RequestMapping("/reservate/{id}")
    public boolean reservation(@PathVariable("id") Long parkingLotId) {

        log.info("Parking lot number in reservation request: " + parkingLotId);

        Optional<ParkingLot> parkingLotOptional = parkingLotService.findById(parkingLotId);

        AtomicBoolean hasErrors = new AtomicBoolean(false);

        //if lot with this number exists in database
        parkingLotOptional.ifPresent(parkingLot -> {

            log.info("Parking lot found in database: " + parkingLot);

            // if parking lot is already reserved
            if (parkingLot.getStatus() == ParkingLotStatus.RESERVED) {
                hasErrors.set(true);
            } else {
                parkingLot.setStatus(ParkingLotStatus.RESERVED);       //get enum value from string
                parkingLot.setUpdatedNow();

                log.info("Updated parking lot: " + parkingLot);

                //saving in database
                parkingLotService.save(parkingLot);

                //saving in local Java memory
                //TODO: Save to local after merge
                //parkingLotLocalService.save(parkingLot);

                //save new statistics to database
                addStatisticsRecord(parkingLot);
            }
        });

        return !hasErrors.get();
    }

    @RequestMapping("/unreservate/{id}")
    public boolean cancelReservation(@PathVariable("id") Long parkingLotId) {

        log.info("Parking lot number in cancel reservation request: " + parkingLotId);

        Optional<ParkingLot> parkingLotOptional = parkingLotService.findById(parkingLotId);

        AtomicBoolean hasErrors = new AtomicBoolean(false);

        //if lot with this number exists in database
        parkingLotOptional.ifPresent(parkingLot -> {

            log.info("Parking lot found in database: " + parkingLot);

            // if parking lot is not reserved
            if (parkingLot.getStatus() != ParkingLotStatus.RESERVED) {
                hasErrors.set(true);
            } else {
                parkingLot.setStatus(ParkingLotStatus.FREE);       //get enum value from string
                parkingLot.setUpdatedNow();

                log.info("Updated parking lot: " + parkingLot);

                //saving in database
                parkingLotService.save(parkingLot);

                //saving in local Java memory
                //TODO: Save to local after merge
                //parkingLotLocalService.save(parkingLot);

                //save new statistics to database
                addStatisticsRecord(parkingLot);
            }
        });

        return !hasErrors.get();
    }

    private void addStatisticsRecord(ParkingLot parkingLot) {
        StatsRow statisticsRecord = StatsRow.builder()//.id(UUID.randomUUID())
                .lotNumber(parkingLot.getNumber())
                .status(parkingLot.getStatus())
                .updatedAt(new Date(System.currentTimeMillis())).build();

        log.info("Statistics record: " + statisticsRecord);
        log.info("Controller update statistics executed...");

        statisticsService.save(statisticsRecord);
    }

    @RequestMapping("/user")
    public Principal user(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization")
                .substring("Basic".length()).trim();
        return () -> new String(Base64.getDecoder()
                .decode(authToken)).split(":")[0];
    }

    @ResponseBody
    @GetMapping("/users")
    public Iterable<User> getAllUsers() {
        return userService.findAll();
    }
}
