package com.bis.sourcelead.dto;

import java.util.List;

public class CreditDebitNoteListDTO extends SearchDTO {

	private List<FileInfo> fileInfo;
	private List<CreditDebitNoteLineItemsDTO> creditDebitNoteLineItems;
	private String orglocationId;
	private String orgRelationId;
	private String invoceId;
	private String ownerUser;
	private String contactUser;
	private List<String> approverUserSplitId;
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
	private String clientName;
	private String generatedId;
	private String creditDebitNoteStatus;
	private String creditDebitUUID;
	private String exchangeCurrencyRate;
	private String afterExchangeGrandTotal;
	private String conversionFrom;
	private String loginCurrencyCode;
	private String vendorCurrencyCode;
	private String account;
	private String status;
	private String rejectReason;
	
	 
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
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getGeneratedId() {
		return generatedId;
	}
	public void setGeneratedId(String generatedId) {
		this.generatedId = generatedId;
	}
	public String getCreditDebitNoteStatus() {
		return creditDebitNoteStatus;
	}
	public void setCreditDebitNoteStatus(String creditDebitNoteStatus) {
		this.creditDebitNoteStatus = creditDebitNoteStatus;
	}
	public String getCreditDebitUUID() {
		return creditDebitUUID;
	}
	public void setCreditDebitUUID(String creditDebitUUID) {
		this.creditDebitUUID = creditDebitUUID;
	}
	public List<String> getApproverUserSplitId() {
		return approverUserSplitId;
	}
	public void setApproverUserSplitId(List<String> approverUserSplitId) {
		this.approverUserSplitId = approverUserSplitId;
	}
	public String getExchangeCurrencyRate() {
		return exchangeCurrencyRate;
	}
	public void setExchangeCurrencyRate(String exchangeCurrencyRate) {
		this.exchangeCurrencyRate = exchangeCurrencyRate;
	}
	public String getAfterExchangeGrandTotal() {
		return afterExchangeGrandTotal;
	}
	public void setAfterExchangeGrandTotal(String afterExchangeGrandTotal) {
		this.afterExchangeGrandTotal = afterExchangeGrandTotal;
	}
	public String getConversionFrom() {
		return conversionFrom;
	}
	public void setConversionFrom(String conversionFrom) {
		this.conversionFrom = conversionFrom;
	}
	public String getLoginCurrencyCode() {
		return loginCurrencyCode;
	}
	public void setLoginCurrencyCode(String loginCurrencyCode) {
		this.loginCurrencyCode = loginCurrencyCode;
	}
	public String getVendorCurrencyCode() {
		return vendorCurrencyCode;
	}
	public void setVendorCurrencyCode(String vendorCurrencyCode) {
		this.vendorCurrencyCode = vendorCurrencyCode;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRejectReason() {
		return rejectReason;
	}
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
	
	

	
}
