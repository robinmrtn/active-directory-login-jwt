package com.romart.ad_login_jwt.service;

import com.romart.ad_login_jwt.domain.local.RefreshToken;
import com.romart.ad_login_jwt.exception.TokenNotFoundException;
import com.romart.ad_login_jwt.repository.local.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {

    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository)  {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public String generateToken(String oid, long date) {

        return Sha512DigestUtils.shaHex(date + oid);
    }

    public RefreshToken save(RefreshToken refreshToken) {
        refreshToken.setId(0);
        RefreshToken newRefreshToken = refreshTokenRepository.save(refreshToken);
        return newRefreshToken;
    }

    public RefreshToken getByUserId(long id) {
        return refreshTokenRepository.getRefreshTokenByUserId(id)
                .orElseThrow(() -> new TokenNotFoundException("Token not found - " + id));
    }
}
