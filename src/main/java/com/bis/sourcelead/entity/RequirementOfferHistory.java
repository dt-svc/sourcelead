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
	@Table(name = "REQUIREMENT_OFFER_HISTORY")
	public class RequirementOfferHistory implements Serializable{

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="OFFER_ID", nullable=false )        
    private RequirementOffers offerId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="SUBMISSION_ID", nullable=false )        
    private RequirementSubmission submissionId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_LOCATION_ID")
   	private OrgLocations orgLocationId;
	
	@Column(name = "OFFER_SOURCE_TYPE")
	private String offerSourceType;
	
	@Column(name="NOTIFY_TO_APPLICANT")
	private String notifyToApplicant;
	
	@Column(name="NOTIFY_TO_VENDOR")
	private String notifyToVendor;
	
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
    private String isNotifyToVendorRequired;
	
	@Transient
    private String createDateStr;
	
	@Transient
    private Long offerResumeId;
	
	@Transient
	@JsonProperty
    private String submissionStatusStr;
	
	@Transient
	@Lob
    private  byte[] docFile;
	
	@Transient
    private String fileContentType;
	
	@Transient
    private String templateUUID;
	
	@Transient
    private String templateName;
	
	@Transient
    private String offerExpireDate;
	
	@Transient
    private String offerUUID;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RequirementOffers getOfferId() {
		return offerId;
	}

	public void setOfferId(RequirementOffers offerId) {
		this.offerId = offerId;
	}

	public RequirementSubmission getSubmissionId() {
		return submissionId;
	}

	public void setSubmissionId(RequirementSubmission submissionId) {
		this.submissionId = submissionId;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getOfferSourceType() {
		return offerSourceType;
	}

	public void setOfferSourceType(String offerSourceType) {
		this.offerSourceType = offerSourceType;
	}

	public String getNotifyToApplicant() {
		return notifyToApplicant;
	}

	public void setNotifyToApplicant(String notifyToApplicant) {
		this.notifyToApplicant = notifyToApplicant;
	}

	public String getNotifyToVendor() {
		return notifyToVendor;
	}

	public void setNotifyToVendor(String notifyToVendor) {
		this.notifyToVendor = notifyToVendor;
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

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getIsNotifyToVendorRequired() {
		return isNotifyToVendorRequired;
	}

	public void setIsNotifyToVendorRequired(String isNotifyToVendorRequired) {
		this.isNotifyToVendorRequired = isNotifyToVendorRequired;
	}

	public String getCreateDateStr() {
		return createDateStr;
	}

	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}

	public Long getOfferResumeId() {
		return offerResumeId;
	}

	public void setOfferResumeId(Long offerResumeId) {
		this.offerResumeId = offerResumeId;
	}

	public String getSubmissionStatusStr()
	{
		return submissionStatusStr;
	}

	public void setSubmissionStatusStr(String submissionStatusStr)
	{
		this.submissionStatusStr = submissionStatusStr;
	}

	public byte[] getDocFile()
	{
		return docFile;
	}

	public void setDocFile(byte[] docFile)
	{
		this.docFile = docFile;
	}

	public String getFileContentType()
	{
		return fileContentType;
	}

	public void setFileContentType(String fileContentType)
	{
		this.fileContentType = fileContentType;
	}

	public String getTemplateUUID() {
		return templateUUID;
	}

	public void setTemplateUUID(String templateUUID) {
		this.templateUUID = templateUUID;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getOfferExpireDate() {
		return offerExpireDate;
	}

	public void setOfferExpireDate(String offerExpireDate) {
		this.offerExpireDate = offerExpireDate;
	}

	public String getOfferUUID() {
		return offerUUID;
	}

	public void setOfferUUID(String offerUUID) {
		this.offerUUID = offerUUID;
	}
}
