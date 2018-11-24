package com.cezarg.regentidev88.config;

import com.cezarg.regentidev88.security.authFilters.facebook.FacebookAuthFilter;
import com.cezarg.regentidev88.security.authFilters.facebook.FacebookAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Order(200)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UnauthorizedHandler unauthorizedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.cors().disable();
        http.csrf().disable();
        http.exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests().anyRequest().fullyAuthenticated();

        // configure filters
        http.addFilterBefore(new FacebookAuthFilter(), UsernamePasswordAuthenticationFilter.class);

        // configure authentication providers
        http.authenticationProvider(new FacebookAuthProvider());

        // @formatter:on
    }


}
