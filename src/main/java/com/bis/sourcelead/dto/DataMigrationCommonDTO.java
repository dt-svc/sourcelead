package com.bis.sourcelead.dto;

public class DataMigrationCommonDTO extends CommonDTO {
	private String filePath;
	private String uploadType;
	private String timesheetMode;
	private String headerPosition;
	
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
}
