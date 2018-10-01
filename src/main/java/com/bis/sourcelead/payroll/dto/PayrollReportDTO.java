package com.bis.sourcelead.payroll.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.bis.sourcelead.dto.SearchDTO;

public class PayrollReportDTO extends SearchDTO{
	
	private String empId;
	
	private String empName;
	
	private BigDecimal empPayRate;
	
	private String regularHours;
	
	private String overTimeHours;
	
	private BigDecimal preTaxDeduction;
	
	private BigDecimal postTaxDeduction;
	
	private BigDecimal taxes;
	
	private BigDecimal netPay;
	
	private BigDecimal grossPay;
	
	private BigDecimal lastPay;
	
	private BigDecimal ytdNet;
	
	private String action;
	
	private String freqStartDate;
	
	private String freqEndDate;
	
	private String payPeriod;
	
	private String approverStatus;
	
	private String department;
	
	private Long payGroup;
	
	private String payRollType;
	
	private String departmentName;

	private String groupId;
	
	private String payCheckDate;
	
	private BigDecimal deductions;
	
	private BigDecimal ytdTaxes;
	
	private BigDecimal ytdDeductions;

	private String depositType;

	private String searchValue;
	
	private Long orgLocationid;

	private String payrollUUID;
	
	private String contributionId;
	
	private String contributionType;
	
	private String schedule;
	
	private BigDecimal dedAmount;
	
	private BigDecimal contributionYTD;

	private String deductionId;
	
	private String deductionType;
		
	private BigDecimal contributionAmount;
	
	private BigDecimal deductionYTD;
	
	private Date checkDate;
	
	private String groupName;
	
	private String groupFrequency;
	
	private String type;
	
	private Date runDate;
	
	private BigDecimal contributionMTD;
	
	private BigDecimal contributionQTD;
	
	private BigDecimal deductionMTD;
	
	private BigDecimal deductionQTD;
	
	
	public BigDecimal getContributionMTD()
	{
		return contributionMTD;
	}


	public BigDecimal getContributionQTD()
	{
		return contributionQTD;
	}


	public void setContributionMTD(BigDecimal contributionMTD)
	{
		this.contributionMTD = contributionMTD;
	}


	public void setContributionQTD(BigDecimal contributionQTD)
	{
		this.contributionQTD = contributionQTD;
	}


	public BigDecimal getContributionAmount()
	{
		return contributionAmount;
	}


	public void setContributionAmount(BigDecimal contributionAmount)
	{
		this.contributionAmount = contributionAmount;
	}


	public BigDecimal getContributionYTD()
	{
		return contributionYTD;
	}


	public void setContributionYTD(BigDecimal contributionYTD)
	{
		this.contributionYTD = contributionYTD;
	}


	public BigDecimal getDedAmount()
	{
		return dedAmount;
	}


	public void setDedAmount(BigDecimal dedAmount)
	{
		this.dedAmount = dedAmount;
	}


	public String getSchedule()
	{
		return schedule;
	}


	public void setSchedule(String schedule)
	{
		this.schedule = schedule;
	}


	public String getContributionId()
	{
		return contributionId;
	}


	public void setContributionId(String contributionId)
	{
		this.contributionId = contributionId;
	}


	public String getPayrollUUID()
	{
		return payrollUUID;
	}


	public void setPayrollUUID(String payrollUUID)
	{
		this.payrollUUID = payrollUUID;
	}


	public String getEmpName()
	{
		return empName;
	}

	
	public String getRegularHours()
	{
		return regularHours;
	}

	

	public BigDecimal getPreTaxDeduction()
	{
		return preTaxDeduction;
	}

	public BigDecimal getPostTaxDeduction()
	{
		return postTaxDeduction;
	}

	public BigDecimal getTaxes()
	{
		return taxes;
	}

	public BigDecimal getNetPay()
	{
		return netPay;
	}

	public BigDecimal getLastPay()
	{
		return lastPay;
	}


	public String getAction()
	{
		return action;
	}

	public void setEmpName(String empName)
	{
		this.empName = empName;
	}


	public void setRegularHours(String regularHours)
	{
		this.regularHours = regularHours;
	}


	public void setPreTaxDeduction(BigDecimal preTaxDeduction)
	{
		this.preTaxDeduction = preTaxDeduction;
	}

	public void setPostTaxDeduction(BigDecimal postTaxDeduction)
	{
		this.postTaxDeduction = postTaxDeduction;
	}

	public void setTaxes(BigDecimal taxes)
	{
		this.taxes = taxes;
	}

	public void setNetPay(BigDecimal netPay)
	{
		this.netPay = netPay;
	}

	public void setLastPay(BigDecimal lastPay)
	{
		this.lastPay = lastPay;
	}

	public void setAction(String action)
	{
		this.action = action;
	}

	public String getFreqStartDate()
	{
		return freqStartDate;
	}

	public void setFreqStartDate(String freqStartDate)
	{
		this.freqStartDate = freqStartDate;
	}

	public String getFreqEndDate()
	{
		return freqEndDate;
	}

	public void setFreqEndDate(String freqEndDate)
	{
		this.freqEndDate = freqEndDate;
	}


	public String getPayPeriod()
	{
		return payPeriod;
	}

	public void setPayPeriod(String payPeriod)
	{
		this.payPeriod = payPeriod;
	}

	public String getApproverStatus()
	{
		return approverStatus;
	}

	public void setApproverStatus(String approverStatus)
	{
		this.approverStatus = approverStatus;
	}

	public String getDepartment()
	{
		return department;
	}

	public void setDepartment(String department)
	{
		this.department = department;
	}

	public Long getPayGroup()
	{
		return payGroup;
	}

	public void setPayGroup(Long payGroup)
	{
		this.payGroup = payGroup;
	}

	public String getPayRollType()
	{
		return payRollType;
	}

	public void setPayRollType(String payRollType)
	{
		this.payRollType = payRollType;
	}

	public String getEmpId()
	{
		return empId;
	}

	public void setEmpId(String empId)
	{
		this.empId = empId;
	}

	public String getDepartmentName()
	{
		return departmentName;
	}

	public void setDepartmentName(String departmentName)
	{
		this.departmentName = departmentName;
	}

	public String getGroupId()
	{
		return groupId;
	}

	public String getPayCheckDate()
	{
		return payCheckDate;
	}

	public void setGroupId(String groupId)
	{
		this.groupId = groupId;
	}

	public void setPayCheckDate(String payCheckDate)
	{
		this.payCheckDate = payCheckDate;
	}

	public BigDecimal getGrossPay()
	{
		return grossPay;
	}

	public void setGrossPay(BigDecimal grossPay)
	{
		this.grossPay = grossPay;
	}

	public BigDecimal getDeductions()
	{
		return deductions;
	}

	public void setDeductions(BigDecimal deductions)
	{
		this.deductions = deductions;
	}

	public BigDecimal getYtdTaxes()
	{
		return ytdTaxes;
	}

	public void setYtdTaxes(BigDecimal ytdTaxes)
	{
		this.ytdTaxes = ytdTaxes;
	}

	public BigDecimal getYtdDeductions()
	{
		return ytdDeductions;
	}

	public void setYtdDeductions(BigDecimal ytdDeductions)
	{
		this.ytdDeductions = ytdDeductions;
	}

	public BigDecimal getEmpPayRate()
	{
		return empPayRate;
	}

	public String getOverTimeHours()
	{
		return overTimeHours;
	}

	public void setOverTimeHours(String overTimeHours)
	{
		this.overTimeHours = overTimeHours;
	}

	public void setEmpPayRate(BigDecimal empPayRate)
	{
		this.empPayRate = empPayRate;
	}

	public BigDecimal getYtdNet()
	{
		return ytdNet;
	}

	public void setYtdNet(BigDecimal ytdNet)
	{
		this.ytdNet = ytdNet;
	}


	public String getDepositType() {
		return depositType;
	}


	public void setDepositType(String depositType) {
		this.depositType = depositType;
	}
	

	public String getContributionType()
	{
		return contributionType;
	}

	public void setContributionType(String contributionType)
	{
		this.contributionType = contributionType;
	}

	public String getSearchValue()
	{
		return searchValue;
	}

	public void setSearchValue(String searchValue)
	{
		this.searchValue = searchValue;
	}

	public Long getOrgLocationid()
	{
		return orgLocationid;
	}

	public void setOrgLocationid(Long orgLocationid)
	{
		this.orgLocationid = orgLocationid;
	}


	public String getDeductionId() {
		return deductionId;
	}


	public void setDeductionId(String deductionId) {
		this.deductionId = deductionId;
	}


	public String getDeductionType() {
		return deductionType;
	}


	public void setDeductionType(String deductionType) {
		this.deductionType = deductionType;
	}


	public BigDecimal getDeductionYTD() {
		return deductionYTD;
	}


	public void setDeductionYTD(BigDecimal deductionYTD) {
		this.deductionYTD = deductionYTD;
	}


	public Date getCheckDate()
	{
		return checkDate;
	}


	public void setCheckDate(Date checkDate)
	{
		this.checkDate = checkDate;
	}


	public String getGroupName()
	{
		return groupName;
	}


	public void setGroupName(String groupName)
	{
		this.groupName = groupName;
	}


	public String getGroupFrequency()
	{
		return groupFrequency;
	}


	public void setGroupFrequency(String groupFrequency)
	{
		this.groupFrequency = groupFrequency;
	}


	public String getType()
	{
		return type;
	}


	public void setType(String type)
	{
		this.type = type;
	}


	public Date getRunDate()
	{
		return runDate;
	}


	public void setRunDate(Date runDate)
	{
		this.runDate = runDate;
	}


	public BigDecimal getContributionMTD() {
		return contributionMTD;
	}


	public void setContributionMTD(BigDecimal contributionMTD) {
		this.contributionMTD = contributionMTD;
	}


	public BigDecimal getContributionQTD() {
		return contributionQTD;
	}


	public void setContributionQTD(BigDecimal contributionQTD) {
		this.contributionQTD = contributionQTD;
	}


	public BigDecimal getDeductionMTD() {
		return deductionMTD;
	}


	public void setDeductionMTD(BigDecimal deductionMTD) {
		this.deductionMTD = deductionMTD;
	}


	public BigDecimal getDeductionQTD() {
		return deductionQTD;
	}


	public void setDeductionQTD(BigDecimal deductionQTD) {
		this.deductionQTD = deductionQTD;
	}	
	
	
}
