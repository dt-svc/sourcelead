package com.bis.sourcelead.dto;

import java.io.Serializable;

public class ApplicantRejectBackOutDTO implements Serializable{

	private String reasonForbackOut;
	
	private String backOutCategoryOther;
	
	private String backOutCategory;
	
	private String rejectedCategory;
	
	private String rejectedCategoryOther;
	
	private String reasonForReject;
	
	private String joinDate;

	public String getReasonForbackOut() {
		return reasonForbackOut;
	}

	public void setReasonForbackOut(String reasonForbackOut) {
		this.reasonForbackOut = reasonForbackOut;
	}

	public String getBackOutCategoryOther() {
		return backOutCategoryOther;
	}

	public void setBackOutCategoryOther(String backOutCategoryOther) {
		this.backOutCategoryOther = backOutCategoryOther;
	}

	public String getBackOutCategory() {
		return backOutCategory;
	}

	public void setBackOutCategory(String backOutCategory) {
		this.backOutCategory = backOutCategory;
	}

	public String getRejectedCategory() {
		return rejectedCategory;
	}

	public void setRejectedCategory(String rejectedCategory) {
		this.rejectedCategory = rejectedCategory;
	}

	public String getRejectedCategoryOther() {
		return rejectedCategoryOther;
	}

	public void setRejectedCategoryOther(String rejectedCategoryOther) {
		this.rejectedCategoryOther = rejectedCategoryOther;
	}

	public String getReasonForReject() {
		return reasonForReject;
	}

	public void setReasonForReject(String reasonForReject) {
		this.reasonForReject = reasonForReject;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}
	
}
