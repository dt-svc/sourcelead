package com.bis.sourcelead.dto;

import java.util.List;

public class RequirementInterviewerDTO {
	
private Long id;
	
private String interviewerName;

private String feedbackDate;

private String FeedbackStatus;

private String uuid;

private List<UserProfileDTO> interviewerUser;

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getInterviewerName() {
	return interviewerName;
}

public void setInterviewerName(String interviewerName) {
	this.interviewerName = interviewerName;
}

public String getFeedbackDate() {
	return feedbackDate;
}

public void setFeedbackDate(String feedbackDate) {
	this.feedbackDate = feedbackDate;
}

public String getFeedbackStatus() {
	return FeedbackStatus;
}

public void setFeedbackStatus(String feedbackStatus) {
	FeedbackStatus = feedbackStatus;
}

public List<UserProfileDTO> getInterviewerUser() {
	return interviewerUser;
}

public void setInterviewerUser(List<UserProfileDTO> interviewerUser) {
	this.interviewerUser = interviewerUser;
}

public String getUuid() {
	return uuid;
}

public void setUuid(String uuid) {
	this.uuid = uuid;
}








}
