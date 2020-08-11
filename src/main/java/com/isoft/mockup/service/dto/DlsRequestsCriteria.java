package com.isoft.mockup.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.isoft.mockup.domain.DlsRequests} entity. This class is used
 * in {@link com.isoft.mockup.web.rest.DlsRequestsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /dls-requests?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DlsRequestsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter requestID;

    private StringFilter firstName;

    private StringFilter middleName;

    private StringFilter lastName;

    private StringFilter familyName;

    private StringFilter nationalID;

    private StringFilter passportNo;

    private StringFilter passportIssueCountry;

    private StringFilter licenseType;

    private StringFilter trafficUnit;

    private StringFilter birthDate;

    public DlsRequestsCriteria(){
    }

    public DlsRequestsCriteria(DlsRequestsCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.requestID = other.requestID == null ? null : other.requestID.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.middleName = other.middleName == null ? null : other.middleName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.familyName = other.familyName == null ? null : other.familyName.copy();
        this.nationalID = other.nationalID == null ? null : other.nationalID.copy();
        this.passportNo = other.passportNo == null ? null : other.passportNo.copy();
        this.passportIssueCountry = other.passportIssueCountry == null ? null : other.passportIssueCountry.copy();
        this.licenseType = other.licenseType == null ? null : other.licenseType.copy();
        this.trafficUnit = other.trafficUnit == null ? null : other.trafficUnit.copy();
        this.birthDate = other.birthDate == null ? null : other.birthDate.copy();
    }

    @Override
    public DlsRequestsCriteria copy() {
        return new DlsRequestsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getRequestID() {
        return requestID;
    }

    public void setRequestID(LongFilter requestID) {
        this.requestID = requestID;
    }

    public StringFilter getFirstName() {
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getMiddleName() {
        return middleName;
    }

    public void setMiddleName(StringFilter middleName) {
        this.middleName = middleName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getFamilyName() {
        return familyName;
    }

    public void setFamilyName(StringFilter familyName) {
        this.familyName = familyName;
    }

    public StringFilter getNationalID() {
        return nationalID;
    }

    public void setNationalID(StringFilter nationalID) {
        this.nationalID = nationalID;
    }

    public StringFilter getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(StringFilter passportNo) {
        this.passportNo = passportNo;
    }

    public StringFilter getPassportIssueCountry() {
        return passportIssueCountry;
    }

    public void setPassportIssueCountry(StringFilter passportIssueCountry) {
        this.passportIssueCountry = passportIssueCountry;
    }

    public StringFilter getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(StringFilter licenseType) {
        this.licenseType = licenseType;
    }

    public StringFilter getTrafficUnit() {
        return trafficUnit;
    }

    public void setTrafficUnit(StringFilter trafficUnit) {
        this.trafficUnit = trafficUnit;
    }

    public StringFilter getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(StringFilter birthDate) {
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
        final DlsRequestsCriteria that = (DlsRequestsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(requestID, that.requestID) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(middleName, that.middleName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(familyName, that.familyName) &&
            Objects.equals(nationalID, that.nationalID) &&
            Objects.equals(passportNo, that.passportNo) &&
            Objects.equals(passportIssueCountry, that.passportIssueCountry) &&
            Objects.equals(licenseType, that.licenseType) &&
            Objects.equals(trafficUnit, that.trafficUnit) &&
            Objects.equals(birthDate, that.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        requestID,
        firstName,
        middleName,
        lastName,
        familyName,
        nationalID,
        passportNo,
        passportIssueCountry,
        licenseType,
        trafficUnit,
        birthDate
        );
    }

    @Override
    public String toString() {
        return "DlsRequestsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (requestID != null ? "requestID=" + requestID + ", " : "") +
                (firstName != null ? "firstName=" + firstName + ", " : "") +
                (middleName != null ? "middleName=" + middleName + ", " : "") +
                (lastName != null ? "lastName=" + lastName + ", " : "") +
                (familyName != null ? "familyName=" + familyName + ", " : "") +
                (nationalID != null ? "nationalID=" + nationalID + ", " : "") +
                (passportNo != null ? "passportNo=" + passportNo + ", " : "") +
                (passportIssueCountry != null ? "passportIssueCountry=" + passportIssueCountry + ", " : "") +
                (licenseType != null ? "licenseType=" + licenseType + ", " : "") +
                (trafficUnit != null ? "trafficUnit=" + trafficUnit + ", " : "") +
                (birthDate != null ? "birthDate=" + birthDate + ", " : "") +
            "}";
    }

}
