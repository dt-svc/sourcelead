package com.bis.sourcelead.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "INVOICE_PAYMENT")
public class InvoicePayment {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="INVOICE_ID", nullable=false )     
	private Invoice invoiceId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ORG_LOCATION_ID", nullable=false )     
	private OrgLocations orgLocation;
	
	@Column(name = "PAYMENT_DATE")
	private Date paymentDate;
	
	@Column(name = "PAYMENT_METHOD")
	private String paymentMethod;
	
	@Column(name = "PAYMENT_REFERENCE_NUMBER")
	private String paymentReferenceNumber;
	
	@Column(name = "PAYMENT_AMOUNT")
	private String paymentAmount;
	
	@Column(name = "CURRENCY_CODE")
	private String currencyCode;
	
	@Column(name = "PAYMENT_COMMENT",columnDefinition = "TEXT default NULL")
	private String paymentComment;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "FILE_CONTENT_TYPE")
	private String fileContentType;
	
	@Column(name = "FILE_NAME", columnDefinition = "TEXT default NULL")
	private String fileName;
	
	@Column(name = "FILE_URL")
	private String fileURL;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Transient
	@JsonProperty
	private String paymentDateStr;
	
	@Transient
	@JsonProperty
	private String createdByName;
	
	@Transient
	@JsonProperty
	private Double paidAmount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Invoice getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Invoice invoiceId) {
		this.invoiceId = invoiceId;
	}

	public OrgLocations getOrgLocation() {
		return orgLocation;
	}

	public void setOrgLocation(OrgLocations orgLocation) {
		this.orgLocation = orgLocation;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentReferenceNumber() {
		return paymentReferenceNumber;
	}

	public void setPaymentReferenceNumber(String paymentReferenceNumber) {
		this.paymentReferenceNumber = paymentReferenceNumber;
	}

	public String getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getPaymentComment() {
		return paymentComment;
	}

	public void setPaymentComment(String paymentComment) {
		this.paymentComment = paymentComment;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getPaymentDateStr() {
		return paymentDateStr;
	}

	public void setPaymentDateStr(String paymentDateStr) {
		this.paymentDateStr = paymentDateStr;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getCurrencyCode()
	{
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode)
	{
		this.currencyCode = currencyCode;
	}

	public String getFileURL()
	{
		return fileURL;
	}

	public void setFileURL(String fileURL)
	{
		this.fileURL = fileURL;
	}

	public Double getPaidAmount()
	{
		return paidAmount;
	}

	public void setPaidAmount(Double paidAmount)
	{
		this.paidAmount = paidAmount;
	}
	
}
