package com.bis.sourcelead.workflow.dto;



public class RequirementWorkFlowStagesDTO {

	private long id;
	
	private String orgLocationId;

	private Long requirementWorkFlowId;
	
	private String stageName;

	private String description;
	
	private String uuid;
	
	private Long requirementQuestionnaireTemplateId;
	
	private Integer status;
	
	private String editable;
	
	private String workFlowName;

	private String requirementWorkFlowUuid;
	
	private String workflowDescription;
	
	private Integer seqOrder;
	
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

	public Long getRequirementWorkFlowId() {
		return requirementWorkFlowId;
	}

	public void setRequirementWorkFlowId(Long requirementWorkFlowId) {
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

	public Long getRequirementQuestionnaireTemplateId() {
		return requirementQuestionnaireTemplateId;
	}

	public void setRequirementQuestionnaireTemplateId(Long requirementQuestionnaireTemplateId) {
		this.requirementQuestionnaireTemplateId = requirementQuestionnaireTemplateId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getEditable() {
		return editable;
	}

	public void setEditable(String editable) {
		this.editable = editable;
	}

	public String getWorkFlowName() {
		return workFlowName;
	}

	public void setWorkFlowName(String workFlowName) {
		this.workFlowName = workFlowName;
	}

	public String getRequirementWorkFlowUuid() {
		return requirementWorkFlowUuid;
	}

	public void setRequirementWorkFlowUuid(String requirementWorkFlowUuid) {
		this.requirementWorkFlowUuid = requirementWorkFlowUuid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSeqOrder() {
		return seqOrder;
	}

	public void setSeqOrder(Integer seqOrder) {
		this.seqOrder = seqOrder;
	}

	public String getWorkflowDescription() {
		return workflowDescription;
	}

	public void setWorkflowDescription(String workflowDescription) {
		this.workflowDescription = workflowDescription;
	}
	
	

}
