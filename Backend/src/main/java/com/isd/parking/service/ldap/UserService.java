package com.isd.parking.service.ldap;

import com.isd.parking.model.User;
import com.isd.parking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * User Service class for ldap storage repository
 * Contains methods for
 * authenticate user,
 * search user by username,
 * create new user entry in ldap repository,
 * modify existed user,
 * get all users from ldap storage
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Method authenticates user with given credentials
     *
     * @param username - user name
     * @param password - user pass
     * @return - success or denied boolean status of user authentication
     */
    public Boolean authenticate(final String username, final String password) {
        User user = userRepository.findByUsernameAndPassword(username, password);
        return user != null;
    }

    /**
     * Method search user by given username
     *
     * @param username - user name
     * @return - List of user names equals with given
     */
    public List<String> search(final String username) {
        List<User> userList = userRepository.findByUsernameLikeIgnoreCase(username);
        if (userList == null) {
            return Collections.emptyList();
        }
        //return userList;
        return userList.stream()
                .map(User::getFullName)
                .collect(Collectors.toList());
    }

    /**
     * Method create user with given username and password
     *
     * @param username - user name
     * @param password - user pass
     */
    public void create(final String username, final String password) {
        User newUser = new User(username, digestSHA(password));
        newUser.setId(LdapUtils.emptyLdapName());

        userRepository.save(newUser);
    }

    /**
     * Method update user with given username and password
     *
     * @param username - user name
     * @param password - user pass
     */
    public void modify(final String username, final String password) {
        User user = userRepository.findByUsername(username);
        user.setPassword(password);

        userRepository.save(user);
    }

    /**
     * Get all users request method
     *
     * @return - list of all users
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Generates SHA encrypted string from users password
     *
     * @param password - user password to be encrypted
     * @return SHA encrypted string from users password
     */
    private String digestSHA(final String password) {
        String base64;

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA");
            digest.update(password.getBytes());
            base64 = Base64.getEncoder()
                    .encodeToString(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return "{SHA}" + base64;
    }
}
