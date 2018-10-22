package com.bis.sourcelead.payroll.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.bis.sourcelead.dto.SearchDTO;

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
}
