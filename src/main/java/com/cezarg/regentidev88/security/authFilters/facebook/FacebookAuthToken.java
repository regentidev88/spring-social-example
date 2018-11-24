package com.cezarg.regentidev88.security.authFilters.facebook;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class FacebookAuthToken extends AbstractAuthenticationToken {

    private String token;

    public FacebookAuthToken(String token) {
        super(null);
        this.token = token;
    }

    @Override
    public String getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
