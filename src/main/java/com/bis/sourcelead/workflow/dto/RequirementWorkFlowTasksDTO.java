package com.bis.sourcelead.workflow.dto;

import com.bis.sourcelead.dto.SearchDTO;

public class RequirementWorkFlowTasksDTO extends SearchDTO {

	private long id;
	
	private String requirementWorkFlowStagesId;
	
	private String orgLocationId;

	private String workFlowTaskName;
	
	private String assignToUserId;

	private String priority;
	
	private String workFlowTaskDescription;

	
	private String uuid;
	
	private Integer status;
	
	private String requirementWorkFlowUuid;
	
	private String user;
	
	private Long StageId;
	
	private String currentStageUuid;
	
	private String stageName;

	private String description;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRequirementWorkFlowStageId() {
		return requirementWorkFlowStagesId;
	}

	public void setRequirementWorkFlowStageId(String requirementWorkFlowStagesId) {
		this.requirementWorkFlowStagesId = requirementWorkFlowStagesId;
	}

	public String getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(String orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	
	public String getWorkFlowTaskName() {
		return workFlowTaskName;
	}

	public void setWorkFlowTaskName(String workFlowTaskName) {
		this.workFlowTaskName = workFlowTaskName;
	}

	public String getAssignToUserId() {
		return assignToUserId;
	}

	public void setAssignToUserId(String assignToUserId) {
		this.assignToUserId = assignToUserId;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getWorkFlowTaskDescription() {
		return workFlowTaskDescription;
	}

	public void setWorkFlowTaskDescription(String workFlowTaskDescription) {
		this.workFlowTaskDescription = workFlowTaskDescription;
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

	public String getRequirementWorkFlowStagesId() {
		return requirementWorkFlowStagesId;
	}

	public void setRequirementWorkFlowStagesId(String requirementWorkFlowStagesId) {
		this.requirementWorkFlowStagesId = requirementWorkFlowStagesId;
	}

	public String getRequirementWorkFlowUuid() {
		return requirementWorkFlowUuid;
	}

	public void setRequirementWorkFlowUuid(String requirementWorkFlowUuid) {
		this.requirementWorkFlowUuid = requirementWorkFlowUuid;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getCurrentStageUuid() {
		return currentStageUuid;
	}

	public void setCurrentStageUuid(String currentStageUuid) {
		this.currentStageUuid = currentStageUuid;
	}

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getStageId() {
		return StageId;
	}

	public void setStageId(Long stageId) {
		StageId = stageId;
	}
	
	

}
