package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name = "CANDIDATE_MARKET_PLACE_CART_VIEW")
public class CandidateMarketPlaceCartView implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "SOURCE_ORG_LOCATION_ID")
	private Long sourceOrgLocationId;
	
	@Column(name = "TARGET_ORG_LOCATION_ID")
	private Long targetOrgLocationId;
	
	@Column(name = "CANDIDATE_ID")
	private Long candidateId;
	
	@Column(name = "CANDIDATE_UUID")
	private String candidateUuid;
	
	@Column(name = "FULLNAME")
	private String  fullName;

	@Column(name = "LOCATION", columnDefinition = "LONGTEXT default NULL")
	private String location;

	@Column(name = "IMAGEURL")
	private String imageUrl;

	@Column(name = "SKILLSNAME", columnDefinition = "TEXT default NULL")
	private String skillsName;

	@Column(name = "DESIGNATION")
	private String designation;
	
	@Column(name = "CURRENT_RATE")
	private String currentRate;
	
	@Column(name = "CURR_RATE_CUR")
	private String currentRateCurrency;
	
	@Column(name = "CURRENT_RATE_TYPE")
	private String currentRateType;
	
	@Column(name = "EXP_RATE_TYPE")
	private String expectedRateType;
	
	@Column(name = "EXPECTED_RATE")
	private String expectedRate;

	@Column(name = "EXP_RATE_CUR")
	private String expectedRateCurrencey;
	
	@Column(name = "EXP_IN_YEARS")
	private String experienceYears;
	
	@Column(name = "EXP_IN_MONTHS")
	private String experienceMonths;
	
	@Column(name = "MARKET_PRICE")
	private String marketPrice;

	@Column(name = "MARKET_CURRENCY")
	private String marketRateCurrencey;
	
	@Column(name = "PROFILE_PERCENTAGE")
	private Integer profilePercentage;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private Long createdBy;
	
	@Column(name = "CREATED_BY_NAME")
	private String createdByName;
	
	@Transient
	@JsonProperty
	private String createdDatestr;

	@Column(name = "CART_STATUS")
	private String cartStatus;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public Long getSourceOrgLocationId() {
		return sourceOrgLocationId;
	}

	public void setSourceOrgLocationId(Long sourceOrgLocationId) {
		this.sourceOrgLocationId = sourceOrgLocationId;
	}

	public Long getTargetOrgLocationId() {
		return targetOrgLocationId;
	}

	public void setTargetOrgLocationId(Long targetOrgLocationId) {
		this.targetOrgLocationId = targetOrgLocationId;
	}

	public Long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}

	public String getCandidateUuid() {
		return candidateUuid;
	}

	public void setCandidateUuid(String candidateUuid) {
		this.candidateUuid = candidateUuid;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getSkillsName() {
		return skillsName;
	}

	public void setSkillsName(String skillsName) {
		this.skillsName = skillsName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getCurrentRate() {
		return currentRate;
	}

	public void setCurrentRate(String currentRate) {
		this.currentRate = currentRate;
	}

	public String getCurrentRateCurrency() {
		return currentRateCurrency;
	}

	public void setCurrentRateCurrency(String currentRateCurrency) {
		this.currentRateCurrency = currentRateCurrency;
	}
	
	public String getCurrentRateType(){
		return currentRateType;
	}
	
	public void setCurrentRateType(String currentRateType){
		this.currentRateType = currentRateType;
	}

	public String getExpectedRate() {
		return expectedRate;
	}

	public void setExpectedRate(String expectedRate) {
		this.expectedRate = expectedRate;
	}

	public String getExpectedRateCurrencey() {
		return expectedRateCurrencey;
	}

	public void setExpectedRateCurrencey(String expectedRateCurrencey) {
		this.expectedRateCurrencey = expectedRateCurrencey;
	}
	

	public String getExpectedRateType(){
		return expectedRateType;
	}
	
	public void setExpectedRateType(String expectedRateType){
		this.expectedRateType = expectedRateType;
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

	public Integer getProfilePercentage() {
		return profilePercentage;
	}

	public void setProfilePercentage(Integer profilePercentage) {
		this.profilePercentage = profilePercentage;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getCreatedDatestr() {
		return createdDatestr;
	}

	public void setCreatedDatestr(String createdDatestr) {
		this.createdDatestr = createdDatestr;
	}

	public String getCartStatus() {
		return cartStatus;
	}

	public void setCartStatus(String cartStatus) {
		this.cartStatus = cartStatus;
	}

	
}
