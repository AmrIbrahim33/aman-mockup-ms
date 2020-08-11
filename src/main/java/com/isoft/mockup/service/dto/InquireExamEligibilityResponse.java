package com.isoft.mockup.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InquireExamEligibilityResponse {

    @JsonProperty("body")
    InquireExamEligibilityResponseBody body;

    @JsonProperty("header")
    ThreeSHeader header;

    public InquireExamEligibilityResponseBody getBody() {
        return body;
    }

    public void setBody(InquireExamEligibilityResponseBody body) {
        this.body = body;
    }

    public ThreeSHeader getHeader() {
        return header;
    }

    public void setHeader(ThreeSHeader header) {
        this.header = header;
    }

    @Override
    public String toString() {
        return "InquireExamEligibilityResponse{" +
            "body=" + body +
            ", header=" + header +
            '}';
    }
}
