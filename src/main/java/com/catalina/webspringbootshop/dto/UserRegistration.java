package com.catalina.webspringbootshop.dto;

import com.catalina.webspringbootshop.config.Roles;
import com.catalina.webspringbootshop.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserRegistration {

    private String username, email, city, password, password_confirmation, first_name, last_name, state, address, address2;
    private int postal_code;

    public UserRegistration() {}

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(int postal_code) {
        this.postal_code = postal_code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPassword_confirmation() {
        return password_confirmation;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public void setPassword_confirmation(String password_confirmation) {
        this.password_confirmation = password_confirmation;
    }

    public boolean passwordMatch() {

        return getPassword() != null && getPassword_confirmation() != null &&
                getPassword().equals(getPassword_confirmation());
    }

    public boolean isValidDetails() {
        return !getUsername().isEmpty() && !getEmail().isEmpty()
                && !getUsername().isEmpty() && !getPassword().isEmpty()
                && !getPassword_confirmation().isEmpty();
    }

    public String toString() {
        return "Username = " + getUsername() +
                "\nEmail = " + getEmail() +
                "\nName = " + getUsername() +
                "\nPassword = " + getPassword() +
                "\nPassword Confirm = " + getPassword_confirmation();
    }

    public User createUserObject() {

        User user = new User(getUsername(),getEmail(),new BCryptPasswordEncoder().encode(getPassword()), Roles.CUSTOMER.toString());
        user.setFirstName(getFirst_name());
        user.setLastName(getLast_name());
        return user;
    }
}