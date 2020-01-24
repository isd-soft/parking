package com.isd.parking.controller;

import com.isd.parking.model.User;
import com.isd.parking.service.ldap.UserLdapService;
import com.isd.parking.service.ldap.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Base64;


@RestController
@CrossOrigin(origins = "*")
@Slf4j
public class UserController {

    private final UserService userService;

    private final UserLdapService userLdapService;

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
    public UserController(UserService userService, UserLdapService userLdapService) {
        this.userService = userService;
        this.userLdapService = userLdapService;
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
            //   return userLdapService.authenticate(user.getUsername(), user.getPassword());
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
