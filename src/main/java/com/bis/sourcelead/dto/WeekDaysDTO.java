package com.bis.sourcelead.dto;

import java.io.Serializable;

public class WeekDaysDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
    
    private String dayOfTheDate;
    
    private String date;
    
    private boolean editable;
    
    private String totalHours;
    
    private String billableHours;
    
    private String nonBillableHours;

	public String getDayOfTheDate() {
		return dayOfTheDate;
	}

	public void setDayOfTheDate(String dayOfTheDate) {
		this.dayOfTheDate = dayOfTheDate;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public String getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(String totalHours) {
		this.totalHours = totalHours;
	}

	public String getBillableHours() {
		return billableHours;
	}

	public void setBillableHours(String billableHours) {
		this.billableHours = billableHours;
	}

	public String getNonBillableHours() {
		return nonBillableHours;
	}

	public void setNonBillableHours(String nonBillableHours) {
		this.nonBillableHours = nonBillableHours;
	}

	
	
	
}
