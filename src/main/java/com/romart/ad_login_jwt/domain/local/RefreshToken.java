package com.romart.ad_login_jwt.domain.local;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "token")
public class RefreshToken {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "token", unique = true)
    private String token;

    @OneToOne(cascade = CascadeType.ALL) @JoinColumn( name = "user_id" )
    private User user;


    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    public RefreshToken(String token, LocalDateTime expiryDate) {
        this.token = token;
        this.expiryDate = expiryDate;
    }

    public RefreshToken() {}

    public long getId() {
        return id;
    }

    public RefreshToken(String token, User user, LocalDateTime expiryDate) {
        this.token = token;
        this.user = user;
        this.expiryDate = expiryDate;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RefreshToken that = (RefreshToken) o;
        return token.equals(that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token);
    }
}
