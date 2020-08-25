package com.romart.ad_login_jwt.domain.local;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "oid", unique = true)
    private String oid;

    @Column(name = "account_name")
    private String accountName;

    @OneToOne(mappedBy = "user")
    private RefreshToken refreshToken;

    public User(String oid, String accountName, RefreshToken refreshToken) {
        this.oid = oid;
        this.accountName = accountName;
        this.refreshToken = refreshToken;
    }

    public User(String oid, String accountName) {
        this.oid = oid;
        this.accountName = accountName;
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public RefreshToken getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(RefreshToken refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(oid, user.oid) &&
                Objects.equals(accountName, user.accountName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, oid, accountName);
    }
}
