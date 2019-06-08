package com.bis.sourcelead.reportsDTO;

public class MissingTimesheetRepotDTO {

	private String projectIds;
	
	private String dateSelection;
	
	private String fromDate;
	
	private String toDate;

	private String fromtype;

	private String year;
	
	private int totalRows;
	private int pageNo;
	private int pageSize;
	private int pageCount;
	private String type;
	
	
 
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getFromtype() {
		return fromtype;
	}

	public void setFromtype(String fromtype) {
		this.fromtype = fromtype;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public int getPageNo() {
		return pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public String getProjectIds() {
		return projectIds;
	}

	public void setProjectIds(String projectIds) {
		this.projectIds = projectIds;
	}
	
}
