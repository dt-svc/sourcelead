package com.bis.sourcelead.dto.performance;

import java.io.Serializable;

public class PerformanceFeedbackDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String jsonCategoriesString;
	
	private Integer feedbackCategoriesScore;
	
	private Integer goalsScore;
	
	private Integer tasksScore;
	
	private Integer competenciesScore;
	
	private Integer finalScore;
	
	private String submissionStage;
	
	private Long orgLocationId;
	
	private Long orgId;
	
	private Long userId;
	
	private String assignedUUID;
	
	private String comments;
	
	private String scoreType;
	
	private double peersScore;
	
	private Integer appliedLeaves;
	
	private String timeSheetHours;

	public String getJsonCategoriesString() {
		return jsonCategoriesString;
	}

	public void setJsonCategoriesString(String jsonCategoriesString) {
		this.jsonCategoriesString = jsonCategoriesString;
	}

	public Integer getFeedbackCategoriesScore() {
		return feedbackCategoriesScore;
	}

	public void setFeedbackCategoriesScore(Integer feedbackCategoriesScore) {
		this.feedbackCategoriesScore = feedbackCategoriesScore;
	}

	public Integer getGoalsScore() {
		return goalsScore;
	}

	public void setGoalsScore(Integer goalsScore) {
		this.goalsScore = goalsScore;
	}

	public Integer getTasksScore() {
		return tasksScore;
	}

	public void setTasksScore(Integer tasksScore) {
		this.tasksScore = tasksScore;
	}

	public Integer getCompetenciesScore() {
		return competenciesScore;
	}

	public void setCompetenciesScore(Integer competenciesScore) {
		this.competenciesScore = competenciesScore;
	}

	public Integer getFinalScore() {
		return finalScore;
	}

	public void setFinalScore(Integer finalScore) {
		this.finalScore = finalScore;
	}

	public String getSubmissionStage() {
		return submissionStage;
	}

	public void setSubmissionStage(String submissionStage) {
		this.submissionStage = submissionStage;
	}

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getAssignedUUID() {
		return assignedUUID;
	}

	public void setAssignedUUID(String assignedUUID) {
		this.assignedUUID = assignedUUID;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getScoreType() {
		return scoreType;
	}

	public void setScoreType(String scoreType) {
		this.scoreType = scoreType;
	}

	public double getPeersScore() {
		return peersScore;
	}

	public void setPeersScore(double peersScore) {
		this.peersScore = peersScore;
	}

	public Integer getAppliedLeaves() {
		return appliedLeaves;
	}

	public void setAppliedLeaves(Integer appliedLeaves) {
		this.appliedLeaves = appliedLeaves;
	}

	public String getTimeSheetHours() {
		return timeSheetHours;
	}

	public void setTimeSheetHours(String timeSheetHours) {
		this.timeSheetHours = timeSheetHours;
	}

	

}
