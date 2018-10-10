package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "CANDIDATE_MARKET_PLACE")
public class CandidateMarketPlace implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CANDIDATE_ID", nullable = false)
	private Candidate candidateId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MASTER_CANDIDATE_ID", nullable = false)
	private MasterCandidate masterCandidateId;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_LOCATION_ID", nullable = false)
	private OrgLocations orgLocationId;

 
	@Column(name = "NAME")
	private String  fullName;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "ALTERNATE_EMAIL")
	private String alternateEmail;

	@Column(name = "ALTERNATE_MOBILE_PHONE")
	private String alternateMobilePhone;

	@Column(name = "MOBILE_PHONE")
	private String mobilePhone;

	@Column(name = "LOCATION", columnDefinition = "LONGTEXT default NULL")
	private String location;
	
	@Column(name = "PROFILE_PERCENTAGE")
	private Integer profilePercentage;
	
	@Column(name = "EXPERIENCE_YEARS")
	private String experienceYears;

	@Column(name = "EXPERIENCE_MONTHS")
	private String experienceMonths;

	@Column(name = "NOTICE_PERIOD")
	private String noticePeriod;

	@Column(name = "DESIGNATION")
	private String designation;

	@Column(name = "EMPLOYEMENT_TYPE")
	private String employmentType;

	@Column(name = "CURRENT_RATE")
	private String currentRate;

	@Column(name = "CURRENT_RATE_TYPE")
	private String currentRateType;

	@Column(name = "CURRENT_RATE_CURRENCY")
	private String currentRateCurrency;

	@Column(name = "EXPECTED_RATE")
	private String expectedRate;

	@Column(name = "EXPECTED_RATE_TYPE")
	private String expectedRateType;

	@Column(name = "EXPECTED_RATE_CURRENCY")
	private String expectedRateCurrencey;
	
	@Column(name = "MARKET_PRICE")
	private String marketPrice;

	@Column(name = "MARKET_RATE_CURRENCY")
	private String marketRateCurrencey;
	
	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@ManyToOne
	@JoinColumn(name = "CREATED_BY", nullable = false)
	private User createdBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@ManyToOne
	@JoinColumn(name = "UPDATED_BY", nullable = false)
	private User updatedBy;

	@Column(name = "CANDIDATE_UUID")
	private String candidateUUID;

	@Column(name = "SKILLS_NAME", columnDefinition = "TEXT default NULL")
	private String skillsName;
	
	@Column(name = "GENDER")
	private String gender;

	@Column(name = "NATIONALITY")
	private String nationality;

	@Column(name = "MARITAL_STATUS")
	private String maritalStatus;
	
	@Column(name = "CAN_MARKET_STATUS")
	private String candidateMarketStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Candidate getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Candidate candidateId) {
		this.candidateId = candidateId;
	}

	public MasterCandidate getMasterCandidateId() {
		return masterCandidateId;
	}

	public void setMasterCandidateId(MasterCandidate masterCandidateId) {
		this.masterCandidateId = masterCandidateId;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}


	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAlternateEmail() {
		return alternateEmail;
	}

	public void setAlternateEmail(String alternateEmail) {
		this.alternateEmail = alternateEmail;
	}

	public String getAlternateMobilePhone() {
		return alternateMobilePhone;
	}

	public void setAlternateMobilePhone(String alternateMobilePhone) {
		this.alternateMobilePhone = alternateMobilePhone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}


	public Integer getProfilePercentage() {
		return profilePercentage;
	}

	public void setProfilePercentage(Integer profilePercentage) {
		this.profilePercentage = profilePercentage;
	}

	public String getExperienceYears() {
		return experienceYears;
	}

	public void setExperienceYears(String experienceYears) {
		this.experienceYears = experienceYears;
	}

	public String getExperienceMonths() {
		return experienceMonths;
	}

	public void setExperienceMonths(String experienceMonths) {
		this.experienceMonths = experienceMonths;
	}

	public String getNoticePeriod() {
		return noticePeriod;
	}

	public void setNoticePeriod(String noticePeriod) {
		this.noticePeriod = noticePeriod;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}

	public String getCurrentRate() {
		return currentRate;
	}

	public void setCurrentRate(String currentRate) {
		this.currentRate = currentRate;
	}

	public String getCurrentRateType() {
		return currentRateType;
	}

	public void setCurrentRateType(String currentRateType) {
		this.currentRateType = currentRateType;
	}

	public String getCurrentRateCurrency() {
		return currentRateCurrency;
	}

	public void setCurrentRateCurrency(String currentRateCurrency) {
		this.currentRateCurrency = currentRateCurrency;
	}

	public String getExpectedRate() {
		return expectedRate;
	}

	public void setExpectedRate(String expectedRate) {
		this.expectedRate = expectedRate;
	}

	public String getExpectedRateType() {
		return expectedRateType;
	}

	public void setExpectedRateType(String expectedRateType) {
		this.expectedRateType = expectedRateType;
	}

	public String getExpectedRateCurrencey() {
		return expectedRateCurrencey;
	}

	public void setExpectedRateCurrencey(String expectedRateCurrencey) {
		this.expectedRateCurrencey = expectedRateCurrencey;
	}

	public String getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getMarketRateCurrencey() {
		return marketRateCurrencey;
	}

	public void setMarketRateCurrencey(String marketRateCurrencey) {
		this.marketRateCurrencey = marketRateCurrencey;
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


	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getCandidateUUID() {
		return candidateUUID;
	}

	public void setCandidateUUID(String candidateUUID) {
		this.candidateUUID = candidateUUID;
	}

	public String getSkillsName() {
		return skillsName;
	}

	public void setSkillsName(String skillsName) {
		this.skillsName = skillsName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getCandidateMarketStatus() {
		return candidateMarketStatus;
	}

	public void setCandidateMarketStatus(String candidateMarketStatus) {
		this.candidateMarketStatus = candidateMarketStatus;
	}

	

}
