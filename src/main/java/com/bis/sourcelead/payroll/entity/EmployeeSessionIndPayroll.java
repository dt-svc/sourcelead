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
