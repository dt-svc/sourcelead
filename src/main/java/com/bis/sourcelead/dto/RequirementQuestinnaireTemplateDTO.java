package com.bis.sourcelead.dto;

public class RequirementQuestinnaireTemplateDTO {

	private long id;
	
	private String requirementWorkFlowTaskId;
	
	private String orgLocationId;

	private String uuid;

	private String 	question;
	
	private String 	helptext;
	
	private String questionPoints;
	
	private String noOfAttempt;
	
	private String timeLimitMinutes;
	
	private String timeLimitSeconds;
	
	private String status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRequirementWorkFlowTaskId() {
		return requirementWorkFlowTaskId;
	}

	public void setRequirementWorkFlowTaskId(String requirementWorkFlowTaskId) {
		this.requirementWorkFlowTaskId = requirementWorkFlowTaskId;
	}

	public String getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(String orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getHelptext() {
		return helptext;
	}

	public void setHelptext(String helptext) {
		this.helptext = helptext;
	}

	public String getQuestionPoints() {
		return questionPoints;
	}

	public void setQuestionPoints(String questionPoints) {
		this.questionPoints = questionPoints;
	}

	public String getNoOfAttempt() {
		return noOfAttempt;
	}

	public void setNoOfAttempt(String noOfAttempt) {
		this.noOfAttempt = noOfAttempt;
	}

	public String getTimeLimitMinutes() {
		return timeLimitMinutes;
	}

	public void setTimeLimitMinutes(String timeLimitMinutes) {
		this.timeLimitMinutes = timeLimitMinutes;
	}

	public String getTimeLimitSeconds() {
		return timeLimitSeconds;
	}

	public void setTimeLimitSeconds(String timeLimitSeconds) {
		this.timeLimitSeconds = timeLimitSeconds;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
