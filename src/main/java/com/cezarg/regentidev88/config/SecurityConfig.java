package com.cezarg.regentidev88.config;

import com.cezarg.regentidev88.security.authFilters.facebook.FacebookAuthFilter;
import com.cezarg.regentidev88.security.authFilters.facebook.FacebookAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UnauthorizedHandler unauthorizedHandler;

    @Autowired
    FacebookAuthProvider facebookAuthProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        // @formatter:off
//        http.addFilterBefore(new FacebookAuthFilter(), UsernamePasswordAuthenticationFilter.class);
//        http.authenticationProvider(facebookAuthProvider);
//
//        http.cors().disable();
//        http.csrf().disable();
//        http.exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
//                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and().authorizeRequests().anyRequest().fullyAuthenticated();
//        // @formatter:on
        http.addFilterBefore(facebookAuthFilter(), BasicAuthenticationFilter.class);
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .csrf().disable()
                .cors().disable();


    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Collections.singletonList(facebookAuthProvider));
    }

    @Bean
    public FacebookAuthFilter facebookAuthFilter() {
        return new FacebookAuthFilter(authenticationManager());
    }
}
