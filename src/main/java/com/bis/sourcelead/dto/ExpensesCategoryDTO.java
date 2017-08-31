package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

public class ExpensesCategoryDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String expensesName;
	
	private String amountType;
	
	private float allowedAmount;
	
	//storing fields
	private String categoryType;
	
	private Long categoryTypeId;
	
	private String expenseDate;
	
	private String receiptIssuedBy;
	
	private String paymentMode;
	
	private String currencyCode;
	
	private Double expenseAmount;
	
	private String description;
	
	private List<String> fileNames;
	
	private List<Long> removedFilesIds;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExpensesName() {
		return expensesName;
	}

	public void setExpensesName(String expensesName) {
		this.expensesName = expensesName;
	}

	public String getAmountType() {
		return amountType;
	}

	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}

	public float getAllowedAmount() {
		return allowedAmount;
	}

	public void setAllowedAmount(float allowedAmount) {
		this.allowedAmount = allowedAmount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public String getExpenseDate() {
		return expenseDate;
	}

	public void setExpenseDate(String expenseDate) {
		this.expenseDate = expenseDate;
	}

	public String getReceiptIssuedBy() {
		return receiptIssuedBy;
	}

	public void setReceiptIssuedBy(String receiptIssuedBy) {
		this.receiptIssuedBy = receiptIssuedBy;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Double getExpenseAmount() {
		return expenseAmount;
	}

	public void setExpenseAmount(Double expenseAmount) {
		this.expenseAmount = expenseAmount;
	}

	public List<String> getFileNames() {
		return fileNames;
	}

	public void setFileNames(List<String> fileNames) {
		this.fileNames = fileNames;
	}

	public List<Long> getRemovedFilesIds() {
		return removedFilesIds;
	}

	public void setRemovedFilesIds(List<Long> removedFilesIds) {
		this.removedFilesIds = removedFilesIds;
	}

	public Long getCategoryTypeId() {
		return categoryTypeId;
	}

	public void setCategoryTypeId(Long categoryTypeId) {
		this.categoryTypeId = categoryTypeId;
	}
	
	
}
