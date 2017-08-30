package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    
    @NotFound(action=NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "PROJECT_ID")
  	private Projects projectId;
    
    @NotFound(action=NotFoundAction.IGNORE)
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
   	private String requestStatus;
    
    @Column(name = "REQUESTED_USER")
   	private String requestedUser;
    
    @NotFound(action=NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "ORG_LOCATION_ID")
  	private OrgLocations orgLocationId;
    
    @Column(name = "STATUS")
   	private Integer status;
    
    @Column(name="REJECTED_REASON", columnDefinition = "LONGTEXT default NULL")
	private String rejectedReason;
    
    @Column(name = "REQUEST_UUID")
   	private String requestUUID;
    
    @Column(name = "REQUEST_TYPE")
   	private String requestType;
    
	@Column(name = "CREATED_DATE")
    private Date createdDate;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
    
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "CREATED_BY")
  	private User createdBy;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "UPDATED_BY")
  	private User updatedBy;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "projectPlanningRequestId")
	private Set<ProjectPlanningResourceRequest> projectPlanningResourceRequestList;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "projectPlanningRequestId")
	private Set<ProjectPlanningNewResourceRequest> projectPlanningNewResourceRequestList;
	
	@Transient
	@JsonProperty
    private String requestedDateStr;
	
	@Transient
	@JsonProperty
    private Long approverId;
	
	@Transient
	@JsonProperty
	List<ProjectPlanningResourceRequest> resourceRequestList;
	
	@Transient
	@JsonProperty
    private String projectName;
	
	@Transient
	@JsonProperty
    private Integer approvedCount;
	
	@Transient
	@JsonProperty
    private Integer rejectedCount;
	
	@Transient
	@JsonProperty
    private Integer waitingCount;
	
	@Transient
	@JsonProperty
    private Integer onholdCount;
    
	@Transient
	@JsonProperty
	List<ProjectPlanningNewResourceRequest> newResourceRequestList;
    
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


	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	public String getRequestedUser() {
		return requestedUser;
	}

	public void setRequestedUser(String requestedUser) {
		this.requestedUser = requestedUser;
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

	public String getRequestUUID() {
		return requestUUID;
	}

	public void setRequestUUID(String requestUUID) {
		this.requestUUID = requestUUID;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
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

	public Set<ProjectPlanningResourceRequest> getProjectPlanningResourceRequestList() {
		return projectPlanningResourceRequestList;
	}

	public void setProjectPlanningResourceRequestList(
			Set<ProjectPlanningResourceRequest> projectPlanningResourceRequestList) {
		this.projectPlanningResourceRequestList = projectPlanningResourceRequestList;
	}

	public String getRequestedDateStr() {
		if(createdDate != null){
			SimpleDateFormat dateFormat = orgLocationId.getLocFullFormDateFormatter();
			requestedDateStr = dateFormat.format(createdDate);	
		}else{
			requestedDateStr = "--";
		}
		return requestedDateStr;
	}

	public void setRequestedDateStr(String requestedDateStr) {
		this.requestedDateStr = requestedDateStr;
	}


	public Set<ProjectPlanningNewResourceRequest> getProjectPlanningNewResourceRequestList() {
		return projectPlanningNewResourceRequestList;
	}

	public void setProjectPlanningNewResourceRequestList(
			Set<ProjectPlanningNewResourceRequest> projectPlanningNewResourceRequestList) {
		this.projectPlanningNewResourceRequestList = projectPlanningNewResourceRequestList;
	}

	public Long getApproverId() {
		if(approverUser != null && approverUser.getId() != null){
			approverId = approverUser.getId();
		}
		return approverId;
	}

	public void setApproverId(Long approverId) {
		this.approverId = approverId;
	}

	public List<ProjectPlanningResourceRequest> getResourceRequestList() {
		resourceRequestList = new ArrayList<>();
		if(projectPlanningResourceRequestList != null && !projectPlanningResourceRequestList.isEmpty()){
			resourceRequestList.addAll(projectPlanningResourceRequestList);
		}
		return resourceRequestList;
	}

	public void setResourceRequestList(List<ProjectPlanningResourceRequest> resourceRequestList) {
		this.resourceRequestList = resourceRequestList;
	}
	
	public List<ProjectPlanningNewResourceRequest> getNewResourceRequestList() {
		newResourceRequestList = new ArrayList<>();
		if(projectPlanningNewResourceRequestList != null && !projectPlanningNewResourceRequestList.isEmpty()){
			newResourceRequestList.addAll(projectPlanningNewResourceRequestList);
		}
		return newResourceRequestList;
	}

	public void setNewResourceRequestList(List<ProjectPlanningNewResourceRequest> newResourceRequestList) {
		this.newResourceRequestList = newResourceRequestList;
	}

	public String getProjectName() {
		if(projectId != null && projectId.getId() != null){
			projectName = projectId.getProjectName();
		}
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getRejectedReason() {
        if(rejectedReason == null || rejectedReason.isEmpty()){
        	resourceRequestList = new ArrayList<>();
        	if(projectPlanningResourceRequestList != null && !projectPlanningResourceRequestList.isEmpty()){
        		resourceRequestList.addAll(projectPlanningResourceRequestList);
        		StringBuilder reason = new StringBuilder();
        		for(ProjectPlanningResourceRequest resourceRequest : resourceRequestList ){
        			if(resourceRequest.getRejectedReason() != null && !resourceRequest.getRejectedReason().isEmpty()){
        				reason = reason.append("<br/>").append("Reason for ").append(resourceRequest.getCandidateName()).append(" : ").append(resourceRequest.getRejectedReason()).append("<br/>");
        			}
        			rejectedReason = reason.toString();
        		}
        	}
		}
		return rejectedReason;
	}

	public void setRejectedReason(String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}

	
	public Integer getApprovedCount() {
		return approvedCount;
	}

	public void setApprovedCount(Integer approvedCount) {
		this.approvedCount = approvedCount;
	}

	public Integer getRejectedCount() {
		return rejectedCount;
	}

	public void setRejectedCount(Integer rejectedCount) {
		this.rejectedCount = rejectedCount;
	}

	public Integer getWaitingCount() {
		return waitingCount;
	}

	public void setWaitingCount(Integer waitingCount) {
		this.waitingCount = waitingCount;
	}

	public Integer getOnholdCount() {
		return onholdCount;
	}

	public void setOnholdCount(Integer onholdCount) {
		this.onholdCount = onholdCount;
	}
	
	
	
	
	
}
