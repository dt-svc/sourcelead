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

import com.fasterxml.jackson.annotation.JsonProperty;

	@Entity
	@Table(name = "RECOMMENDED_CUSTOMIZE_SCORE")
	public class RecommendedCustomizeScore implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="REQIREMENT_ID", nullable=false )        
    private Requirement reqirementId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ORGLOCATION_ID", nullable=false )        
    private OrgLocations orgLocationId;
	
	@Column(name = "CUSTOMIZE_SKILLS", columnDefinition = "TEXT default NULL")
	private String customizeSkills;
	
	@Column(name = "CUSTOMIZE_SKILL_SCORE")
	private String customizeSkillScore;
	
	@Column(name = "CUSTOMIZE_MIN_EXPERIENCE")
	private String customizeMinExperience;
	
	@Column(name = "CUSTOMIZE_MAX_EXPERIENCE")
	private String customizeMaxExperience;
	
	@Column(name = "CUSTOMIZE_EXPERIENCE_SCORE")
	private String customizeExperienceScore;
	
	@Column(name = "CUSTOMIZE_RATE")
	private String customizeRate;
	
	@Column(name = "CUSTOMIZE_RATE_TYPE")
	private String customizeRatetype;
	
	@Column(name = "CUSTOMIZE_RATE_SCORE")
	private String customizeRateScore;
	
	@Column(name = "CUSTOMIZE_NOTICEPERIOD", columnDefinition = "TEXT default NULL")
	private String customizeNoticeperiod;
	
	@Column(name = "CUSTOMIZE_NOTICEPERIOD_SCORE")
	private String customizeNoticeperiodScore;
	
	@Column(name = "CUSTOMIZE_KEYWORDS", columnDefinition = "TEXT default NULL")
	private String customizeKeywords;
	
	@Column(name = "CUSTOMIZE_KEYWORDS_SCORE")
	private String customizeKeywordsScore;
	
	@Column(name = "CUSTOMIZE_COUNTRY", columnDefinition = "LONGTEXT default NULL")
	private String customizeCountry;
	
	@Column(name = "CUSTOMIZE_STATE", columnDefinition = "LONGTEXT default NULL")
	private String customizeState;
	
	@Column(name = "CUSTOMIZE_CITY", columnDefinition = "LONGTEXT default NULL")
	private String customizeCity;
	
	@Column(name = "CUSTOMIZE_LOCATION_SCORE")
	private String customizeLocationScore;
	
	@Column(name = "CUSTOMIZE_EDUCATION", columnDefinition = "TEXT default NULL")
	private String customizeEducation;
	
	@Column(name = "CUSTOMIZE_EDUCATION_SCORE")
	private String customizeEducationScore;
	
	@Column(name = "CUSTOMIZE_VISATYPE", columnDefinition = "TEXT default NULL")
	private String customizeVisatype;
	
	@Column(name = "CUSTOMIZE_VISATYPE_SCORE")
	private String customizeVisatypecore;
	
	@Column(name="STATUS")
	private Integer status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Transient
	@JsonProperty
	private String dbSkills;
	
	@Transient
	@JsonProperty
	private String dbVisaType;
	
	@Transient
	@JsonProperty
	private String dbEducation;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Requirement getReqirementId() {
		return reqirementId;
	}

	public void setReqirementId(Requirement reqirementId) {
		this.reqirementId = reqirementId;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getCustomizeSkills() {
		return customizeSkills;
	}

	public void setCustomizeSkills(String customizeSkills) {
		this.customizeSkills = customizeSkills;
	}

	public String getCustomizeSkillScore() {
		return customizeSkillScore;
	}

	public void setCustomizeSkillScore(String customizeSkillScore) {
		this.customizeSkillScore = customizeSkillScore;
	}

	public String getCustomizeMinExperience() {
		return customizeMinExperience;
	}

	public void setCustomizeMinExperience(String customizeMinExperience) {
		this.customizeMinExperience = customizeMinExperience;
	}

	public String getCustomizeMaxExperience() {
		return customizeMaxExperience;
	}

	public void setCustomizeMaxExperience(String customizeMaxExperience) {
		this.customizeMaxExperience = customizeMaxExperience;
	}

	public String getCustomizeExperienceScore() {
		return customizeExperienceScore;
	}

	public void setCustomizeExperienceScore(String customizeExperienceScore) {
		this.customizeExperienceScore = customizeExperienceScore;
	}

	public String getCustomizeRate() {
		return customizeRate;
	}

	public void setCustomizeRate(String customizeRate) {
		this.customizeRate = customizeRate;
	}

	public String getCustomizeRatetype() {
		return customizeRatetype;
	}

	public void setCustomizeRatetype(String customizeRatetype) {
		this.customizeRatetype = customizeRatetype;
	}

	public String getCustomizeRateScore() {
		return customizeRateScore;
	}

	public void setCustomizeRateScore(String customizeRateScore) {
		this.customizeRateScore = customizeRateScore;
	}

	public String getCustomizeNoticeperiod() {
		return customizeNoticeperiod;
	}

	public void setCustomizeNoticeperiod(String customizeNoticeperiod) {
		this.customizeNoticeperiod = customizeNoticeperiod;
	}

	public String getCustomizeNoticeperiodScore() {
		return customizeNoticeperiodScore;
	}

	public void setCustomizeNoticeperiodScore(String customizeNoticeperiodScore) {
		this.customizeNoticeperiodScore = customizeNoticeperiodScore;
	}

	public String getCustomizeKeywords() {
		return customizeKeywords;
	}

	public void setCustomizeKeywords(String customizeKeywords) {
		this.customizeKeywords = customizeKeywords;
	}

	public String getCustomizeKeywordsScore() {
		return customizeKeywordsScore;
	}

	public void setCustomizeKeywordsScore(String customizeKeywordsScore) {
		this.customizeKeywordsScore = customizeKeywordsScore;
	}

	public String getCustomizeCountry() {
		return customizeCountry;
	}

	public void setCustomizeCountry(String customizeCountry) {
		this.customizeCountry = customizeCountry;
	}

	public String getCustomizeState() {
		return customizeState;
	}

	public void setCustomizeState(String customizeState) {
		this.customizeState = customizeState;
	}

	public String getCustomizeCity() {
		return customizeCity;
	}

	public void setCustomizeCity(String customizeCity) {
		this.customizeCity = customizeCity;
	}

	public String getCustomizeLocationScore() {
		return customizeLocationScore;
	}

	public void setCustomizeLocationScore(String customizeLocationScore) {
		this.customizeLocationScore = customizeLocationScore;
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

	public String getDbSkills() {
		return dbSkills;
	}

	public void setDbSkills(String dbSkills) {
		this.dbSkills = dbSkills;
	}

	public String getCustomizeEducation() {
		return customizeEducation;
	}

	public void setCustomizeEducation(String customizeEducation) {
		this.customizeEducation = customizeEducation;
	}

	public String getCustomizeEducationScore() {
		return customizeEducationScore;
	}

	public void setCustomizeEducationScore(String customizeEducationScore) {
		this.customizeEducationScore = customizeEducationScore;
	}

	public String getCustomizeVisatype() {
		return customizeVisatype;
	}

	public void setCustomizeVisatype(String customizeVisatype) {
		this.customizeVisatype = customizeVisatype;
	}

	public String getCustomizeVisatypecore() {
		return customizeVisatypecore;
	}

	public void setCustomizeVisatypecore(String customizeVisatypecore) {
		this.customizeVisatypecore = customizeVisatypecore;
	}

	public String getDbVisaType() {
		return dbVisaType;
	}

	public void setDbVisaType(String dbVisaType) {
		this.dbVisaType = dbVisaType;
	}

	public String getDbEducation() {
		return dbEducation;
	}

	public void setDbEducation(String dbEducation) {
		this.dbEducation = dbEducation;
	}
}
