package com.bis.sourcelead.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
	
	@Column(name = "INVOICE_ID")
	private String invoiceId;
	
	@Column(name = "INVOICE_TYPE")
	private String invoiceType;
	
	@Column(name = "INVOICE_UUID")
	private String invoiceUUID;
	
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
	
	@Column(name = "DUE_AMOUNT")
	private String dueAmount;
	
	
	@Column(name = "INVOICE_COMMENT",columnDefinition = "TEXT default NULL")
	private String invoiceComment;
	
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
	private String invoiceDateStr;
	
	@Transient
	private String dueDateStr;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
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

}
