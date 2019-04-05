package com.bis.sourcelead.entity;

import java.io.Serializable;
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
	@Table(name = "REQUIREMENT_OFFERS")
	public class RequirementOffers implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne(fetch = FetchType.LAZY)
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
	
	@Column(name = "OFFER_UUID")
	private String offerUUID;
	
	
	@Column(name = "PAYMENT_MODE")
	private String paymentMode;
	
	@Column(name = "POSITION_TYPE")
	private String positionType;
	
	@Column(name = "OTHER_POSITION_TYPE")
	private String otherPositionType;
	
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
	
	@Column(name = "OFFER_EXPIRE_DATE")
	private Date offerExpireDate;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="OFFER_LETTER_TEMPLATE", nullable=false )        
    private OfferLetterTemplate offerLetterTemplate;
	
	
	@Transient
	@JsonProperty
    private String dateOfJoiningStr;
	
	@Transient
	@JsonProperty
    private String updatedDateStr;
	
	@Transient
	@JsonProperty
    private String clientName;
	
	@Transient
    private Boolean requirementReleasedOrNot;
	
	@Transient
	@JsonProperty
    private String jobTitle;
	
	@Transient
    private Long candidateId;
	
	@Transient
	@JsonProperty
    private String candidateName;
	
	@Transient
	@JsonProperty
    private String vendorContact;
	
	@Transient
	@JsonProperty
    private String contractUUID;
	
	@Transient
	@JsonProperty
    private boolean vendorMsaCreated;
	
	@Transient
	@JsonProperty
    private Long orgRelationId;
	
	@Transient
	@JsonProperty
    private String spocUserCode;
	
	@Transient
	@JsonProperty
    private Long spocId;
	
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
	
	
	public String getOtherPositionType() {
		return otherPositionType;
	}

	public void setOtherPositionType(String otherPositionType) {
		this.otherPositionType = otherPositionType;
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

	public String getUpdatedDateStr() {
		return updatedDateStr;
	}

	public void setUpdatedDateStr(String updatedDateStr) {
		this.updatedDateStr = updatedDateStr;
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

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public Long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getOfferUUID() {
		return offerUUID;
	}

	public void setOfferUUID(String offerUUID) {
		this.offerUUID = offerUUID;
	}

	public String getVendorContact()
	{
		return vendorContact;
	}

	public void setVendorContact(String vendorContact)
	{
		this.vendorContact = vendorContact;
	}

	public String getContractUUID()
	{
		return contractUUID;
	}

	public void setContractUUID(String contractUUID)
	{
		this.contractUUID = contractUUID;
	}

	public boolean isVendorMsaCreated()
	{
		return vendorMsaCreated;
	}

	public void setVendorMsaCreated(boolean vendorMsaCreated)
	{
		this.vendorMsaCreated = vendorMsaCreated;
	}

	public Long getOrgRelationId()
	{
		return orgRelationId;
	}

	public void setOrgRelationId(Long orgRelationId)
	{
		this.orgRelationId = orgRelationId;
	}

	public String getSpocUserCode() {
		return spocUserCode;
	}

	public void setSpocUserCode(String spocUserCode) {
		this.spocUserCode = spocUserCode;
	}

	public Long getSpocId() {
		return spocId;
	}

	public void setSpocId(Long spocId) {
		this.spocId = spocId;
	}

	public Date getOfferExpireDate() {
		return offerExpireDate;
	}

	public void setOfferExpireDate(Date offerExpireDate) {
		this.offerExpireDate = offerExpireDate;
	}

	public OfferLetterTemplate getOfferLetterTemplate() {
		return offerLetterTemplate;
	}

	public void setOfferLetterTemplate(OfferLetterTemplate offerLetterTemplate) {
		this.offerLetterTemplate = offerLetterTemplate;
	}
}
