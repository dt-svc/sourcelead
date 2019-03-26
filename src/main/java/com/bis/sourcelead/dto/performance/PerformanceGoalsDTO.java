package com.bis.sourcelead.dto.performance;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.annotations.Required;
import com.bis.sourcelead.dto.FileInfo;

public class PerformanceGoalsDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long goalId;
	
	@Required(desc="goalName")
	private String goalName;
	
	@Required(desc="goalStartDate")
	private String goalStartDate;
	
	@Required(desc="goalEndDate")
	private String goalEndDate;
	
	private String goalsAssignTo;
	
	private String description;
	
	private String staticPath;
	
    private List<Map<String, String>> fileInfo;

	private List<FileInfo> uploadedFileInfo;
	
	private String uuid;

	public Long getGoalId() {
		return goalId;
	}

	public void setGoalId(Long goalId) {
		this.goalId = goalId;
	}

	public String getGoalName() {
		return goalName;
	}

	public void setGoalName(String goalName) {
		this.goalName = goalName;
	}

	public String getGoalStartDate() {
		return goalStartDate;
	}

	public void setGoalStartDate(String goalStartDate) {
		this.goalStartDate = goalStartDate;
	}

	public String getGoalEndDate() {
		return goalEndDate;
	}

	public void setGoalEndDate(String goalEndDate) {
		this.goalEndDate = goalEndDate;
	}

	public String getGoalsAssignTo() {
		return goalsAssignTo;
	}

	public void setGoalsAssignTo(String goalsAssignTo) {
		this.goalsAssignTo = goalsAssignTo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStaticPath() {
		return staticPath;
	}

	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}

	public List<Map<String, String>> getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(List<Map<String, String>> fileInfo) {
		this.fileInfo = fileInfo;
	}

	public List<FileInfo> getUploadedFileInfo() {
		return uploadedFileInfo;
	}

	public void setUploadedFileInfo(List<FileInfo> uploadedFileInfo) {
		this.uploadedFileInfo = uploadedFileInfo;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	
}
