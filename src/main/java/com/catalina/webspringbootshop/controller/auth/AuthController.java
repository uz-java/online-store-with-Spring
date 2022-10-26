package com.catalina.webspringbootshop.controller.auth;

import com.catalina.webspringbootshop.dto.UserLogin;
import com.catalina.webspringbootshop.dto.UserRegistration;
import com.catalina.webspringbootshop.repository.UserRepository;
import com.catalina.webspringbootshop.service.AuthenticationService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;

    Logger logger = LoggerFactory.getLogger(AuthController.class);

    @RequestMapping(value = "/register", method = {RequestMethod.GET, RequestMethod.POST})
    public String register(ModelMap model, HttpServletRequest req, RedirectAttributes attr, UserRegistration userRegistration) {
        if (StringUtils.equals(req.getMethod(), RequestMethod.GET.toString())) {
            model.addAttribute("user", userRegistration);
            return "register";
        }
        if (StringUtils.equals(req.getMethod(), RequestMethod.POST.toString())) {
            return authenticationService.doRegister(userRegistration, attr, req);
        }

        return invalidRequestResponse(attr);
    }

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String login(ModelMap model, HttpServletRequest req, RedirectAttributes attr, UserLogin userLogin) {
        if (StringUtils.equals(req.getMethod(), RequestMethod.GET.toString())) {
            model.addAttribute("user", userLogin);
            return "login";
        }

        if (StringUtils.equals(req.getMethod(), RequestMethod.POST.toString())) {
            return authenticationService.doLogin(userLogin, attr, req);
        }

        return invalidRequestResponse(attr);
    }

    private String invalidRequestResponse(RedirectAttributes attr) {
        attr.addFlashAttribute("error", "Invalid Request Method");
        return "redirect:/";
    }
}
