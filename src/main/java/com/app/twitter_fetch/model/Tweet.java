package com.app.twitter_fetch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tweet {
    private TweetData data;

    public Tweet() {
        super();
    }

    public TweetData getData() {
        return data;
    }

    public void setData(TweetData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        String output = "Tweet { data=" + data + '}';
        return output;
    }
}
