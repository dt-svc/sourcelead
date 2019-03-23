package com.bis.sourcelead.dto;

public class RequirementWorkFlowDTO {

	private long id;
	
	private String orgLocationId;

	private String workFlowName;

	private String description;
	
	private String noOfStages;
	
	private String uuid;
	
	private String status;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
}
