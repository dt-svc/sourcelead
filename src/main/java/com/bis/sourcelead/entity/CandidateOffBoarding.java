package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "CANDIDATE_OFF_BOARDING")
public class CandidateOffBoarding implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User userId;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "ADDRESS_1")
	@Type(type="text")
	private String address1;
	
	@Column(name = "ADDRESS_2")
	@Type(type="text")
	private String address2;
	
	@Column(name = "LOCATION", columnDefinition = "LONGTEXT default NULL")
	private String location;
	
	@Column(name = "COUNTRY", columnDefinition = "LONGTEXT default NULL")
	private String country;
	
	@Column(name = "STATE", columnDefinition = "LONGTEXT default NULL")
	private String state;
	
	@Column(name = "CITY", columnDefinition = "LONGTEXT default NULL")
	private String city;
	
	@Column(name = "ZIPCODE")
	private String zipCode;
	
	@Column(name = "MOBILE_NUMBER")
	private String mobileNumber;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "ROLE")
	private String role;
	
	@Column(name = "DATE_OF_JOIN")
	private Date dateOfJoin;
	
	@Column(name = "END_DATE")
	private Date endDate;
	
	@Column(name = "REASON")
	private String reason;
	
	@Column(name = "EXPLANATION")
	@Type(type="text")
	private String explanation;
	
	@Column(name = "NOTICE_PERIOD")
	private String noticePeriod;
	
	@Column(name = "EFFECTIVE_DATE")
	private Date effectiveDate;
	
	@Column(name = "HAVE_TO_WORK_IN_NOTICE_PERIOD")
	private String haveToWorkInNoticePeriod;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_LOCATION_ID")
	private OrgLocations orgLocationId;
	
	@Column(name = "CANDIDATE_OFFBOARD_UUID")
	private String offboardUUID;
	
	@Column(name = "CANDIDATE_OFFBOARD_STATUS")
	private String offBoardStatus;
	
	@Column(name = "OFFBOARD_TEMPLATE_ID")
	@Type(type="text")
	private String offboardTemplateId;
	
	@Column(name = "OFFBOARD_SUBMITTED")
	private String offboardSubmitted;
	
	@Column(name = "WANTED_TO_SELL_PROPERTY")
	private String wantedToSellProperty;
	
	@Column(name = "WANTED_TO_SUB_LEASE")
	private String wantedToSubLease;
	
	@Column(name = "LOOKING_FOR_PUBLIC_STORAGE")
	private String lookingForPublicStorage;
	
	@Column(name = "LOOKING_FOR_ACCOMMODATION")
	private String lookingForAccommodation;
	
	@Column(name = "RATING")
	private Integer rating;
	
	@Column(name = "SURVEY_EXPLANATION")
	@Type(type="text")
	private String surveyExplanation;
	
	@Column(name = "SURVEY_REASON")
	@Type(type="text")
	private String surveyReason;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "EMPLOYMENT_TYPE")
	private String employmentType;
	
	
	@Transient
	private String dateOfJoinStr;
	
	@Transient
	private String endDateStr;
	
	@Transient
	private String effectiveDateStr;
	
	@Transient
	private String candidateName;
	
	@Transient
	private Long offBoardUserId;
	
	@Transient
	private String templateNames;
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}


	public Date getDateOfJoin() {
		return dateOfJoin;
	}

	public void setDateOfJoin(Date dateOfJoin) {
		this.dateOfJoin = dateOfJoin;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public String getNoticePeriod() {
		return noticePeriod;
	}

	public void setNoticePeriod(String noticePeriod) {
		this.noticePeriod = noticePeriod;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getHaveToWorkInNoticePeriod() {
		return haveToWorkInNoticePeriod;
	}

	public void setHaveToWorkInNoticePeriod(String haveToWorkInNoticePeriod) {
		this.haveToWorkInNoticePeriod = haveToWorkInNoticePeriod;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getOffboardUUID() {
		return offboardUUID;
	}

	public void setOffboardUUID(String offboardUUID) {
		this.offboardUUID = offboardUUID;
	}

	public String getOffBoardStatus() {
		return offBoardStatus;
	}

	public void setOffBoardStatus(String offBoardStatus) {
		this.offBoardStatus = offBoardStatus;
	}

	public String getOffboardSubmitted() {
		return offboardSubmitted;
	}

	public void setOffboardSubmitted(String offboardSubmitted) {
		this.offboardSubmitted = offboardSubmitted;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}


	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getOffboardTemplateId() {
		return offboardTemplateId;
	}

	public void setOffboardTemplateId(String offboardTemplateId) {
		this.offboardTemplateId = offboardTemplateId;
	}

	public String getDateOfJoinStr() {
		return dateOfJoinStr;
	}

	public void setDateOfJoinStr(String dateOfJoinStr) {
		this.dateOfJoinStr = dateOfJoinStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public String getEffectiveDateStr() {
		return effectiveDateStr;
	}

	public void setEffectiveDateStr(String effectiveDateStr) {
		this.effectiveDateStr = effectiveDateStr;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public Long getOffBoardUserId() {
		return offBoardUserId;
	}

	public void setOffBoardUserId(Long offBoardUserId) {
		this.offBoardUserId = offBoardUserId;
	}

	public String getWantedToSellProperty() {
		return wantedToSellProperty;
	}

	public void setWantedToSellProperty(String wantedToSellProperty) {
		this.wantedToSellProperty = wantedToSellProperty;
	}

	public String getWantedToSubLease() {
		return wantedToSubLease;
	}

	public void setWantedToSubLease(String wantedToSubLease) {
		this.wantedToSubLease = wantedToSubLease;
	}

	public String getLookingForPublicStorage() {
		return lookingForPublicStorage;
	}

	public void setLookingForPublicStorage(String lookingForPublicStorage) {
		this.lookingForPublicStorage = lookingForPublicStorage;
	}

	public String getLookingForAccommodation() {
		return lookingForAccommodation;
	}

	public void setLookingForAccommodation(String lookingForAccommodation) {
		this.lookingForAccommodation = lookingForAccommodation;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getSurveyExplanation() {
		return surveyExplanation;
	}

	public void setSurveyExplanation(String surveyExplanation) {
		this.surveyExplanation = surveyExplanation;
	}

	public String getSurveyReason() {
		return surveyReason;
	}

	public void setSurveyReason(String surveyReason) {
		this.surveyReason = surveyReason;
	}

	public String getTemplateNames() {
		return templateNames;
	}

	public void setTemplateNames(String templateNames) {
		this.templateNames = templateNames;
	}

	public String getEmploymentType()
	{
		return employmentType;
	}

	public void setEmploymentType(String employmentType)
	{
		this.employmentType = employmentType;
	}
	

}
