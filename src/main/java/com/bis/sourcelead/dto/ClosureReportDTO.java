package com.bis.sourcelead.dto;

import java.io.Serializable;

public class ClosureReportDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String frequencyYear;
	
	private String frequencyMonth;
	
	private float employeeBillableHours;
	
	private float contractorsBillableHours;
	
	private int employeeCount;
	
	private int contractorsCount;
	
	private float totalBillableHours;

	public String getFrequencyYear() {
		return frequencyYear;
	}

	public void setFrequencyYear(String frequencyYear) {
		this.frequencyYear = frequencyYear;
	}

	public String getFrequencyMonth() {
		return frequencyMonth;
	}

	public void setFrequencyMonth(String frequencyMonth) {
		this.frequencyMonth = frequencyMonth;
	}

	public float getEmployeeBillableHours() {
		return employeeBillableHours;
	}

	public void setEmployeeBillableHours(float employeeBillableHours) {
		this.employeeBillableHours = employeeBillableHours;
	}

	public float getContractorsBillableHours() {
		return contractorsBillableHours;
	}

	public void setContractorsBillableHours(float contractorsBillableHours) {
		this.contractorsBillableHours = contractorsBillableHours;
	}

	public float getTotalBillableHours() {
		return totalBillableHours;
	}

	public void setTotalBillableHours(float totalBillableHours) {
		this.totalBillableHours = totalBillableHours;
	}

	public int getEmployeeCount() {
		return employeeCount;
	}

	public void setEmployeeCount(int employeeCount) {
		this.employeeCount = employeeCount;
	}

	public int getContractorsCount() {
		return contractorsCount;
	}

	public void setContractorsCount(int contractorsCount) {
		this.contractorsCount = contractorsCount;
	}
	
	
}
