package com.isd.parking.repository;

import com.isd.parking.model.User;
import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User ldap repository
 */
@Repository
public interface UserRepository extends LdapRepository<User> {

    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);

    List<User> findByUsernameLikeIgnoreCase(String username);

    List<User> findAll();
}
