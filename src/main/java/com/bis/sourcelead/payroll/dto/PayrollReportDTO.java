package com.bis.sourcelead.payroll.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.bis.sourcelead.dto.SearchDTO;
import com.bis.sourcelead.payroll.entity.EmployeePayrollTax;


@SuppressWarnings("serial")
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
	
	private String ssnNumber;
	
	private String candidateWorkAddress;
	
	private String fedMaritalStat;
	
	private String stateMaritalStat;
	
	private String fedExempt;
	
	private String stateExempt;
	
	private String fedAddTax;
	
	private String stateAddTax;
	
	private String resState;
	
	private String workState;
	
	private String dateOfJoin;
	
	private String empStatus;

	private Date checkDate;
	
	private String groupName;
	
	private String groupFrequency;
	
	private String type;
	
	private Date runDate;
	
	private BigDecimal contributionMTD;
	
	private BigDecimal contributionQTD;
	
	private BigDecimal deductionMTD;
	
	private BigDecimal deductionQTD;
	
	private String ytdHours;
	
	private BigDecimal empRegularAmount;
	
	private List<PayrollReportDTO> deductionsList;
	
	private Set<EmployeePayrollTax> taxHistory;
	
	private YTDPayrollDTO ytdPayrollDTO;
	
	private BigDecimal federalTax;
	
	private BigDecimal socialSecTax;
	
	private BigDecimal medicare;
	
	private BigDecimal futa;
	
	private BigDecimal stateTax;
	
	private BigDecimal localTax;
	
	private BigDecimal suta;
	
	private String fromDate;
	
	private String toDate;
	
	private String employees;
	
	private String payrollDepartment;
	
	private String exportType;
	
	private String assAmount;
	
	private String totalVendorAmount;
	
	private String vendorName;
	
	private String invoiceId;
	
	private String invPaidAmount;
	
	private String orgDateFormat;
	
	private String runNo;
	
	private BigDecimal current;

	public String getEmpId()
	{
		return empId;
	}

	public void setEmpId(String empId)
	{
		this.empId = empId;
	}

	public String getEmpName()
	{
		return empName;
	}

	public void setEmpName(String empName)
	{
		this.empName = empName;
	}

	public BigDecimal getEmpPayRate()
	{
		return empPayRate;
	}

	public void setEmpPayRate(BigDecimal empPayRate)
	{
		this.empPayRate = empPayRate;
	}

	public String getRegularHours()
	{
		return regularHours;
	}

	public void setRegularHours(String regularHours)
	{
		this.regularHours = regularHours;
	}

	public String getOverTimeHours()
	{
		return overTimeHours;
	}

	public void setOverTimeHours(String overTimeHours)
	{
		this.overTimeHours = overTimeHours;
	}

	public BigDecimal getPreTaxDeduction()
	{
		return preTaxDeduction;
	}

	public void setPreTaxDeduction(BigDecimal preTaxDeduction)
	{
		this.preTaxDeduction = preTaxDeduction;
	}

	public BigDecimal getPostTaxDeduction()
	{
		return postTaxDeduction;
	}

	public void setPostTaxDeduction(BigDecimal postTaxDeduction)
	{
		this.postTaxDeduction = postTaxDeduction;
	}

	public BigDecimal getTaxes()
	{
		return taxes;
	}

	public void setTaxes(BigDecimal taxes)
	{
		this.taxes = taxes;
	}

	public BigDecimal getNetPay()
	{
		return netPay;
	}

	public void setNetPay(BigDecimal netPay)
	{
		this.netPay = netPay;
	}

	public BigDecimal getGrossPay()
	{
		return grossPay;
	}

	public void setGrossPay(BigDecimal grossPay)
	{
		this.grossPay = grossPay;
	}

	public BigDecimal getLastPay()
	{
		return lastPay;
	}

	public void setLastPay(BigDecimal lastPay)
	{
		this.lastPay = lastPay;
	}

	public BigDecimal getYtdNet()
	{
		return ytdNet;
	}

	public void setYtdNet(BigDecimal ytdNet)
	{
		this.ytdNet = ytdNet;
	}

	public String getAction()
	{
		return action;
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

	public void setGroupId(String groupId)
	{
		this.groupId = groupId;
	}

	public String getPayCheckDate()
	{
		return payCheckDate;
	}

	public void setPayCheckDate(String payCheckDate)
	{
		this.payCheckDate = payCheckDate;
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

	public String getDepositType()
	{
		return depositType;
	}

	public void setDepositType(String depositType)
	{
		this.depositType = depositType;
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

	public String getPayrollUUID()
	{
		return payrollUUID;
	}

	public void setPayrollUUID(String payrollUUID)
	{
		this.payrollUUID = payrollUUID;
	}

	public String getContributionId()
	{
		return contributionId;
	}

	public void setContributionId(String contributionId)
	{
		this.contributionId = contributionId;
	}

	public String getContributionType()
	{
		return contributionType;
	}

	public void setContributionType(String contributionType)
	{
		this.contributionType = contributionType;
	}

	public String getSchedule()
	{
		return schedule;
	}

	public void setSchedule(String schedule)
	{
		this.schedule = schedule;
	}

	public BigDecimal getDedAmount()
	{
		return dedAmount;
	}

	public void setDedAmount(BigDecimal dedAmount)
	{
		this.dedAmount = dedAmount;
	}

	public BigDecimal getContributionYTD()
	{
		return contributionYTD;
	}

	public void setContributionYTD(BigDecimal contributionYTD)
	{
		this.contributionYTD = contributionYTD;
	}

	public String getDeductionId()
	{
		return deductionId;
	}

	public void setDeductionId(String deductionId)
	{
		this.deductionId = deductionId;
	}

	public String getDeductionType()
	{
		return deductionType;
	}

	public void setDeductionType(String deductionType)
	{
		this.deductionType = deductionType;
	}

	public BigDecimal getContributionAmount()
	{
		return contributionAmount;
	}

	public void setContributionAmount(BigDecimal contributionAmount)
	{
		this.contributionAmount = contributionAmount;
	}

	public BigDecimal getDeductionYTD()
	{
		return deductionYTD;
	}

	public void setDeductionYTD(BigDecimal deductionYTD)
	{
		this.deductionYTD = deductionYTD;
	}

	public String getSsnNumber()
	{
		return ssnNumber;
	}

	public void setSsnNumber(String ssnNumber)
	{
		this.ssnNumber = ssnNumber;
	}

	public String getCandidateWorkAddress()
	{
		return candidateWorkAddress;
	}

	public void setCandidateWorkAddress(String candidateWorkAddress)
	{
		this.candidateWorkAddress = candidateWorkAddress;
	}

	public String getFedMaritalStat()
	{
		return fedMaritalStat;
	}

	public void setFedMaritalStat(String fedMaritalStat)
	{
		this.fedMaritalStat = fedMaritalStat;
	}

	public String getStateMaritalStat()
	{
		return stateMaritalStat;
	}

	public void setStateMaritalStat(String stateMaritalStat)
	{
		this.stateMaritalStat = stateMaritalStat;
	}

	public String getFedExempt()
	{
		return fedExempt;
	}

	public void setFedExempt(String fedExempt)
	{
		this.fedExempt = fedExempt;
	}

	public String getStateExempt()
	{
		return stateExempt;
	}

	public void setStateExempt(String stateExempt)
	{
		this.stateExempt = stateExempt;
	}

	public String getFedAddTax()
	{
		return fedAddTax;
	}

	public void setFedAddTax(String fedAddTax)
	{
		this.fedAddTax = fedAddTax;
	}

	public String getStateAddTax()
	{
		return stateAddTax;
	}

	public void setStateAddTax(String stateAddTax)
	{
		this.stateAddTax = stateAddTax;
	}

	public String getResState()
	{
		return resState;
	}

	public void setResState(String resState)
	{
		this.resState = resState;
	}

	public String getWorkState()
	{
		return workState;
	}

	public void setWorkState(String workState)
	{
		this.workState = workState;
	}

	public String getDateOfJoin()
	{
		return dateOfJoin;
	}

	public void setDateOfJoin(String dateOfJoin)
	{
		this.dateOfJoin = dateOfJoin;
	}

	public String getEmpStatus()
	{
		return empStatus;
	}

	public void setEmpStatus(String empStatus)
	{
		this.empStatus = empStatus;
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

	public BigDecimal getContributionMTD()
	{
		return contributionMTD;
	}

	public void setContributionMTD(BigDecimal contributionMTD)
	{
		this.contributionMTD = contributionMTD;
	}

	public BigDecimal getContributionQTD()
	{
		return contributionQTD;
	}

	public void setContributionQTD(BigDecimal contributionQTD)
	{
		this.contributionQTD = contributionQTD;
	}

	public BigDecimal getDeductionMTD()
	{
		return deductionMTD;
	}

	public void setDeductionMTD(BigDecimal deductionMTD)
	{
		this.deductionMTD = deductionMTD;
	}

	public BigDecimal getDeductionQTD()
	{
		return deductionQTD;
	}

	public void setDeductionQTD(BigDecimal deductionQTD)
	{
		this.deductionQTD = deductionQTD;
	}

	public String getYtdHours()
	{
		return ytdHours;
	}

	public void setYtdHours(String ytdHours)
	{
		this.ytdHours = ytdHours;
	}

	public BigDecimal getEmpRegularAmount()
	{
		return empRegularAmount;
	}

	public void setEmpRegularAmount(BigDecimal empRegularAmount)
	{
		this.empRegularAmount = empRegularAmount;
	}

	public List<PayrollReportDTO> getDeductionsList()
	{
		return deductionsList;
	}

	public void setDeductionsList(List<PayrollReportDTO> deductionsList)
	{
		this.deductionsList = deductionsList;
	}

	public Set<EmployeePayrollTax> getTaxHistory()
	{
		return taxHistory;
	}

	public void setTaxHistory(Set<EmployeePayrollTax> taxHistory)
	{
		this.taxHistory = taxHistory;
	}

	public YTDPayrollDTO getYtdPayrollDTO()
	{
		return ytdPayrollDTO;
	}

	public void setYtdPayrollDTO(YTDPayrollDTO ytdPayrollDTO)
	{
		this.ytdPayrollDTO = ytdPayrollDTO;
	}

	public BigDecimal getFederalTax()
	{
		return federalTax;
	}

	public void setFederalTax(BigDecimal federalTax)
	{
		this.federalTax = federalTax;
	}

	public BigDecimal getSocialSecTax()
	{
		return socialSecTax;
	}

	public void setSocialSecTax(BigDecimal socialSecTax)
	{
		this.socialSecTax = socialSecTax;
	}

	public BigDecimal getMedicare()
	{
		return medicare;
	}

	public void setMedicare(BigDecimal medicare)
	{
		this.medicare = medicare;
	}

	public BigDecimal getFuta()
	{
		return futa;
	}

	public void setFuta(BigDecimal futa)
	{
		this.futa = futa;
	}

	public BigDecimal getStateTax()
	{
		return stateTax;
	}

	public void setStateTax(BigDecimal stateTax)
	{
		this.stateTax = stateTax;
	}

	public BigDecimal getLocalTax()
	{
		return localTax;
	}

	public void setLocalTax(BigDecimal localTax)
	{
		this.localTax = localTax;
	}

	public BigDecimal getSuta()
	{
		return suta;
	}

	public void setSuta(BigDecimal suta)
	{
		this.suta = suta;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getEmployees() {
		return employees;
	}

	public void setEmployees(String employees) {
		this.employees = employees;
	}

	public String getPayrollDepartment() {
		return payrollDepartment;
	}

	public void setPayrollDepartment(String payrollDepartment) {
		this.payrollDepartment = payrollDepartment;
	}

	public String getExportType() {
		return exportType;
	}

	public void setExportType(String exportType) {
		this.exportType = exportType;
	}

	public String getAssAmount()
	{
		return assAmount;
	}

	public void setAssAmount(String assAmount)
	{
		this.assAmount = assAmount;
	}

	public String getTotalVendorAmount()
	{
		return totalVendorAmount;
	}

	public void setTotalVendorAmount(String totalVendorAmount)
	{
		this.totalVendorAmount = totalVendorAmount;
	}

	public String getVendorName()
	{
		return vendorName;
	}

	public void setVendorName(String vendorName)
	{
		this.vendorName = vendorName;
	}

	public String getInvoiceId()
	{
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId)
	{
		this.invoiceId = invoiceId;
	}

	public String getInvPaidAmount()
	{
		return invPaidAmount;
	}

	public void setInvPaidAmount(String invPaidAmount)
	{
		this.invPaidAmount = invPaidAmount;
	}

	public String getOrgDateFormat() {
		return orgDateFormat;
	}

	public void setOrgDateFormat(String orgDateFormat) {
		this.orgDateFormat = orgDateFormat;
	}

	public String getRunNo()
	{
		return runNo;
	}

	public void setRunNo(String runNo)
	{
		this.runNo = runNo;
	}

	public BigDecimal getCurrent()
	{
		return current;
	}

	public void setCurrent(BigDecimal current)
	{
		this.current = current;
	}

}