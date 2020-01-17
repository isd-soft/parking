package com.isd.parking.model;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;

@Entry(base = "ou=users", objectClasses = {"person", "inetOrgPerson", "top"})
public final class User {

    @Id
    private Name id;

    private @Attribute(name = "cn")
    String fullName;

    private @Attribute(name = "sn")
    String lastName;

    private @Attribute(name = "uid")
    String username;

    private @Attribute(name = "userPassword")
    String password;

    public User() {
    }

    public User(String fullName, String lastName, String password) {
        this.fullName = fullName;
        this.lastName = lastName;
        this.password = password;
    }

    public User(String uid, String digestSHA) {
        this.username = uid;
        this.password = digestSHA;
    }

    public Name getId() {
        return id;
    }

    public void setId(Name id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return fullName;
    }
}
