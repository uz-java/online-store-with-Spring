package com.catalina.webspringbootshop.service;

import com.catalina.webspringbootshop.entity.User;
import com.catalina.webspringbootshop.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImplementation implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImplementation.class);
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService userDetailsService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }


    @Override
    public void login(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        authenticationManager.authenticate(token);

        if (token.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(token);
            System.out.println("User " + username);
            logger.debug(String.format("User %s logged in successfully!", username));
        } else {
            logger.error(String.format("Error with %s authentication!", username));
        }
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public void edit(User user, User newUser) {
        user.setLastName(newUser.getLastName());
        user.setFirstName(newUser.getFirstName());
        user.setUsername(newUser.getUsername());
        if (newUser.passwordValid()) {
            user.setPassword(new BCryptPasswordEncoder().encode(newUser.getPassword()));
        }
        user.setAddress(newUser.getAddress());
        user.setEmail(newUser.getEmail());
        user.setPostalCode(newUser.getPostalCode());
        user.setState(newUser.getState());
        user.setCity(newUser.getCity());

        userRepository.save(user);
    }

    public User getCurrentlyLoggedInUser(Authentication authentication) {
        if(authentication == null) {
            return null;
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username);
        return user;
    }
}

