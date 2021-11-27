package com.davacom.employeemanagemnetsystem.filters;

import com.davacom.employeemanagemnetsystem.utility.JwtTokenProviderUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.davacom.employeemanagemnetsystem.constants.SecurityConstant.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private JwtTokenProviderUtility jwtTokenProviderUtility;

    @Autowired
    public JwtAuthorizationFilter(JwtTokenProviderUtility jwtTokenProviderUtility) {
        this.jwtTokenProviderUtility = jwtTokenProviderUtility;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain  filterChain) throws ServletException, IOException {
        if(request.getMethod().equalsIgnoreCase(OPTIONS_HTTP_METHOD)){
            response.setStatus(OK.value());
        }else{
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (authorizationHeader == null || !(authorizationHeader.startsWith(TOKEN_PREFIX))){
                filterChain.doFilter(request,response);
                return;
            }

            String token = authorizationHeader.substring(TOKEN_PREFIX.length());
            String userName =jwtTokenProviderUtility.getSubject(token);
            if (jwtTokenProviderUtility.isTokenValid(userName, token) && SecurityContextHolder.getContext().getAuthentication() == null ){
                List<GrantedAuthority> authorities = jwtTokenProviderUtility.getAuthorities(token);
                Authentication authentication = jwtTokenProviderUtility.getAuthentication(userName, authorities, request);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else{
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request, response);
    }
}
