package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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

	@Entity
	@Table(name = "REQUIREMENT_OFFERS", catalog = "sourcelead")
	@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "requirementOffers")
	public class RequirementOffers implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name="SUBMISSION_ID", nullable=false )        
    private RequirementSubmission submissionId;
	
	@Column(name = "DATE_OF_JOINING")
	private Date dateOfJoining;
	
	@Column(name = "PAYMENT_TYPE")
	private String paymentType;
	
	@Column(name = "AMOUNT")
	private String amount;
	
	@Column(name = "Offer_Status")
	private String offerStatus;
	
	
	@Column(name = "PAYMENT_MODE")
	private String paymentMode;
	
	@Column(name = "POSITION_TYPE")
	private String positionType;
	
	@Column(name = "Notify_To_Vendor")
	private String notifyToVendor;
	
	@Column(name = "Notify_To_Applicant")
	private String notifyToApplicant;
	
	@Column(name = "Rejected_Reason" , columnDefinition = "TEXT default NULL")
	private String rejectedReason;
	
	@Column(name = " DOC_FILE",columnDefinition="mediumblob")
	@Lob
	private  byte[] docFile;
	
	@Column(name = "FILE_CONTENT_TYPE")
	private String fileContentType;
	
	@Column(name = "FILE_NAME", columnDefinition = "TEXT default NULL")
	private String fileName;
	
	@Column(name="STATUS")
	private Integer status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Transient
    private String dateOfJoiningStr;
	
	@Transient
    private String clientName;
	
	@Transient
    private Boolean requirementReleasedOrNot;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RequirementSubmission getSubmissionId() {
		return submissionId;
	}

	public void setSubmissionId(RequirementSubmission submissionId) {
		this.submissionId = submissionId;
	}

	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getPositionType() {
		return positionType;
	}

	public void setPositionType(String positionType) {
		this.positionType = positionType;
	}

	public String getNotifyToVendor() {
		return notifyToVendor;
	}

	public void setNotifyToVendor(String notifyToVendor) {
		this.notifyToVendor = notifyToVendor;
	}

	public String getNotifyToApplicant() {
		return notifyToApplicant;
	}

	public void setNotifyToApplicant(String notifyToApplicant) {
		this.notifyToApplicant = notifyToApplicant;
	}

	public String getRejectedReason() {
		return rejectedReason;
	}

	public void setRejectedReason(String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}

	public byte[] getDocFile() {
		return docFile;
	}

	public void setDocFile(byte[] docFile) {
		this.docFile = docFile;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getDateOfJoiningStr() {
		return dateOfJoiningStr;
	}

	public void setDateOfJoiningStr(String dateOfJoiningStr) {
		this.dateOfJoiningStr = dateOfJoiningStr;
	}

	public String getOfferStatus() {
		return offerStatus;
	}

	public void setOfferStatus(String offerStatus) {
		this.offerStatus = offerStatus;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Boolean getRequirementReleasedOrNot() {
		return requirementReleasedOrNot;
	}

	public void setRequirementReleasedOrNot(Boolean requirementReleasedOrNot) {
		this.requirementReleasedOrNot = requirementReleasedOrNot;
	}
	
	
}
