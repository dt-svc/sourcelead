package com.bis.sourcelead.dto.performance;

import java.io.Serializable;
import java.util.List;

import com.bis.sourcelead.dto.FileInfo;

public class PerformanceFeedbackCategoryDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<FileInfo> uploadedFileInfo;
	
	private String assignedUUID;
	
	private String staticPath;
	
	private String jsonData;
	
	private String jsonDataValues;
	
	private Long orgLocationId;
	
	private Long orgId;
	
	private Long userId;
	
	private String feedbackTemplateUUID;
	
	private Integer categoryScore;
	
	
	public List<FileInfo> getUploadedFileInfo() {
		return uploadedFileInfo;
	}

	public void setUploadedFileInfo(List<FileInfo> uploadedFileInfo) {
		this.uploadedFileInfo = uploadedFileInfo;
	}

	public String getAssignedUUID() {
		return assignedUUID;
	}

	public void setAssignedUUID(String assignedUUID) {
		this.assignedUUID = assignedUUID;
	}

	public String getStaticPath() {
		return staticPath;
	}

	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public String getJsonDataValues() {
		return jsonDataValues;
	}

	public void setJsonDataValues(String jsonDataValues) {
		this.jsonDataValues = jsonDataValues;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFeedbackTemplateUUID() {
		return feedbackTemplateUUID;
	}

	public void setFeedbackTemplateUUID(String feedbackTemplateUUID) {
		this.feedbackTemplateUUID = feedbackTemplateUUID;
	}

	public Integer getCategoryScore() {
		return categoryScore;
	}

	public void setCategoryScore(Integer categoryScore) {
		this.categoryScore = categoryScore;
	}
	
	
}
