package com.bis.sourcelead.dto;

import java.util.List;
import java.util.Set;

import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.InvoiceDocument;

public class InvoiceTimelineDTO {

	private String status;
	private String statusValue;
	private String dateValue;
	private String timeValue;
	private String reason;
	private String paymentMethod;
	private String paymentReferenceNum;
	private String paymentAmount;
	private String paymentCurrency;
	private String invoicePaymentReminderStatus;
	private String paymentDate;
	private String invoiceReminderEmailBody;
	private String invoiceReminderEmailFrom;
	private String invoiceReminderEmailTo;
	private String invoiceReminderEmailSubject;
	private String paymentComment;
	private EmailAttachment emailAttachment;
	private List<EmailAttachment> emailAttachmentList;
	private String invoiceId;
	private String invoiceUUID;
	private String ownerName;
	private String statusBy;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusValue() {
		return statusValue;
	}
	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}
	public String getDateValue() {
		return dateValue;
	}
	public void setDateValue(String dateValue) {
		this.dateValue = dateValue;
	}
	public String getTimeValue() {
		return timeValue;
	}
	public void setTimeValue(String timeValue) {
		this.timeValue = timeValue;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getPaymentReferenceNum() {
		return paymentReferenceNum;
	}
	public void setPaymentReferenceNum(String paymentReferenceNum) {
		this.paymentReferenceNum = paymentReferenceNum;
	}
	public String getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public String getPaymentCurrency() {
		return paymentCurrency;
	}
	public void setPaymentCurrency(String paymentCurrency) {
		this.paymentCurrency = paymentCurrency;
	}
	public String getInvoicePaymentReminderStatus() {
		return invoicePaymentReminderStatus;
	}
	public void setInvoicePaymentReminderStatus(String invoicePaymentReminderStatus) {
		this.invoicePaymentReminderStatus = invoicePaymentReminderStatus;
	}
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getInvoiceReminderEmailBody() {
		return invoiceReminderEmailBody;
	}
	public void setInvoiceReminderEmailBody(String invoiceReminderEmailBody) {
		this.invoiceReminderEmailBody = invoiceReminderEmailBody;
	}
	public String getInvoiceReminderEmailFrom() {
		return invoiceReminderEmailFrom;
	}
	public void setInvoiceReminderEmailFrom(String invoiceReminderEmailFrom) {
		this.invoiceReminderEmailFrom = invoiceReminderEmailFrom;
	}
	public String getInvoiceReminderEmailTo() {
		return invoiceReminderEmailTo;
	}
	public void setInvoiceReminderEmailTo(String invoiceReminderEmailTo) {
		this.invoiceReminderEmailTo = invoiceReminderEmailTo;
	}
	public String getInvoiceReminderEmailSubject() {
		return invoiceReminderEmailSubject;
	}
	public void setInvoiceReminderEmailSubject(String invoiceReminderEmailSubject) {
		this.invoiceReminderEmailSubject = invoiceReminderEmailSubject;
	}
	public String getPaymentComment() {
		return paymentComment;
	}
	public void setPaymentComment(String paymentComment) {
		this.paymentComment = paymentComment;
	}
	public List<EmailAttachment> getEmailAttachmentList() {
		return emailAttachmentList;
	}
	public void setEmailAttachmentList(List<EmailAttachment> emailAttachmentList) {
		this.emailAttachmentList = emailAttachmentList;
	}
	public EmailAttachment getEmailAttachment() {
		return emailAttachment;
	}
	public void setEmailAttachment(EmailAttachment emailAttachment) {
		this.emailAttachment = emailAttachment;
	}
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	public String getInvoiceUUID() {
		return invoiceUUID;
	}
	public void setInvoiceUUID(String invoiceUUID) {
		this.invoiceUUID = invoiceUUID;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getStatusBy() {
		return statusBy;
	}
	public void setStatusBy(String statusBy) {
		this.statusBy = statusBy;
	}
	
}
