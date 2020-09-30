package com.app.twitter_fetch.model.filter_json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Filter {
    private Long id;
    private String value;
    private String tag;

    public Filter() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        String output = "{ id=" + id + ", value='" + value + "', tag='" + tag + '\'' + '}';
        return output;
    }

    public Filter(String value, String tag) {
        this.value = value;
        this.tag = tag;
    }
}
