package com.bis.sourcelead.dto;

import java.io.Serializable;

public class EmployeeListDTO extends SearchDTO implements Serializable{

	private String company;
	private String branch;
	private String empId;
	private String candidateName;
	private double rate;
	private double fixedRate;
	private double payRate;
	private double fixedPay;
	private double insurance;
	private double ded;
	private double effectiveRate;
	private double payrollDifference;
	private String immigration;
	private String comments;
	private double advanceHcp;
	private double balance;
	
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getCandidateName() {
		return candidateName;
	}
	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public double getFixedRate() {
		return fixedRate;
	}
	public void setFixedRate(double fixedRate) {
		this.fixedRate = fixedRate;
	}
	public double getPayRate() {
		return payRate;
	}
	public void setPayRate(double payRate) {
		this.payRate = payRate;
	}
	public double getFixedPay() {
		return fixedPay;
	}
	public void setFixedPay(double fixedPay) {
		this.fixedPay = fixedPay;
	}
	public double getInsurance() {
		return insurance;
	}
	public void setInsurance(double insurance) {
		this.insurance = insurance;
	}
	public double getDed() {
		return ded;
	}
	public void setDed(double ded) {
		this.ded = ded;
	}
	public double getEffectiveRate() {
		return effectiveRate;
	}
	public void setEffectiveRate(double effectiveRate) {
		this.effectiveRate = effectiveRate;
	}
	public double getPayrollDifference() {
		return payrollDifference;
	}
	public void setPayrollDifference(double payrollDifference) {
		this.payrollDifference = payrollDifference;
	}
	public String getImmigration() {
		return immigration;
	}
	public void setImmigration(String immigration) {
		this.immigration = immigration;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public double getAdvanceHcp() {
		return advanceHcp;
	}
	public void setAdvanceHcp(double advanceHcp) {
		this.advanceHcp = advanceHcp;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	
	
}
