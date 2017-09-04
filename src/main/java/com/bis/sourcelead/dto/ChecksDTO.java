package com.bis.sourcelead.dto;

import java.io.Serializable;

public class ChecksDTO extends SearchDTO implements Serializable{

	private String empId;
	private String empName;
	private String typeOfExpense;
	private String bankName;
	private String month;
	private String legalFees;
	private String electronicWithdrawals;
	private String checkNo;
	private String userId;
	private String date;
	private String advance;
	private String generalExpenses;
	private String vendorPayments;
	private String payrollChecks;
	private String amount;
	private String memo;
	
	
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
	public String getLegalFees() {
		return legalFees;
	}
	public void setLegalFees(String legalFees) {
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAdvance() {
		return advance;
	}
	public void setAdvance(String advance) {
		this.advance = advance;
	}
	public String getGeneralExpenses() {
		return generalExpenses;
	}
	public void setGeneralExpenses(String generalExpenses) {
		this.generalExpenses = generalExpenses;
	}
	public String getVendorPayments() {
		return vendorPayments;
	}
	public void setVendorPayments(String vendorPayments) {
		this.vendorPayments = vendorPayments;
	}
	public String getPayrollChecks() {
		return payrollChecks;
	}
	public void setPayrollChecks(String payrollChecks) {
		this.payrollChecks = payrollChecks;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}

	
	
	
}
