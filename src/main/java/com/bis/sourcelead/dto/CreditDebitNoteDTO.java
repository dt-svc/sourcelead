package com.bis.sourcelead.dto;

import java.util.List;

public class CreditDebitNoteDTO extends SearchDTO {

	private List<FileInfo> fileInfo;
	private List<CreditDebitNoteLineItemsDTO> creditDebitNoteLineItems;
	private String orglocationId;
	private String orgRelationId;
	private String invoceId;
	private String ownerUser;
	private String contactUser;
	private String spocUser;
	private String billingAddress;
	private String shippingAddress;
	private String referenceNumber;
	private String reason;
	private String subTotalAmount;
	private String grandTotal;
	private String creditDebitNotedate;
	private String approverCheckBox;
	private String approvers;
	private String savedNoteId;
	private String discountTotal;
	private String discountValue;
	private String discountType;
	private String noteType;
	private String creditDebitNoteStatus;
	private String status;
	private String tags;
	private String dayWiseType;
	private String afterExchangeGrandTotal;
	private String exchangeCurrencyRate;
	private String vendorCurrencyCode;
	private String loginCurrencyCode;
	private String account;
	private String startDate;
	private String endDate;
	
	 
	public List<FileInfo> getFileInfo() {
		return fileInfo;
	}
	public void setFileInfo(List<FileInfo> fileInfo) {
		this.fileInfo = fileInfo;
	}
	public List<CreditDebitNoteLineItemsDTO> getCreditDebitNoteLineItems() {
		return creditDebitNoteLineItems;
	}
	public void setCreditDebitNoteLineItems(List<CreditDebitNoteLineItemsDTO> creditDebitNoteLineItems) {
		this.creditDebitNoteLineItems = creditDebitNoteLineItems;
	}
	public String getOrglocationId() {
		return orglocationId;
	}
	public void setOrglocationId(String orglocationId) {
		this.orglocationId = orglocationId;
	}
	public String getOrgRelationId() {
		return orgRelationId;
	}
	public void setOrgRelationId(String orgRelationId) {
		this.orgRelationId = orgRelationId;
	}
	public String getInvoceId() {
		return invoceId;
	}
	public void setInvoceId(String invoceId) {
		this.invoceId = invoceId;
	}
	public String getOwnerUser() {
		return ownerUser;
	}
	public void setOwnerUser(String ownerUser) {
		this.ownerUser = ownerUser;
	}
	public String getContactUser() {
		return contactUser;
	}
	public void setContactUser(String contactUser) {
		this.contactUser = contactUser;
	}
	public String getSpocUser() {
		return spocUser;
	}
	public void setSpocUser(String spocUser) {
		this.spocUser = spocUser;
	}
	public String getBillingAddress() {
		return billingAddress;
	}
	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}
	public String getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public String getReferenceNumber() {
		return referenceNumber;
	}
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getSubTotalAmount() {
		return subTotalAmount;
	}
	public void setSubTotalAmount(String subTotalAmount) {
		this.subTotalAmount = subTotalAmount;
	}
	public String getGrandTotal() {
		return grandTotal;
	}
	public void setGrandTotal(String grandTotal) {
		this.grandTotal = grandTotal;
	}
	public String getCreditDebitNotedate() {
		return creditDebitNotedate;
	}
	public void setCreditDebitNotedate(String creditDebitNotedate) {
		this.creditDebitNotedate = creditDebitNotedate;
	}
	public String getApproverCheckBox() {
		return approverCheckBox;
	}
	public void setApproverCheckBox(String approverCheckBox) {
		this.approverCheckBox = approverCheckBox;
	}
	public String getApprovers() {
		return approvers;
	}
	public void setApprovers(String approvers) {
		this.approvers = approvers;
	}
	public String getSavedNoteId() {
		return savedNoteId;
	}
	public void setSavedNoteId(String savedNoteId) {
		this.savedNoteId = savedNoteId;
	}
	public String getDiscountTotal() {
		return discountTotal;
	}
	public void setDiscountTotal(String discountTotal) {
		this.discountTotal = discountTotal;
	}
	public String getDiscountValue() {
		return discountValue;
	}
	public void setDiscountValue(String discountValue) {
		this.discountValue = discountValue;
	}
	public String getDiscountType() {
		return discountType;
	}
	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}
	public String getNoteType() {
		return noteType;
	}
	public void setNoteType(String noteType) {
		this.noteType = noteType;
	}
	public String getCreditDebitNoteStatus() {
		return creditDebitNoteStatus;
	}
	public void setCreditDebitNoteStatus(String creditDebitNoteStatus) {
		this.creditDebitNoteStatus = creditDebitNoteStatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getDayWiseType() {
		return dayWiseType;
	}
	public void setDayWiseType(String dayWiseType) {
		this.dayWiseType = dayWiseType;
	}
	public String getAfterExchangeGrandTotal() {
		return afterExchangeGrandTotal;
	}
	public void setAfterExchangeGrandTotal(String afterExchangeGrandTotal) {
		this.afterExchangeGrandTotal = afterExchangeGrandTotal;
	}
	public String getExchangeCurrencyRate() {
		return exchangeCurrencyRate;
	}
	public void setExchangeCurrencyRate(String exchangeCurrencyRate) {
		this.exchangeCurrencyRate = exchangeCurrencyRate;
	}
	public String getVendorCurrencyCode() {
		return vendorCurrencyCode;
	}
	public void setVendorCurrencyCode(String vendorCurrencyCode) {
		this.vendorCurrencyCode = vendorCurrencyCode;
	}
	public String getLoginCurrencyCode() {
		return loginCurrencyCode;
	}
	public void setLoginCurrencyCode(String loginCurrencyCode) {
		this.loginCurrencyCode = loginCurrencyCode;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
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

	
}
