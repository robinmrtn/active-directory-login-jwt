package com.romart.ad_login_jwt.controller;

import com.romart.ad_login_jwt.domain.CustomUserDetails;
import com.romart.ad_login_jwt.domain.ismnet.EventPlanSubscription;
import com.romart.ad_login_jwt.domain.local.User;
import com.romart.ad_login_jwt.security.IAuthenticationFacade;
import com.romart.ad_login_jwt.service.RefreshTokenService;
import com.romart.ad_login_jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class HomeController {

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    UserService userService;

    @Autowired
    private IAuthenticationFacade authenticationFacade;


    @RequestMapping("/")
    public String index() {

        /*
         * Get Current User
         */
        Authentication authentication = authenticationFacade.getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        if (userService.existsByAccountNameAndOid(userDetails.getAccountName(), userDetails.getOid())) {
            EventPlanSubscription eventPlanSubscription = userService.getSubscriptionByPoid("XV1##MD7#5#1##");
            return "User already exists" + eventPlanSubscription;
        } else {

            User user = new User(userDetails.getOid(), userDetails.getAccountName());

            User newUser = userService.create(user);

            return "Welcome to the home page!" + userDetails.getOid() + " "
                    + newUser.getRefreshToken().getToken()
                    + userDetails.getAccountName();
        }

    }

    @GetMapping("/test")
    public String test() {

        Principal principal = SecurityContextHolder.getContext().getAuthentication();

        return principal.getName();
    }

}
