package com.cezarg.regentidev88.security.authFilters.facebook;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FacebookAuthFilter extends AbstractAuthenticationProcessingFilter {

    public FacebookAuthFilter(AuthenticationManager authManager) {
        super("/*");
        setAuthenticationManager(authManager);
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (request.getHeader("Authorization") == null) {
            return null; // no header found, continue on to other security filters
        }

        return this.getAuthenticationManager().authenticate( new FacebookAuthToken(request.getHeader("Authorization")));
    }
}