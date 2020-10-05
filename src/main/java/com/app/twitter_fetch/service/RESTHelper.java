package com.app.twitter_fetch.service;

import java.util.Collections;
import java.util.ResourceBundle;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class RESTHelper {
    
    public static String getBearerToken() {
        ResourceBundle bundle = ResourceBundle.getBundle("application");

        String bearerToken = bundle.getString("twitter.bearer_token");

        return "Bearer " + bearerToken;
    }
}
