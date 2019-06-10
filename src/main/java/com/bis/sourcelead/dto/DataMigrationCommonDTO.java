package com.bis.sourcelead.dto;

public class DataMigrationCommonDTO extends CommonDTO {
	private String filePath;
	private String uploadType;
	private String timesheetMode;
	private String headerPosition;
	private String projectUUID;
	private String projectClientUUID;
	private Long requirementId;
	
	public String getFilePath()
	{
		return filePath;
	}
	
	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}
	
	public String getUploadType()
	{
		return uploadType;
	}
	
	public void setUploadType(String uploadType)
	{
		this.uploadType = uploadType;
	}
	
	public String getTimesheetMode()
	{
		return timesheetMode;
	}
	
	public void setTimesheetMode(String timesheetMode)
	{
		this.timesheetMode = timesheetMode;
	}

	public String getHeaderPosition()
	{
		return headerPosition;
	}

	public void setHeaderPosition(String headerPosition)
	{
		this.headerPosition = headerPosition;
	}

	public String getProjectUUID() {
		return projectUUID;
	}

	public void setProjectUUID(String projectUUID) {
		this.projectUUID = projectUUID;
	}

	public String getProjectClientUUID() {
		return projectClientUUID;
	}

	public void setProjectClientUUID(String projectClientUUID) {
		this.projectClientUUID = projectClientUUID;
	}

	public Long getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(Long requirementId) {
		this.requirementId = requirementId;
	}
}
