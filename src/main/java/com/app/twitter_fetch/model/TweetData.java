package com.app.twitter_fetch.model;

import com.app.twitter_fetch.model.filter_json.Filter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TweetData {
    private Long id;
    private String text;

    @JsonProperty("matching_rules")
    private Filter[] matchingRules;

    public TweetData() {
        super();
    }

    public Filter[] getMatchingRules() {
        return matchingRules;
    }

    public void setMatchingRules(Filter[] matchingRules) {
        this.matchingRules = matchingRules;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        String output = "Data {" + "id=" + id + ", text='" + text + '\'' + '}';
        return output;
    }
}
