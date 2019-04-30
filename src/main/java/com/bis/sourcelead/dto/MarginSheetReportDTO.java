package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.math.BigInteger;


public class MarginSheetReportDTO extends SearchDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private BigInteger projectId;
	private String projectName;
	private BigInteger uid;
	private String employeeName;
	private double payRate;
	private String payRateType;
	private String payRateCurrency;
	private double billRate;
	private String billRateType;
	private String billRateTypeCurrency;
	private String clinetName;
	private BigInteger orglocationId;
	private BigInteger orgRelationId;
	private double totalPayRate;
	private double totalbilRate;
	private String clientId;
	private double marginRate;
	private double totalMarginRate;
	private String employeeId;
	private String projectDuration;
	private String associateJoinedDate;
	
	

	public BigInteger getProjectId()
	{
		return projectId;
	}
	public void setProjectId(BigInteger projectId)
	{
		this.projectId = projectId;
	}
	public String getProjectName()
	{
		return projectName;
	}
	public void setProjectName(String projectName)
	{
		this.projectName = projectName;
	}
	public String getEmployeeName()
	{
		return employeeName;
	}
	public void setEmployeeName(String employeeName)
	{
		this.employeeName = employeeName;
	}
	public double getPayRate()
	{
		return payRate;
	}
	public void setPayRate(double payRate)
	{
		this.payRate = payRate;
	}
	public String getPayRateType()
	{
		return payRateType;
	}
	public void setPayRateType(String payRateType)
	{
		this.payRateType = payRateType;
	}
	public String getPayRateCurrency()
	{
		return payRateCurrency;
	}
	public void setPayRateCurrency(String payRateCurrency)
	{
		this.payRateCurrency = payRateCurrency;
	}
	public double getBillRate()
	{
		return billRate;
	}
	public void setBillRate(double billRate)
	{
		this.billRate = billRate;
	}
	public String getBillRateType()
	{
		return billRateType;
	}
	public void setBillRateType(String billRateType)
	{
		this.billRateType = billRateType;
	}
	public String getBillRateTypeCurrency()
	{
		return billRateTypeCurrency;
	}
	public void setBillRateTypeCurrency(String billRateTypeCurrency)
	{
		this.billRateTypeCurrency = billRateTypeCurrency;
	}
	public String getClinetName()
	{
		return clinetName;
	}
	public void setClinetName(String clinetName)
	{
		this.clinetName = clinetName;
	}
	public BigInteger getUid()
	{
		return uid;
	}
	public void setUid(BigInteger uid)
	{
		this.uid = uid;
	}
	public BigInteger getOrglocationId()
	{
		return orglocationId;
	}
	public void setOrglocationId(BigInteger orglocationId)
	{
		this.orglocationId = orglocationId;
	}
	public BigInteger getOrgRelationId()
	{
		return orgRelationId;
	}
	public void setOrgRelationId(BigInteger orgRelationId)
	{
		this.orgRelationId = orgRelationId;
	}
	public double getTotalPayRate()
	{
		return totalPayRate;
	}
	public void setTotalPayRate(double totalPayRate)
	{
		this.totalPayRate = totalPayRate;
	}
	public double getTotalbilRate()
	{
		return totalbilRate;
	}
	public void setTotalbilRate(double totalbilRate)
	{
		this.totalbilRate = totalbilRate;
	}
	public String getClientId()
	{
		return clientId;
	}
	public void setClientId(String clientId)
	{
		this.clientId = clientId;
	}
	public double getMarginRate()
	{
		return marginRate;
	}
	public void setMarginRate(double marginRate)
	{
		this.marginRate = marginRate;
	}
	public double getTotalMarginRate()
	{
		return totalMarginRate;
	}
	public void setTotalMarginRate(double totalMarginRate)
	{
		this.totalMarginRate = totalMarginRate;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getProjectDuration() {
		return projectDuration;
	}
	public void setProjectDuration(String projectDuration) {
		this.projectDuration = projectDuration;
	}
	public String getAssociateJoinedDate() {
		return associateJoinedDate;
	}
	public void setAssociateJoinedDate(String associateJoinedDate) {
		this.associateJoinedDate = associateJoinedDate;
	}
	
	
}
