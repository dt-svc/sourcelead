package com.bis.sourcelead.reportsDTO;

import java.util.Map;

import com.bis.sourcelead.dto.SearchDTO;

public class PaidBillDTO extends SearchDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String projectName;
	private String billDate;
	private String payDate;
	private double amount;
	private String billType;
	private String orgName;
	private int numberOfAssociates;
	private int numberOfprojects;
	private double totalAmount;
	private double totalCalAmount;
	private long orgRelationId;
	private String orgId;
	private String filterType;
	private String fromDate;
	private String toDate;
	
	private Map<Long,Double> totalAmountMap;
	
	public String getProjectName()
	{
		return projectName;
	}
	
	public void setProjectName(String projectName)
	{
		this.projectName = projectName;
	}
	
	public String getBillDate()
	{
		return billDate;
	}
	
	public void setBillDate(String billDate)
	{
		this.billDate = billDate;
	}
	
	public String getPayDate()
	{
		return payDate;
	}
	
	public void setPayDate(String payDate)
	{
		this.payDate = payDate;
	}
	
	public double getAmount()
	{
		return amount;
	}
	
	public void setAmount(double amount)
	{
		this.amount = amount;
	}
	
	public String getBillType()
	{
		return billType;
	}
	
	public void setBillType(String billType)
	{
		this.billType = billType;
	}
	
	public String getOrgName()
	{
		return orgName;
	}
	
	public void setOrgName(String orgName)
	{
		this.orgName = orgName;
	}
	
	public int getNumberOfAssociates()
	{
		return numberOfAssociates;
	}
	
	public void setNumberOfAssociates(int numberOfAssociates)
	{
		this.numberOfAssociates = numberOfAssociates;
	}
	
	public int getNumberOfprojects()
	{
		return numberOfprojects;
	}
	
	public void setNumberOfprojects(int numberOfprojects)
	{
		this.numberOfprojects = numberOfprojects;
	}
	
	public double getTotalAmount()
	{
		return totalAmount;
	}
	
	public void setTotalAmount(double totalAmount)
	{
		this.totalAmount = totalAmount;
	}
	
	public long getOrgRelationId()
	{
		return orgRelationId;
	}
	
	public void setOrgRelationId(long orgRelationId)
	{
		this.orgRelationId = orgRelationId;
	}
	
	public String getOrgId()
	{
		return orgId;
	}
	
	public void setOrgId(String orgId)
	{
		this.orgId = orgId;
	}
	
	public String getFilterType()
	{
		return filterType;
	}
	
	public void setFilterType(String filterType)
	{
		this.filterType = filterType;
	}

	public String getFromDate()
	{
		return fromDate;
	}

	public void setFromDate(String fromDate)
	{
		this.fromDate = fromDate;
	}

	public String getToDate()
	{
		return toDate;
	}

	public void setToDate(String toDate)
	{
		this.toDate = toDate;
	}

	public Map<Long, Double> getTotalAmountMap() {
		return totalAmountMap;
	}

	public void setTotalAmountMap(Map<Long, Double> totalAmountMap) {
		this.totalAmountMap = totalAmountMap;
	}

	public double getTotalCalAmount() {
		return totalCalAmount;
	}

	public void setTotalCalAmount(double totalCalAmount) {
		this.totalCalAmount = totalCalAmount;
	}
	
}
