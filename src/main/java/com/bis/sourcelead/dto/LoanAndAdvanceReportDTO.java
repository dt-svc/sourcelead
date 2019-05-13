package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import com.bis.sourcelead.annotations.Required;

public class LoanAndAdvanceReportDTO extends SearchDTO implements Serializable{
	
	 private static final long serialVersionUID = 1L;
	 
	 @Required(desc="loanAndAdvType")
	 private String loanAndAdvType;
	 
	 @Required(desc="loanAndAdvStatus")
	 private String loanAndAdvStatus;
	 
     private BigDecimal outstandingAmount;
     
     private Double rateOfInterest;
     
     private String firstName;
     
     private String lastName;
     
     private String employeeId;
     
     private String empType;
     
     private Date startDate;
     
     private Date loanEndDate;
     
     private Double loanTotalAmount;
     
     private Double loanPaidAmount;
     
     private Double loanPendingAmount;
     
     private Double loanDeductAmount;
     
     private int loanNoOfSchedules;
     
     private String loanName;
     
     private Long id;
     
     private String searchValue;
     
     private String fromDate;
     
     private String toDate;
     
     private BigInteger filterCount;
     
     private String exportType;
     
	public String getExportType() {
		return exportType;
	}

	public void setExportType(String exportType) {
		this.exportType = exportType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getLoanAndAdvType() {
		return loanAndAdvType;
	}

	public String getLoanAndAdvStatus() {
		return loanAndAdvStatus;
	}

	public BigDecimal getOutstandingAmount() {
		return outstandingAmount;
	}

	public Double getRateOfInterest() {
		return rateOfInterest;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public String getEmpType() {
		return empType;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getLoanEndDate() {
		return loanEndDate;
	}

	public Double getLoanTotalAmount() {
		return loanTotalAmount;
	}

	public Double getLoanPaidAmount() {
		return loanPaidAmount;
	}

	public Double getLoanPendingAmount() {
		return loanPendingAmount;
	}

	public Double getLoanDeductAmount() {
		return loanDeductAmount;
	}

	public int getLoanNoOfSchedules() {
		return loanNoOfSchedules;
	}

	public String getLoanName() {
		return loanName;
	}

	public Long getId() {
		return id;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public String getFromDate() {
		return fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setLoanAndAdvType(String loanAndAdvType) {
		this.loanAndAdvType = loanAndAdvType;
	}

	public void setLoanAndAdvStatus(String loanAndAdvStatus) {
		this.loanAndAdvStatus = loanAndAdvStatus;
	}

	public void setOutstandingAmount(BigDecimal outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
	}

	public void setRateOfInterest(Double rateOfInterest) {
		this.rateOfInterest = rateOfInterest;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public void setEmpType(String empType) {
		this.empType = empType;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setLoanEndDate(Date loanEndDate) {
		this.loanEndDate = loanEndDate;
	}

	public void setLoanTotalAmount(Double loanTotalAmount) {
		this.loanTotalAmount = loanTotalAmount;
	}

	public void setLoanPaidAmount(Double loanPaidAmount) {
		this.loanPaidAmount = loanPaidAmount;
	}

	public void setLoanPendingAmount(Double loanPendingAmount) {
		this.loanPendingAmount = loanPendingAmount;
	}

	public void setLoanDeductAmount(Double loanDeductAmount) {
		this.loanDeductAmount = loanDeductAmount;
	}

	public void setLoanNoOfSchedules(int loanNoOfSchedules) {
		this.loanNoOfSchedules = loanNoOfSchedules;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public BigInteger getFilterCount() {
		return filterCount;
	}

	public void setFilterCount(BigInteger filterCount) {
		this.filterCount = filterCount;
	}

     
}
