package com.bis.sourcelead.payroll.entity;

import java.io.Serializable;

import com.bis.sourcelead.dto.SearchDTO;

public class PayrollBalance extends SearchDTO implements Serializable{
	
	private String income;
	private String empIncome;
	private String orgLocationId;
	private String directDepositGross;
	private String directDepositNet;
	private String processedWithCheckOptionGross;
	private String processedWithCheckOptionNet;
	private String actualChecksDepositedNet;
	private String manualAdvance;
	private String generalExpenses;
	private String vendorPayment;
	private String healthcareInsurance;
	private String immigration;
	private String directDepositAdvanceNegetive;
	private String directDepositDeductionPositive;
	private String balance;
	private String due;
	private String billed;
	private String empName;
	private String searchValue;
	private String empBalance;
	private String empBilled;
	
	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getEmpIncome()
	{
		return empIncome;
	}

	public void setEmpIncome(String empIncome)
	{
		this.empIncome = empIncome;
	}

	public String getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(String orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getDirectDepositGross() {
		return directDepositGross;
	}

	public void setDirectDepositGross(String directDepositGross) {
		this.directDepositGross = directDepositGross;
	}

	public String getDirectDepositNet() {
		return directDepositNet;
	}

	public void setDirectDepositNet(String directDepositNet) {
		this.directDepositNet = directDepositNet;
	}

	public String getProcessedWithCheckOptionGross() {
		return processedWithCheckOptionGross;
	}

	public void setProcessedWithCheckOptionGross(String processedWithCheckOptionGross) {
		this.processedWithCheckOptionGross = processedWithCheckOptionGross;
	}

	public String getProcessedWithCheckOptionNet() {
		return processedWithCheckOptionNet;
	}

	public void setProcessedWithCheckOptionNet(String processedWithCheckOptionNet) {
		this.processedWithCheckOptionNet = processedWithCheckOptionNet;
	}

	public String getActualChecksDepositedNet() {
		return actualChecksDepositedNet;
	}

	public void setActualChecksDepositedNet(String actualChecksDepositedNet) {
		this.actualChecksDepositedNet = actualChecksDepositedNet;
	}

	public String getManualAdvance() {
		return manualAdvance;
	}

	public void setManualAdvance(String manualAdvance) {
		this.manualAdvance = manualAdvance;
	}

	public String getGeneralExpenses() {
		return generalExpenses;
	}

	public void setGeneralExpenses(String generalExpenses) {
		this.generalExpenses = generalExpenses;
	}

	public String getVendorPayment() {
		return vendorPayment;
	}

	public void setVendorPayment(String vendorPayment) {
		this.vendorPayment = vendorPayment;
	}

	public String getHealthcareInsurance() {
		return healthcareInsurance;
	}

	public void setHealthcareInsurance(String healthcareInsurance) {
		this.healthcareInsurance = healthcareInsurance;
	}

	public String getImmigration() {
		return immigration;
	}

	public void setImmigration(String immigration) {
		this.immigration = immigration;
	}

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public String getDirectDepositAdvanceNegetive() {
		return directDepositAdvanceNegetive;
	}

	public void setDirectDepositAdvanceNegetive(String directDepositAdvanceNegetive) {
		this.directDepositAdvanceNegetive = directDepositAdvanceNegetive;
	}

	public String getDirectDepositDeductionPositive() {
		return directDepositDeductionPositive;
	}

	public void setDirectDepositDeductionPositive(String directDepositDeductionPositive) {
		this.directDepositDeductionPositive = directDepositDeductionPositive;
	}

	public String getDue() {
		return due;
	}

	public void setDue(String due) {
		this.due = due;
	}

	public String getBilled() {
		return billed;
	}

	public void setBilled(String billed) {
		this.billed = billed;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getEmpBalance()
	{
		return empBalance;
	}

	public void setEmpBalance(String empBalance)
	{
		this.empBalance = empBalance;
	}

	public String getEmpBilled()
	{
		return empBilled;
	}

	public void setEmpBilled(String empBilled)
	{
		this.empBilled = empBilled;
	}
}
