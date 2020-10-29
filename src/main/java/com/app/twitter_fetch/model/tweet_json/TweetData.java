package com.app.twitter_fetch.model.tweet_json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TweetData {
    private Tweet data;

    public TweetData() {
        super();
    }

    public Tweet getData() {
        return data;
    }

    public void setData(Tweet data) {
        this.data = data;
    }

    @Override
    public String toString() {
        String output = "Tweet { data=" + data + '}';
        return output;
    }
}
