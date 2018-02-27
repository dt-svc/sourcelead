package com.bis.sourcelead.reportsDTO;

import com.bis.sourcelead.dto.SearchDTO;

public class PaidBillDTO extends SearchDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String projectName;
	private String billDate;
	private String payDate;
	private String amount;
    private String billType;
	private String orgName;
	private int numberOfAssociates;
	private int numberOfprojects;
	private double totalAmount;
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public int getNumberOfAssociates() {
		return numberOfAssociates;
	}

	public void setNumberOfAssociates(int numberOfAssociates) {
		this.numberOfAssociates = numberOfAssociates;
	}

	public int getNumberOfprojects() {
		return numberOfprojects;
	}

	public void setNumberOfprojects(int numberOfprojects) {
		this.numberOfprojects = numberOfprojects;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	

}
