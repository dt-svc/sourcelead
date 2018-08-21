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
	private String subTotal;
	private String total;
	private String creditDebitNotedate;
	private String approverCheckBox;
	private String approvers;
	private String savedNoteId;
	
	
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
	public String getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(String subTotal) {
		this.subTotal = subTotal;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
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

	
}
