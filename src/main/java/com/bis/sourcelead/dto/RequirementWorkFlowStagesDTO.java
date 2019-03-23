package com.bis.sourcelead.dto;

public class RequirementWorkFlowStagesDTO {

	private long id;
	
	private String orgLocationId;

	private String requirementWorkFlowId;
	
	private String stageName;

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

	public String getRequirementWorkFlowId() {
		return requirementWorkFlowId;
	}

	public void setRequirementWorkFlowId(String requirementWorkFlowId) {
		this.requirementWorkFlowId = requirementWorkFlowId;
	}

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
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
