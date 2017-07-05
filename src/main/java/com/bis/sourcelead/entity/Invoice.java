package com.bis.sourcelead.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table(name = "INVOICE", catalog = "sourcelead")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "invoice")
public class Invoice {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	
	
	@ManyToOne
    @JoinColumn(name="ORG_RELATION_ID", nullable=false )     
	private OrgRelations orgRelationID;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CONTACT_USER_ID")
	private User contactUser;
	
	@ManyToOne
    @JoinColumn(name="ORG_LOCATION_ID", nullable=false )     
	private OrgLocations orgLocation;
	
	@ManyToOne
    @JoinColumn(name="OWNER_USER_ID", nullable=false )     
	private User ownerUserId;
	
	@ManyToOne
    @JoinColumn(name="RESPONDER_LOCATION_ID", nullable=false )     
	private OrgLocations responderLocationId;
	
	@Column(name = "INVOICE_ID")
	private String invoiceId;
	
	@ManyToOne
    @JoinColumn(name="PROJECT_ID", nullable=true) 
	private Projects projectID;
	
	@Column(name = "INVOICE_TYPE")
	private String invoiceType;
	
	@ManyToOne
    @JoinColumn(name="PARENT_INVOICE_ID")        
	private Invoice parentInvoiceId;
	
	@Column(name = "LINK_INVOICE_REF_INVOICE_ID")
	private String linkInvoiceRefInvoiceId;
	
	@Column(name = "INVOICE_UUID")
	private String invoiceUUID;
	
	@Column(name = "LINK_INVOICE_REFERENCE_ID")
	private Long linkInvoiceReferenceId;
	
	@Column(name = "INVOICE_DATE")
	private Date invoiceDate;
	
	@Column(name = "DUE_DATE")
	private Date dueDate;
	
	@Column(name = "BILLING_ADDRESS",columnDefinition = "TEXT default NULL")
	private String billingAddress;
	
	@Column(name = "DISCOUNT_AMOUNT")
	private String discountAmount;
	
	@Column(name = "AFTER_DISCOUNT_VALUE")
	private String afterDiscountValue;
	
	@Column(name = "SUB_TOTAL")
	private String subTotal;
	
	@Column(name = "TOTAL")
	private String total;
	
	@Column(name = "CURRENCY_CODE")
	private String currencyCode;
	
	@Column(name = "EXCHANGE_RATE")
	private String exchangeRate;
	
	@Column(name = "CONVERT_AMOUNT")
	private String convertAmount;
	
	@Column(name = "CONVERT_CURRENCY_CODE")
	private String convertCurrencyCode;
	
	@Column(name = "DUE_AMOUNT")
	private String dueAmount;
	
	@Column(name = "INVOICE_COMMENT",columnDefinition = "TEXT default NULL")
	private String invoiceComment;
	
	@Column(name = "REJECT_REASON",columnDefinition = "TEXT default NULL")
	private String rejectReason;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "INVOICE_STATUS")
	private String invoiceStatus;
	
	@Column(name = "PAYMENT_STATUS")
	private String paymentStatus;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "DISCOUNT_TYPE")
	private String discountType;
	
	@Column(name = "PAYMENT_FREQUENCY")
	private String paymentFrequency;
	
	@Column(name = "DISCOUNT_VALUE")
	private String discountValue;
	
	@Column(name = "IS_SEPARATE_INVOICE")
	private String isSeparateInvoice;
	
	@Column(name = "INVOICE_FREQUENCY")
	private String invoiceFrequency;
	
	@Column(name = "DOC_FILE",columnDefinition="mediumblob")
	
	@Lob private byte[] docFile;
	@Column(name = "DOC_FILENAME") private String fileName; 
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "invoice")
	private Set<InvoiceLineItems> invoiceLineItems;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "invoice")
	private Set<InvoiceTax> invoiceTax;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "invoice")
	private Set<InvoiceDocument> invoiceDocumentSet;
	
	@Transient
	private String clientName;
	
	@Transient
	private String clientContactName;
	
	@Transient
	private String clientContactEmail;
	
	@Transient
	private String invoiceDateStr;
	
	@Transient
	private String dueDateStr;

	@Transient
	private Long vendorOrgRelationId;
	
	@Transient
	private Long vendorContactId;
	
	@Transient
	private String orgName;
	
	@Transient
	List<InvoiceLineItems> invoiceLineItemsList;
	
	@Transient
	List<InvoiceDocument> invoiceDocumentsList;
	
	@Transient
	List<InvoiceTax> invoiceTaxList;

	@Transient
	private String projectId;
	
	@Transient
	private String projectName;
	
	
	@Transient
	private Integer draftInvoiceCount;
	
	@Transient
	private Integer paidInvoiceCount;
	
	@Transient
	private String draftInvoiceAmount;
	
	@Transient
	private String paidInvoiceAmount;
	
	@Transient
	private String dueAfterInvoiceAmount;
	
	@Transient
	private String dueBeforeInvoiceAmount;
	
	@Transient
	private Integer dueAfterInvoiceCount;
	
	@Transient
	private Integer dueBeforeInvoiceCount;
	
	@Transient
	private Long invoiceParentId;
	
	@Transient
	private List<String> submissionApproverHierarchyIds;
	
//	  
	
	@Transient
	private float netIncome;
	
	@Transient
	private float recievableAmount;
	
	@Transient
	private float payableAmount;
	
	@Transient
	private String vendorCurrency;
	
	@Transient
	private String clientCurrency;
	
	
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}


	public Projects getProjectID()
	{
		return projectID;
	}

	public void setProjectID(Projects projectID)
	{
		this.projectID = projectID;
	}

	public OrgRelations getOrgRelationID() {
		return orgRelationID;
	}

	public void setOrgRelationID(OrgRelations orgRelationID) {
		this.orgRelationID = orgRelationID;
	}

	public User getContactUser() {
		return contactUser;
	}

	public void setContactUser(User contactUser) {
		this.contactUser = contactUser;
	}

	public String getInvoiceId()
	{
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId)
	{
		this.invoiceId = invoiceId;
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

	public String getDiscountAmount()
	{
		return discountAmount;
	}

	public void setDiscountAmount(String discountAmount)
	{
		this.discountAmount = discountAmount;
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

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public Date getCreatedDate()
	{
		return createdDate;
	}

	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
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

	public Set<InvoiceLineItems> getInvoiceLineItems()
	{
		return invoiceLineItems;
	}

	public void setInvoiceLineItems(Set<InvoiceLineItems> invoiceLineItems)
	{
		this.invoiceLineItems = invoiceLineItems;
	}

	public Set<InvoiceTax> getInvoiceTax() {
		return invoiceTax;
	}

	public void setInvoiceTax(Set<InvoiceTax> invoiceTax) {
		this.invoiceTax = invoiceTax;
	}
	
	public Set<InvoiceDocument> getInvoiceDocumentSet() {
		return invoiceDocumentSet;
	}

	public void setInvoiceDocumentSet(Set<InvoiceDocument> invoiceDocumentSet) {
		this.invoiceDocumentSet = invoiceDocumentSet;
	}

	public OrgLocations getOrgLocation()
	{
		return orgLocation;
	}

	public void setOrgLocation(OrgLocations orgLocation)
	{
		this.orgLocation = orgLocation;
	}

	public User getOwnerUserId() {
		return ownerUserId;
	}

	public void setOwnerUserId(User ownerUserId) {
		this.ownerUserId = ownerUserId;
	}

	public OrgLocations getResponderLocationId() {
		return responderLocationId;
	}

	public void setResponderLocationId(OrgLocations responderLocationId) {
		this.responderLocationId = responderLocationId;
	}

	public String getInvoiceStatus()
	{
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus)
	{
		this.invoiceStatus = invoiceStatus;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientContactName() {
		return clientContactName;
	}

	public void setClientContactName(String clientContactName) {
		this.clientContactName = clientContactName;
	}

	public String getClientContactEmail() {
		return clientContactEmail;
	}

	public void setClientContactEmail(String clientContactEmail) {
		this.clientContactEmail = clientContactEmail;
	}

	public String getInvoiceDateStr() {
		return invoiceDateStr;
	}

	public void setInvoiceDateStr(String invoiceDateStr) {
		this.invoiceDateStr = invoiceDateStr;
	}

	public String getDueDateStr() {
		return dueDateStr;
	}

	public void setDueDateStr(String dueDateStr) {
		this.dueDateStr = dueDateStr;
	}
	
	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public String getPaymentFrequency() {
		return paymentFrequency;
	}

	public void setPaymentFrequency(String paymentFrequency) {
		this.paymentFrequency = paymentFrequency;
	}

	public String getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(String discountValue) {
		this.discountValue = discountValue;
	}

	public String getIsSeparateInvoice() {
		return isSeparateInvoice;
	}

	public void setIsSeparateInvoice(String isSeparateInvoice) {
		this.isSeparateInvoice = isSeparateInvoice;
	}

	public String getAfterDiscountValue() {
		return afterDiscountValue;
	}

	public void setAfterDiscountValue(String afterDiscountValue) {
		this.afterDiscountValue = afterDiscountValue;
	}

	public Invoice getParentInvoiceId() {
		return parentInvoiceId;
	}

	public void setParentInvoiceId(Invoice parentInvoiceId) {
		this.parentInvoiceId = parentInvoiceId;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getInvoiceUUID() {
		return invoiceUUID;
	}

	public void setInvoiceUUID(String invoiceUUID) {
		this.invoiceUUID = invoiceUUID;
	}


	public Long getVendorOrgRelationId() {
		return vendorOrgRelationId;
	}

	public void setVendorOrgRelationId(Long vendorOrgRelationId) {
		this.vendorOrgRelationId = vendorOrgRelationId;
	}

	

	public Long getVendorContactId() {
		return vendorContactId;
	}

	public void setVendorContactId(Long vendorContactId) {
		this.vendorContactId = vendorContactId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public List<InvoiceLineItems> getInvoiceLineItemsList() {
		return invoiceLineItemsList;
	}

	public void setInvoiceLineItemsList(List<InvoiceLineItems> invoiceLineItemsList) {
		this.invoiceLineItemsList = invoiceLineItemsList;
	}

	public List<InvoiceDocument> getInvoiceDocumentsList() {
		return invoiceDocumentsList;
	}

	public void setInvoiceDocumentsList(List<InvoiceDocument> invoiceDocumentsList) {
		this.invoiceDocumentsList = invoiceDocumentsList;
	}

	public List<InvoiceTax> getInvoiceTaxList() {
		return invoiceTaxList;
	}

	public void setInvoiceTaxList(List<InvoiceTax> invoiceTaxList) {
		this.invoiceTaxList = invoiceTaxList;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Integer getDraftInvoiceCount() {
		return draftInvoiceCount;
	}

	public void setDraftInvoiceCount(Integer draftInvoiceCount) {
		this.draftInvoiceCount = draftInvoiceCount;
	}

	public String getDraftInvoiceAmount() {
		return draftInvoiceAmount;
	}

	public void setDraftInvoiceAmount(String draftInvoiceAmount) {
		this.draftInvoiceAmount = draftInvoiceAmount;
	}

	public String getPaidInvoiceAmount() {
		return paidInvoiceAmount;
	}

	public void setPaidInvoiceAmount(String paidInvoiceAmount) {
		this.paidInvoiceAmount = paidInvoiceAmount;
	}

	public Integer getPaidInvoiceCount() {
		return paidInvoiceCount;
	}

	public void setPaidInvoiceCount(Integer paidInvoiceCount) {
		this.paidInvoiceCount = paidInvoiceCount;
	}

	public String getDueAfterInvoiceAmount() {
		return dueAfterInvoiceAmount;
	}

	public void setDueAfterInvoiceAmount(String dueAfterInvoiceAmount) {
		this.dueAfterInvoiceAmount = dueAfterInvoiceAmount;
	}

	public String getDueBeforeInvoiceAmount() {
		return dueBeforeInvoiceAmount;
	}

	public void setDueBeforeInvoiceAmount(String dueBeforeInvoiceAmount) {
		this.dueBeforeInvoiceAmount = dueBeforeInvoiceAmount;
	}

	public Integer getDueAfterInvoiceCount() {
		return dueAfterInvoiceCount;
	}

	public void setDueAfterInvoiceCount(Integer dueAfterInvoiceCount) {
		this.dueAfterInvoiceCount = dueAfterInvoiceCount;
	}

	public Integer getDueBeforeInvoiceCount() {
		return dueBeforeInvoiceCount;
	}

	public void setDueBeforeInvoiceCount(Integer dueBeforeInvoiceCount) {
		this.dueBeforeInvoiceCount = dueBeforeInvoiceCount;
	}

	public String getInvoiceFrequency()
	{
		return invoiceFrequency;
	}

	public void setInvoiceFrequency(String invoiceFrequency)
	{
		this.invoiceFrequency = invoiceFrequency;
	}

	public String getProjectName()
	{
		return projectName;
	}

	public void setProjectName(String projectName)
	{
		this.projectName = projectName;
	}

	public byte[] getDocFile()
	{
		return docFile;
	}

	public void setDocFile(byte[] docFile)
	{
		this.docFile = docFile;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public Long getInvoiceParentId() {
		return invoiceParentId;
	}

	public void setInvoiceParentId(Long invoiceParentId) {
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

	public String getCurrencyCode()
	{
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode)
	{
		this.currencyCode = currencyCode;
	}

	public String getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getConvertAmount() {
		return convertAmount;
	}

	public void setConvertAmount(String convertAmount) {
		this.convertAmount = convertAmount;
	}

	public String getConvertCurrencyCode() {
		return convertCurrencyCode;
	}

	public void setConvertCurrencyCode(String convertCurrencyCode) {
		this.convertCurrencyCode = convertCurrencyCode;
	}

	public String getRejectReason()
	{
		return rejectReason;
	}

	public void setRejectReason(String rejectReason)
	{
		this.rejectReason = rejectReason;
	}

	public String getLinkInvoiceRefInvoiceId()
	{
		return linkInvoiceRefInvoiceId;
	}

	public void setLinkInvoiceRefInvoiceId(String linkInvoiceRefInvoiceId)
	{
		this.linkInvoiceRefInvoiceId = linkInvoiceRefInvoiceId;
	}

	public Long getLinkInvoiceReferenceId()
	{
		return linkInvoiceReferenceId;
	}

	public void setLinkInvoiceReferenceId(Long linkInvoiceReferenceId)
	{
		this.linkInvoiceReferenceId = linkInvoiceReferenceId;
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

	public String getVendorCurrency() {
		return vendorCurrency;
	}

	public void setVendorCurrency(String vendorCurrency) {
		this.vendorCurrency = vendorCurrency;
	}

	public String getClientCurrency() {
		return clientCurrency;
	}

	public void setClientCurrency(String clientCurrency) {
		this.clientCurrency = clientCurrency;
	}
	
	
}
