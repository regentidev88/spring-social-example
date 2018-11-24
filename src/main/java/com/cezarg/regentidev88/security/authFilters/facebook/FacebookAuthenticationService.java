package com.cezarg.regentidev88.security.authFilters.facebook;

import com.cezarg.regentidev88.exception.BadRequestException;
import com.cezarg.regentidev88.security.authFilters.AuthenticationService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;

@Component
public class FacebookAuthenticationService implements AuthenticationService {

    @Override
    public String verifyToken(String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity entity = new HttpEntity(headers);
            String fields = "id,email,first_name,last_name";
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("https://graph.facebook.com/me")
                    .queryParam("access_token", token).queryParam("fields", fields);

            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.exchange(
                    uriBuilder.toUriString(), HttpMethod.GET, entity, String.class).getBody();
        } catch (HttpClientErrorException e) {
            throw new BadRequestException("Invalid access token");
        } catch (Exception exp) {
            throw new BadRequestException("Invalid user");
        }
    }
}