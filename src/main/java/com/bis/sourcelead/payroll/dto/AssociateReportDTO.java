package com.bis.sourcelead.payroll.dto;

import java.math.BigDecimal;

import com.bis.sourcelead.dto.SearchDTO;


public class AssociateReportDTO extends SearchDTO{
	
	private String empId;
	
	private String empName;
	
	private BigDecimal preTaxDeduction;
	
	private BigDecimal postTaxDeduction;
	
	private BigDecimal netPay;
	
	private BigDecimal grossPay;
	
	private BigDecimal totalFederalTax;
	
	private BigDecimal totalFederalWithholding;
	
	private BigDecimal totalStateWithholding;
	
	private BigDecimal totalMedicareWithholding;
	
	private BigDecimal totalSui;
	
	private BigDecimal totalfica;

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

	public BigDecimal getPreTaxDeduction() {
		return preTaxDeduction;
	}

	public void setPreTaxDeduction(BigDecimal preTaxDeduction) {
		this.preTaxDeduction = preTaxDeduction;
	}

	public BigDecimal getPostTaxDeduction() {
		return postTaxDeduction;
	}

	public void setPostTaxDeduction(BigDecimal postTaxDeduction) {
		this.postTaxDeduction = postTaxDeduction;
	}

	public BigDecimal getNetPay() {
		return netPay;
	}

	public void setNetPay(BigDecimal netPay) {
		this.netPay = netPay;
	}

	public BigDecimal getGrossPay() {
		return grossPay;
	}

	public void setGrossPay(BigDecimal grossPay) {
		this.grossPay = grossPay;
	}

	public BigDecimal getTotalFederalTax() {
		return totalFederalTax;
	}

	public void setTotalFederalTax(BigDecimal totalFederalTax) {
		this.totalFederalTax = totalFederalTax;
	}

	public BigDecimal getTotalFederalWithholding() {
		return totalFederalWithholding;
	}

	public void setTotalFederalWithholding(BigDecimal totalFederalWithholding) {
		this.totalFederalWithholding = totalFederalWithholding;
	}

	public BigDecimal getTotalStateWithholding() {
		return totalStateWithholding;
	}

	public void setTotalStateWithholding(BigDecimal totalStateWithholding) {
		this.totalStateWithholding = totalStateWithholding;
	}

	public BigDecimal getTotalMedicareWithholding() {
		return totalMedicareWithholding;
	}

	public void setTotalMedicareWithholding(BigDecimal totalMedicareWithholding) {
		this.totalMedicareWithholding = totalMedicareWithholding;
	}

	public BigDecimal getTotalSui() {
		return totalSui;
	}

	public void setTotalSui(BigDecimal totalSui) {
		this.totalSui = totalSui;
	}

	public BigDecimal getTotalfica() {
		return totalfica;
	}

	public void setTotalfica(BigDecimal totalfica) {
		this.totalfica = totalfica;
	}
	
	
}