package com.bis.sourcelead.payroll.dto;

import java.util.List;
import java.util.Map;

public class MyPayrollSummaryDTO {
	
	private List<MyPayrollDTO> myPayroll;
	
	private Map<String, Double> preDed;
	
	private Map<String, Double> postDed;
	
	private Map<String, Double> contribution;
	
	private double dedTotal;
	
	private double contriTotal;

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


	public void setPreDed(Map<String, Double> preDed)
	{
		this.preDed = preDed;
	}

	public void setPostDed(Map<String, Double> postDed)
	{
		this.postDed = postDed;
	}


	public double getDedTotal()
	{
		return dedTotal;
	}

	public double getContriTotal()
	{
		return contriTotal;
	}

	public void setDedTotal(double dedTotal)
	{
		this.dedTotal = dedTotal;
	}

	public void setContriTotal(double contriTotal)
	{
		this.contriTotal = contriTotal;
	}

	public Map<String, Double> getContribution()
	{
		return contribution;
	}

	public void setContribution(Map<String, Double> contribution)
	{
		this.contribution = contribution;
	}
	
	
	
	
	
}
