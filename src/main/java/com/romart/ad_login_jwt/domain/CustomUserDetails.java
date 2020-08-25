package com.romart.ad_login_jwt.domain;

import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;

public class CustomUserDetails extends LdapUserDetailsImpl {

    private String oid;
    private long pwdLastSet;
    private String accountName;

    public long getPwdLastSet() {
        return pwdLastSet;
    }

    public void setPwdLastSet(long pwdLastSet) {
        this.pwdLastSet = pwdLastSet;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    @Override
    public String toString() {
        return "CustomUserDetails{" +
                "oid='" + oid + '\'' +
                ", pwdLastSet=" + pwdLastSet +
                ", accountName='" + accountName + '\'' +
                '}';
    }
}
