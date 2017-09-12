package com.bis.sourcelead.dto;

import java.util.List;

public class CategoryDTO {

	private Long id;
	
	private String categoryType;
	
	private String categoryDate;
	
	private String expenseAmount;
	
	private String paymentMode;
	
	private String expenseRateType;
	
	private String entryDes;
	
	private String receiptIssuedBy;
	
	private String maxRecCount;
	
	private String expenseEntryId;
	
	private List<FileInfo> fileInfo;

	private String staticPath;
	
	private String DbdocFileId;
	
	private String DbdocFileIsDeleted;
	
	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public String getCategoryDate() {
		return categoryDate;
	}

	public void setCategoryDate(String categoryDate) {
		this.categoryDate = categoryDate;
	}

	public String getExpenseAmount() {
		return expenseAmount;
	}

	public void setExpenseAmount(String expenseAmount) {
		this.expenseAmount = expenseAmount;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getExpenseRateType() {
		return expenseRateType;
	}

	public void setExpenseRateType(String expenseRateType) {
		this.expenseRateType = expenseRateType;
	}

	public String getEntryDes() {
		return entryDes;
	}

	public void setEntryDes(String entryDes) {
		this.entryDes = entryDes;
	}

	public String getReceiptIssuedBy() {
		return receiptIssuedBy;
	}

	public void setReceiptIssuedBy(String receiptIssuedBy) {
		this.receiptIssuedBy = receiptIssuedBy;
	}

	public String getMaxRecCount() {
		return maxRecCount;
	}

	public void setMaxRecCount(String maxRecCount) {
		this.maxRecCount = maxRecCount;
	}

	public String getExpenseEntryId() {
		return expenseEntryId;
	}

	public void setExpenseEntryId(String expenseEntryId) {
		this.expenseEntryId = expenseEntryId;
	}

	public List<FileInfo> getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(List<FileInfo> fileInfo) {
		this.fileInfo = fileInfo;
	}

	public String getDbdocFileId() {
		return DbdocFileId;
	}

	public void setDbdocFileId(String dbdocFileId) {
		DbdocFileId = dbdocFileId;
	}

	public String getStaticPath() {
		return staticPath;
	}

	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDbdocFileIsDeleted() {
		return DbdocFileIsDeleted;
	}

	public void setDbdocFileIsDeleted(String dbdocFileIsDeleted) {
		DbdocFileIsDeleted = dbdocFileIsDeleted;
	}
	
}





