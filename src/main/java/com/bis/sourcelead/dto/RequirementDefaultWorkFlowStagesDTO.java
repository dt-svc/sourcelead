package com.bis.sourcelead.dto;

	public class RequirementDefaultWorkFlowStagesDTO {

	private Integer id;
	
	private String stageName;
	
	private String  description;
	
	private Integer seqOrder;
	
	private String editable;
	
	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getSeqOrder() {
		return seqOrder;
	}

	public void setSeqOrder(Integer seqOrder) {
		this.seqOrder = seqOrder;
	}

	public String getEditable() {
		return editable;
	}

	public void setEditable(String editable) {
		this.editable = editable;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	

}
