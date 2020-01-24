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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.sql.Date;
import java.util.Base64;
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

        log.info("Request body: " + user);
        //TODO: implement here LDAP authentificate

        log.info("LDAP enabled: " + Boolean.parseBoolean(ldapEnabled));

        if (Boolean.parseBoolean(ldapEnabled)) {
            // LDAP
            return userService.authenticate(user.getUsername(), user.getPassword());
            //   return userLdapService.authenticate(user.getUsername(), user.getPassword());       unusable don't return boolean
        } else {
            return user.getUsername().equals(admin) && user.getPassword().equals(adminPass)
                    || user.getUsername().equals(userName) && user.getPassword().equals(userPass);
        }
    }

    @RequestMapping("/registration")
    public void registration(@RequestBody User user) {
        //TODO: implement here registration logic

        //verify if user exists in db and throw error, else create

        //TODO: implement here LDAP save

        userService.create(user.getUsername(), user.getPassword());
    }

    @RequestMapping("/reservate/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Boolean> reservation(@PathVariable("id") Long parkingLotId) {

        Optional<ParkingLot> parkingLotOptional = parkingLotService.findById(Long.valueOf(parkingLotId));

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
                StatsRow statisticsRecord = StatsRow.builder()//.id(UUID.randomUUID())
                        .lotNumber(parkingLot.getNumber())
                        .status(parkingLot.getStatus())
                        .updatedAt(new Date(System.currentTimeMillis())).build();

                log.info("Statistics record: " + statisticsRecord);
                log.info("Controller update statistics executed...");

                statisticsService.save(statisticsRecord);
            }
        });

        return ResponseEntity.ok().body(!hasErrors.get());
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
