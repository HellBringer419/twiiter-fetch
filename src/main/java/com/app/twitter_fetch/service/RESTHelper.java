package com.app.twitter_fetch.service;

import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class RESTHelper {
    public static HttpEntity getHeaders() {
        

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        headers.set("Authorization",
                "Bearer AAAAAAAAAAAAAAAAAAAAAG9zIAEAAAAAYAX%2BDKaXrLwnPuUrzdAqo9FLY7E%3Dzn7GnSRVgkzDaZZJwjXDMEZ24zPYdlLldUTE8TdWSjLKVcmzWq");

        HttpEntity entity = new HttpEntity(headers);

        return entity;
    }
}
