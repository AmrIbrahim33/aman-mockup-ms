package com.isoft.mockup.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

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

    @Column(name = "request_id")
    private Long requestID;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "family_name")
    private String familyName;

    @Column(name = "national_id")
    private String nationalID;

    @Column(name = "passport_no")
    private String passportNo;

    @Column(name = "passport_issue_country")
    private String passportIssueCountry;

    @Column(name = "license_type")
    private String licenseType;

    @Column(name = "traffic_unit")
    private String trafficUnit;

    @Column(name = "birth_date")
    private String birthDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRequestID() {
        return requestID;
    }

    public DlsRequests requestID(Long requestID) {
        this.requestID = requestID;
        return this;
    }

    public void setRequestID(Long requestID) {
        this.requestID = requestID;
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

    public String getNationalID() {
        return nationalID;
    }

    public DlsRequests nationalID(String nationalID) {
        this.nationalID = nationalID;
        return this;
    }

    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public DlsRequests passportNo(String passportNo) {
        this.passportNo = passportNo;
        return this;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
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

    public String getLicenseType() {
        return licenseType;
    }

    public DlsRequests licenseType(String licenseType) {
        this.licenseType = licenseType;
        return this;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getTrafficUnit() {
        return trafficUnit;
    }

    public DlsRequests trafficUnit(String trafficUnit) {
        this.trafficUnit = trafficUnit;
        return this;
    }

    public void setTrafficUnit(String trafficUnit) {
        this.trafficUnit = trafficUnit;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public DlsRequests birthDate(String birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
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
