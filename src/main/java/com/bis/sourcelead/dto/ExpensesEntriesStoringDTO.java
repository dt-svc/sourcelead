package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ExpensesEntriesStoringDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String expenseId;

	private String purpose;

	private Long projectMemberId;

	private String clientName;

	private String startDate;

	private String endDate;

    private List<ExpensesCategoryDTO> categoryList;
	
	private MultipartFile[] files;

	public String getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(String expenseId) {
		this.expenseId = expenseId;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public Long getProjectMemberId() {
		return projectMemberId;
	}

	public void setProjectMemberId(Long projectMemberId) {
		this.projectMemberId = projectMemberId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
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

	public List<ExpensesCategoryDTO> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<ExpensesCategoryDTO> categoryList) {
		this.categoryList = categoryList;
	}

	public MultipartFile[] getFiles() {
		return files;
	}

	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}
	
	

}
