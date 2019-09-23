package com.bis.sourcelead.dto;

import java.util.List;
import java.util.Map;

import com.bis.sourcelead.annotations.CleanHtml;
import com.bis.sourcelead.annotations.Required;

public class InvoiceDTO extends SearchDTO {

	@Required (desc = "ClientOrgRelationId", dependsOn = "clientOrgRelation", parentValue = "createInvoice")
	private String clientOrgRelationId;
	
	private String savedInvoiceId;
	private String clientCurrency;
	
	@Required (desc = "ClientContact", either = "vendorContact")
	private String clientContact;
	
	@Required (desc = "InvoiceId")
	private String invoiceId;
	
	@Required (desc = "InvoiceDate")
	private String invoiceDate;
	
	private String paymentFrequency;
	
	@Required (desc = "DueDate")
	private String dueDate;
	
	@CleanHtml(escape=false)
	private String billingAddress;
	
	@CleanHtml(escape=false)
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
	private String isJobClosure;
	
	@Required (desc = "vendorOrgRelationId", dependsOn = "vendorOrgRelation", parentValue = "createPayables")
    private String vendorOrgRelationId;
	
	@Required (desc = "vendorContact", either = "clientContact")
    private String vendorContact;
	
	@Required (desc = "projectId", dependsOn = "project", parentValueAny = true)
    private String projectId;
	
	@Required (desc = "frequency", dependsOn = "frequency", parentValueAny = true)
    private String frequency;
	
    private String vendorCurrency;
    private String hiddenHours;
    private String invoiceforexpenses;
    private String projectMemberId;
    private String expensesId;
   
    private List<InvoiceExpensesDTO> expenseInvoice;
    private String staticPath;
    
    private String clientName;
    
    private String projectName;
    private String inoviceDue;
    private String invoiceStatus;
    private String paymentStatus;
    private String action;
    private String dayWisetype;
    private String startDate;
    private String endDate;
    private String invoiceType;
    private String exportType;
    private String durationType;
    private String exportStartDate;
    private String exportEndDate;
    private String vendorName;
    private String locationdateForList;
    private String orgLocationId;
    private String orgRelationId;
    private String paymentAccount;
    private String invoiceUUID;
    private String count;
    private String tags;
    private String invoiceDeleteStatus;
    private long invoiceTemplateId;
    private String notifyToOthers;
    
	public long getInvoiceTemplateId() {
		return invoiceTemplateId;
	}

	public void setInvoiceTemplateId(long invoiceTemplateId) {
		this.invoiceTemplateId = invoiceTemplateId;
	}

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

	public String getStaticPath() {
		return staticPath;
	}

	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getInoviceDue() {
		return inoviceDue;
	}

	public void setInoviceDue(String inoviceDue) {
		this.inoviceDue = inoviceDue;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getDayWisetype() {
		return dayWisetype;
	}

	public void setDayWisetype(String dayWisetype) {
		this.dayWisetype = dayWisetype;
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

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getExportType() {
		return exportType;
	}

	public void setExportType(String exportType) {
		this.exportType = exportType;
	}

	public String getDurationType() {
		return durationType;
	}

	public void setDurationType(String durationType) {
		this.durationType = durationType;
	}

	public String getExportStartDate() {
		return exportStartDate;
	}

	public void setExportStartDate(String exportStartDate) {
		this.exportStartDate = exportStartDate;
	}

	public String getExportEndDate() {
		return exportEndDate;
	}

	public void setExportEndDate(String exportEndDate) {
		this.exportEndDate = exportEndDate;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getLocationdateForList() {
		return locationdateForList;
	}

	public void setLocationdateForList(String locationdateForList) {
		this.locationdateForList = locationdateForList;
	}

	public String getOrgLocationId()
	{
		return orgLocationId;
	}

	public void setOrgLocationId(String orgLocationId)
	{
		this.orgLocationId = orgLocationId;
	}

	public String getPaymentAccount() {
		return paymentAccount;
	}

	public void setPaymentAccount(String paymentAccount) {
		this.paymentAccount = paymentAccount;
	}

	public String getInvoiceUUID()
	{
		return invoiceUUID;
	}

	public void setInvoiceUUID(String invoiceUUID)
	{
		this.invoiceUUID = invoiceUUID;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}


	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getOrgRelationId() {
		return orgRelationId;
	}

	public void setOrgRelationId(String orgRelationId) {
		this.orgRelationId = orgRelationId;
	}

	public String getInvoiceDeleteStatus() {
		return invoiceDeleteStatus;
	}

	public void setInvoiceDeleteStatus(String invoiceDeleteStatus) {
		this.invoiceDeleteStatus = invoiceDeleteStatus;
	}


	public String getIsJobClosure() {
		return isJobClosure;
	}

	public void setIsJobClosure(String isJobClosure) {
		this.isJobClosure = isJobClosure;
	}

	public String getNotifyToOthers() {
		return notifyToOthers;
	}

	public void setNotifyToOthers(String notifyToOthers) {
		this.notifyToOthers = notifyToOthers;
	}
	

}
