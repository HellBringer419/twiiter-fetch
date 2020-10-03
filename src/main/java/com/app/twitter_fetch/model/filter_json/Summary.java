package com.app.twitter_fetch.model.filter_json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Summary {
    private Integer created;

    @JsonProperty("not_created")
    private Integer notCreated;

    public Summary() {
        super();
    }

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
    }

    public Integer getNotCreated() {
        return notCreated;
    }
    
    public void setNotCreated(Integer notCreated) {
        this.notCreated = notCreated;
    }
}
