package com.catalina.webspringbootshop.service;

import com.catalina.webspringbootshop.entity.User;
import org.springframework.security.core.Authentication;

public interface UserService {
    void save(User user);

    void login(String username, String password);

    User findByUsername(String username);

    User findByEmail(String email);

    User findById(int id);

    void edit(User user, User newUser);

    User getCurrentlyLoggedInUser(Authentication authentication);
}
