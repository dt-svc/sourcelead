package com.bis.sourcelead.dto;

import java.io.Serializable;

public class ReportsTimeDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String weekTotalHours;
	private String weekBillabeHours;
	private String weekNonBillableHours;
	private String weeekApprovalStatus;
	private String approvalClass;
	
	
	public String getWeekTotalHours() {
		return weekTotalHours;
	}
	public void setWeekTotalHours(String weekTotalHours) {
		this.weekTotalHours = weekTotalHours;
	}
	public String getWeekBillabeHours() {
		return weekBillabeHours;
	}
	public void setWeekBillabeHours(String weekBillabeHours) {
		this.weekBillabeHours = weekBillabeHours;
	}
	public String getWeekNonBillableHours() {
		return weekNonBillableHours;
	}
	public void setWeekNonBillableHours(String weekNonBillableHours) {
		this.weekNonBillableHours = weekNonBillableHours;
	}
	public String getWeeekApprovalStatus() {
		return weeekApprovalStatus;
	}
	public void setWeeekApprovalStatus(String weeekApprovalStatus) {
		this.weeekApprovalStatus = weeekApprovalStatus;
	}
	public String getApprovalClass() {
		return approvalClass;
	}
	public void setApprovalClass(String approvalClass) {
		this.approvalClass = approvalClass;
	}
	
	
}
