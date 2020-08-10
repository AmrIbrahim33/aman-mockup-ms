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
import io.github.jhipster.service.filter.LocalDateFilter;

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

    private StringFilter transactionType;

    private StringFilter licenseCategory;

    private StringFilter requestNo;

    private BooleanFilter exported;

    private StringFilter familyName;

    private StringFilter firstName;

    private StringFilter lastName;

    private StringFilter middleName;

    private StringFilter fullName;

    private StringFilter nationalId;

    private StringFilter passportIssueCountry;

    private StringFilter passportKey;

    private StringFilter trafficUnitCode;

    private LocalDateFilter birthDate;

    private LocalDateFilter licenceExpiryDate;

    private StringFilter licenceTypeAr;

    private StringFilter licenceTypeEn;

    private StringFilter licenceStatusAr;

    private StringFilter licenceStatusEn;

    private IntegerFilter licenceStatus;

    private LongFilter applicantId;

    private LongFilter userId;

    private LongFilter centerId;

    public DlsRequestsCriteria(){
    }

    public DlsRequestsCriteria(DlsRequestsCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.transactionType = other.transactionType == null ? null : other.transactionType.copy();
        this.licenseCategory = other.licenseCategory == null ? null : other.licenseCategory.copy();
        this.requestNo = other.requestNo == null ? null : other.requestNo.copy();
        this.exported = other.exported == null ? null : other.exported.copy();
        this.familyName = other.familyName == null ? null : other.familyName.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.middleName = other.middleName == null ? null : other.middleName.copy();
        this.fullName = other.fullName == null ? null : other.fullName.copy();
        this.nationalId = other.nationalId == null ? null : other.nationalId.copy();
        this.passportIssueCountry = other.passportIssueCountry == null ? null : other.passportIssueCountry.copy();
        this.passportKey = other.passportKey == null ? null : other.passportKey.copy();
        this.trafficUnitCode = other.trafficUnitCode == null ? null : other.trafficUnitCode.copy();
        this.birthDate = other.birthDate == null ? null : other.birthDate.copy();
        this.licenceExpiryDate = other.licenceExpiryDate == null ? null : other.licenceExpiryDate.copy();
        this.licenceTypeAr = other.licenceTypeAr == null ? null : other.licenceTypeAr.copy();
        this.licenceTypeEn = other.licenceTypeEn == null ? null : other.licenceTypeEn.copy();
        this.licenceStatusAr = other.licenceStatusAr == null ? null : other.licenceStatusAr.copy();
        this.licenceStatusEn = other.licenceStatusEn == null ? null : other.licenceStatusEn.copy();
        this.licenceStatus = other.licenceStatus == null ? null : other.licenceStatus.copy();
        this.applicantId = other.applicantId == null ? null : other.applicantId.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.centerId = other.centerId == null ? null : other.centerId.copy();
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

    public StringFilter getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(StringFilter transactionType) {
        this.transactionType = transactionType;
    }

    public StringFilter getLicenseCategory() {
        return licenseCategory;
    }

    public void setLicenseCategory(StringFilter licenseCategory) {
        this.licenseCategory = licenseCategory;
    }

    public StringFilter getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(StringFilter requestNo) {
        this.requestNo = requestNo;
    }

    public BooleanFilter getExported() {
        return exported;
    }

    public void setExported(BooleanFilter exported) {
        this.exported = exported;
    }

    public StringFilter getFamilyName() {
        return familyName;
    }

    public void setFamilyName(StringFilter familyName) {
        this.familyName = familyName;
    }

    public StringFilter getFirstName() {
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getMiddleName() {
        return middleName;
    }

    public void setMiddleName(StringFilter middleName) {
        this.middleName = middleName;
    }

    public StringFilter getFullName() {
        return fullName;
    }

    public void setFullName(StringFilter fullName) {
        this.fullName = fullName;
    }

    public StringFilter getNationalId() {
        return nationalId;
    }

    public void setNationalId(StringFilter nationalId) {
        this.nationalId = nationalId;
    }

    public StringFilter getPassportIssueCountry() {
        return passportIssueCountry;
    }

    public void setPassportIssueCountry(StringFilter passportIssueCountry) {
        this.passportIssueCountry = passportIssueCountry;
    }

    public StringFilter getPassportKey() {
        return passportKey;
    }

    public void setPassportKey(StringFilter passportKey) {
        this.passportKey = passportKey;
    }

    public StringFilter getTrafficUnitCode() {
        return trafficUnitCode;
    }

    public void setTrafficUnitCode(StringFilter trafficUnitCode) {
        this.trafficUnitCode = trafficUnitCode;
    }

    public LocalDateFilter getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateFilter birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDateFilter getLicenceExpiryDate() {
        return licenceExpiryDate;
    }

    public void setLicenceExpiryDate(LocalDateFilter licenceExpiryDate) {
        this.licenceExpiryDate = licenceExpiryDate;
    }

    public StringFilter getLicenceTypeAr() {
        return licenceTypeAr;
    }

    public void setLicenceTypeAr(StringFilter licenceTypeAr) {
        this.licenceTypeAr = licenceTypeAr;
    }

    public StringFilter getLicenceTypeEn() {
        return licenceTypeEn;
    }

    public void setLicenceTypeEn(StringFilter licenceTypeEn) {
        this.licenceTypeEn = licenceTypeEn;
    }

    public StringFilter getLicenceStatusAr() {
        return licenceStatusAr;
    }

    public void setLicenceStatusAr(StringFilter licenceStatusAr) {
        this.licenceStatusAr = licenceStatusAr;
    }

    public StringFilter getLicenceStatusEn() {
        return licenceStatusEn;
    }

    public void setLicenceStatusEn(StringFilter licenceStatusEn) {
        this.licenceStatusEn = licenceStatusEn;
    }

    public IntegerFilter getLicenceStatus() {
        return licenceStatus;
    }

    public void setLicenceStatus(IntegerFilter licenceStatus) {
        this.licenceStatus = licenceStatus;
    }

    public LongFilter getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(LongFilter applicantId) {
        this.applicantId = applicantId;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getCenterId() {
        return centerId;
    }

    public void setCenterId(LongFilter centerId) {
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
        final DlsRequestsCriteria that = (DlsRequestsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(transactionType, that.transactionType) &&
            Objects.equals(licenseCategory, that.licenseCategory) &&
            Objects.equals(requestNo, that.requestNo) &&
            Objects.equals(exported, that.exported) &&
            Objects.equals(familyName, that.familyName) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(middleName, that.middleName) &&
            Objects.equals(fullName, that.fullName) &&
            Objects.equals(nationalId, that.nationalId) &&
            Objects.equals(passportIssueCountry, that.passportIssueCountry) &&
            Objects.equals(passportKey, that.passportKey) &&
            Objects.equals(trafficUnitCode, that.trafficUnitCode) &&
            Objects.equals(birthDate, that.birthDate) &&
            Objects.equals(licenceExpiryDate, that.licenceExpiryDate) &&
            Objects.equals(licenceTypeAr, that.licenceTypeAr) &&
            Objects.equals(licenceTypeEn, that.licenceTypeEn) &&
            Objects.equals(licenceStatusAr, that.licenceStatusAr) &&
            Objects.equals(licenceStatusEn, that.licenceStatusEn) &&
            Objects.equals(licenceStatus, that.licenceStatus) &&
            Objects.equals(applicantId, that.applicantId) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(centerId, that.centerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        transactionType,
        licenseCategory,
        requestNo,
        exported,
        familyName,
        firstName,
        lastName,
        middleName,
        fullName,
        nationalId,
        passportIssueCountry,
        passportKey,
        trafficUnitCode,
        birthDate,
        licenceExpiryDate,
        licenceTypeAr,
        licenceTypeEn,
        licenceStatusAr,
        licenceStatusEn,
        licenceStatus,
        applicantId,
        userId,
        centerId
        );
    }

    @Override
    public String toString() {
        return "DlsRequestsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (transactionType != null ? "transactionType=" + transactionType + ", " : "") +
                (licenseCategory != null ? "licenseCategory=" + licenseCategory + ", " : "") +
                (requestNo != null ? "requestNo=" + requestNo + ", " : "") +
                (exported != null ? "exported=" + exported + ", " : "") +
                (familyName != null ? "familyName=" + familyName + ", " : "") +
                (firstName != null ? "firstName=" + firstName + ", " : "") +
                (lastName != null ? "lastName=" + lastName + ", " : "") +
                (middleName != null ? "middleName=" + middleName + ", " : "") +
                (fullName != null ? "fullName=" + fullName + ", " : "") +
                (nationalId != null ? "nationalId=" + nationalId + ", " : "") +
                (passportIssueCountry != null ? "passportIssueCountry=" + passportIssueCountry + ", " : "") +
                (passportKey != null ? "passportKey=" + passportKey + ", " : "") +
                (trafficUnitCode != null ? "trafficUnitCode=" + trafficUnitCode + ", " : "") +
                (birthDate != null ? "birthDate=" + birthDate + ", " : "") +
                (licenceExpiryDate != null ? "licenceExpiryDate=" + licenceExpiryDate + ", " : "") +
                (licenceTypeAr != null ? "licenceTypeAr=" + licenceTypeAr + ", " : "") +
                (licenceTypeEn != null ? "licenceTypeEn=" + licenceTypeEn + ", " : "") +
                (licenceStatusAr != null ? "licenceStatusAr=" + licenceStatusAr + ", " : "") +
                (licenceStatusEn != null ? "licenceStatusEn=" + licenceStatusEn + ", " : "") +
                (licenceStatus != null ? "licenceStatus=" + licenceStatus + ", " : "") +
                (applicantId != null ? "applicantId=" + applicantId + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (centerId != null ? "centerId=" + centerId + ", " : "") +
            "}";
    }

}
