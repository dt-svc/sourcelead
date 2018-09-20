package com.bis.sourcelead.entity;

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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "PROJECT_PLANNING_NEW_RESOURCE_REQUEST")
public class ProjectPlanningNewResourceRequest implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
 	
	@Column(name = "ROLE_NAME")
	private String roleName;
	
	@Column(name = "OTHER_ROLE")
	private String otherRole;
    
	
	@ManyToOne(fetch = FetchType.EAGER)
   	@JoinColumn(name = "ROLE_ID")
  	private Roles roleId;
	 
	
    @ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "PROJECT_PLANNING_REQUREST_ID")
	@JsonBackReference
  	private ProjectPlanningRequest projectPlanningRequestId;
    
    @Column(name = "NO_OF_RESOURCES")
	private Integer noOfResource;
    
    @Column(name = "MIN_EXP")
	private Integer minExperience;
    
    @Column(name = "MAX_EXP")
  	private Integer maxExperience;
    
    @Column(name = "SKILLS", columnDefinition = "TEXT default NULL")
    private String skills;
    
    @Column(name = "BILL_RATE")
   	private String billRate;
    
    @Column(name = "PAY_RATE")
   	private String payRate;
    
    @Column(name = "RATE_CURRENCY")
   	private String rateCurrency;
    
    @Column(name = "RATE_TYPE")
   	private String rateType;
    
    @Column(name = "START_DATE")
   	private Date startDate;
    
    @Column(name = "END_DATE")
   	private Date endDate;
    
    @Column(name="ESTIMATED_HOURS")
    private Double estimatedHours;
    
    @Column(name="ESTIMATED_BUDGET")
    private Double estimatedBudget;
    
    @Column(name = "ESTIMATED_BUDGET_CURRENCY")
   	private String estimatedBudgetCurrency;
    
    @Column(name = "CONSULTING_TYPE")
   	private String consultingType;
    
    @Column(name = "STATUS")
   	private Integer status;
    
    @Column(name="REJECTED_REASON", columnDefinition = "LONGTEXT default NULL")
   	private String rejectedReason;
    
    @Column(name = "NEW_RESOURCE_STATUS")
   	private String resourceStatus;
    
    @Column(name = "REQUEST_UUID")
   	private String requestUUID;
    
	@Column(name = "CREATED_DATE")
    private Date createdDate;
    
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "CREATED_BY")
  	private User createdBy;
	
    
	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "UPDATED_BY")
  	private User updatedBy;

    
	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "ORG_LOCATION_ID")
  	private OrgLocations orgLocationId;
	
	@Transient
	@JsonProperty
	private String startDateStr;
	
	@Transient
	@JsonProperty
	private String endDateStr;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Roles getRoleId() {
		return roleId;
	}

	public void setRoleId(Roles roleId) {
		this.roleId = roleId;
	}

	public String getOtherRole() {
		return otherRole;
	}

	public void setOtherRole(String otherRole) {
		this.otherRole = otherRole;
	}

	public ProjectPlanningRequest getProjectPlanningRequestId() {
		return projectPlanningRequestId;
	}

	public void setProjectPlanningRequestId(ProjectPlanningRequest projectPlanningRequestId) {
		this.projectPlanningRequestId = projectPlanningRequestId;
	}

	public Integer getNoOfResource() {
		return noOfResource;
	}

	public void setNoOfResource(Integer noOfResource) {
		this.noOfResource = noOfResource;
	}

	public Integer getMinExperience() {
		return minExperience;
	}

	public void setMinExperience(Integer minExperience) {
		this.minExperience = minExperience;
	}

	public Integer getMaxExperience() {
		return maxExperience;
	}

	public void setMaxExperience(Integer maxExperience) {
		this.maxExperience = maxExperience;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getBillRate() {
		return billRate;
	}

	public void setBillRate(String billRate) {
		this.billRate = billRate;
	}

	public String getPayRate() {
		return payRate;
	}

	public void setPayRate(String payRate) {
		this.payRate = payRate;
	}
	
	public String getRateCurrency() {
		return rateCurrency;
	}

	public void setRateCurrency(String rateCurrency) {
		this.rateCurrency = rateCurrency;
	}

	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Double getEstimatedHours() {
		return estimatedHours;
	}

	public void setEstimatedHours(Double estimatedHours) {
		this.estimatedHours = estimatedHours;
	}

	public Double getEstimatedBudget() {
		return estimatedBudget;
	}

	public void setEstimatedBudget(Double estimatedBudget) {
		this.estimatedBudget = estimatedBudget;
	}

	public String getEstimatedBudgetCurrency() {
		return estimatedBudgetCurrency;
	}

	public void setEstimatedBudgetCurrency(String estimatedBudgetCurrency) {
		this.estimatedBudgetCurrency = estimatedBudgetCurrency;
	}

	public String getConsultingType() {
		return consultingType;
	}

	public void setConsultingType(String consultingType) {
		this.consultingType = consultingType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getRejectedReason() {
		return rejectedReason;
	}

	public void setRejectedReason(String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}

	public String getResourceStatus() {
		return resourceStatus;
	}

	public void setResourceStatus(String resourceStatus) {
		this.resourceStatus = resourceStatus;
	}

	public String getRequestUUID() {
		return requestUUID;
	}

	public void setRequestUUID(String requestUUID) {
		this.requestUUID = requestUUID;
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
	public String getStartDateStr() {
		if(startDate != null){
			SimpleDateFormat dateFormat = orgLocationId.getLocFullFormDateFormatter();
				startDateStr = dateFormat.format(startDate);	
		}
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public String getEndDateStr() {
		if(endDate != null){
			SimpleDateFormat dateFormat = orgLocationId.getLocFullFormDateFormatter();
			endDateStr = dateFormat.format(endDate);	
		}
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}
}
