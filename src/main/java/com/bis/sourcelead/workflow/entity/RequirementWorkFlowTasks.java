package com.bis.sourcelead.workflow.entity;

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

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

@Entity
@Table(name = "REQUIREMENT_WORKFLOW_TASKS")
public class RequirementWorkFlowTasks implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REQUIREMENT_WORKFLOW_STAGES_ID", nullable = false)
	private RequirementWorkFlowStages requirementWorkFlowStagesId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_LOCATION_ID", nullable = false)
	private OrgLocations orgLocationId;
	
	@Column(name = "TASK_NAME")
	private String workFlowTaskName;
	
	@ManyToOne
	@JoinColumn(name = "ASSIGN_TO_USER_ID")
	private User assignTo;
	
	@Column(name = "PRIORITY")
	private String priority;
	
	@Column(name = "DESCRIPTION", columnDefinition = "LONGTEXT default NULL")
	private String description;
	
	@Column(name = "UUID")
	private String uuid;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@ManyToOne
	@JoinColumn(name = "CREATED_BY")
	private User createdBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@ManyToOne
	@JoinColumn(name = "UPDATED_BY")
	private User updatedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RequirementWorkFlowStages getRequirementWorkFlowStagesId() {
		return requirementWorkFlowStagesId;
	}

	public void setRequirementWorkFlowStagesId(RequirementWorkFlowStages requirementWorkFlowStagesId) {
		this.requirementWorkFlowStagesId = requirementWorkFlowStagesId;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getWorkFlowTaskName() {
		return workFlowTaskName;
	}

	public void setWorkFlowTaskName(String workFlowTaskName) {
		this.workFlowTaskName = workFlowTaskName;
	}

	public User getAssignTo() {
		return assignTo;
	}

	public void setAssignTo(User assignTo) {
		this.assignTo = assignTo;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	
}
