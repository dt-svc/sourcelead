package com.bis.sourcelead.reportsDTO;

import java.util.List;
import java.util.Map;

import com.bis.sourcelead.dto.SearchDTO;

public class JobFilterDTO extends SearchDTO {

	private String jobIds;
	
	private String dateSelection;
	
	private String fromDate;
	
	private String toDate;

	private String fromtype;

	private String year;
	
	private String searchValue;
	
	private String jobCreatedDateRange;
	
	private String clientIds;
	
	private String startDateStr;
	
	private String endDateStr;
	
	private String sortColumn = "id";
	
	private String sortOrder = "desc";
	
	private String  jobType;
	
	private String exportType;
	
	private List<Map<String, String>> order;


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

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getJobCreatedDateRange() {
		return jobCreatedDateRange;
	}

	public void setJobCreatedDateRange(String jobCreatedDateRange) {
		this.jobCreatedDateRange = jobCreatedDateRange;
	}

	public String getClientIds() {
		return clientIds;
	}

	public void setClientIds(String clientIds) {
		this.clientIds = clientIds;
	}

	public String getStartDateStr() {
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public List<Map<String, String>> getOrder() {
		return order;
	}

	public void setOrder(List<Map<String, String>> order) {
		this.order = order;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getExportType() {
		return exportType;
	}

	public void setExportType(String exportType) {
		this.exportType = exportType;
	}
	
	
}
