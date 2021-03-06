package com.isoft.mockup.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.isoft.mockup.domain.DlsRequests} entity.
 */
public class DlsRequestsDTO implements Serializable {

    private Long id;

    private Long requestID;

    private String firstName;

    private String middleName;

    private String lastName;

    private String familyName;

    private String nationalID;

    private String passportNo;

    private String passportIssueCountry;

    private String licenseType;

    private String trafficUnit;

    private String birthDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRequestID() {
        return requestID;
    }

    public void setRequestID(Long requestID) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DlsRequestsDTO dlsRequestsDTO = (DlsRequestsDTO) o;
        if (dlsRequestsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dlsRequestsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DlsRequestsDTO{" +
            "id=" + getId() +
            ", requestID=" + getRequestID() +
            ", firstName='" + getFirstName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", familyName='" + getFamilyName() + "'" +
            ", nationalID='" + getNationalID() + "'" +
            ", passportNo='" + getPassportNo() + "'" +
            ", passportIssueCountry='" + getPassportIssueCountry() + "'" +
            ", licenseType='" + getLicenseType() + "'" +
            ", trafficUnit='" + getTrafficUnit() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            "}";
    }
}
