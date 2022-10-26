package com.catalina.webspringbootshop.service;

import com.catalina.webspringbootshop.dto.UserLogin;
import com.catalina.webspringbootshop.dto.UserRegistration;
import com.catalina.webspringbootshop.entity.User;
import com.catalina.webspringbootshop.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Service
public class AuthenticationService {

    private UserRepository userRepository;
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    AuthenticationManager authManager;


    @Autowired
    public AuthenticationService(UserRepository userRepository, AuthenticationManager authenticationManager, UserService userService) {
        this.userRepository = userRepository;
        this.authManager = authenticationManager;
        this.userService = userService;
    }

    public String doRegister(UserRegistration userRegistration, RedirectAttributes attr, HttpServletRequest req) {
        if (!userRegistration.isValidDetails()) {
            attr.addFlashAttribute("error", "One or More Field is Missing");
            attr.addFlashAttribute("formdata", userRegistration);
            return "redirect:/register";
        }

        if (!userRegistration.passwordMatch()) {
            attr.addFlashAttribute("error", "Passwords Do Not Match");
            attr.addFlashAttribute("formdata", userRegistration);
            return "redirect:/register";
        }

        if (userRepository.findByUsername(userRegistration.getUsername()) != null) {
            attr.addFlashAttribute("error", "Username not available, pick another one please");
            attr.addFlashAttribute("formdata", userRegistration);
            return "redirect:/register";
        }

        if (userRepository.findByEmail(userRegistration.getEmail()) != null) {
            attr.addFlashAttribute("error", "Email not available, use another one please");
            attr.addFlashAttribute("formdata", userRegistration);
            return "redirect:/register";
        }

        User user = userRegistration.createUserObject();
        userRepository.save(user);

        attr.addFlashAttribute("success", "Registration Successful!");

        return "redirect:/login";
    }

    public String doLogin(UserLogin userLogin, RedirectAttributes attr, HttpServletRequest req) {
        userService.login(userLogin.getUsername(), userLogin.getPassword());
        return "redirect:/";
    }
}