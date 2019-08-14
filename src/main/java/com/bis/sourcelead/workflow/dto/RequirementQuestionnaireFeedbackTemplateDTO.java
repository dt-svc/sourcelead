package com.bis.sourcelead.workflow.dto;

import java.util.List;

import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.workflow.entity.RequirementWorkFlow;

public class RequirementQuestionnaireFeedbackTemplateDTO {

	private Long id;
	
	private OrgLocations orgLocationId;
	
	private Candidate candidateId;

	private RequirementWorkFlow workflowId;
	
	private String jsonStringData;
	
	private String uuid;
	
	private List<FileInfo> uploadedFileInfo;
	
	private String staticPath;
	
	private String questionnaireTemplateUuid;
	
	private String jsonString ;
	
	private String jsonStringValues ;
	
	private Integer timeLimitMinutes;
	
	private Integer timeLimitSeconds;
	
	private Integer submissionsCount;
	
	private Integer totalPoints;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public Candidate getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Candidate candidateId) {
		this.candidateId = candidateId;
	}

	public RequirementWorkFlow getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(RequirementWorkFlow workflowId) {
		this.workflowId = workflowId;
	}

	public String getJsonStringData() {
		return jsonStringData;
	}

	public void setJsonStringData(String jsonStringData) {
		this.jsonStringData = jsonStringData;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<FileInfo> getUploadedFileInfo() {
		return uploadedFileInfo;
	}

	public void setUploadedFileInfo(List<FileInfo> uploadedFileInfo) {
		this.uploadedFileInfo = uploadedFileInfo;
	}

	public String getStaticPath() {
		return staticPath;
	}

	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}

	public String getQuestionnaireTemplateUuid() {
		return questionnaireTemplateUuid;
	}

	public void setQuestionnaireTemplateUuid(String questionnaireTemplateUuid) {
		this.questionnaireTemplateUuid = questionnaireTemplateUuid;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public String getJsonStringValues() {
		return jsonStringValues;
	}

	public void setJsonStringValues(String jsonStringValues) {
		this.jsonStringValues = jsonStringValues;
	}

	public Integer getTimeLimitMinutes() {
		return timeLimitMinutes;
	}

	public void setTimeLimitMinutes(Integer timeLimitMinutes) {
		this.timeLimitMinutes = timeLimitMinutes;
	}

	public Integer getTimeLimitSeconds() {
		return timeLimitSeconds;
	}

	public void setTimeLimitSeconds(Integer timeLimitSeconds) {
		this.timeLimitSeconds = timeLimitSeconds;
	}

	public Integer getSubmissionsCount() {
		return submissionsCount;
	}

	public void setSubmissionsCount(Integer submissionsCount) {
		this.submissionsCount = submissionsCount;
	}

	public Integer getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(Integer totalPoints) {
		this.totalPoints = totalPoints;
	}

	
}
