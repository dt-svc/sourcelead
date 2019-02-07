package com.bis.sourcelead.indPayroll.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.bis.sourcelead.dto.AssociateLoanAndAdvancesHistoryDTO;
import com.bis.sourcelead.payroll.dto.AssociatePayrollPayheadHistoryDTO;

public class INDPayrollDTO {
	private Long id;
	
	private Long empPayrollId;
	
	private String empId;
	
	private Long employee;

	private String empName;
	
	private String regularHours;	
	
	private String overtimeHours;
	
	private BigDecimal grossPay;
	
	private BigDecimal netPay;
	
	private BigDecimal lopAmount;
	
	private BigDecimal regularHrsAmt;
	
	private BigDecimal otHoursAmt;
	
	private BigDecimal noOfLeaves;
	
	private BigDecimal noOfDays;
	
	private BigDecimal payRate;
	
	private String empPayrollUUID;
	
	private String employeeAddress;
	
    private double totalHours;
	
	private Long userId;
	
	private Long paySheetFrequencyId;
	
	private Long candidateId;
	
	private String approverStatus;
	
	private String employeeDesignationType;
	
	private BigDecimal tds;
	
	private BigDecimal professionalTax;
	
	private String payType; 
	
	 private BigDecimal payPerSalary;
	 
	 private String indEmpPayrollUUID;
	 
	 private String paySheetFrequencyUUID;

	 private Date payCheckDate;
		
	 private Date createdDate;
	
	 private Long createdBy;
	
	 private Date updatedDate;
	
 	 private Long updatedBy;
	 
	 List<AssociatePayrollPayheadHistoryDTO> payHeadHistoryDTOList;
	 
	 List<AssociateLoanAndAdvancesHistoryDTO> loanAdvHistoryDTOList;
	 
	 private Long payrollPaySlipInd;
	 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEmpPayrollId() {
		return empPayrollId;
	}

	public void setEmpPayrollId(Long empPayrollId) {
		this.empPayrollId = empPayrollId;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getRegularHours() {
		return regularHours;
	}

	public void setRegularHours(String regularHours) {
		this.regularHours = regularHours;
	}

	public String getOvertimeHours() {
		return overtimeHours;
	}

	public void setOvertimeHours(String overtimeHours) {
		this.overtimeHours = overtimeHours;
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

	public BigDecimal getLopAmount() {
		return lopAmount;
	}

	public void setLopAmount(BigDecimal lopAmount) {
		this.lopAmount = lopAmount;
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

	public BigDecimal getPayRate() {
		return payRate;
	}

	public void setPayRate(BigDecimal payRate) {
		this.payRate = payRate;
	}

	public String getEmpPayrollUUID() {
		return empPayrollUUID;
	}

	public void setEmpPayrollUUID(String empPayrollUUID) {
		this.empPayrollUUID = empPayrollUUID;
	}

	public String getEmployeeAddress() {
		return employeeAddress;
	}

	public void setEmployeeAddress(String employeeAddress) {
		this.employeeAddress = employeeAddress;
	}

	public double getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(double totalHours) {
		this.totalHours = totalHours;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getPaySheetFrequencyId() {
		return paySheetFrequencyId;
	}

	public void setPaySheetFrequencyId(Long paySheetFrequencyId) {
		this.paySheetFrequencyId = paySheetFrequencyId;
	}

	public Long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}

	public String getApproverStatus() {
		return approverStatus;
	}

	public void setApproverStatus(String approverStatus) {
		this.approverStatus = approverStatus;
	}

	public String getEmployeeDesignationType() {
		return employeeDesignationType;
	}

	public void setEmployeeDesignationType(String employeeDesignationType) {
		this.employeeDesignationType = employeeDesignationType;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public BigDecimal getRegularHrsAmt() {
		return regularHrsAmt;
	}

	public void setRegularHrsAmt(BigDecimal regularHrsAmt) {
		this.regularHrsAmt = regularHrsAmt;
	}

	public BigDecimal getOtHoursAmt() {
		return otHoursAmt;
	}

	public void setOtHoursAmt(BigDecimal otHoursAmt) {
		this.otHoursAmt = otHoursAmt;
	}

	public BigDecimal getTds() {
		return tds;
	}

	public void setTds(BigDecimal tds) {
		this.tds = tds;
	}

	public BigDecimal getProfessionalTax() {
		return professionalTax;
	}

	public void setProfessionalTax(BigDecimal professionalTax) {
		this.professionalTax = professionalTax;
	}

	public BigDecimal getPayPerSalary() {
		return payPerSalary;
	}

	public void setPayPerSalary(BigDecimal payPerSalary) {
		this.payPerSalary = payPerSalary;
	}

	public String getIndEmpPayrollUUID() {
		return indEmpPayrollUUID;
	}

	public void setIndEmpPayrollUUID(String indEmpPayrollUUID) {
		this.indEmpPayrollUUID = indEmpPayrollUUID;
	}

	public String getPaySheetFrequencyUUID() {
		return paySheetFrequencyUUID;
	}

	public void setPaySheetFrequencyUUID(String paySheetFrequencyUUID) {
		this.paySheetFrequencyUUID = paySheetFrequencyUUID;
	}

	public List<AssociatePayrollPayheadHistoryDTO> getPayHeadHistoryDTOList() {
		return payHeadHistoryDTOList;
	}

	public void setPayHeadHistoryDTOList(List<AssociatePayrollPayheadHistoryDTO> payHeadHistoryDTOList) {
		this.payHeadHistoryDTOList = payHeadHistoryDTOList;
	}

	public List<AssociateLoanAndAdvancesHistoryDTO> getLoanAdvHistoryDTOList() {
		return loanAdvHistoryDTOList;
	}

	public void setLoanAdvHistoryDTOList(List<AssociateLoanAndAdvancesHistoryDTO> loanAdvHistoryDTOList) {
		this.loanAdvHistoryDTOList = loanAdvHistoryDTOList;
	}

	public Long getEmployee() {
		return employee;
	}

	public void setEmployee(Long employee) {
		this.employee = employee;
	}

	
	public Date getPayCheckDate() {
		return payCheckDate;
	}

	public void setPayCheckDate(Date payCheckDate) {
		this.payCheckDate = payCheckDate;
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

	public Long getPayrollPaySlipInd() {
		return payrollPaySlipInd;
	}

	public void setPayrollPaySlipInd(Long payrollPaySlipInd) {
		this.payrollPaySlipInd = payrollPaySlipInd;
	}
	
	
}
