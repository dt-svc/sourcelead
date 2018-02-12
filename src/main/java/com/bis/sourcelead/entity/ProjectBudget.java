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
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "PROJECT_BUDGET")
public class ProjectBudget implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	@Column(name = "BUDGET_REQUESTED_ID")
   	private String budgetRequestedId;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
	@JoinColumn(name = "PROJECT_ID", nullable = false)
	private Projects projectId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
   	@JoinColumn(name = "BUDGET_APPROVER_USER_ID", nullable = false)
   	private User approverUserId;
    
	@Column(name = "ESTIMATED_HOURS")
   	private Double estimatedHours;
	
	@Column(name = "ESTIMATED_BUDGET_CURRENCY")
   	private String estimatedBudgetCurrency;
	
	@Column(name = "ESTIMATED_BUDGET_VALUE")
   	private Double estimatedBudgetValue;
	
	@Column(name = "BUDGET_STATUS")
   	private String budgetStatus;
	
	@Column(name = "STATUS")
   	private Integer status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
   	@JoinColumn(name = "CREATED_USER_ID")
   	private User createUserId;
  
	@Column(name = "CREATED_DATE")
    private Date createdDate;
	
	@Column(name = "BUDGET_DESCRIPTION")
	@Type(type="text")
	private String budgetDescription;
	 
	@Column(name = "BUDGET_REJECTED_REASON")
	@Type(type="text")
	private String rejectedReason;
	
	@Column(name = "UPDATED_DATE")
    private Date updatedDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
   	@JoinColumn(name = "UPDATED_USER_ID")
   	private User updateUserId;
	
    @JsonProperty
    @Transient
	private String createdStr;

    @JsonProperty
    @Transient
	private Long userId;
    
    @JsonProperty
    @Transient
	private Long approverUserIdlLong;
    
    @JsonProperty
    @Transient
	private String createdUserName;
    
    @JsonProperty
    @Transient
	private String updatedUserName;
    
    @JsonProperty
    @Transient
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
    private String projectIdUUID;
	
	public Long getId() {
		return id;
	}

	public String getBudgetRequestedId() {
		return budgetRequestedId;
	}

	public void setBudgetRequestedId(String budgetRequestedId) {
		this.budgetRequestedId = budgetRequestedId;
	}

	
	public Projects getProjectId() {
		return projectId;
	}

	public User getApproverUserId() {
		return approverUserId;
	}


	public String getEstimatedBudgetCurrency() {
		return estimatedBudgetCurrency;
	}


	public String getBudgetStatus() {
		return budgetStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public User getCreateUserId() {
		return createUserId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public String getBudgetDescription() {
		return budgetDescription;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public User getUpdateUserId() {
		return updateUserId;
	}

	public String getCreatedStr() {
		if(createdDate != null){
			SimpleDateFormat dateFormat = projectId.getOrgLocationsId().getLocFullFormDateFormatter();
			createdStr = dateFormat.format(createdDate);	
		}
		return createdStr;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setProjectId(Projects projectId) {
		this.projectId = projectId;
	}

	public void setApproverUserId(User approverUserId) {
		this.approverUserId = approverUserId;
	}


	public void setEstimatedBudgetCurrency(String estimatedBudgetCurrency) {
		this.estimatedBudgetCurrency = estimatedBudgetCurrency;
	}

	public Double getEstimatedBudgetValue() {
		return estimatedBudgetValue;
	}

	public void setEstimatedBudgetValue(Double estimatedBudgetValue) {
		this.estimatedBudgetValue = estimatedBudgetValue;
	}

	public Double getEstimatedHours() {
		return estimatedHours;
	}

	public void setEstimatedHours(Double estimatedHours) {
		this.estimatedHours = estimatedHours;
	}

	public void setBudgetStatus(String budgetStatus) {
		this.budgetStatus = budgetStatus;
	}

	public void setCreateUserId(User createUserId) {
		this.createUserId = createUserId;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setBudgetDescription(String budgetDescription) {
		this.budgetDescription = budgetDescription;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public void setUpdateUserId(User updateUserId) {
		this.updateUserId = updateUserId;
	}

	public void setCreatedStr(String createdStr) {
		this.createdStr = createdStr;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCreatedUserName() {
		if(createUserId != null) {
			createdUserName = createUserId.getFullName();
		}
		return createdUserName;
	}

	public void setCreatedUserName(String createdUserName) {
		this.createdUserName = createdUserName;
	}

	public String getRejectedReason() {
		return rejectedReason;
	}

	public void setRejectedReason(String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}

	public Long getApproverUserIdlLong() {
		if(approverUserId != null) {
			approverUserIdlLong = approverUserId.getId();
		}
		return approverUserIdlLong;
	}

	public void setApproverUserIdlLong(Long approverUserIdlLong) {
		this.approverUserIdlLong = approverUserIdlLong;
	}
	
	public String getProjectName() {
		if(projectId != null) {
			projectName = projectId.getProjectName();
		}
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Integer getApprovedCount() {
		return approvedCount;
	}

	public Integer getRejectedCount() {
		return rejectedCount;
	}

	public Integer getWaitingCount() {
		return waitingCount;
	}

	public Integer getOnholdCount() {
		return onholdCount;
	}

	public void setApprovedCount(Integer approvedCount) {
		this.approvedCount = approvedCount;
	}

	public void setRejectedCount(Integer rejectedCount) {
		this.rejectedCount = rejectedCount;
	}

	public void setWaitingCount(Integer waitingCount) {
		this.waitingCount = waitingCount;
	}

	public void setOnholdCount(Integer onholdCount) {
		this.onholdCount = onholdCount;
	}

	
	public String getProjectIdUUID() {
		if(projectId != null) {
			projectIdUUID = projectId.getProjectUUID();
		}
		return projectIdUUID;
	}

	public void setProjectIdUUID(String projectIdUUID) {
		this.projectIdUUID = projectIdUUID;
	}

	public String getUpdatedUserName() {
			if(updateUserId != null) {
				updatedUserName = updateUserId.getFullName();
			}
		return updatedUserName;
	}

	public void setUpdatedUserName(String updatedUserName) {
		this.updatedUserName = updatedUserName;
	}
	
	
}