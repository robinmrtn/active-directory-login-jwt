package com.romart.ad_login_jwt.repository.local;

import com.romart.ad_login_jwt.domain.local.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getUserByOid(String oid);

    boolean existsUserByAccountNameAndOid(String accountName, String oid);

}
