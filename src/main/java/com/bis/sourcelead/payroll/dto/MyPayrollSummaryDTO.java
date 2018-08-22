package com.bis.sourcelead.payroll.dto;

import java.util.List;
import java.util.Map;

public class MyPayrollSummaryDTO {
	
	private List<MyPayrollDTO> myPayroll;
	
	Map<String, Double> preDed;
	
	Map<String, Double> postDed;
	
	Map<String, Double> preContribution;
	
	Map<String, Double> postContribution;

	public List<MyPayrollDTO> getMyPayroll()
	{
		return myPayroll;
	}

	public void setMyPayroll(List<MyPayrollDTO> myPayroll)
	{
		this.myPayroll = myPayroll;
	}

	public Map<String, Double> getPreDed()
	{
		return preDed;
	}

	public Map<String, Double> getPostDed()
	{
		return postDed;
	}

	public Map<String, Double> getPreContribution()
	{
		return preContribution;
	}

	public Map<String, Double> getPostContribution()
	{
		return postContribution;
	}

	public void setPreDed(Map<String, Double> preDed)
	{
		this.preDed = preDed;
	}

	public void setPostDed(Map<String, Double> postDed)
	{
		this.postDed = postDed;
	}

	public void setPreContribution(Map<String, Double> preContribution)
	{
		this.preContribution = preContribution;
	}

	public void setPostContribution(Map<String, Double> postContribution)
	{
		this.postContribution = postContribution;
	}
	
	
	
	
	
}
