package com.bis.sourcelead.reportsDTO;

public class JobFilterDTO {

	private String jobIds;
	
	private String dateSelection;
	
	private String fromDate;
	
	private String toDate;
	
	private String dateFrequency;
	private String fromtype;

	public String getJobIds() {
		return jobIds;
	}

	public void setJobIds(String jobIds) {
		this.jobIds = jobIds;
	}

	public String getDateSelection() {
		return dateSelection;
	}

	public void setDateSelection(String dateSelection) {
		this.dateSelection = dateSelection;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getDateFrequency() {
		return dateFrequency;
	}

	public void setDateFrequency(String dateFrequency) {
		this.dateFrequency = dateFrequency;
	}

	public String getFromtype() {
		return fromtype;
	}

	public void setFromtype(String fromtype) {
		this.fromtype = fromtype;
	}
	
}
