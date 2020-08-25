package com.romart.ad_login_jwt.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtRequestFilter extends GenericFilterBean {

    private final RequestMatcher requestMatcher;

    public JwtRequestFilter(String path) {
        this.requestMatcher = new AntPathRequestMatcher(path);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException, JWTVerificationException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        final String requestTokenHeader = request.getHeader("Authorization");

        if (!requiresAuthentication(request) || requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String jwtToken = requestTokenHeader.substring(7);
        System.out.println(jwtToken);
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("ism")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(jwtToken);

            Authentication auth = buildAuthFromJwt(jwt, request);

            SecurityContextHolder.getContext().setAuthentication(auth);

            chain.doFilter(request, response);
        } catch (JWTVerificationException exception) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }

        SecurityContextHolder.clearContext();
    }

    private boolean requiresAuthentication(HttpServletRequest request) {
        return requestMatcher.matches(request);
    }

    private Authentication buildAuthFromJwt(DecodedJWT jwt, HttpServletRequest request) {
        String subject = jwt.getSubject();

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(subject, null, null);

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        return authenticationToken;

    }


}
