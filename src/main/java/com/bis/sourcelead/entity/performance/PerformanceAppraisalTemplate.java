package com.bis.sourcelead.entity.performance;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "PERFORMANCE_APPRAISAL_TEMPLATE")
public class PerformanceAppraisalTemplate implements Serializable{

    private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "APPRAISAL_START_DATE")
	private Date appraisalStartDate;
	
	@Column(name = "APPRAISAL_END_DATE")
	private Date appraisalEndDate;
	
	@Column(name = "DEPARTMENT", columnDefinition = "TEXT default NULL")
	private String department ;
	
	@Column(name = "ROLES", columnDefinition = "TEXT default NULL")
	private String roles ;
	
	@Column(name = "EMPLOYEE_TYPE", columnDefinition = "TEXT default NULL")
	private String employeeType ;
	
	@Column(name = "SELF_APPRAISAL_START_DATE")
	private Date selfAppraisalStartDate;
	
	@Column(name = "SELF_APPRAISAL_END_DATE")
	private Date selfAppraisalEndDate;
	
	@Column(name = "APPROVING_FROM_START_DATE")
	private Date approvingFromStartDate;
	
	@Column(name = "APPROVING_FROM_END_DATE")
	private Date approvingFromEndDate;
	
	@Column(name = "APPRAISAL_APPLICABLE_FOR")
	private String appraisalApplicableFor;
	
	@Column(name = "EXPERIENCE_YEARS ")
	private Integer experienceYears;
	
	@Column(name = "EXPERIENCE_MONTHS ")
	private Integer experienceMonths;
	
	@Column(name = "DESCRIPTION", columnDefinition = "LONGTEXT default NULL")
	private String description ;
	
	@Column(name = "DESCRIPTION_DISPLAY")
	private String descriptionDisplay;
	
	@Column(name = "CATEGORIES", columnDefinition = "TEXT default NULL")
	private String categories;
	
	@Column(name = "CATEGORIES_JSON_STRING", columnDefinition = "LONGTEXT default NULL")
	private String categoriesJsonString ;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ORG_LOCATION_ID", nullable=false )        
    private OrgLocations orgLocationId;
	
	@Column(name = "UUID")
	private String uuid;
	
	@Column(name = "STATUS ")
	private Integer status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CREATED_BY", nullable=false )        
    private User createdBy;
	
	@Column(name = " UPDATED_DATE ")
	private Date updatedDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="UPDATED_BY", nullable=false )        
    private User updatedBy;
	
	@Transient
	@JsonProperty
    private String appraisalStartDateStr;
	
	@Transient
	@JsonProperty
    private String appraisalEndDateStr;
	
	@Transient
	@JsonProperty
	private String selfAppraisalStartDateStr;
	
	@Transient
	@JsonProperty
	private String selfAppraisalEndDateStr;
	
	@Transient
	@JsonProperty
	private String approvingFromStartDateStr;
	
	@Transient
	@JsonProperty
	private String approvingFromEndDateStr;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getAppraisalStartDate() {
		return appraisalStartDate;
	}

	public void setAppraisalStartDate(Date appraisalStartDate) {
		this.appraisalStartDate = appraisalStartDate;
	}

	public Date getAppraisalEndDate() {
		return appraisalEndDate;
	}

	public void setAppraisalEndDate(Date appraisalEndDate) {
		this.appraisalEndDate = appraisalEndDate;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	public Date getSelfAppraisalStartDate() {
		return selfAppraisalStartDate;
	}

	public void setSelfAppraisalStartDate(Date selfAppraisalStartDate) {
		this.selfAppraisalStartDate = selfAppraisalStartDate;
	}

	public Date getSelfAppraisalEndDate() {
		return selfAppraisalEndDate;
	}

	public void setSelfAppraisalEndDate(Date selfAppraisalEndDate) {
		this.selfAppraisalEndDate = selfAppraisalEndDate;
	}

	public Date getApprovingFromStartDate() {
		return approvingFromStartDate;
	}

	public void setApprovingFromStartDate(Date approvingFromStartDate) {
		this.approvingFromStartDate = approvingFromStartDate;
	}

	public Date getApprovingFromEndDate() {
		return approvingFromEndDate;
	}

	public void setApprovingFromEndDate(Date approvingFromEndDate) {
		this.approvingFromEndDate = approvingFromEndDate;
	}

	public String getAppraisalApplicableFor() {
		return appraisalApplicableFor;
	}

	public void setAppraisalApplicableFor(String appraisalApplicableFor) {
		this.appraisalApplicableFor = appraisalApplicableFor;
	}

	public Integer getExperienceYears() {
		return experienceYears;
	}

	public void setExperienceYears(Integer experienceYears) {
		this.experienceYears = experienceYears;
	}

	public Integer getExperienceMonths() {
		return experienceMonths;
	}

	public void setExperienceMonths(Integer experienceMonths) {
		this.experienceMonths = experienceMonths;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescriptionDisplay() {
		return descriptionDisplay;
	}

	public void setDescriptionDisplay(String descriptionDisplay) {
		this.descriptionDisplay = descriptionDisplay;
	}

	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}

	public String getCategoriesJsonString() {
		return categoriesJsonString;
	}

	public void setCategoriesJsonString(String categoriesJsonString) {
		this.categoriesJsonString = categoriesJsonString;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getAppraisalStartDateStr() {
		
		if(appraisalStartDate != null){
			SimpleDateFormat dateFormat = orgLocationId.getLocFullFormDateFormatter();
			appraisalStartDateStr = dateFormat.format(appraisalStartDate);	
		}
		
		return appraisalStartDateStr;
	}

	public void setAppraisalStartDateStr(String appraisalStartDateStr) {
		this.appraisalStartDateStr = appraisalStartDateStr;
	}

	public String getAppraisalEndDateStr() {
		if(appraisalEndDate != null){
			SimpleDateFormat dateFormat = orgLocationId.getLocFullFormDateFormatter();
			appraisalEndDateStr = dateFormat.format(appraisalEndDate);	
		}
		return appraisalEndDateStr;
	}

	public void setAppraisalEndDateStr(String appraisalEndDateStr) {
		this.appraisalEndDateStr = appraisalEndDateStr;
	}

	public String getSelfAppraisalStartDateStr() {
		
		if(selfAppraisalStartDate != null){
			SimpleDateFormat dateFormat = orgLocationId.getLocFullFormDateFormatter();
			selfAppraisalStartDateStr = dateFormat.format(selfAppraisalStartDate);	
		}
		
		return selfAppraisalStartDateStr;
	}

	public void setSelfAppraisalStartDateStr(String selfAppraisalStartDateStr) {
		this.selfAppraisalStartDateStr = selfAppraisalStartDateStr;
	}

	public String getSelfAppraisalEndDateStr() {
		
		if(selfAppraisalEndDate != null){
			SimpleDateFormat dateFormat = orgLocationId.getLocFullFormDateFormatter();
			selfAppraisalEndDateStr = dateFormat.format(selfAppraisalEndDate);	
		}
		return selfAppraisalEndDateStr;
	}

	public void setSelfAppraisalEndDateStr(String selfAppraisalEndDateStr) {
		this.selfAppraisalEndDateStr = selfAppraisalEndDateStr;
	}

	public String getApprovingFromStartDateStr() {
		if(approvingFromStartDate != null){
			SimpleDateFormat dateFormat = orgLocationId.getLocFullFormDateFormatter();
			approvingFromStartDateStr = dateFormat.format(approvingFromStartDate);	
		}
		return approvingFromStartDateStr;
	}

	public void setApprovingFromStartDateStr(String approvingFromStartDateStr) {
		this.approvingFromStartDateStr = approvingFromStartDateStr;
	}

	public String getApprovingFromEndDateStr() {
		if(approvingFromEndDate != null){
			SimpleDateFormat dateFormat = orgLocationId.getLocFullFormDateFormatter();
			approvingFromEndDateStr = dateFormat.format(approvingFromEndDate);	
		}
		return approvingFromEndDateStr;
	}

	public void setApprovingFromEndDateStr(String approvingFromEndDateStr) {
		this.approvingFromEndDateStr = approvingFromEndDateStr;
	}
	
	
}
