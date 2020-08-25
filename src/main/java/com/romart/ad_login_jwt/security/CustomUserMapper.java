package com.romart.ad_login_jwt.security;

import com.romart.ad_login_jwt.domain.CustomUserDetails;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;

import java.util.Collection;

public class CustomUserMapper extends LdapUserDetailsMapper {
    @Override
    public CustomUserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection<? extends GrantedAuthority> authorities) {

        UserDetails details = super.mapUserFromContext(ctx, username, authorities);

        CustomUserDetails customUserDetails = new CustomUserDetails();

        customUserDetails.setOid(ctx.getStringAttribute("homephone"));
        customUserDetails.setPwdLastSet(Long.parseLong(ctx.getStringAttribute("pwdLastSet")));
        customUserDetails.setAccountName(ctx.getStringAttribute("samaccountname"));

        return customUserDetails;

    }
}

