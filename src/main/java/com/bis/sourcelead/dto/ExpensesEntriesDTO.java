package com.bis.sourcelead.dto;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class ExpensesEntriesDTO {

	private Long id;
	
	private String maxRecCount;
	
	private String categoryType;
	
	private long categoryTypeId;
	
	private String categoryName;
	
	private String dateOccurred;
	
	private String receiptIssuedBy;
	
	private String paymentMode;
	
	private float allowedAmount;
	
	private String categoryAmount;
	
	private String categoryAmountType;
	
	private String categoryApprovedAmountType;
	
	private String categoryApprovedAmount;
	
	private String paidBy;
	
	private String entryDescription;
	
	private List<FileInfo> fileInfo;
	
	private BigDecimal amountRoundOff;
	
	private Boolean billable;
	
	private String amountchangereason;
	
	private BigDecimal submittedamount;
	
	private BigDecimal amount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMaxRecCount() {
		return maxRecCount;
	}

	public void setMaxRecCount(String maxRecCount) {
		this.maxRecCount = maxRecCount;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getDateOccurred() {
		return dateOccurred;
	}

	public void setDateOccurred(String dateOccurred) {
		this.dateOccurred = dateOccurred;
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

	public float getAllowedAmount() {
		return allowedAmount;
	}

	public void setAllowedAmount(float allowedAmount) {
		this.allowedAmount = allowedAmount;
	}

	public String getCategoryAmount() {
		return categoryAmount;
	}

	public void setCategoryAmount(String categoryAmount) {
		this.categoryAmount = categoryAmount;
	}

	public String getCategoryAmountType() {
		return categoryAmountType;
	}

	public void setCategoryAmountType(String categoryAmountType) {
		this.categoryAmountType = categoryAmountType;
	}

	public String getEntryDescription() {
		return entryDescription;
	}

	public void setEntryDescription(String entryDescription) {
		this.entryDescription = entryDescription;
	}

	public List<FileInfo> getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(List<FileInfo> fileInfo) {
		this.fileInfo = fileInfo;
	}


	public BigDecimal getAmountRoundOff() {
		return amountRoundOff;
	}

	public void setAmountRoundOff(BigDecimal amountRoundOff) {
		this.amountRoundOff = amountRoundOff;
	}

	public long getCategoryTypeId() {
		return categoryTypeId;
	}

	public void setCategoryTypeId(long categoryTypeId) {
		this.categoryTypeId = categoryTypeId;
	}

	public String getPaidBy() {
		return paidBy;
	}

	public void setPaidBy(String paidBy) {
		this.paidBy = paidBy;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Boolean getBillable() {
		return billable;
	}

	public void setBillable(Boolean billable) {
		this.billable = billable;
	}

	public String getAmountchangereason() {
		return amountchangereason;
	}

	public void setAmountchangereason(String amountchangereason) {
		this.amountchangereason = amountchangereason;
	}

	public BigDecimal getSubmittedamount() {
		return submittedamount;
	}

	public void setSubmittedamount(BigDecimal submittedamount) {
		this.submittedamount = submittedamount;
	}

	public String getCategoryApprovedAmountType() {
		return categoryApprovedAmountType;
	}

	public void setCategoryApprovedAmountType(String categoryApprovedAmountType) {
		this.categoryApprovedAmountType = categoryApprovedAmountType;
	}

	public String getCategoryApprovedAmount() {
		return categoryApprovedAmount;
	}

	public void setCategoryApprovedAmount(String categoryApprovedAmount) {
		this.categoryApprovedAmount = categoryApprovedAmount;
	}


	
	
	
}





