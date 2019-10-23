package com.bis.sourcelead.indPayroll.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.dto.SearchDTO;
import com.bis.sourcelead.payroll.dto.AssociatePayrollPayheadHistoryDTO;

public class IndEmployeePayrollDTO  extends SearchDTO{
	
	private static final long serialVersionUID = 1L;

	private Long id;

	private Long employeeId;

	private Long candidateId;

	private Long indPaysheetFrequencyId;
	
	private String indEmpPayrollUUID;

	private BigDecimal regularHoursAmount;

	private String regularHours;

	private BigDecimal overTimeAmount;

	private String overTimeHours;

	private BigDecimal noOfLeaves;
	
	private BigDecimal noOfDays;
	
	private Date payCheckDate;
	
	private BigDecimal empPayRate;
	
	private String payType; 
	 
    private BigDecimal payPerSalary;
	
	private BigDecimal grossPay;

	private BigDecimal netPay;

	private BigDecimal professionalTax;
	
	private BigDecimal tds;

	private String submitForApproverStatus;
	
	private String type;
	
	private Date createdDate;
	
	private Long createdBy;
	
	private Date updatedDate;
	
	private Long updatedBy;
	
	private String payPeriod;
	
	private String payCheckDateStr;
	
	private List<AssociatePayrollPayheadHistoryDTO> associateSalaryPayheadIndEarningList;
	
	private List<AssociatePayrollPayheadHistoryDTO> associateSalaryPayheadIndDeductionList;
	
	private String paySheetFrequencyUUID;
	
	private List<String> indEmpPayrollUUIDList;
	 
	private String allEmployees;
	
	private Long payGroup;

	private String department;
	
	private String payRollType;
	
    private String freqStartDate;
	
	private String freqEndDate;
	
	private String searchValue;

	private String employeeName;
	
	private String employeeid;
	
	private String departmentName;
	
	private BigDecimal currentHours;
	
	private BigDecimal ytdTaxes;
	
	private BigDecimal deducationAmount;
	
	private BigDecimal ytdNetPay;
	
	private String accountNumber;
	
	private String bankName;
	
	private String ifscCode;
	
	private String exportType;
	
	private String employeeIds;
	
	private String departmentId;
	
	private String startDate;
	
	private String endDate;
	
	private BigDecimal deducations;
	
	private BigDecimal noOfWorkingDays;
	
	private String financialYear;
	
	//Salary statement related
	private Date deteOfJoin;
	
	private String firstName;
	
	private String lastName;
	
	private String middleName;

	private String payHeadName;
	
	private BigDecimal payHeadAmount;
	
	private String salaryMonthName;
	
	private BigInteger canId;
	
	private String payHeadType;

	private Map<String,String> payHeadMapList;
	
	//ESI and PF related
	private String toMonth;
	
	private String fromMonth;
	
	private BigInteger payrollPayheadId;
	
	private String shortCode;
	
	private String uanNumber;
	
	private String pfNumber;
	
	private String esiNumber;
	
	private String eligibleForEsi;
	
	private String eligibleForPf;
	
	private Double employeeEsi;
	
	private Double employerEsi;
	
	private Double employeePf;
	
	private Double employerPf;
	
	private Double totalESI;
	
	private Double totalPF;
	
	//Salary Register
	private Date effectiveDate;
	
	private String incresePercentage;
	
	private BigDecimal annualSalary;
	
	private BigDecimal totalCtc;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}

	public Long getIndPaysheetFrequencyId() {
		return indPaysheetFrequencyId;
	}

	public void setIndPaysheetFrequencyId(Long indPaysheetFrequencyId) {
		this.indPaysheetFrequencyId = indPaysheetFrequencyId;
	}

	public String getIndEmpPayrollUUID() {
		return indEmpPayrollUUID;
	}

	public void setIndEmpPayrollUUID(String indEmpPayrollUUID) {
		this.indEmpPayrollUUID = indEmpPayrollUUID;
	}

	public BigDecimal getRegularHoursAmount() {
		return regularHoursAmount;
	}

	public void setRegularHoursAmount(BigDecimal regularHoursAmount) {
		this.regularHoursAmount = regularHoursAmount;
	}

	public String getRegularHours() {
		return regularHours;
	}

	public void setRegularHours(String regularHours) {
		this.regularHours = regularHours;
	}

	public BigDecimal getOverTimeAmount() {
		return overTimeAmount;
	}

	public void setOverTimeAmount(BigDecimal overTimeAmount) {
		this.overTimeAmount = overTimeAmount;
	}

	public String getOverTimeHours() {
		return overTimeHours;
	}

	public void setOverTimeHours(String overTimeHours) {
		this.overTimeHours = overTimeHours;
	}

	public BigDecimal getNoOfLeaves() {
		return noOfLeaves;
	}

	public void setNoOfLeaves(BigDecimal noOfLeaves) {
		this.noOfLeaves = noOfLeaves;
	}

	public BigDecimal getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(BigDecimal noOfDays) {
		this.noOfDays = noOfDays;
	}

	public Date getPayCheckDate() {
		return payCheckDate;
	}

	public void setPayCheckDate(Date payCheckDate) {
		this.payCheckDate = payCheckDate;
	}

	public BigDecimal getEmpPayRate() {
		return empPayRate;
	}

	public void setEmpPayRate(BigDecimal empPayRate) {
		this.empPayRate = empPayRate;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public BigDecimal getPayPerSalary() {
		return payPerSalary;
	}

	public void setPayPerSalary(BigDecimal payPerSalary) {
		this.payPerSalary = payPerSalary;
	}

	public BigDecimal getGrossPay() {
		return grossPay;
	}

	public void setGrossPay(BigDecimal grossPay) {
		this.grossPay = grossPay;
	}

	public BigDecimal getNetPay() {
		return netPay;
	}

	public void setNetPay(BigDecimal netPay) {
		this.netPay = netPay;
	}

	public BigDecimal getProfessionalTax() {
		return professionalTax;
	}

	public void setProfessionalTax(BigDecimal professionalTax) {
		this.professionalTax = professionalTax;
	}

	public BigDecimal getTds() {
		return tds;
	}

	public void setTds(BigDecimal tds) {
		this.tds = tds;
	}

	public String getSubmitForApproverStatus() {
		return submitForApproverStatus;
	}

	public void setSubmitForApproverStatus(String submitForApproverStatus) {
		this.submitForApproverStatus = submitForApproverStatus;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getPayPeriod() {
		return payPeriod;
	}

	public void setPayPeriod(String payPeriod) {
		this.payPeriod = payPeriod;
	}

	public String getPayCheckDateStr() {
		return payCheckDateStr;
	}

	public void setPayCheckDateStr(String payCheckDateStr) {
		this.payCheckDateStr = payCheckDateStr;
	}

	public List<AssociatePayrollPayheadHistoryDTO> getAssociateSalaryPayheadIndEarningList() {
		return associateSalaryPayheadIndEarningList;
	}

	public void setAssociateSalaryPayheadIndEarningList(
			List<AssociatePayrollPayheadHistoryDTO> associateSalaryPayheadIndEarningList) {
		this.associateSalaryPayheadIndEarningList = associateSalaryPayheadIndEarningList;
	}

	public List<AssociatePayrollPayheadHistoryDTO> getAssociateSalaryPayheadIndDeductionList() {
		return associateSalaryPayheadIndDeductionList;
	}

	public void setAssociateSalaryPayheadIndDeductionList(
			List<AssociatePayrollPayheadHistoryDTO> associateSalaryPayheadIndDeductionList) {
		this.associateSalaryPayheadIndDeductionList = associateSalaryPayheadIndDeductionList;
	}

	public String getPaySheetFrequencyUUID() {
		return paySheetFrequencyUUID;
	}

	public void setPaySheetFrequencyUUID(String paySheetFrequencyUUID) {
		this.paySheetFrequencyUUID = paySheetFrequencyUUID;
	}

	public List<String> getIndEmpPayrollUUIDList() {
		return indEmpPayrollUUIDList;
	}

	public void setIndEmpPayrollUUIDList(List<String> indEmpPayrollUUIDList) {
		this.indEmpPayrollUUIDList = indEmpPayrollUUIDList;
	}

	public String getAllEmployees() {
		return allEmployees;
	}

	public void setAllEmployees(String allEmployees) {
		this.allEmployees = allEmployees;
	}

	public Long getPayGroup() {
		return payGroup;
	}

	public String getDepartment() {
		return department;
	}

	public String getPayRollType() {
		return payRollType;
	}

	public String getFreqStartDate() {
		return freqStartDate;
	}

	public String getFreqEndDate() {
		return freqEndDate;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setPayGroup(Long payGroup) {
		this.payGroup = payGroup;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public void setPayRollType(String payRollType) {
		this.payRollType = payRollType;
	}

	public void setFreqStartDate(String freqStartDate) {
		this.freqStartDate = freqStartDate;
	}

	public void setFreqEndDate(String freqEndDate) {
		this.freqEndDate = freqEndDate;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public String getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public BigDecimal getCurrentHours() {
		return currentHours;
	}

	public BigDecimal getYtdTaxes() {
		return ytdTaxes;
	}

	public BigDecimal getDeducationAmount() {
		return deducationAmount;
	}

	public BigDecimal getYtdNetPay() {
		return ytdNetPay;
	}

	public void setCurrentHours(BigDecimal currentHours) {
		this.currentHours = currentHours;
	}

	public void setYtdTaxes(BigDecimal ytdTaxes) {
		this.ytdTaxes = ytdTaxes;
	}

	public void setDeducationAmount(BigDecimal deducationAmount) {
		this.deducationAmount = deducationAmount;
	}

	public void setYtdNetPay(BigDecimal ytdNetPay) {
		this.ytdNetPay = ytdNetPay;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public String getBankName() {
		return bankName;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getExportType() {
		return exportType;
	}

	public void setExportType(String exportType) {
		this.exportType = exportType;
	}

	public String getEmployeeIds() {
		return employeeIds;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEmployeeIds(String employeeIds) {
		this.employeeIds = employeeIds;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getDeducations() {
		return deducations;
	}

	public void setDeducations(BigDecimal deducations) {
		this.deducations = deducations;
	}

	public BigDecimal getNoOfWorkingDays() {
		return noOfWorkingDays;
	}

	public void setNoOfWorkingDays(BigDecimal noOfWorkingDays) {
		this.noOfWorkingDays = noOfWorkingDays;
	}

	public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	public Date getDeteOfJoin() {
		return deteOfJoin;
	}

	public void setDeteOfJoin(Date deteOfJoin) {
		this.deteOfJoin = deteOfJoin;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPayHeadName() {
		return payHeadName;
	}

	public BigDecimal getPayHeadAmount() {
		return payHeadAmount;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPayHeadName(String payHeadName) {
		this.payHeadName = payHeadName;
	}

	public void setPayHeadAmount(BigDecimal payHeadAmount) {
		this.payHeadAmount = payHeadAmount;
	}

	public String getSalaryMonthName() {
		return salaryMonthName;
	}

	public void setSalaryMonthName(String salaryMonthName) {
		this.salaryMonthName = salaryMonthName;
	}

	public BigInteger getCanId() {
		return canId;
	}

	public void setCanId(BigInteger canId) {
		this.canId = canId;
	}

	public Map<String, String> getPayHeadMapList() {
		return payHeadMapList;
	}

	public void setPayHeadMapList(Map<String, String> payHeadMapList) {
		this.payHeadMapList = payHeadMapList;
	}

	public String getPayHeadType() {
		return payHeadType;
	}

	public void setPayHeadType(String payHeadType) {
		this.payHeadType = payHeadType;
	}

	public String getToMonth() {
		return toMonth;
	}

	public String getFromMonth() {
		return fromMonth;
	}

	public void setToMonth(String toMonth) {
		this.toMonth = toMonth;
	}

	public void setFromMonth(String fromMonth) {
		this.fromMonth = fromMonth;
	}

	public BigInteger getPayrollPayheadId() {
		return payrollPayheadId;
	}

	public String getShortCode() {
		return shortCode;
	}

	public String getUanNumber() {
		return uanNumber;
	}

	public String getPfNumber() {
		return pfNumber;
	}

	public String getEsiNumber() {
		return esiNumber;
	}

	public String getEligibleForEsi() {
		return eligibleForEsi;
	}

	public String getEligibleForPf() {
		return eligibleForPf;
	}

	public Double getEmployerEsi() {
		return employerEsi;
	}

	public Double getEmployerPf() {
		return employerPf;
	}

	public void setPayrollPayheadId(BigInteger payrollPayheadId) {
		this.payrollPayheadId = payrollPayheadId;
	}

	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

	public void setUanNumber(String uanNumber) {
		this.uanNumber = uanNumber;
	}

	public void setPfNumber(String pfNumber) {
		this.pfNumber = pfNumber;
	}

	public void setEsiNumber(String esiNumber) {
		this.esiNumber = esiNumber;
	}

	public void setEligibleForEsi(String eligibleForEsi) {
		this.eligibleForEsi = eligibleForEsi;
	}

	public void setEligibleForPf(String eligibleForPf) {
		this.eligibleForPf = eligibleForPf;
	}

	public void setEmployeeEsi(Double employeeEsi) {
		this.employeeEsi = employeeEsi;
	}

	public void setEmployerEsi(Double employerEsi) {
		this.employerEsi = employerEsi;
	}

	public void setEmployeePf(Double employeePf) {
		this.employeePf = employeePf;
	}

	public void setEmployerPf(Double employerPf) {
		this.employerPf = employerPf;
	}

	public Double getTotalESI() {
		return totalESI;
	}

	public Double getTotalPF() {
		return totalPF;
	}

	public void setTotalESI(Double totalESI) {
		this.totalESI = totalESI;
	}

	public void setTotalPF(Double totalPF) {
		this.totalPF = totalPF;
	}

	public Double getEmployeeEsi() {
		return employeeEsi;
	}

	public Double getEmployeePf() {
		return employeePf;
	}


	public String getIncresePercentage() {
		return incresePercentage;
	}

	public BigDecimal getAnnualSalary() {
		return annualSalary;
	}

	 

	public void setIncresePercentage(String incresePercentage) {
		this.incresePercentage = incresePercentage;
	}

	public void setAnnualSalary(BigDecimal annualSalary) {
		this.annualSalary = annualSalary;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public BigDecimal getTotalCtc() {
		return totalCtc;
	}

	public void setTotalCtc(BigDecimal totalCtc) {
		this.totalCtc = totalCtc;
	}
	
}
