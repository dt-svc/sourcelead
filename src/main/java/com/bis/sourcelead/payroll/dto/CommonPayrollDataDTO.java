package com.bis.sourcelead.payroll.dto;

import java.util.List;

public class CommonPayrollDataDTO {
	
	private String payrollFreqStartDate;
	
	private String payrollFreqEndDate;
	
	private String payrollCheckDate;
	
	private String payrollRunDate;
	
	private String payrollFrequency;
	
	private List<RecentPayrollRunDataDTO> payrollGraphData;
	
	private List<RecentPayrollRunDataDTO> payrollGridData;

	public String getPayrollFrequency()
	{
		return payrollFrequency;
	}

	public void setPayrollFrequency(String payrollFrequency)
	{
		this.payrollFrequency = payrollFrequency;
	}

	public String getPayrollFreqStartDate()
	{
		return payrollFreqStartDate;
	}

	public String getPayrollFreqEndDate()
	{
		return payrollFreqEndDate;
	}

	public String getPayrollCheckDate()
	{
		return payrollCheckDate;
	}

	public String getPayrollRunDate()
	{
		return payrollRunDate;
	}

	public void setPayrollFreqStartDate(String payrollFreqStartDate)
	{
		this.payrollFreqStartDate = payrollFreqStartDate;
	}

	public void setPayrollFreqEndDate(String payrollFreqEndDate)
	{
		this.payrollFreqEndDate = payrollFreqEndDate;
	}

	public void setPayrollCheckDate(String payrollCheckDate)
	{
		this.payrollCheckDate = payrollCheckDate;
	}

	public void setPayrollRunDate(String payrollRunDate)
	{
		this.payrollRunDate = payrollRunDate;
	}

	public List<RecentPayrollRunDataDTO> getPayrollGraphData()
	{
		return payrollGraphData;
	}

	public void setPayrollGraphData(List<RecentPayrollRunDataDTO> payrollGraphData)
	{
		this.payrollGraphData = payrollGraphData;
	}

	public List<RecentPayrollRunDataDTO> getPayrollGridData()
	{
		return payrollGridData;
	}

	public void setPayrollGridData(List<RecentPayrollRunDataDTO> payrollGridData)
	{
		this.payrollGridData = payrollGridData;
	}

	


}
