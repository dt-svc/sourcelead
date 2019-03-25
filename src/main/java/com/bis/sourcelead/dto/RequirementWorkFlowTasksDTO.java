package com.bis.sourcelead.dto;

public class RequirementWorkFlowTasksDTO {

	private long id;
	
	private String requirementWorkFlowStageId;
	
	private String orgLocationId;

	private String assignToUserId;

	private String priority;
	
	private String 	description;
	
	private String questionnaireName;
	
	private String 	questionnaireDese;
	
	private String uuid;
	
	private Integer status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRequirementWorkFlowStageId() {
		return requirementWorkFlowStageId;
	}

	public void setRequirementWorkFlowStageId(String requirementWorkFlowStageId) {
		this.requirementWorkFlowStageId = requirementWorkFlowStageId;
	}

	public String getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(String orgLocationId) {
		this.orgLocationId = orgLocationId;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getQuestionnaireName() {
		return questionnaireName;
	}

	public void setQuestionnaireName(String questionnaireName) {
		this.questionnaireName = questionnaireName;
	}

	public String getQuestionnaireDese() {
		return questionnaireDese;
	}

	public void setQuestionnaireDese(String questionnaireDese) {
		this.questionnaireDese = questionnaireDese;
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


}
