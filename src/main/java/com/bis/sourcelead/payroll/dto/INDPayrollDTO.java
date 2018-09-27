package com.bis.sourcelead.payroll.dto;

import java.math.BigDecimal;

public class INDPayrollDTO {
	
	private Long empPayrollId;
	
	private String empId;
	
	private String empName;
	
	private double regularHours;	
	
	private double overtimeHours;
	
	private BigDecimal grossPay;
	
	private BigDecimal netPay;
	
	private BigDecimal regularHrsTotalAmt;	
	
	private BigDecimal otHoursTotalAmt;
	
	private BigDecimal payRate;
	
	private String empPayrollUUID;
	
	private String employeeAddress;
	
    private double totalHours;
	
	private Long userId;
	
	private Long paySheetFrequencyId;
	
	private Long candidateId;
	
	private String approverStatus;
	
	private String employeeDesignationType;

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

	public double getRegularHours() {
		return regularHours;
	}

	public void setRegularHours(double regularHours) {
		this.regularHours = regularHours;
	}

	public double getOvertimeHours() {
		return overtimeHours;
	}

	public void setOvertimeHours(double overtimeHours) {
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

	public BigDecimal getRegularHrsTotalAmt() {
		return regularHrsTotalAmt;
	}

	public void setRegularHrsTotalAmt(BigDecimal regularHrsTotalAmt) {
		this.regularHrsTotalAmt = regularHrsTotalAmt;
	}

	public BigDecimal getOtHoursTotalAmt() {
		return otHoursTotalAmt;
	}

	public void setOtHoursTotalAmt(BigDecimal otHoursTotalAmt) {
		this.otHoursTotalAmt = otHoursTotalAmt;
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
	
	
	
	
}
