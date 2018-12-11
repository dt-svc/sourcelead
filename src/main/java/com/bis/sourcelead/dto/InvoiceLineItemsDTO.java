package com.bis.sourcelead.dto;

import com.bis.sourcelead.annotations.Required;

public class InvoiceLineItemsDTO {
	private String candidateName;
	private String candidate;
	private String lineItemId;
	
	@Required (desc = "Description")
	private String Description;
	
	private String sow;
	
	@Required (desc = "Rate")
	private String rate;
	
	private String totalHours;
	private String amount;
	private String invoiceStartDate;
	private String invoiceEndDate;
    private String submissionInvoiceId;
    private String submissionApproverHierarchyId;
    private String user;
    private String requirementSubmissionId;
    
    private String quantity;
    
    private String billRateType;
//	 
	public String getCandidateName() {
		return candidateName;
	}
	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}
	public String getCandidate() {
		return candidate;
	}
	public void setCandidate(String candidate) {
		this.candidate = candidate;
	}
	public String getLineItemId() {
		return lineItemId;
	}
	public void setLineItemId(String lineItemId) {
		this.lineItemId = lineItemId;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getSow() {
		return sow;
	}
	public void setSow(String sow) {
		this.sow = sow;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getTotalHours() {
		return totalHours;
	}
	public void setTotalHours(String totalHours) {
		this.totalHours = totalHours;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getInvoiceStartDate() {
		return invoiceStartDate;
	}
	public void setInvoiceStartDate(String invoiceStartDate) {
		this.invoiceStartDate = invoiceStartDate;
	}
	public String getInvoiceEndDate() {
		return invoiceEndDate;
	}
	public void setInvoiceEndDate(String invoiceEndDate) {
		this.invoiceEndDate = invoiceEndDate;
	}
	public String getSubmissionInvoiceId() {
		return submissionInvoiceId;
	}
	public void setSubmissionInvoiceId(String submissionInvoiceId) {
		this.submissionInvoiceId = submissionInvoiceId;
	}
	public String getSubmissionApproverHierarchyId() {
		return submissionApproverHierarchyId;
	}
	public void setSubmissionApproverHierarchyId(String submissionApproverHierarchyId) {
		this.submissionApproverHierarchyId = submissionApproverHierarchyId;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getBillRateType() {
		return billRateType;
	}
	public void setBillRateType(String billRateType) {
		this.billRateType = billRateType;
	}
	public String getRequirementSubmissionId() {
		return requirementSubmissionId;
	}
	public void setRequirementSubmissionId(String requirementSubmissionId) {
		this.requirementSubmissionId = requirementSubmissionId;
	}
	
	
}
