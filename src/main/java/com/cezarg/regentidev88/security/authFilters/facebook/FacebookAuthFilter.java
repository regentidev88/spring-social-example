package com.cezarg.regentidev88.security.authFilters.facebook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FacebookAuthFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    public FacebookAuthFilter() {
        super("/*");
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (request.getHeader("Authorization") == null) {
            return null; // no header found, continue on to other security filters
        }

        return new FacebookAuthToken(request.getHeader("Authorization"));
    }
}