package com.bis.sourcelead.payroll.entity;

public class FinalPayrollReport {
	private String empId;
	private String name;
	private String totalMarchHrs;
	private String halfHrs;
	private String payRate;
	private double amount;
	private double preDeductions;
	private double finalAmount;
	private double postDeductions;
	private double reimbursement;
	private String status;
	private String comments;
	private String aRreceivedTill;
	private String aRcomment;

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTotalMarchHrs() {
		return totalMarchHrs;
	}

	public void setTotalMarchHrs(String totalMarchHrs) {
		this.totalMarchHrs = totalMarchHrs;
	}

	public String getHalfHrs() {
		return halfHrs;
	}

	public void setHalfHrs(String halfHrs) {
		this.halfHrs = halfHrs;
	}

	

	public String getPayRate() {
		return payRate;
	}

	public void setPayRate(String payRate) {
		this.payRate = payRate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getPreDeductions() {
		return preDeductions;
	}

	public void setPreDeductions(double preDeductions) {
		this.preDeductions = preDeductions;
	}

	public double getFinalAmount() {
		return finalAmount;
	}

	public void setFinalAmount(double finalAmount) {
		this.finalAmount = finalAmount;
	}

	public double getPostDeductions() {
		return postDeductions;
	}

	public void setPostDeductions(double postDeductions) {
		this.postDeductions = postDeductions;
	}

	public double getReimbursement() {
		return reimbursement;
	}

	public void setReimbursement(double reimbursement) {
		this.reimbursement = reimbursement;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getaRreceivedTill() {
		return aRreceivedTill;
	}

	public void setaRreceivedTill(String aRreceivedTill) {
		this.aRreceivedTill = aRreceivedTill;
	}

	public String getaRcomment() {
		return aRcomment;
	}

	public void setaRcomment(String aRcomment) {
		this.aRcomment = aRcomment;
	}

}
