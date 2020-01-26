package com.isd.parking;

import com.isd.parking.model.User;
import com.isd.parking.service.ldap.UserLdapService;
import com.isd.parking.service.ldap.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Slf4j
public class SpringLdapIntegrationTest {

    @Autowired
    private UserLdapService userLdapService;

    @Test
    public void testGetAllUsers() {
        List<User> users = userLdapService.getAllUsers();
        assertNotNull(users);

        log.info("Users found:" + users);
        log.info("Users count:" + users.size());

        assertEquals(users.size(), 6);
    }

    @Test
    public void testGetAllUsersNames() {
        List<String> users = userLdapService.getAllUserNames();
        assertNotNull(users);
        assertEquals(users.size(), 6);
    }

    @Test
    public void testFindPerson() {
        User user = userLdapService.findUser("uid=alex,ou=users,dc=isd,dc=com");
        assertNotNull(user);
        assertEquals(user.getFullName(), "Alex G");
    }

    @Test
    public void testCreateUser() {
        userLdapService.create("test_user", "qwerty");

        User user = userLdapService.findUser("uid=test_user,ou=users,dc=isd,dc=com");
        assertNotNull(user);
        //assertEquals(user.getFullName(), "Alex G");

        log.info("User found:" + user);
    }

    @Autowired
    private UserService userService;

    /*@Test
    public void givenLdapClient_whenCorrectCredentials_thenSuccessfulLogin() {
        Boolean isValid = userService.authenticate(USER3, USER3_PWD);

        assertEquals(true, isValid);
    }*/

    @Test
    public void testFindAllUsers() {
        List<User> users = userService.findAll();
        assertNotNull(users);

        log.info("Users found:" + users);
        log.info("Users count:" + users.size());

        assertEquals(users.size(), 6);
    }

    @Test
    public void testSearchUser() {
        List<User> users = userService.search("alex");
        assertNotNull(users);
        assertEquals(users.get(0).getFullName(), "Alex G");
    }
}
