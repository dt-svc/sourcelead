package com.bis.sourcelead.entity;

import java.util.Date;
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
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.bis.sourcelead.dto.PaymentAccountDTO;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name = "INVOICE")
public class Invoice {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ORG_RELATION_ID", nullable=false )
	@Fetch(FetchMode.JOIN)
	private OrgRelations orgRelationID;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONTACT_USER_ID")
	private User contactUser;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ORG_LOCATION_ID", nullable=false )     
	private OrgLocations orgLocation;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="OWNER_USER_ID", nullable=false )     
	private User ownerUserId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="RESPONDER_LOCATION_ID", nullable=false )     
	private OrgLocations responderLocationId;
	
	@Column(name = "INVOICE_ID")
	private String invoiceId;
	
	
	@ManyToOne
    @JoinColumn(name="PROJECT_ID", nullable=true) 
	private Projects projectID;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PAYMENT_ACCOUNT_ID", nullable=false )     
	private PaymentAccount paymentAccount;
	
	@Column(name="IS_JOB_CLOSURE")     
	private String isJobClosure;
	
	
	@Column(name = "INVOICE_TYPE")
	private String invoiceType;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
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
	
	@Column(name = "ACCOUNT_DETAILS",columnDefinition = "TEXT default NULL")
	private String accountDetails;
	
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
	
	@Column(name = "FILE_URL")
	private String fileUrl;
	
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
	
	@Column(name = "PREVIOUS_INVOICE_STATUS")
	private String previousInvoiceStatus;
	
	@Column(name = "DOC_FILENAME") 
	private String fileName; 
	
	@Column(name = "SUBMISSION_APPROVER_HIERARCHY_ID")
	private String submissionApproverHierarchyId;
	
	@Column(name = "INVOICE_CATEGORY")
	private String invoiceCategory;
	
	@Column(name = "EXPENSES_INVOICES")
	private String expensesInvoices;
	
	@Column(name = "OWNER_USERS")
	private String ownerUsers;
	
	@Column(name = "CONTACT_USERS")
	private String contactUsers;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="EXPENSES_ID", nullable=true) 
	private Expenses expensesId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PROJECT_MEMBER_ID", nullable=true) 
	private ProjectMembers projectMemberId;
	
	@Column(name = "IS_GENERATE_AUTOMATIC_INVOICE")
	private String isAutomaticGenerateInvoice;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TEMPLATE_ID")
	private CustomInvoiceTemplate customInvoiceTemplate;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "invoice")
	private Set<InvoiceLineItems> invoiceLineItems;
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "invoice")
	private Set<InvoiceTax> invoiceTax;
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "invoice")
	private Set<InvoiceDocument> invoiceDocumentSet;
	
	@Transient
	@JsonProperty
	private String clientName;
	
	@Column(name = "NOTIFY_TO_OTHERS", columnDefinition = "LONGTEXT default NULL")
	private String notifyToOthers;
	
	@Transient
	@JsonProperty
	private String clientContactName;
	
	@Transient
	@JsonProperty
	private String clientContactEmail;
	
	@Transient
	@JsonProperty
	private String invoiceDateStr;
	
	@Transient
	@JsonProperty
	private long paymentAccountId;

	@Transient
	@JsonProperty
	private String dueDateStr;

	@Transient
	@JsonProperty
	private Long vendorOrgRelationId;
	
	@Transient
	@JsonProperty
	private Long vendorContactId;
	
	@Transient
	@JsonProperty
	private String orgName;
	
	@JsonProperty
	@Transient
	List<InvoiceLineItems> invoiceLineItemsList;
	
	@Transient
	@JsonProperty
	List<InvoiceDocument> invoiceDocumentsList;
	
	@Transient
	@JsonProperty
	List<InvoiceTax> invoiceTaxList;

	@Transient
	@JsonProperty
	private String projectId;
	
	@Transient
	@JsonProperty
	private String projectName;
	
	
	@Transient
	@JsonProperty
	private Integer draftInvoiceCount;
	
	@Transient
	@JsonProperty
	private Integer paidInvoiceCount;
	
	@Transient
	@JsonProperty
	private String draftInvoiceAmount;
	
	@Transient
	@JsonProperty
	private String paidInvoiceAmount;
	
	@Transient
	@JsonProperty
	private String dueAfterInvoiceAmount;
	
	@Transient
	@JsonProperty
	private String dueBeforeInvoiceAmount;
	
	@Transient
	@JsonProperty
	private Integer dueAfterInvoiceCount;
	
	@Transient
	@JsonProperty
	private Integer dueBeforeInvoiceCount;
	
	@Transient
	@JsonProperty
	private Long invoiceParentId;
	
	@Transient
	@JsonProperty
	private List<String> submissionApproverHierarchyIds;
	
//	  
	
	@Transient
	@JsonProperty
	private float netIncome;
	
	@Transient
	@JsonProperty
	private float recievableAmount;
	
	@Transient
	@JsonProperty
	private float payableAmount;
	
	@Transient
	@JsonProperty
	private String vendorCurrency;
	
	@Transient
	@JsonProperty
	private String clientCurrency;
	
	@Transient
	@JsonProperty
	private String projectMemberName;
	
	@Transient
	@JsonProperty
	private String memberId;
	
	@Transient
	@JsonProperty
	private String expensesFrequency;
	
	@Transient
	@JsonProperty
	private String expId;
	
	@Transient
	@JsonProperty
	private String orgRelationUUID;
	
	@Transient
	@JsonProperty
	private PaymentAccountDTO paymentAccountDTO;
	
	@Transient
	@JsonProperty
	private String timelineHistoryReminder;
	
	@Transient
    @JsonProperty
    private String invoiceTags;
	
	@Transient
    @JsonProperty
    private String billsTags;
	
	@Transient
	@JsonProperty
    private Set<String> tagsList;
	
	@Transient
	@JsonProperty
	private String generateInvoice;
	
	@Transient
	@JsonProperty
	private String generateBill;
	
	@Transient
	@JsonProperty
	private long customInvoiceId;
	
	@Transient
	@JsonProperty
	private String customInvoiceName;
	
	@Transient
	@JsonProperty
	private long dataResponderLocationId;
	
	@Transient
	@JsonProperty
	private String orgType;

	
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

	public String getAccountDetails() {
		return accountDetails;
	}

	public void setAccountDetails(String accountDetails) {
		this.accountDetails = accountDetails;
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

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public String getInvoiceCategory() {
		return invoiceCategory;
	}

	public void setInvoiceCategory(String invoiceCategory) {
		this.invoiceCategory = invoiceCategory;
	}

	public String getExpensesInvoices() {
		return expensesInvoices;
	}

	public void setExpensesInvoices(String expensesInvoices) {
		this.expensesInvoices = expensesInvoices;
	}

	public Expenses getExpensesId() {
		return expensesId;
	}

	public void setExpensesId(Expenses expensesId) {
		this.expensesId = expensesId;
	}

	public ProjectMembers getProjectMemberId() {
		return projectMemberId;
	}

	public void setProjectMemberId(ProjectMembers projectMemberId) {
		this.projectMemberId = projectMemberId;
	}

	public String getProjectMemberName() {
		return projectMemberName;
	}

	public void setProjectMemberName(String projectMemberName) {
		this.projectMemberName = projectMemberName;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getExpensesFrequency() {
		return expensesFrequency;
	}

	public void setExpensesFrequency(String expensesFrequency) {
		this.expensesFrequency = expensesFrequency;
	}

	public String getExpId() {
		return expId;
	}

	public void setExpId(String expId) {
		this.expId = expId;
	}

	public String getIsAutomaticGenerateInvoice() {
		return isAutomaticGenerateInvoice;
	}

	public void setIsAutomaticGenerateInvoice(String isAutomaticGenerateInvoice) {
		this.isAutomaticGenerateInvoice = isAutomaticGenerateInvoice;
	}

	public CustomInvoiceTemplate getCustomInvoiceTemplate() {
		return customInvoiceTemplate;
	}

	public void setCustomInvoiceTemplate(CustomInvoiceTemplate customInvoiceTemplate) {
		this.customInvoiceTemplate = customInvoiceTemplate;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getOrgRelationUUID()
	{
		return orgRelationUUID;
	}

	public void setOrgRelationUUID(String orgRelationUUID)
	{
		this.orgRelationUUID = orgRelationUUID;
	}

	public PaymentAccount getPaymentAccount() {
		return paymentAccount;
	}

	public long getPaymentAccountId() {
		return paymentAccountId;
	}

	public void setPaymentAccount(PaymentAccount paymentAccount) {
		this.paymentAccount = paymentAccount;
	}

	public void setPaymentAccountId(long paymentAccountId) {
		this.paymentAccountId = paymentAccountId;
	}

	public PaymentAccountDTO getPaymentAccountDTO() {
		return paymentAccountDTO;
	}

	public void setPaymentAccountDTO(PaymentAccountDTO paymentAccountDTO) {
		this.paymentAccountDTO = paymentAccountDTO;
	}

	public String getTimelineHistoryReminder() {
		return timelineHistoryReminder;
	}

	public void setTimelineHistoryReminder(String timelineHistoryReminder) {
		this.timelineHistoryReminder = timelineHistoryReminder;
	}
	
	public String getInvoiceTags() {
		return invoiceTags;
	}

	public String getBillsTags() {
		return billsTags;
	}

	public Set<String> getTagsList() {
		return tagsList;
	}

	public void setInvoiceTags(String invoiceTags) {
		this.invoiceTags = invoiceTags;
	}

	public void setBillsTags(String billsTags) {
		this.billsTags = billsTags;
	}

	public void setTagsList(Set<String> tagsList) {
		this.tagsList = tagsList;
	}

	public String getOwnerUsers() {
		return ownerUsers;
	}

	public void setOwnerUsers(String ownerUsers) {
		this.ownerUsers = ownerUsers;
	}

	public String getContactUsers() {
		return contactUsers;
	}

	public void setContactUsers(String contactUsers) {
		this.contactUsers = contactUsers;
	}

	public String getGenerateInvoice() {
		return generateInvoice;
	}

	public void setGenerateInvoice(String generateInvoice) {
		this.generateInvoice = generateInvoice;
	}

	public String getGenerateBill() {
		return generateBill;
	}

	public void setGenerateBill(String generateBill) {
		this.generateBill = generateBill;
	}

	public String getIsJobClosure() {
		return isJobClosure;
	}

	public void setIsJobClosure(String isJobClosure) {
		this.isJobClosure = isJobClosure;
	}

	public long getCustomInvoiceId() {
		return customInvoiceId;
	}

	public void setCustomInvoiceId(long customInvoiceId) {
		this.customInvoiceId = customInvoiceId;
	}

	public String getCustomInvoiceName() {
		return customInvoiceName;
	}

	public void setCustomInvoiceName(String customInvoiceName) {
		this.customInvoiceName = customInvoiceName;
	}

	public long getDataResponderLocationId() {
		return dataResponderLocationId;
	}

	public void setDataResponderLocationId(long dataResponderLocationId) {
		this.dataResponderLocationId = dataResponderLocationId;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getNotifyToOthers() {
		return notifyToOthers;
	}

	public void setNotifyToOthers(String notifyToOthers) {
		this.notifyToOthers = notifyToOthers;
	}

	

	
}
