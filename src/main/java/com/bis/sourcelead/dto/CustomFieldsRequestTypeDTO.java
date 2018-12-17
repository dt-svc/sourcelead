package com.bis.sourcelead.dto;

public class CustomFieldsRequestTypeDTO {
	private Long sectionId;
	private Long requirementId;
	private String requestFrom;
	private Long candidateId;
	private Long userId;
	private Long orgRelationID;
	private Long clientLocationId;
	private Long projectId;
	private Long contractId;
	private Long assetId;
	private Long projectTask;
	private String requestType;
	private String clientVendorContactType;
	public Long getSectionId() {
		return sectionId;
	}
	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}
	public Long getRequirementId() {
		return requirementId;
	}
	public void setRequirementId(Long requirementId) {
		this.requirementId = requirementId;
	}
	public String getRequestFrom() {
		return requestFrom;
	}
	public void setRequestFrom(String requestFrom) {
		this.requestFrom = requestFrom;
	}
	public Long getCandidateId() {
		return candidateId;
	}
	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getOrgRelationID() {
		return orgRelationID;
	}
	public void setOrgRelationID(Long orgRelationID) {
		this.orgRelationID = orgRelationID;
	}
	public Long getClientLocationId() {
		return clientLocationId;
	}
	public void setClientLocationId(Long clientLocationId) {
		this.clientLocationId = clientLocationId;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public Long getContractId() {
		return contractId;
	}
	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}
	public Long getAssetId() {
		return assetId;
	}
	public void setAssetId(Long assetId) {
		this.assetId = assetId;
	}
	public Long getProjectTask() {
		return projectTask;
	}
	public void setProjectTask(Long projectTask) {
		this.projectTask = projectTask;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getClientVendorContactType() {
		return clientVendorContactType;
	}
	public void setClientVendorContactType(String clientVendorContactType) {
		this.clientVendorContactType = clientVendorContactType;
	}
	
}
