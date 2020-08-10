package com.isoft.mockup.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DlsRequests.
 */
@Entity
@Table(name = "dls_requests")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DlsRequests extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dls_req")
    @SequenceGenerator(name = "dls_req", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "transaction_type", nullable = false)
    private String transactionType;

    @NotNull
    @Column(name = "license_category", nullable = false)
    private String licenseCategory;

    @NotNull
    @Column(name = "request_no", nullable = false)
    private String requestNo;

    @Column(name = "exported")
    private Boolean exported;

    @Column(name = "family_name")
    private String familyName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "national_id")
    private String nationalId;

    @Column(name = "passport_issue_country")
    private String passportIssueCountry;

    @Column(name = "passport_key")
    private String passportKey;

    @Column(name = "traffic_unit_code")
    private String trafficUnitCode;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "licence_expiry_date")
    private LocalDate licenceExpiryDate;

    @Column(name = "licence_type_ar")
    private String licenceTypeAr;

    @Column(name = "licence_type_en")
    private String licenceTypeEn;

    @Column(name = "licence_status_ar")
    private String licenceStatusAr;

    @Column(name = "licence_status_en")
    private String licenceStatusEn;

    @Column(name = "licence_status")
    private Integer licenceStatus;

    @Column(name = "applicant_id")
    private Long applicantId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "center_id")
    private Long centerId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public DlsRequests transactionType(String transactionType) {
        this.transactionType = transactionType;
        return this;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getLicenseCategory() {
        return licenseCategory;
    }

    public DlsRequests licenseCategory(String licenseCategory) {
        this.licenseCategory = licenseCategory;
        return this;
    }

    public void setLicenseCategory(String licenseCategory) {
        this.licenseCategory = licenseCategory;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public DlsRequests requestNo(String requestNo) {
        this.requestNo = requestNo;
        return this;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public Boolean isExported() {
        return exported;
    }

    public DlsRequests exported(Boolean exported) {
        this.exported = exported;
        return this;
    }

    public void setExported(Boolean exported) {
        this.exported = exported;
    }

    public String getFamilyName() {
        return familyName;
    }

    public DlsRequests familyName(String familyName) {
        this.familyName = familyName;
        return this;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getFirstName() {
        return firstName;
    }

    public DlsRequests firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public DlsRequests lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public DlsRequests middleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getFullName() {
        return fullName;
    }

    public DlsRequests fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNationalId() {
        return nationalId;
    }

    public DlsRequests nationalId(String nationalId) {
        this.nationalId = nationalId;
        return this;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getPassportIssueCountry() {
        return passportIssueCountry;
    }

    public DlsRequests passportIssueCountry(String passportIssueCountry) {
        this.passportIssueCountry = passportIssueCountry;
        return this;
    }

    public void setPassportIssueCountry(String passportIssueCountry) {
        this.passportIssueCountry = passportIssueCountry;
    }

    public String getPassportKey() {
        return passportKey;
    }

    public DlsRequests passportKey(String passportKey) {
        this.passportKey = passportKey;
        return this;
    }

    public void setPassportKey(String passportKey) {
        this.passportKey = passportKey;
    }

    public String getTrafficUnitCode() {
        return trafficUnitCode;
    }

    public DlsRequests trafficUnitCode(String trafficUnitCode) {
        this.trafficUnitCode = trafficUnitCode;
        return this;
    }

    public void setTrafficUnitCode(String trafficUnitCode) {
        this.trafficUnitCode = trafficUnitCode;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public DlsRequests birthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getLicenceExpiryDate() {
        return licenceExpiryDate;
    }

    public DlsRequests licenceExpiryDate(LocalDate licenceExpiryDate) {
        this.licenceExpiryDate = licenceExpiryDate;
        return this;
    }

    public void setLicenceExpiryDate(LocalDate licenceExpiryDate) {
        this.licenceExpiryDate = licenceExpiryDate;
    }

    public String getLicenceTypeAr() {
        return licenceTypeAr;
    }

    public DlsRequests licenceTypeAr(String licenceTypeAr) {
        this.licenceTypeAr = licenceTypeAr;
        return this;
    }

    public void setLicenceTypeAr(String licenceTypeAr) {
        this.licenceTypeAr = licenceTypeAr;
    }

    public String getLicenceTypeEn() {
        return licenceTypeEn;
    }

    public DlsRequests licenceTypeEn(String licenceTypeEn) {
        this.licenceTypeEn = licenceTypeEn;
        return this;
    }

    public void setLicenceTypeEn(String licenceTypeEn) {
        this.licenceTypeEn = licenceTypeEn;
    }

    public String getLicenceStatusAr() {
        return licenceStatusAr;
    }

    public DlsRequests licenceStatusAr(String licenceStatusAr) {
        this.licenceStatusAr = licenceStatusAr;
        return this;
    }

    public void setLicenceStatusAr(String licenceStatusAr) {
        this.licenceStatusAr = licenceStatusAr;
    }

    public String getLicenceStatusEn() {
        return licenceStatusEn;
    }

    public DlsRequests licenceStatusEn(String licenceStatusEn) {
        this.licenceStatusEn = licenceStatusEn;
        return this;
    }

    public void setLicenceStatusEn(String licenceStatusEn) {
        this.licenceStatusEn = licenceStatusEn;
    }

    public Integer getLicenceStatus() {
        return licenceStatus;
    }

    public DlsRequests licenceStatus(Integer licenceStatus) {
        this.licenceStatus = licenceStatus;
        return this;
    }

    public void setLicenceStatus(Integer licenceStatus) {
        this.licenceStatus = licenceStatus;
    }

    public Long getApplicantId() {
        return applicantId;
    }

    public DlsRequests applicantId(Long applicantId) {
        this.applicantId = applicantId;
        return this;
    }

    public void setApplicantId(Long applicantId) {
        this.applicantId = applicantId;
    }

    public Long getUserId() {
        return userId;
    }

    public DlsRequests userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCenterId() {
        return centerId;
    }

    public DlsRequests centerId(Long centerId) {
        this.centerId = centerId;
        return this;
    }

    public void setCenterId(Long centerId) {
        this.centerId = centerId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DlsRequests)) {
            return false;
        }
        return id != null && id.equals(((DlsRequests) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DlsRequests{" +
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
