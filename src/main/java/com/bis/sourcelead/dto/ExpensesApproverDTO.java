package com.bis.sourcelead.dto;

import java.util.LinkedList;

public class ExpensesApproverDTO extends SearchDTO {

	private static final long serialVersionUID = 5465842887294836128L;
	private LinkedList<ExpensesApproverHierarchyDTO>  expensesApproverHierarchyDTO;
	private int totalRows;
	private int pageNo;
	private int pageSize;
	private int pageCount;
	private String expenseStatus;
	private String startDate;
	private String endDate;
	private String type;
	
	public LinkedList<ExpensesApproverHierarchyDTO> getExpensesApproverHierarchyDTO() {
		return expensesApproverHierarchyDTO;
	}
	public void setExpensesApproverHierarchyDTO(LinkedList<ExpensesApproverHierarchyDTO> expensesApproverHierarchyDTO) {
		this.expensesApproverHierarchyDTO = expensesApproverHierarchyDTO;
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
	public String getExpenseStatus() {
		return expenseStatus;
	}
	public void setExpenseStatus(String expenseStatus) {
		this.expenseStatus = expenseStatus;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
