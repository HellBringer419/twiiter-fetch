package com.app.twitter_fetch.service;

public class APIHelper {
    
    public static String getBearerToken() {
        String bearerToken = System.getenv("TWITTER_BEARER_TOKEN");

        return "Bearer " + bearerToken;
    }
}
