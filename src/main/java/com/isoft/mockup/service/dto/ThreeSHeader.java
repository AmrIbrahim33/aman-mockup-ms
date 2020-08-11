package com.isoft.mockup.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ThreeSHeader {

    @JsonProperty("version")
    private String version;

    @JsonProperty("category")
    private String category;

    @JsonProperty("service")
    private String service;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("tid")
    private String tid;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    @Override
    public String toString() {
        return "ThreeSRequestHeader{" +
            "version='" + version + '\'' +
            ", category='" + category + '\'' +
            ", service='" + service + '\'' +
            ", timestamp='" + timestamp + '\'' +
            ", tid='" + tid + '\'' +
            '}';
    }
}
