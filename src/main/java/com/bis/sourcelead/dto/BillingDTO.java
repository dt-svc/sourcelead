package com.bis.sourcelead.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import com.bis.sourcelead.annotations.CleanHtml;
import com.bis.sourcelead.entity.InvoiceDocument;
import com.bis.sourcelead.entity.InvoiceLineItems;
import com.bis.sourcelead.entity.InvoiceTax;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BillingDTO {
	
	private Long id;
	private Long orgRelationID;
	private Long contactUser;
	private Long orgLocation;
	private Long ownerUserId;
	private Long responderLocationId;
	private String invoiceId;
	private Long projectID;
	private String invoiceType;
	private Long parentInvoiceId;
	private String linkInvoiceRefInvoiceId;
	private String invoiceUUID;
	private Long linkInvoiceReferenceId;
	private Date invoiceDate;
	private Date dueDate;
	@CleanHtml(escape=false)
	private String billingAddress;
	@CleanHtml(escape=false)
	private String accountDetails;
	private String discountAmount;
	private String afterDiscountValue;
	private String subTotal;
	private String total;
	private String currencyCode;
	private String exchangeRate;
	private String convertAmount;
	private String convertCurrencyCode;
	private String dueAmount;
	private String invoiceComment;
	private String rejectReason;
	private String status;
	private String invoiceStatus;
	private String paymentStatus;
	private Date createdDate;
	private String fileUrl;
	private String createdBy;
	private Date updatedDate;
	private String updatedBy;
	private String discountType;
	private String paymentFrequency;
	private String discountValue;
	private String isSeparateInvoice;
	private String invoiceFrequency;
	private String previousInvoiceStatus;
	private String submissionApproverHierarchyId;
	private String invoiceCategory;
	private String expensesInvoices;
	private Long expensesId;
	private Long projectMemberId;
	private String isAutomaticGenerateInvoice;
	private List<InvoiceLineItems> invoiceLineItems;
	private List<InvoiceTax> invoiceTax;
	private List<InvoiceDocument> invoiceDocumentSet;
	private String clientName;
	private String clientContactName;
	private String clientContactEmail;
	private String invoiceDateStr;
	private String dueDateStr;
	private Long vendorOrgRelationId;
	private Long vendorContactId;
	private String orgName;
	List<InvoiceLineItems> invoiceLineItemsList;
	List<InvoiceDocument> invoiceDocumentsList;
	List<InvoiceTax> invoiceTaxList;
	private String projectId;
	private String projectName;
	private Integer draftInvoiceCount;
	private Integer paidInvoiceCount;
	private String draftInvoiceAmount;
	private String paidInvoiceAmount;
	private String dueAfterInvoiceAmount;
	private String dueBeforeInvoiceAmount;
	private Integer dueAfterInvoiceCount;
	private Integer dueBeforeInvoiceCount;
	private Long invoiceParentId;
	private List<String> submissionApproverHierarchyIds;
	private float netIncome;
	private float recievableAmount;
	private float payableAmount;
	private String vendorCurrency;
	private String clientCurrency;
	private String projectMemberName;
	private String memberId;
	private String expensesFrequency;
	private String expId;
	private PaymentAccountDTO paymentAccountDTO;
	private String orgRelationUUID;
	private String orgType;
	
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public Long getOrgRelationID()
	{
		return orgRelationID;
	}
	public void setOrgRelationID(Long orgRelationID)
	{
		this.orgRelationID = orgRelationID;
	}
	public Long getContactUser()
	{
		return contactUser;
	}
	public void setContactUser(Long contactUser)
	{
		this.contactUser = contactUser;
	}
	public Long getOrgLocation()
	{
		return orgLocation;
	}
	public void setOrgLocation(Long orgLocation)
	{
		this.orgLocation = orgLocation;
	}
	public Long getOwnerUserId()
	{
		return ownerUserId;
	}
	public void setOwnerUserId(Long ownerUserId)
	{
		this.ownerUserId = ownerUserId;
	}
	public Long getResponderLocationId()
	{
		return responderLocationId;
	}
	public void setResponderLocationId(Long responderLocationId)
	{
		this.responderLocationId = responderLocationId;
	}
	public String getInvoiceId()
	{
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId)
	{
		this.invoiceId = invoiceId;
	}
	public Long getProjectID()
	{
		return projectID;
	}
	public void setProjectID(Long projectID)
	{
		this.projectID = projectID;
	}
	public String getInvoiceType()
	{
		return invoiceType;
	}
	public void setInvoiceType(String invoiceType)
	{
		this.invoiceType = invoiceType;
	}
	public Long getParentInvoiceId()
	{
		return parentInvoiceId;
	}
	public void setParentInvoiceId(Long parentInvoiceId)
	{
		this.parentInvoiceId = parentInvoiceId;
	}
	public String getLinkInvoiceRefInvoiceId()
	{
		return linkInvoiceRefInvoiceId;
	}
	public void setLinkInvoiceRefInvoiceId(String linkInvoiceRefInvoiceId)
	{
		this.linkInvoiceRefInvoiceId = linkInvoiceRefInvoiceId;
	}
	public String getInvoiceUUID()
	{
		return invoiceUUID;
	}
	public void setInvoiceUUID(String invoiceUUID)
	{
		this.invoiceUUID = invoiceUUID;
	}
	public Long getLinkInvoiceReferenceId()
	{
		return linkInvoiceReferenceId;
	}
	public void setLinkInvoiceReferenceId(Long linkInvoiceReferenceId)
	{
		this.linkInvoiceReferenceId = linkInvoiceReferenceId;
	}
	public Date getInvoiceDate()
	{
		return invoiceDate;
	}
	public void setInvoiceDate(Date invoiceDate)
	{
		this.invoiceDate = invoiceDate;
	}
	public Date getDueDate()
	{
		return dueDate;
	}
	public void setDueDate(Date dueDate)
	{
		this.dueDate = dueDate;
	}
	public String getBillingAddress()
	{
		return billingAddress;
	}
	public void setBillingAddress(String billingAddress)
	{
		this.billingAddress = billingAddress;
	}
	public String getAccountDetails()
	{
		return accountDetails;
	}
	public void setAccountDetails(String accountDetails)
	{
		this.accountDetails = accountDetails;
	}
	public String getDiscountAmount()
	{
		return discountAmount;
	}
	public void setDiscountAmount(String discountAmount)
	{
		this.discountAmount = discountAmount;
	}
	public String getAfterDiscountValue()
	{
		return afterDiscountValue;
	}
	public void setAfterDiscountValue(String afterDiscountValue)
	{
		this.afterDiscountValue = afterDiscountValue;
	}
	public String getSubTotal()
	{
		return subTotal;
	}
	public void setSubTotal(String subTotal)
	{
		this.subTotal = subTotal;
	}
	public String getTotal()
	{
		return total;
	}
	public void setTotal(String total)
	{
		this.total = total;
	}
	public String getCurrencyCode()
	{
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode)
	{
		this.currencyCode = currencyCode;
	}
	public String getExchangeRate()
	{
		return exchangeRate;
	}
	public void setExchangeRate(String exchangeRate)
	{
		this.exchangeRate = exchangeRate;
	}
	public String getConvertAmount()
	{
		return convertAmount;
	}
	public void setConvertAmount(String convertAmount)
	{
		this.convertAmount = convertAmount;
	}
	public String getConvertCurrencyCode()
	{
		return convertCurrencyCode;
	}
	public void setConvertCurrencyCode(String convertCurrencyCode)
	{
		this.convertCurrencyCode = convertCurrencyCode;
	}
	public String getDueAmount()
	{
		return dueAmount;
	}
	public void setDueAmount(String dueAmount)
	{
		this.dueAmount = dueAmount;
	}
	public String getInvoiceComment()
	{
		return invoiceComment;
	}
	public void setInvoiceComment(String invoiceComment)
	{
		this.invoiceComment = invoiceComment;
	}
	public String getRejectReason()
	{
		return rejectReason;
	}
	public void setRejectReason(String rejectReason)
	{
		this.rejectReason = rejectReason;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public String getInvoiceStatus()
	{
		return invoiceStatus;
	}
	public void setInvoiceStatus(String invoiceStatus)
	{
		this.invoiceStatus = invoiceStatus;
	}
	public String getPaymentStatus()
	{
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus)
	{
		this.paymentStatus = paymentStatus;
	}
	public Date getCreatedDate()
	{
		return createdDate;
	}
	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}
	public String getFileUrl()
	{
		return fileUrl;
	}
	public void setFileUrl(String fileUrl)
	{
		this.fileUrl = fileUrl;
	}
	public String getCreatedBy()
	{
		return createdBy;
	}
	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}
	public Date getUpdatedDate()
	{
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate)
	{
		this.updatedDate = updatedDate;
	}
	public String getUpdatedBy()
	{
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy)
	{
		this.updatedBy = updatedBy;
	}
	public String getDiscountType()
	{
		return discountType;
	}
	public void setDiscountType(String discountType)
	{
		this.discountType = discountType;
	}
	public String getPaymentFrequency()
	{
		return paymentFrequency;
	}
	public void setPaymentFrequency(String paymentFrequency)
	{
		this.paymentFrequency = paymentFrequency;
	}
	public String getDiscountValue()
	{
		return discountValue;
	}
	public void setDiscountValue(String discountValue)
	{
		this.discountValue = discountValue;
	}
	public String getIsSeparateInvoice()
	{
		return isSeparateInvoice;
	}
	public void setIsSeparateInvoice(String isSeparateInvoice)
	{
		this.isSeparateInvoice = isSeparateInvoice;
	}
	public String getInvoiceFrequency()
	{
		return invoiceFrequency;
	}
	public void setInvoiceFrequency(String invoiceFrequency)
	{
		this.invoiceFrequency = invoiceFrequency;
	}
	public String getPreviousInvoiceStatus()
	{
		return previousInvoiceStatus;
	}
	public void setPreviousInvoiceStatus(String previousInvoiceStatus)
	{
		this.previousInvoiceStatus = previousInvoiceStatus;
	}
	public String getSubmissionApproverHierarchyId()
	{
		return submissionApproverHierarchyId;
	}
	public void setSubmissionApproverHierarchyId(String submissionApproverHierarchyId)
	{
		this.submissionApproverHierarchyId = submissionApproverHierarchyId;
	}
	public String getInvoiceCategory()
	{
		return invoiceCategory;
	}
	public void setInvoiceCategory(String invoiceCategory)
	{
		this.invoiceCategory = invoiceCategory;
	}
	public String getExpensesInvoices()
	{
		return expensesInvoices;
	}
	public void setExpensesInvoices(String expensesInvoices)
	{
		this.expensesInvoices = expensesInvoices;
	}
	public Long getExpensesId()
	{
		return expensesId;
	}
	public void setExpensesId(Long expensesId)
	{
		this.expensesId = expensesId;
	}
	public Long getProjectMemberId()
	{
		return projectMemberId;
	}
	public void setProjectMemberId(Long projectMemberId)
	{
		this.projectMemberId = projectMemberId;
	}
	public String getIsAutomaticGenerateInvoice()
	{
		return isAutomaticGenerateInvoice;
	}
	public void setIsAutomaticGenerateInvoice(String isAutomaticGenerateInvoice)
	{
		this.isAutomaticGenerateInvoice = isAutomaticGenerateInvoice;
	}
	public List<InvoiceLineItems> getInvoiceLineItems()
	{
		return invoiceLineItems;
	}
	public void setInvoiceLineItems(List<InvoiceLineItems> invoiceLineItems)
	{
		this.invoiceLineItems = invoiceLineItems;
	}
	public List<InvoiceTax> getInvoiceTax()
	{
		return invoiceTax;
	}
	public void setInvoiceTax(List<InvoiceTax> invoiceTax)
	{
		this.invoiceTax = invoiceTax;
	}
	public List<InvoiceDocument> getInvoiceDocumentSet()
	{
		return invoiceDocumentSet;
	}
	public void setInvoiceDocumentSet(List<InvoiceDocument> invoiceDocumentSet)
	{
		this.invoiceDocumentSet = invoiceDocumentSet;
	}
	public String getClientName()
	{
		return clientName;
	}
	public void setClientName(String clientName)
	{
		this.clientName = clientName;
	}
	public String getClientContactName()
	{
		return clientContactName;
	}
	public void setClientContactName(String clientContactName)
	{
		this.clientContactName = clientContactName;
	}
	public String getClientContactEmail()
	{
		return clientContactEmail;
	}
	public void setClientContactEmail(String clientContactEmail)
	{
		this.clientContactEmail = clientContactEmail;
	}
	public String getInvoiceDateStr()
	{
		return invoiceDateStr;
	}
	public void setInvoiceDateStr(String invoiceDateStr)
	{
		this.invoiceDateStr = invoiceDateStr;
	}
	public String getDueDateStr()
	{
		return dueDateStr;
	}
	public void setDueDateStr(String dueDateStr)
	{
		this.dueDateStr = dueDateStr;
	}
	public Long getVendorOrgRelationId()
	{
		return vendorOrgRelationId;
	}
	public void setVendorOrgRelationId(Long vendorOrgRelationId)
	{
		this.vendorOrgRelationId = vendorOrgRelationId;
	}
	public Long getVendorContactId()
	{
		return vendorContactId;
	}
	public void setVendorContactId(Long vendorContactId)
	{
		this.vendorContactId = vendorContactId;
	}
	public String getOrgName()
	{
		return orgName;
	}
	public void setOrgName(String orgName)
	{
		this.orgName = orgName;
	}
	public List<InvoiceLineItems> getInvoiceLineItemsList()
	{
		return invoiceLineItemsList;
	}
	public void setInvoiceLineItemsList(List<InvoiceLineItems> invoiceLineItemsList)
	{
		this.invoiceLineItemsList = invoiceLineItemsList;
	}
	public List<InvoiceDocument> getInvoiceDocumentsList()
	{
		return invoiceDocumentsList;
	}
	public void setInvoiceDocumentsList(List<InvoiceDocument> invoiceDocumentsList)
	{
		this.invoiceDocumentsList = invoiceDocumentsList;
	}
	public List<InvoiceTax> getInvoiceTaxList()
	{
		return invoiceTaxList;
	}
	public void setInvoiceTaxList(List<InvoiceTax> invoiceTaxList)
	{
		this.invoiceTaxList = invoiceTaxList;
	}
	public String getProjectId()
	{
		return projectId;
	}
	public void setProjectId(String projectId)
	{
		this.projectId = projectId;
	}
	public String getProjectName()
	{
		return projectName;
	}
	public void setProjectName(String projectName)
	{
		this.projectName = projectName;
	}
	public Integer getDraftInvoiceCount()
	{
		return draftInvoiceCount;
	}
	public void setDraftInvoiceCount(Integer draftInvoiceCount)
	{
		this.draftInvoiceCount = draftInvoiceCount;
	}
	public Integer getPaidInvoiceCount()
	{
		return paidInvoiceCount;
	}
	public void setPaidInvoiceCount(Integer paidInvoiceCount)
	{
		this.paidInvoiceCount = paidInvoiceCount;
	}
	public String getDraftInvoiceAmount()
	{
		return draftInvoiceAmount;
	}
	public void setDraftInvoiceAmount(String draftInvoiceAmount)
	{
		this.draftInvoiceAmount = draftInvoiceAmount;
	}
	public String getPaidInvoiceAmount()
	{
		return paidInvoiceAmount;
	}
	public void setPaidInvoiceAmount(String paidInvoiceAmount)
	{
		this.paidInvoiceAmount = paidInvoiceAmount;
	}
	public String getDueAfterInvoiceAmount()
	{
		return dueAfterInvoiceAmount;
	}
	public void setDueAfterInvoiceAmount(String dueAfterInvoiceAmount)
	{
		this.dueAfterInvoiceAmount = dueAfterInvoiceAmount;
	}
	public String getDueBeforeInvoiceAmount()
	{
		return dueBeforeInvoiceAmount;
	}
	public void setDueBeforeInvoiceAmount(String dueBeforeInvoiceAmount)
	{
		this.dueBeforeInvoiceAmount = dueBeforeInvoiceAmount;
	}
	public Integer getDueAfterInvoiceCount()
	{
		return dueAfterInvoiceCount;
	}
	public void setDueAfterInvoiceCount(Integer dueAfterInvoiceCount)
	{
		this.dueAfterInvoiceCount = dueAfterInvoiceCount;
	}
	public Integer getDueBeforeInvoiceCount()
	{
		return dueBeforeInvoiceCount;
	}
	public void setDueBeforeInvoiceCount(Integer dueBeforeInvoiceCount)
	{
		this.dueBeforeInvoiceCount = dueBeforeInvoiceCount;
	}
	public Long getInvoiceParentId()
	{
		return invoiceParentId;
	}
	public void setInvoiceParentId(Long invoiceParentId)
	{
		this.invoiceParentId = invoiceParentId;
	}
	public List<String> getSubmissionApproverHierarchyIds()
	{
		return submissionApproverHierarchyIds;
	}
	public void setSubmissionApproverHierarchyIds(List<String> submissionApproverHierarchyIds)
	{
		this.submissionApproverHierarchyIds = submissionApproverHierarchyIds;
	}
	public float getNetIncome()
	{
		return netIncome;
	}
	public void setNetIncome(float netIncome)
	{
		this.netIncome = netIncome;
	}
	public float getRecievableAmount()
	{
		return recievableAmount;
	}
	public void setRecievableAmount(float recievableAmount)
	{
		this.recievableAmount = recievableAmount;
	}
	public float getPayableAmount()
	{
		return payableAmount;
	}
	public void setPayableAmount(float payableAmount)
	{
		this.payableAmount = payableAmount;
	}
	public String getVendorCurrency()
	{
		return vendorCurrency;
	}
	public void setVendorCurrency(String vendorCurrency)
	{
		this.vendorCurrency = vendorCurrency;
	}
	public String getClientCurrency()
	{
		return clientCurrency;
	}
	public void setClientCurrency(String clientCurrency)
	{
		this.clientCurrency = clientCurrency;
	}
	public String getProjectMemberName()
	{
		return projectMemberName;
	}
	public void setProjectMemberName(String projectMemberName)
	{
		this.projectMemberName = projectMemberName;
	}
	public String getMemberId()
	{
		return memberId;
	}
	public void setMemberId(String memberId)
	{
		this.memberId = memberId;
	}
	public String getExpensesFrequency()
	{
		return expensesFrequency;
	}
	public void setExpensesFrequency(String expensesFrequency)
	{
		this.expensesFrequency = expensesFrequency;
	}
	public String getExpId()
	{
		return expId;
	}
	public void setExpId(String expId)
	{
		this.expId = expId;
	}
	public String getOrgRelationUUID()
	{
		return orgRelationUUID;
	}
	public void setOrgRelationUUID(String orgRelationUUID)
	{
		this.orgRelationUUID = orgRelationUUID;
	}
	public PaymentAccountDTO getPaymentAccountDTO() {
		return paymentAccountDTO;
	}
	public void setPaymentAccountDTO(PaymentAccountDTO paymentAccountDTO) {
		this.paymentAccountDTO = paymentAccountDTO;
	}
	public String getOrgType() {
		return orgType;
	}
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	
}
