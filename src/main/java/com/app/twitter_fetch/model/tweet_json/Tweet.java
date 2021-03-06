package com.app.twitter_fetch.model.tweet_json;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.app.twitter_fetch.model.filter_json.Filter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name="tweets")
public class Tweet {
    
    @Id
    private Long id;
    
    @Column
    private String text;

    @Column
    public String created_at;

    @JsonProperty("matching_rules")
    private Filter[] matchingRules;

    public Tweet() {
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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        String output = "Data {" + "id=" + id + ", text='" + text + '\'' + "created_at=" + created_at + + '}';
        return output;
    }
}
