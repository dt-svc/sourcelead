package com.bis.sourcelead.dto;

import java.util.List;

import com.bis.sourcelead.dto.TimeSheetDayEntriesDTOList;

public class TimeSheetDayEntriesDTO extends SearchDTO{
	
	private static final long serialVersionUID = 1L;
	private int totalRows;
	private int pageNo;
	private int pageSize;
	private int pageCount;
	private List<TimeSheetDayEntriesDTOList> timeSheetDayEntriesDTOList;
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
	public List<TimeSheetDayEntriesDTOList> getTimeSheetDayEntriesDTOList() {
		return timeSheetDayEntriesDTOList;
	}
	public void setTimeSheetDayEntriesDTOList(List<TimeSheetDayEntriesDTOList> timeSheetDayEntriesDTOList) {
		this.timeSheetDayEntriesDTOList = timeSheetDayEntriesDTOList;
	}
	
	
}
