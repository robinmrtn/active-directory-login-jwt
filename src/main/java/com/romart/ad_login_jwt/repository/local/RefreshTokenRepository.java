package com.romart.ad_login_jwt.repository.local;

import com.romart.ad_login_jwt.domain.local.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> getRefreshTokenByUserId(long userId);

    void deleteByExpiryDateBefore(LocalDateTime dateTime);
}
