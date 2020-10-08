package com.app.twitter_fetch.model.filter_json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class FilterMetaDataSent {
    private String sent;
    private Summary summary;

    public FilterMetaDataSent() {
        super();
    }

    public String getSent() {
        return sent;
    }

    public void setSent(String sent) {
        this.sent = sent;
    }

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        String output = "MetaDataSent: { sent = " + sent + '}';
        return output;
    }
}
