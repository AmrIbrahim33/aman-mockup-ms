package com.isoft.mockup.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InquireExamEligibilityResponseBody {

    @JsonProperty("error_Message")
    private int  errorMessage;

    @JsonProperty("requests")
    private List<InquireExamEligibilityResponseDetails> requests;

    public int getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(int errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<InquireExamEligibilityResponseDetails> getRequests() {
        return requests;
    }

    public void setRequests(List<InquireExamEligibilityResponseDetails> requests) {
        this.requests = requests;
    }
}
