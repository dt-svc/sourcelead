package com.bis.sourcelead.dashboard.dto;

public class ReportConfigureDTO {
	
	private String reportTypeArray;
	
	private String customDashBoardConfUUID;

	public String getReportTypeArray()
	{
		return reportTypeArray;
	}

	public String getCustomDashBoardConfUUID()
	{
		return customDashBoardConfUUID;
	}

	public void setReportTypeArray(String reportTypeArray)
	{
		this.reportTypeArray = reportTypeArray;
	}

	public void setCustomDashBoardConfUUID(String customDashBoardConfUUID)
	{
		this.customDashBoardConfUUID = customDashBoardConfUUID;
	}
	
	
}
