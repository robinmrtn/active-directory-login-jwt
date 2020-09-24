package com.romart.ad_login_jwt.config;

import com.romart.ad_login_jwt.security.CustomUserMapper;
import com.romart.ad_login_jwt.security.RestAccessDeniedHandler;
import com.romart.ad_login_jwt.security.SecurityAuthenticationEntryPoint;
import com.romart.ad_login_jwt.security.jwt.JwtRequestFilter;
import com.romart.ad_login_jwt.security.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.security.web.access.ExceptionTranslationFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtUtil jwtUtil;

    private JwtRequestFilter jwtRequestFilter(String path) {
        return new JwtRequestFilter(path, jwtUtil);
    }

    @Value("${active-directory.domain}")
    private String acitiveDirectoryDomain;

    @Value("${active-directory.url}")
    private String activeDirectoryUrl;

    @Bean
    public ActiveDirectoryLdapAuthenticationProvider activeDirectoryLdapAuthenticationProvider() {
        ActiveDirectoryLdapAuthenticationProvider activeDirectoryLdapAuthenticationProvider = new
                ActiveDirectoryLdapAuthenticationProvider(acitiveDirectoryDomain, activeDirectoryUrl);
        activeDirectoryLdapAuthenticationProvider.setConvertSubErrorCodesToExceptions(true);
        activeDirectoryLdapAuthenticationProvider.setUseAuthenticationRequestCredentials(true);
        activeDirectoryLdapAuthenticationProvider.setUserDetailsContextMapper(userDetailsContextMapper());
        return activeDirectoryLdapAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Arrays.asList(activeDirectoryLdapAuthenticationProvider()));
    }


    @Bean
    public UserDetailsContextMapper userDetailsContextMapper() {
        return new CustomUserMapper();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http
                .csrf().disable()

                .addFilterAfter(new JwtRequestFilter("/**",jwtUtil), ExceptionTranslationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(new SecurityAuthenticationEntryPoint())
                .accessDeniedHandler(new RestAccessDeniedHandler())
                .and()
                .anonymous()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/auth").permitAll()
                .anyRequest().authenticated()
        //    .and()
        //  .formLogin();
        ;
    }
}
