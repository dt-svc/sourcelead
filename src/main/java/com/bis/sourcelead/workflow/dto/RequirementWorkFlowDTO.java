package com.bis.sourcelead.workflow.dto;

import com.bis.sourcelead.annotations.Required;
import com.bis.sourcelead.dto.SearchDTO;
import com.bis.sourcelead.entity.User;

public class RequirementWorkFlowDTO extends SearchDTO{

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	private long id;
	
	private String orgLocationId;

	@Required(desc = "workFlow name")
	private String workFlowName;

	private String description;
	
	private String noOfStages;
	
	private String uuid;
	
	private Integer status;
	
	private String reqWorkFlowId;

	private String configureType;
	
	private String stageUuid;
	
	private String currentStageUuid;
	
	private String updatedBy;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(String orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getWorkFlowName() {
		return workFlowName;
	}

	public void setWorkFlowName(String workFlowName) {
		this.workFlowName = workFlowName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNoOfStages() {
		return noOfStages;
	}

	public void setNoOfStages(String noOfStages) {
		this.noOfStages = noOfStages;
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

	public String getReqWorkFlowId() {
		return reqWorkFlowId;
	}

	public void setReqWorkFlowId(String reqWorkFlowId) {
		this.reqWorkFlowId = reqWorkFlowId;
	}

	public String getConfigureType() {
		return configureType;
	}

	public void setConfigureType(String configureType) {
		this.configureType = configureType;
	}

	public String getStageUuid() {
		return stageUuid;
	}

	public void setStageUuid(String stageUuid) {
		this.stageUuid = stageUuid;
	}

	public String getCurrentStageUuid() {
		return currentStageUuid;
	}

	public void setCurrentStageUuid(String currentStageUuid) {
		this.currentStageUuid = currentStageUuid;
	}
	
	
	
}
