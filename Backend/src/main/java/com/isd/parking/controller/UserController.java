package com.isd.parking.controller;

import com.isd.parking.model.ParkingLot;

import com.isd.parking.model.StatisticsRecord;
import com.isd.parking.model.User;
import com.isd.parking.model.enums.ParkingLotStatus;
import com.isd.parking.service.ParkingLotLocalService;
import com.isd.parking.service.ParkingLotService;
import com.isd.parking.service.StatisticsService;

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


/**
 * User controller
 * Provides methods for login, registration of user,
 * reservation and cancel reservation parking lot by user
 */
@RestController
@CrossOrigin(origins = "*")
@Slf4j
public class UserController {

    private final UserService userService;

    private final ParkingLotService parkingLotService;

    private final ParkingLotLocalService parkingLotLocalService;

    private final StatisticsService statisticsService;

    @Value("${http.auth.admin.name}")
    private String admin;

    @Value("${http.auth.admin.pass}")
    private String adminPass;

    @Value("${http.auth.user.name}")
    private String userName;

    @Value("${http.auth.user.pass}")
    private String userPass;

    // indicates ldap enabled
    @Value("${ldap.enabled}")
    private String ldapEnabled;

    @Autowired
    public UserController(UserService userService, ParkingLotService parkingLotService, ParkingLotLocalService parkingLotLocalService, StatisticsService statisticsService) {
        this.userService = userService;
        this.parkingLotService = parkingLotService;
        this.parkingLotLocalService = parkingLotLocalService;
        this.statisticsService = statisticsService;
    }

    /**
     * Users login controller
     * Used to authentificate user and login in system
     *
     * @return - success status of provided login
     */
    @RequestMapping("/login")
    public boolean login(@RequestBody User user) {

        final String username = user.getUsername();
        final String password = user.getPassword();

        // log.info("Request body: " + user);
        // log.info("LDAP enabled: " + Boolean.parseBoolean(ldapEnabled));

        if (Boolean.parseBoolean(ldapEnabled)) {
            // LDAP
            // log.info("Request body: login " + username + " " + password);
            // log.info(String.valueOf(userService.authenticate(username, password)));
            return userService.authenticate(username, password);
        } else {
            return username.equals(admin) && password.equals(adminPass)
                    || username.equals(userName) && password.equals(userPass);
        }
    }

    /**
     * Users registration controller
     * Handles user registration in system
     *
     * @return - success status of provided registration
     */
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

    /**
     * Parking lot reservation controller
     * Used to reservate parking lot
     *
     * @param parkingLotId - id of parking lot
     * @return - success status of parking lot reservation
     */
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
                parkingLotLocalService.save(parkingLot);

                //save new statistics to database
                addStatisticsRecord(parkingLot);
            }
        });

        return !hasErrors.get();
    }

    /**
     * Parking lot reset reservation controller
     * Used to cancel reservation status of parking lot
     *
     * @param parkingLotId - id of parking lot
     * @return - success status of parking lot reservation
     */
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
                parkingLotLocalService.save(parkingLot);

                //save new statistics to database
                addStatisticsRecord(parkingLot);
            }
        });

        return !hasErrors.get();
    }

    /**
     * Method creates new statistics record
     *
     * @param parkingLot - input parking lot object
     */
    private void addStatisticsRecord(ParkingLot parkingLot) {
        StatisticsRecord statisticsRecord = StatisticsRecord.builder()
                .lotNumber(parkingLot.getNumber())
                .status(parkingLot.getStatus())
                .updatedAt(new Date(System.currentTimeMillis())).build();

        log.info("Statistics record: " + statisticsRecord);
        log.info("Controller update statistics executed...");

        statisticsService.save(statisticsRecord);
    }

    /**
     * Get user by token from ldap storage
     *
     * @return - Principal user
     */
    @RequestMapping("/user")
    public Principal user(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization")
                .substring("Basic".length()).trim();
        return () -> new String(Base64.getDecoder()
                .decode(authToken)).split(":")[0];
    }


    /**
     * Get all users from ldap storage
     *
     * @return - Iterable of all users
     */
    @ResponseBody
    @GetMapping("/users")
    public Iterable<User> getAllUsers() {
        return userService.findAll();
    }
}
