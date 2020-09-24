package com.romart.ad_login_jwt.security.jwt;

import io.jsonwebtoken.JwtException;
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

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final RequestMatcher requestMatcher;

    private final JwtUtil jwtUtil;

    public JwtRequestFilter(String path, JwtUtil jwtUtil) {
        this.requestMatcher = new AntPathRequestMatcher(path);
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        final String requestTokenHeader = request.getHeader(AUTHORIZATION_HEADER);

        if (!requiresAuthentication(request) || requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String jwtToken = resolveToken(requestTokenHeader);
        System.out.println("token: " + jwtToken);
        String subject = null;
        try {
            subject = jwtUtil.getSubjectFromToken(jwtToken);
            Authentication auth = buildAuthFromJwt(subject, request);
            SecurityContextHolder.getContext().setAuthentication(auth);

            chain.doFilter(request, response);
        } catch (JwtException exception) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
        SecurityContextHolder.clearContext();
    }

    /* remove 'Bearer ' from token header string */
    private String resolveToken(String requestTokenHeader) {
        return requestTokenHeader.substring(7);
    }

    private boolean requiresAuthentication(HttpServletRequest request) {
        return requestMatcher.matches(request);
    }

    private Authentication buildAuthFromJwt(String subject, HttpServletRequest request) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(subject, null, null);

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        return authenticationToken;
    }
}
