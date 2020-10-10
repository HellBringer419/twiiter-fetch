package com.app.twitter_fetch.model.filter_json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Filter {
    private String id;
    private String value;
    private String tag;

    public Filter() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
