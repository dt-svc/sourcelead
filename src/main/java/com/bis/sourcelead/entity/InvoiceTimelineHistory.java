package com.bis.sourcelead.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "INVOICE_TIMELINE_HISTORY")
public class InvoiceTimelineHistory {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "INVOICE_ID")
	private Invoice invoice;

	@ManyToOne
	@JoinColumn(name = "ORG_LOCATION_ID")
	private OrgLocations orgLocation;
	
	@ManyToOne
	@JoinColumn(name = "ORG_RELATION_ID")
	private OrgRelations orgRelation;
	
	@ManyToOne
	@JoinColumn(name = "CONTACT_USER_ID")
	private User contactUser;
	
	@ManyToOne
	@JoinColumn(name = "OWNER_USER_ID")
	private User ownerUser;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@ManyToOne
	@JoinColumn(name = "CREATED_BY")
	private User createdBy;
	
	@ManyToOne
	@JoinColumn(name = "LINKED_CHILD_INVOICE_ID")
	private Invoice linkedChildinvoice;

	@Column(name = "INVOICE_STATUS")
	private String invoiceStatus;
	
	@Column(name = "INVOICE_PAYMENT_REMINDER_ID")
	private Long invoicePaymentReminderId;
	
	@Column(name = "INVOICE_PAYMENT_REMINDER_STATUS")
	private String invoicePaymentReminderStatus;
	
	@Column(name = "INVOICE_PAYMENT_REMINDER_EMAIL_BODY", columnDefinition = "TEXT default NULL")
	private String invoicePaymentReminderEmailBody;
	
	@Column(name = "INVOICE_PAYMENT_REMINDER_EMAIL_FROM")
	private String invoicePaymentReminderEmailFrom;
	
	@Column(name = "INVOICE_PAYMENT_REMINDER_EMAIL_TO")
	private String invoicePaymentReminderEmailTo;
	
	@Column(name = "INVOICE_PAYMENT_REMINDER_EMAIL_SUBJECT")
	private String invoicePaymentReminderEmailSubject;
	
	@Column(name = "REASON", columnDefinition = "TEXT default NULL")
	private String rejectReason;
	
	@Column(name = "OWNER_USERS")
	private String ownerUsers;
	
	@Column(name = "CONTACT_USERS")
	private String contactUsers;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public OrgLocations getOrgLocation() {
		return orgLocation;
	}

	public void setOrgLocation(OrgLocations orgLocation) {
		this.orgLocation = orgLocation;
	}

	public OrgRelations getOrgRelation() {
		return orgRelation;
	}

	public void setOrgRelation(OrgRelations orgRelation) {
		this.orgRelation = orgRelation;
	}

	public User getContactUser() {
		return contactUser;
	}

	public void setContactUser(User contactUser) {
		this.contactUser = contactUser;
	}

	public User getOwnerUser() {
		return ownerUser;
	}

	public void setOwnerUser(User ownerUser) {
		this.ownerUser = ownerUser;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Invoice getLinkedChildinvoice() {
		return linkedChildinvoice;
	}

	public void setLinkedChildinvoice(Invoice linkedChildinvoice) {
		this.linkedChildinvoice = linkedChildinvoice;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public Long getInvoicePaymentReminderId() {
		return invoicePaymentReminderId;
	}

	public void setInvoicePaymentReminderId(Long invoicePaymentReminderId) {
		this.invoicePaymentReminderId = invoicePaymentReminderId;
	}

	public String getInvoicePaymentReminderStatus() {
		return invoicePaymentReminderStatus;
	}

	public void setInvoicePaymentReminderStatus(String invoicePaymentReminderStatus) {
		this.invoicePaymentReminderStatus = invoicePaymentReminderStatus;
	}

	public String getInvoicePaymentReminderEmailBody() {
		return invoicePaymentReminderEmailBody;
	}

	public void setInvoicePaymentReminderEmailBody(String invoicePaymentReminderEmailBody) {
		this.invoicePaymentReminderEmailBody = invoicePaymentReminderEmailBody;
	}

	public String getInvoicePaymentReminderEmailFrom() {
		return invoicePaymentReminderEmailFrom;
	}

	public void setInvoicePaymentReminderEmailFrom(String invoicePaymentReminderEmailFrom) {
		this.invoicePaymentReminderEmailFrom = invoicePaymentReminderEmailFrom;
	}

	public String getInvoicePaymentReminderEmailTo() {
		return invoicePaymentReminderEmailTo;
	}

	public void setInvoicePaymentReminderEmailTo(String invoicePaymentReminderEmailTo) {
		this.invoicePaymentReminderEmailTo = invoicePaymentReminderEmailTo;
	}

	public String getInvoicePaymentReminderEmailSubject() {
		return invoicePaymentReminderEmailSubject;
	}

	public void setInvoicePaymentReminderEmailSubject(String invoicePaymentReminderEmailSubject) {
		this.invoicePaymentReminderEmailSubject = invoicePaymentReminderEmailSubject;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public String getOwnerUsers()
	{
		return ownerUsers;
	}

	public void setOwnerUsers(String ownerUsers)
	{
		this.ownerUsers = ownerUsers;
	}

	public String getContactUsers()
	{
		return contactUsers;
	}

	public void setContactUsers(String contactUsers)
	{
		this.contactUsers = contactUsers;
	}
	
	
	
	
}
