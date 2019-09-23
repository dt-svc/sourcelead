package com.bis.sourcelead.dto.performance;

import java.io.Serializable;

import com.bis.sourcelead.annotations.Required;

public class PerformanceGoalsCommentsDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Required(desc="goalsComment")
	private String goalsComment;
	
	@Required(desc="goalId")
	private Long goalId;
	
	private String mentionedUserIds;
	
	private Long createdUserId;
	
	private String createdUserName;
	
	private String imgUrl;
	
	private String createdDate;
	
	private String createdTime;
	

	public String getGoalsComment() {
		return goalsComment;
	}

	public void setGoalsComment(String goalsComment) {
		this.goalsComment = goalsComment;
	}

	public Long getGoalId() {
		return goalId;
	}

	public void setGoalId(Long goalId) {
		this.goalId = goalId;
	}

	public String getMentionedUserIds() {
		return mentionedUserIds;
	}

	public void setMentionedUserIds(String mentionedUserIds) {
		this.mentionedUserIds = mentionedUserIds;
	}

	public Long getCreatedUserId() {
		return createdUserId;
	}

	public void setCreatedUserId(Long createdUserId) {
		this.createdUserId = createdUserId;
	}

	public String getCreatedUserName() {
		return createdUserName;
	}

	public void setCreatedUserName(String createdUserName) {
		this.createdUserName = createdUserName;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	
	
	
}
