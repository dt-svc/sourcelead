package com.bis.sourcelead.dto;

public class CustomFieldsRequestTypeDTO {
	Long sectionId;
	Long requirementId;
	String requestFrom;
	Long candidateId;
	Long userId;
	
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
	
}
