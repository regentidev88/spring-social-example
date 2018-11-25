package com.cezarg.regentidev88.security.authFilters.facebook;

import com.cezarg.regentidev88.exception.InvalidTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class FacebookAuthProvider implements AuthenticationProvider {

    @Autowired
    private FacebookAuthenticationService authenticationService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        FacebookAuthToken auth = (FacebookAuthToken) authentication;

        String username = authenticationService.verifyToken(auth.getCredentials());
        if (username == null) {
            throw new InvalidTokenException("Invalid Token");
        }

        auth.setAuthenticated(true);

        return auth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return FacebookAuthToken.class.isAssignableFrom(authentication);
    }
}