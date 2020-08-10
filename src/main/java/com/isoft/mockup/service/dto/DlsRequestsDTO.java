package com.isoft.mockup.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.isoft.mockup.domain.DlsRequests} entity.
 */
public class DlsRequestsDTO implements Serializable {

    private Long id;

    @NotNull
    private String transactionType;

    @NotNull
    private String licenseCategory;

    @NotNull
    private String requestNo;

    private Boolean exported;

    private String familyName;

    private String firstName;

    private String lastName;

    private String middleName;

    private String fullName;

    private String nationalId;

    private String passportIssueCountry;

    private String passportKey;

    private String trafficUnitCode;

    private LocalDate birthDate;

    private LocalDate licenceExpiryDate;

    private String licenceTypeAr;

    private String licenceTypeEn;

    private String licenceStatusAr;

    private String licenceStatusEn;

    private Integer licenceStatus;

    private Long applicantId;

    private Long userId;

    private Long centerId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getLicenseCategory() {
        return licenseCategory;
    }

    public void setLicenseCategory(String licenseCategory) {
        this.licenseCategory = licenseCategory;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public Boolean isExported() {
        return exported;
    }

    public void setExported(Boolean exported) {
        this.exported = exported;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getPassportIssueCountry() {
        return passportIssueCountry;
    }

    public void setPassportIssueCountry(String passportIssueCountry) {
        this.passportIssueCountry = passportIssueCountry;
    }

    public String getPassportKey() {
        return passportKey;
    }

    public void setPassportKey(String passportKey) {
        this.passportKey = passportKey;
    }

    public String getTrafficUnitCode() {
        return trafficUnitCode;
    }

    public void setTrafficUnitCode(String trafficUnitCode) {
        this.trafficUnitCode = trafficUnitCode;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getLicenceExpiryDate() {
        return licenceExpiryDate;
    }

    public void setLicenceExpiryDate(LocalDate licenceExpiryDate) {
        this.licenceExpiryDate = licenceExpiryDate;
    }

    public String getLicenceTypeAr() {
        return licenceTypeAr;
    }

    public void setLicenceTypeAr(String licenceTypeAr) {
        this.licenceTypeAr = licenceTypeAr;
    }

    public String getLicenceTypeEn() {
        return licenceTypeEn;
    }

    public void setLicenceTypeEn(String licenceTypeEn) {
        this.licenceTypeEn = licenceTypeEn;
    }

    public String getLicenceStatusAr() {
        return licenceStatusAr;
    }

    public void setLicenceStatusAr(String licenceStatusAr) {
        this.licenceStatusAr = licenceStatusAr;
    }

    public String getLicenceStatusEn() {
        return licenceStatusEn;
    }

    public void setLicenceStatusEn(String licenceStatusEn) {
        this.licenceStatusEn = licenceStatusEn;
    }

    public Integer getLicenceStatus() {
        return licenceStatus;
    }

    public void setLicenceStatus(Integer licenceStatus) {
        this.licenceStatus = licenceStatus;
    }

    public Long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Long applicantId) {
        this.applicantId = applicantId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCenterId() {
        return centerId;
    }

    public void setCenterId(Long centerId) {
        this.centerId = centerId;
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
            ", transactionType='" + getTransactionType() + "'" +
            ", licenseCategory='" + getLicenseCategory() + "'" +
            ", requestNo='" + getRequestNo() + "'" +
            ", exported='" + isExported() + "'" +
            ", familyName='" + getFamilyName() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", nationalId='" + getNationalId() + "'" +
            ", passportIssueCountry='" + getPassportIssueCountry() + "'" +
            ", passportKey='" + getPassportKey() + "'" +
            ", trafficUnitCode='" + getTrafficUnitCode() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", licenceExpiryDate='" + getLicenceExpiryDate() + "'" +
            ", licenceTypeAr='" + getLicenceTypeAr() + "'" +
            ", licenceTypeEn='" + getLicenceTypeEn() + "'" +
            ", licenceStatusAr='" + getLicenceStatusAr() + "'" +
            ", licenceStatusEn='" + getLicenceStatusEn() + "'" +
            ", licenceStatus=" + getLicenceStatus() +
            ", applicantId=" + getApplicantId() +
            ", userId=" + getUserId() +
            ", centerId=" + getCenterId() +
            "}";
    }
}
