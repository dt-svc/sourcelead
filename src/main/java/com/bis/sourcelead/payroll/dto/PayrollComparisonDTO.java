package com.bis.sourcelead.payroll.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.dto.AssociatePayItemDTO;

public class PayrollComparisonDTO {
	
	private String payGroup;
	private Long groupId;
	private String payrollType;
	private String payPeriod;
	private String payPeriod2;
	private String payrollDepartment;
	private String compareDate1;
	private String compareDate2;
	
	private CompareDataDTO compareData1;
	private CompareDataDTO compareData2;
	
	
	public String getPayGroup() {
		return payGroup;
	}
	public void setPayGroup(String payGroup) {
		this.payGroup = payGroup;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public String getPayrollType() {
		return payrollType;
	}
	public void setPayrollType(String payrollType) {
		this.payrollType = payrollType;
	}
	public String getPayPeriod() {
		return payPeriod;
	}
	public void setPayPeriod(String payPeriod) {
		this.payPeriod = payPeriod;
	}
	public String getPayPeriod2() {
		return payPeriod2;
	}
	public void setPayPeriod2(String payPeriod2) {
		this.payPeriod2 = payPeriod2;
	}
	public String getPayrollDepartment() {
		return payrollDepartment;
	}
	public void setPayrollDepartment(String payrollDepartment) {
		this.payrollDepartment = payrollDepartment;
	}
	public String getCompareDate1() {
		return compareDate1;
	}
	public void setCompareDate1(String compareDate1) {
		this.compareDate1 = compareDate1;
	}
	public String getCompareDate2() {
		return compareDate2;
	}
	public void setCompareDate2(String compareDate2) {
		this.compareDate2 = compareDate2;
	}
	public CompareDataDTO getCompareData1() {
		return compareData1;
	}
	public void setCompareData1(CompareDataDTO compareData1) {
		this.compareData1 = compareData1;
	}
	public CompareDataDTO getCompareData2() {
		return compareData2;
	}
	public void setCompareData2(CompareDataDTO compareData2) {
		this.compareData2 = compareData2;
	}
	
}
