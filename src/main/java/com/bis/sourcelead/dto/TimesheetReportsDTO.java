package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;


/**
 * @author BIS115
 *
 */
public class TimesheetReportsDTO extends SearchDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String frequencyYear;
	private String frequencyMonth;
	private LinkedHashSet<String> frequencyYearList;
	private LinkedList<String> allMonthsList;
	private LinkedHashSet<String> dataTableDateList;
	private HashMap<String, LinkedList<String>> monthYearList;
	private List<TimeReportsSubmissionDetailsDTO> timeReportsSubmissionDetails;
	private LinkedHashMap<Date, Date> displayWeeksList;
   
	private String exportType;
	
	private int totalRows;
	private int pageNo;
	private int pageSize;
	private int pageCount;
	    
	
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
	public LinkedHashSet<String> getFrequencyYearList() {
		return frequencyYearList;
	}
	public void setFrequencyYearList(LinkedHashSet<String> frequencyYearList) {
		this.frequencyYearList = frequencyYearList;
	}
	public LinkedList<String> getAllMonthsList() {
		return allMonthsList;
	}
	public void setAllMonthsList(LinkedList<String> allMonthsList) {
		this.allMonthsList = allMonthsList;
	}
	public LinkedHashSet<String> getDataTableDateList() {
		return dataTableDateList;
	}
	public void setDataTableDateList(LinkedHashSet<String> dataTableDateList) {
		this.dataTableDateList = dataTableDateList;
	}
	public HashMap<String, LinkedList<String>> getMonthYearList() {
		return monthYearList;
	}
	public void setMonthYearList(HashMap<String, LinkedList<String>> monthYearList) {
		this.monthYearList = monthYearList;
	}
	public List<TimeReportsSubmissionDetailsDTO> getTimeReportsSubmissionDetails() {
		return timeReportsSubmissionDetails;
	}
	public void setTimeReportsSubmissionDetails(List<TimeReportsSubmissionDetailsDTO> timeReportsSubmissionDetails) {
		this.timeReportsSubmissionDetails = timeReportsSubmissionDetails;
	}
	public LinkedHashMap<Date, Date> getDisplayWeeksList() {
		return displayWeeksList;
	}
	public void setDisplayWeeksList(LinkedHashMap<Date, Date> displayWeeksList) {
		this.displayWeeksList = displayWeeksList;
	}
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public String getExportType() {
		return exportType;
	}
	public void setExportType(String exportType) {
		this.exportType = exportType;
	}
	
}