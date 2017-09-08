package com.bis.sourcelead.dto;

import java.util.List;
import java.util.Map;

public class InvoiceDTO {

	private String clientOrgRelationId;
	private String savedInvoiceId;
	private String clientCurrency;
	private String clientContact;
	private String invoiceId;
	private String invoiceDate;
	private String paymentFrequency;
	private String dueDate;
	private String billingAddress;
	private String accountDetails;
	private List<FileInfo> fileInfo;
	private List<InvoiceLineItemsDTO> lineItems;
	private List<InvoiceTaxDTO> invoiceTax;
	private String inoviceTotal;
	private String hiddenTotal;
	private String comment;
	private String discountValue;
	private String discountTotal;
	private String afterDiscountTotal;
	private String grandTotal;
	private String disCount;
    private String vendorOrgRelationId;
    private String vendorContact;
    private String projectId;
    private String frequency;
    private String vendorCurrency;
    private String hiddenHours;
    private String invoiceforexpenses;
    private String projectMemberId;
    private String expensesId;
    private List<InvoiceExpensesDTO> expenseInvoice;
	public List<InvoiceExpensesDTO> getExpenseInvoice() {
		return expenseInvoice;
	}

	public void setExpenseInvoice(List<InvoiceExpensesDTO> expenseInvoice) {
		this.expenseInvoice = expenseInvoice;
	}

	public String getVendorCurrency() {
		return vendorCurrency;
	}

	public void setVendorCurrency(String vendorCurrency) {
		this.vendorCurrency = vendorCurrency;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getClientOrgRelationId() {
		return clientOrgRelationId;
	}

	public void setClientOrgRelationId(String clientOrgRelationId) {
		this.clientOrgRelationId = clientOrgRelationId;
	}

	public String getSavedInvoiceId() {
		return savedInvoiceId;
	}

	public void setSavedInvoiceId(String savedInvoiceId) {
		this.savedInvoiceId = savedInvoiceId;
	}

	public String getClientCurrency() {
		return clientCurrency;
	}

	public void setClientCurrency(String clientCurrency) {
		this.clientCurrency = clientCurrency;
	}

	public String getClientContact() {
		return clientContact;
	}

	public void setClientContact(String clientContact) {
		this.clientContact = clientContact;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getPaymentFrequency() {
		return paymentFrequency;
	}

	public void setPaymentFrequency(String paymentFrequency) {
		this.paymentFrequency = paymentFrequency;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getAccountDetails() {
		return accountDetails;
	}

	public void setAccountDetails(String accountDetails) {
		this.accountDetails = accountDetails;
	}

	

	public List<InvoiceLineItemsDTO> getLineItems() {
		return lineItems;
	}

	public void setLineItems(List<InvoiceLineItemsDTO> lineItems) {
		this.lineItems = lineItems;
	}

	public List<InvoiceTaxDTO> getInvoiceTax() {
		return invoiceTax;
	}

	public void setInvoiceTax(List<InvoiceTaxDTO> invoiceTax) {
		this.invoiceTax = invoiceTax;
	}

	public String getInoviceTotal() {
		return inoviceTotal;
	}

	public void setInoviceTotal(String inoviceTotal) {
		this.inoviceTotal = inoviceTotal;
	}

	public String getHiddenTotal() {
		return hiddenTotal;
	}

	public void setHiddenTotal(String hiddenTotal) {
		this.hiddenTotal = hiddenTotal;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(String discountValue) {
		this.discountValue = discountValue;
	}

	public String getDiscountTotal() {
		return discountTotal;
	}

	public void setDiscountTotal(String discountTotal) {
		this.discountTotal = discountTotal;
	}

	public String getAfterDiscountTotal() {
		return afterDiscountTotal;
	}

	public void setAfterDiscountTotal(String afterDiscountTotal) {
		this.afterDiscountTotal = afterDiscountTotal;
	}

	public String getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(String grandTotal) {
		this.grandTotal = grandTotal;
	}

	public String getDisCount() {
		return disCount;
	}

	public void setDisCount(String disCount) {
		this.disCount = disCount;
	}


	public List<FileInfo> getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(List<FileInfo> fileInfo) {
		this.fileInfo = fileInfo;
	}

	public String getVendorOrgRelationId() {
		return vendorOrgRelationId;
	}

	public void setVendorOrgRelationId(String vendorOrgRelationId) {
		this.vendorOrgRelationId = vendorOrgRelationId;
	}

	public String getVendorContact() {
		return vendorContact;
	}

	public void setVendorContact(String vendorContact) {
		this.vendorContact = vendorContact;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getHiddenHours() {
		return hiddenHours;
	}

	public void setHiddenHours(String hiddenHours) {
		this.hiddenHours = hiddenHours;
	}

	public String getInvoiceforexpenses() {
		return invoiceforexpenses;
	}

	public void setInvoiceforexpenses(String invoiceforexpenses) {
		this.invoiceforexpenses = invoiceforexpenses;
	}

	public String getProjectMemberId() {
		return projectMemberId;
	}

	public void setProjectMemberId(String projectMemberId) {
		this.projectMemberId = projectMemberId;
	}

	public String getExpensesId() {
		return expensesId;
	}

	public void setExpensesId(String expensesId) {
		this.expensesId = expensesId;
	}

}
