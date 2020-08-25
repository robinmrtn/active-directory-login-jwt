package com.romart.ad_login_jwt.service;


import com.romart.ad_login_jwt.domain.ismnet.EventPlanSubscription;
import com.romart.ad_login_jwt.domain.local.RefreshToken;
import com.romart.ad_login_jwt.domain.local.User;
import com.romart.ad_login_jwt.repository.ismnet.EventPlanSubscriptionRepository;
import com.romart.ad_login_jwt.repository.local.RefreshTokenRepository;
import com.romart.ad_login_jwt.repository.local.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final EventPlanSubscriptionRepository eventPlanSubscriptionRepository;

    @Autowired
    public UserService(UserRepository userRepository, RefreshTokenService refreshTokenService,
                       EventPlanSubscriptionRepository eventPlanSubscriptionRepository, RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.refreshTokenService = refreshTokenService;
        this.refreshTokenRepository = refreshTokenRepository;
        this.eventPlanSubscriptionRepository = eventPlanSubscriptionRepository;
    }

    public User create(User user) {

        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(5);
        String token = refreshTokenService.generateToken(user.getOid(), System.currentTimeMillis());
        RefreshToken refreshToken = new RefreshToken(token, expiryDate);

        user.setRefreshToken(refreshToken);
        refreshToken.setUser(user);

        refreshTokenRepository.save(refreshToken);
        User newUser = userRepository.save(user);

        return newUser;
    }

    public boolean existsByAccountNameAndOid(String accountName, String oid) {
        return userRepository.existsUserByAccountNameAndOid(accountName, oid);
    }

    public EventPlanSubscription getSubscriptionByPoid(String poid) {
        return eventPlanSubscriptionRepository.getSubscriptionByPoid(poid).orElse(null);
    }
}
