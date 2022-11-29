package com.backend.vaccinebookingsystem.domain.dto;

import com.backend.vaccinebookingsystem.service.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class SecurityFilter extends OncePerRequestFilter {

    private static final String JWT_HEADER = "Authorization";

    private static final String JWT_TOKEN_PREFIX = "Bearer ";

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            log.info("Do filter internal");
            String token = getJwtFromRequest(request);

            if (token != null && jwtTokenProvider.validateJwtToken(token)) {
                log.info("Token isn't null");
                String username = jwtTokenProvider.getUsername(token);
                UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        } catch (UsernameNotFoundException e) {
            log.error("Do filter internal got an error. Error {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest httpServletRequest) {
        log.info("Getting JWT from request");
        String bearerJwtToken = httpServletRequest.getHeader(JWT_HEADER);

        if (StringUtils.hasText(bearerJwtToken) && bearerJwtToken.startsWith(JWT_TOKEN_PREFIX)) {
            log.info("Got bearer JWT token");
            return bearerJwtToken.substring(7);
        }

        return null;
    }
}
