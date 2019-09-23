package com.bis.sourcelead.payroll.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.dto.AssociateLoanAndAdvancesDTO;

public class MyPayrollSummaryDTO {
	
	private List<MyPayrollDTO> myPayroll;
	
	private Map<String, Double> preDed;
	
	private Map<String, Double> postDed;
	
	private Map<String, Double> contribution;
	
	private double dedTotal;
	
	private double contriTotal;
	
	private ConfirmPayTotalSumDTO confirmPayTotalSum;
	
	private Map<String, Double> payItem;
	
	private List<AssociateLoanAndAdvancesDTO> loanAdvance;
	
	private BigDecimal loanAmountTotal;
	
	
	private EmployeeHoursDTO hours;
	

	public EmployeeHoursDTO getHours()
	{
		return hours;
	}

	public void setHours(EmployeeHoursDTO hours)
	{
		this.hours = hours;
	}

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

	public ConfirmPayTotalSumDTO getConfirmPayTotalSum()
	{
		return confirmPayTotalSum;
	}

	public void setConfirmPayTotalSum(ConfirmPayTotalSumDTO confirmPayTotalSum)
	{
		this.confirmPayTotalSum = confirmPayTotalSum;
	}

	public Map<String, Double> getPayItem()
	{
		return payItem;
	}

	public void setPayItem(Map<String, Double> payItem)
	{
		this.payItem = payItem;
	}

	public List<AssociateLoanAndAdvancesDTO> getLoanAdvance() {
		return loanAdvance;
	}

	public void setLoanAdvance(List<AssociateLoanAndAdvancesDTO> loanAdvance) {
		this.loanAdvance = loanAdvance;
	}

	public BigDecimal getLoanAmountTotal() {
		return loanAmountTotal;
	}

	public void setLoanAmountTotal(BigDecimal loanAmountTotal) {
		this.loanAmountTotal = loanAmountTotal;
	}

	
	
}
