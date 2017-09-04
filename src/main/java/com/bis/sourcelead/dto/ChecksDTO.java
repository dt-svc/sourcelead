package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public class ChecksDTO extends SearchDTO implements Serializable{

	private String empId;
	private String empName;
	private OrgLocations orgLocationId;
	private String typeOfExpense;
	private String bankName;
	private String month;
	private double legalFees;
	private String electronicWithdrawals;
	private String checkNo;
	private Long userId;
	private Date date;
	private double advance;
	private double generalExpenses;
	private double vendorPayments;
	private double payrollChecks;
	private double amount;

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
	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}
	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}
	public String getTypeOfExpense() {
		return typeOfExpense;
	}
	public void setTypeOfExpense(String typeOfExpense) {
		this.typeOfExpense = typeOfExpense;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public double getLegalFees() {
		return legalFees;
	}
	public void setLegalFees(double legalFees) {
		this.legalFees = legalFees;
	}
	public String getElectronicWithdrawals() {
		return electronicWithdrawals;
	}
	public void setElectronicWithdrawals(String electronicWithdrawals) {
		this.electronicWithdrawals = electronicWithdrawals;
	}
	public String getCheckNo() {
		return checkNo;
	}
	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getAdvance() {
		return advance;
	}
	public void setAdvance(double advance) {
		this.advance = advance;
	}
	public double getGeneralExpenses() {
		return generalExpenses;
	}
	public void setGeneralExpenses(double generalExpenses) {
		this.generalExpenses = generalExpenses;
	}
	public double getVendorPayments() {
		return vendorPayments;
	}
	public void setVendorPayments(double vendorPayments) {
		this.vendorPayments = vendorPayments;
	}
	public double getPayrollChecks() {
		return payrollChecks;
	}
	public void setPayrollChecks(double payrollChecks) {
		this.payrollChecks = payrollChecks;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
	
}
