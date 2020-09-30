package com.app.twitter_fetch.model.filter_json;

public class FilterData {
    private Filter[] data;
    private FilterMetaDataSent meta;

    public FilterData() {
        super();
    }

    public FilterMetaDataSent getMeta() {
        return meta;
    }

    public void setMeta(FilterMetaDataSent meta) {
        this.meta = meta;
    }

    public Filter[] getData() {
        return data;
    }

    public void setData(Filter[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        String output = "Filters { data=" + data + '}';
        return output;
    }
}
