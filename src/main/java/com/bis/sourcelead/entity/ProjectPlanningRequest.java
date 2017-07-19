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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "PROJECT_PLANNING_REQUREST")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL,region="projectPlanningRequest")
public class ProjectPlanningRequest implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
 	
	@Column(name = "REQUEST_ID")
	private String requestId;
	
    @Column(name = "DESCRIPTION", columnDefinition = "LONGTEXT default NULL")
    private String description;
    
    @ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "PROJECT_ID")
  	private Projects projectId;
    
    @ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "APPROVER_USER_ID")
  	private User approverUser;
    
    @Column(name = "APPROVER_NAME")
	private String approverName;
    
    @Column(name = "REQUESTED_RESOURCES")
	private Integer requestedResources;
    
    @Column(name = "ALLOCATED_RESOURCES")
  	private Integer allocatedResources;
    
    @Column(name="ESTIMATED_HOURS")
    private Double estimatedHours;
    
    @Column(name="ESTIMATED_BUDGET")
    private Double estimatedBudget;
    
    @Column(name = "ESTIMATED_BUDGET_CURRENCY")
   	private String estimatedBudgetCurrency;
    
    @Column(name = "REQUEST_STATUS")
   	private String REQUEST_STATUS;
    
    @Column(name = "REQUESTED_USER")
   	private String REQUESTED_USER;
    
    @ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "ORG_LOCATION_ID")
  	private OrgLocations orgLocationId;
    
    @Column(name = "STATUS")
   	private Integer status;
    
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
    
    
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Projects getProjectId() {
		return projectId;
	}

	public void setProjectId(Projects projectId) {
		this.projectId = projectId;
	}

	public User getApproverUser() {
		return approverUser;
	}

	public void setApproverUser(User approverUser) {
		this.approverUser = approverUser;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public Integer getRequestedResources() {
		return requestedResources;
	}

	public void setRequestedResources(Integer requestedResources) {
		this.requestedResources = requestedResources;
	}

	public Integer getAllocatedResources() {
		return allocatedResources;
	}

	public void setAllocatedResources(Integer allocatedResources) {
		this.allocatedResources = allocatedResources;
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

	public String getREQUEST_STATUS() {
		return REQUEST_STATUS;
	}

	public void setREQUEST_STATUS(String rEQUEST_STATUS) {
		REQUEST_STATUS = rEQUEST_STATUS;
	}

	public String getREQUESTED_USER() {
		return REQUESTED_USER;
	}

	public void setREQUESTED_USER(String rEQUESTED_USER) {
		REQUESTED_USER = rEQUESTED_USER;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
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

	
}
