package com.isd.parking.controller;

import com.isd.parking.model.User;
import com.isd.parking.service.ldap.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Base64;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login_test")
    public boolean login(@RequestBody User user) {
        return false;
        //user.getUserName().equals("user") && user.getPassword().equals("password");

        //TODO: implement here LDAP authentificate
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
