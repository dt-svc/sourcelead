package com.bis.sourcelead.payroll.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bis.sourcelead.dto.AssociateLoanAndAdvancesHistoryDTO;
import com.bis.sourcelead.payroll.dto.AssociatePayrollPayheadHistoryDTO;

@Entity
@Table(name = "IND_PAYROLL_CACHE")
public class EmployeeSessionIndPayroll {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name= "EMPLOYEE_ID")
	private String employeeId;
	
	@Column(name ="EMPLOYE_NAME")
	private String employeeName;
	
	@Column(name ="REGULAR_HOURS")
	private String regularHours;
	
	@Column(name="OT_HOURS")
	private String overtimeHours;
	
	@Column(name="NO_OF_LEAVES")
	private BigDecimal noOfLeaves;
	
	@Column(name="GROSS_PAY")
	private BigDecimal grossPay;
	
	@Column(name="TDS")
	private BigDecimal tds;
	
	@Column(name="NET_PAY")
	private BigDecimal netPay;
	
	@Column(name="EXCLUDE_UUID")
	private String excludeUUID;

	@Column(name="PAYSHEET_UUID")
	private String paysheetUUID;
	
	@Column(name="JSON_STRING")
	private String jsonString;
	
	@Column(name="JSON_PAYHEADHISTORY", columnDefinition = "LONGTEXT default NULL")
	private String jsonPayHeadHistory;
	
	@Column(name="JSON_LOANADVHISTORY", columnDefinition = "LONGTEXT default NULL")
	private String jsonLoanAdvHistory;
	
	@Column(name="JSON_EMPLOYEE", columnDefinition = "LONGTEXT default NULL")
	private String jsonEmployee;
	
	@Column(name="JSON_IND_EMPLOYEE_PAYROLL_CACHE", columnDefinition = "LONGTEXT default NULL")
	private String jsonIndEmployeePayrollCache;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getOvertimeHours() {
		return overtimeHours;
	}

	public void setOvertimeHours(String overtimeHours) {
		this.overtimeHours = overtimeHours;
	}

	public String getRegularHours() {
		return regularHours;
	}

	public void setRegularHours(String regularHours) {
		this.regularHours = regularHours;
	}

	 
	public BigDecimal getNoOfLeaves() {
		return noOfLeaves;
	}

	public void setNoOfLeaves(BigDecimal noOfLeaves) {
		this.noOfLeaves = noOfLeaves;
	}

	public BigDecimal getGrossPay() {
		return grossPay;
	}

	public void setGrossPay(BigDecimal grossPay) {
		this.grossPay = grossPay;
	}

	public BigDecimal getTds() {
		return tds;
	}

	public void setTds(BigDecimal tds) {
		this.tds = tds;
	}

	public BigDecimal getNetPay() {
		return netPay;
	}

	public void setNetPay(BigDecimal netPay) {
		this.netPay = netPay;
	}

	public String getExcludeUUID() {
		return excludeUUID;
	}

	public void setExcludeUUID(String excludeUUID) {
		this.excludeUUID = excludeUUID;
	}

	public String getPaysheetUUID() {
		return paysheetUUID;
	}

	public void setPaysheetUUID(String paysheetUUID) {
		this.paysheetUUID = paysheetUUID;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public String getJsonPayHeadHistory() {
		return jsonPayHeadHistory;
	}

	public void setJsonPayHeadHistory(String jsonPayHeadHistory) {
		this.jsonPayHeadHistory = jsonPayHeadHistory;
	}

	public String getJsonLoanAdvHistory() {
		return jsonLoanAdvHistory;
	}

	public void setJsonLoanAdvHistory(String jsonLoanAdvHistory) {
		this.jsonLoanAdvHistory = jsonLoanAdvHistory;
	}

	public String getJsonEmployee() {
		return jsonEmployee;
	}

	public void setJsonEmployee(String jsonEmployee) {
		this.jsonEmployee = jsonEmployee;
	}

	public String getJsonIndEmployeePayrollCache() {
		return jsonIndEmployeePayrollCache;
	}

	public void setJsonIndEmployeePayrollCache(String jsonIndEmployeePayrollCache) {
		this.jsonIndEmployeePayrollCache = jsonIndEmployeePayrollCache;
	}
	
}
