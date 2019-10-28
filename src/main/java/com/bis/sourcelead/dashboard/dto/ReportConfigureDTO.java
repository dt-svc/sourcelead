package com.bis.sourcelead.dashboard.dto;

import com.bis.sourcelead.annotations.Required;

public class ReportConfigureDTO {
	
	@Required(desc="reportTypeArray")
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
