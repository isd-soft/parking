package com.isd.parking.repository;

import com.isd.parking.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends LdapRepository<User> {

    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);

    List<User> findByUsernameLikeIgnoreCase(String username);

    List<User> findAll();
}
