package com.bis.sourcelead.dto;

public class LeaveHolidayHoursDTO {
	
	private String billableHrs	;
	private String nonBillableHrs;
	private String totalHrs;
	public String getBillableHrs() {
		return billableHrs;
	}
	public void setBillableHrs(String billableHrs) {
		this.billableHrs = billableHrs;
	}
	public String getNonBillableHrs() {
		return nonBillableHrs;
	}
	public void setNonBillableHrs(String nonBillableHrs) {
		this.nonBillableHrs = nonBillableHrs;
	}
	public String getTotalHrs() {
		return totalHrs;
	}
	public void setTotalHrs(String totalHrs) {
		this.totalHrs = totalHrs;
	}
	
	
	
	
}
