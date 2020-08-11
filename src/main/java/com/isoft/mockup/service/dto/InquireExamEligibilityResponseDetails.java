package com.isoft.mockup.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InquireExamEligibilityResponseDetails {

    @JsonProperty("requestID")
    private long requestID;

    @JsonProperty("fName")
    private String firstName;

    @JsonProperty("mName")
    private String middleName;

    @JsonProperty("lName")
    private String lastName;

    @JsonProperty("exName")
    private String familyName;

    @JsonProperty("nationalID")
    private String nationalID;

    @JsonProperty("passportNo")
    private String passportNo;

    @JsonProperty("passportIssueCountry")
    private String passportIssueCountry;

    @JsonProperty("licenseType")
    private String licenseType;

    @JsonProperty("trafficUnit")
    private String trafficUnit;

    @JsonProperty("birthDate")
    private String birthDate;

    @JsonProperty("queueNumber")
    private String queueNumber;

    public long getRequestID() {
        return requestID;
    }

    public void setRequestID(long requestID) {
        this.requestID = requestID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getNationalID() {
        return nationalID;
    }

    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public String getPassportIssueCountry() {
        return passportIssueCountry;
    }

    public void setPassportIssueCountry(String passportIssueCountry) {
        this.passportIssueCountry = passportIssueCountry;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getTrafficUnit() {
        return trafficUnit;
    }

    public void setTrafficUnit(String trafficUnit) {
        this.trafficUnit = trafficUnit;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getQueueNumber() {
        return queueNumber;
    }

    public void setQueueNumber(String queueNumber) {
        this.queueNumber = queueNumber;
    }

    @Override
    public String toString() {
        return "InquireExamEligibilityResponseDetails{" +
            "requestID=" + requestID +
            ", firstName='" + firstName + '\'' +
            ", middleName='" + middleName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", familyName='" + familyName + '\'' +
            ", nationalID='" + nationalID + '\'' +
            ", passportNo='" + passportNo + '\'' +
            ", passportIssueCountry='" + passportIssueCountry + '\'' +
            ", licenseType='" + licenseType + '\'' +
            ", trafficUnit='" + trafficUnit + '\'' +
            ", birthDate='" + birthDate + '\'' +
            ", queueNumber='" + queueNumber + '\'' +
            '}';
    }
}
