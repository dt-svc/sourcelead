package com.bis.sourcelead.entity;

import java.util.Date;
import java.util.List;

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

/**
 * Entity for CONTRACTS_TEMPLATE
 * @author root
 *
 */

@Entity
@Table(name = "CONTRACTS", catalog = "sourcelead")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "contracts")
public class Contracts {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "CONTRACT_TYPE")
	private String contractType;
	
	@Column(name = "CONTRACT_TITLE ")
	private String contractTitle;
	
	@Column(name = "TYPE_OF_CONTRACT")
	private String typeOfContract;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne 
    @JoinColumn(name="TEMPLATE_ID", nullable=false )  
	private ContractsTemplate contractsTemplate; 
	 
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "OWNER_USER_ID")
	private User ownerUserId;
	
	@Column(name = "START_DATE")
	private Date startDate;
	
	@Column(name = "END_DATE")
	private Date endDate;
	
	@Column(name = "JOIN_DATE")
	private Date joinDate;
	
	@Column(name = "WORK_LOCATION")
	private String workLocation;
	
	@Column(name = "INITIATOR_TYPE")
	private String initiatorType;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne
    @JoinColumn(name="ORG_LOCATION_ID", nullable=false )     
	private OrgLocations orgLocationId;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne
    @JoinColumn(name="PARENT_CONTRACT_ID")     
	private Contracts parentContractId;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne
    @JoinColumn(name="CONTRACT_ORG_LOCATION_ID", nullable=false )     
	private OrgLocations contractOrgLocationId;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CONTACT_USER_ID")
	private User contactUser;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ORG_RELATION_ID")
	private OrgRelations orgRelations;
	
	/*@Column(name = "STATE_OF_JURISDICTION")
	private String stateOfJurisdiction;*/
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "APPROVER_USER_ID") 
	private User approverUser;
	
	@Column(name = "CONTRACT_STATUS")
	private String contractStatus;
	
	@Column(name = "DOC_ID",columnDefinition = "TEXT default NULL")
	private String docId;
	
	@Column(name = "ENVELOP_ID",columnDefinition = "TEXT default NULL")
	private String envelopId;
	
	@Column(name = "DESCRIPTION",columnDefinition = "TEXT default NULL")
	private String description;
	
	@Column(name = "BILLING_AMOUNT")
	private String billingAmount;
	
	@Column(name = "BILLING_RATE_TYPE")
	private String billingRateType;
	
	@Column(name = "BILLING_RATE_PER")
	private String billingRatePer;
	
	@Column(name = "BILLING_PER")
	private String billingPer;
	
	@Column(name = "INVOICE_FREQUENCY")
	private String invoiceFrequency;
	
	@Column(name = "PAYMENT_TERM")
	private String paymentTerm;
	
	@Column(name = "TAX")
	private String tax;
	
	@Column(name = "TAX_TYPE")
	private String taxType;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="REQUIREMENT_SUBMISSION_ID")     
	private RequirementSubmission requirementSubmission;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="CANDIDATE_ID")     
	private Candidate candidate;
	
	@Column(name = "HOURS_PER_WEEK")
	private String hoursPerWeek;
	
	@Column(name = "OVER_TIME_HOURS")
	private String overTimeHours;
	
	@Column(name = "TOTAL_HOURS")
	private String totalHours;
	
	@Column(name = "OVERTIME_RATE")
	private String overtimeRate;
	
	@Column(name = "OVERTIME_RATE_TYPE")
	private String overtimeRateType;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "TEMPLATE_SUBMISSION_STATUS")
	private String templateSubmissionStatus;

	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "CONTRACT_UUID")
	private String contractUUID;
	
	@Column(name = "STATE_JURISDICTION")
	private String stateJurisdiction;
	
	@Column(name = "FILE_NAME", columnDefinition = "TEXT default NULL")
	private String fileName;
	
	@Column(name = "DOC_FILE",columnDefinition="mediumblob")
	@Lob
	private  byte[] docFile;
	
	@Column(name = "FILE_TYPE")
	private String fileType;
	
	@Column(name = "FILE_URL")
	private String fileURL;
	
	@Column(name = "REJECTED_REASON" , columnDefinition = "TEXT default NULL")
	private String rejectedReason;
	
	@Transient
	private String ownerName;
	
	@Transient
	private String vendorContactName;
	
	@Transient
	private String startDateStr;
	
	@Transient
	private String endDateStr;

	@Transient
	private String parentId;
	
	@Transient
	private Integer reqDocCount;
	
	@Transient
	List<Contracts> vendorcontractsList;
	
	@Transient
	List<Contracts> clientcontractsList;
	
	@Transient
	List<Contracts> fullTimeoffercontractsList;

	@Transient
	List<ContractRequestDocuments>	contractRequestDocumentsList;
	
	@Transient
	private String tempEnvId;

	@Transient
	private String tempdocId;
	
	@Transient
	private String approverUserName;
	
	@Transient
	private String contactUserName;
	
	@Transient
	private Integer totalDraftCount;
	
	@Transient
	private Integer totalWTACount;
	
	@Transient
	private Integer totalWTSCount;
	
	@Transient
	private Integer totalWTVCount;
	
	@Transient
	private Integer totalFinalizedCount;
	
	@Transient
	private Integer totalExpiredCount;
	
	@Transient
	private Integer totalRejectedCount;
	
	@Transient
	private Integer totalDraftVendorCount;
	
	@Transient
	private Integer totalDraftClientCount;
	
	@Transient
	private String createdDateStr;
	
	@Transient
	private String updatedDateStr;
	
	@Transient
	private String joinedDateStr;
	
	@Transient
	private String createdByName;
	
	@Transient
	private String updatedByName;
	
	@Transient
	private String requirementName;
	
	@Transient
	private String applicantName;
	
	@Transient
	private String templateName;
	
	@Transient
	private String offerValue;
	
	@Transient
	private String contractWith;
	
	@Transient
	private String empName;
	
	@Transient
	private String tempUUID;
	
	@Transient
	private Long approverUserId;
	
	@Transient
	private Long ownerUser;
	
	
	public List<ContractRequestDocuments> getContractRequestDocumentsList()
	{
		return contractRequestDocumentsList;
	}

	public void setContractRequestDocumentsList(List<ContractRequestDocuments> contractRequestDocumentsList)
	{
		this.contractRequestDocumentsList = contractRequestDocumentsList;
	}

	public String getTempUUID()
	{
		return tempUUID;
	}

	public void setTempUUID(String tempUUID)
	{
		this.tempUUID = tempUUID;
	}

	public String getEmpName()
	{
		return empName;
	}

	public void setEmpName(String empName)
	{
		this.empName = empName;
	}

	public List<Contracts> getFullTimeoffercontractsList()
	{
		return fullTimeoffercontractsList;
	}

	public void setFullTimeoffercontractsList(List<Contracts> fullTimeoffercontractsList)
	{
		this.fullTimeoffercontractsList = fullTimeoffercontractsList;
	}

	public String getJoinedDateStr()
	{
		return joinedDateStr;
	}

	public void setJoinedDateStr(String joinedDateStr)
	{
		this.joinedDateStr = joinedDateStr;
	}

	public Date getJoinDate()
	{
		return joinDate;
	}

	public void setJoinDate(Date joinDate)
	{
		this.joinDate = joinDate;
	}

	public String getWorkLocation()
	{
		return workLocation;
	}

	public void setWorkLocation(String workLocation)
	{
		this.workLocation = workLocation;
	}

	public Integer getReqDocCount()
	{
		return reqDocCount;
	}

	public void setReqDocCount(Integer reqDocCount)
	{
		this.reqDocCount = reqDocCount;
	}

	public String getContactUserName() {
		return contactUserName;
	}

	public void setContactUserName(String contactUserName) {
		this.contactUserName = contactUserName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getDocFile() {
		return docFile;
	}

	public void setDocFile(byte[] docFile) {
		this.docFile = docFile;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getTempEnvId() {
		return tempEnvId;
	}

	public void setTempEnvId(String tempEnvId) {
		this.tempEnvId = tempEnvId;
	}

	public String getTempdocId() {
		return tempdocId;
	}

	public void setTempdocId(String tempdocId) {
		this.tempdocId = tempdocId;
	}

	public String getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getEnvelopId() {
		return envelopId;
	}

	public void setEnvelopId(String envelopId) {
		this.envelopId = envelopId;
	}

	public List<Contracts> getVendorcontractsList()
	{
		return vendorcontractsList;
	}

	public void setVendorcontractsList(List<Contracts> vendorcontractsList)
	{
		this.vendorcontractsList = vendorcontractsList;
	}

	public List<Contracts> getClientcontractsList()
	{
		return clientcontractsList;
	}

	public void setClientcontractsList(List<Contracts> clientcontractsList)
	{
		this.clientcontractsList = clientcontractsList;
	}

	public String getParentId()
	{
		return parentId;
	}

	public void setParentId(String parentId)
	{
		this.parentId = parentId;
	}

	public Contracts getParentContractId()
	{
		return parentContractId;
	}

	public void setParentContractId(Contracts parentContractId)
	{
		this.parentContractId = parentContractId;
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

	public String getContractUUID() {
		return contractUUID;
	}

	public void setContractUUID(String contractUUID) {
		this.contractUUID = contractUUID;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getContractTitle() {
		return contractTitle;
	}

	public void setContractTitle(String contractTitle) {
		this.contractTitle = contractTitle;
	}

	public String getTypeOfContract() {
		return typeOfContract;
	}

	public void setTypeOfContract(String typeOfContract) {
		this.typeOfContract = typeOfContract;
	}

	public ContractsTemplate getContractsTemplate() {
		return contractsTemplate;
	}

	public void setContractsTemplate(ContractsTemplate contractsTemplate) {
		this.contractsTemplate = contractsTemplate;
	}

	public User getOwnerUserId() {
		return ownerUserId;
	}

	public void setOwnerUserId(User ownerUserId) {
		this.ownerUserId = ownerUserId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getInitiatorType() {
		return initiatorType;
	}

	public void setInitiatorType(String initiatorType) {
		this.initiatorType = initiatorType;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public OrgLocations getContractOrgLocationId() {
		return contractOrgLocationId;
	}

	public void setContractOrgLocationId(OrgLocations contractOrgLocationId) {
		this.contractOrgLocationId = contractOrgLocationId;
	}

	public User getContactUser() {
		return contactUser;
	}

	public void setContactUser(User contactUser) {
		this.contactUser = contactUser;
	}

	public OrgRelations getOrgRelations() {
		return orgRelations;
	}

	public void setOrgRelations(OrgRelations orgRelations) {
		this.orgRelations = orgRelations;
	}

	/*public String getStateOfJurisdiction() {
		return stateOfJurisdiction;
	}

	public void setStateOfJurisdiction(String stateOfJurisdiction) {
		this.stateOfJurisdiction = stateOfJurisdiction;
	}*/

	public User getApproverUser() {
		return approverUser;
	}

	public void setApproverUser(User approverUser) {
		this.approverUser = approverUser;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBillingAmount() {
		return billingAmount;
	}

	public void setBillingAmount(String billingAmount) {
		this.billingAmount = billingAmount;
	}

	public String getBillingRateType() {
		return billingRateType;
	}

	public void setBillingRateType(String billingRateType) {
		this.billingRateType = billingRateType;
	}

	public String getBillingRatePer() {
		return billingRatePer;
	}

	public void setBillingRatePer(String billingRatePer) {
		this.billingRatePer = billingRatePer;
	}

	public String getBillingPer() {
		return billingPer;
	}

	public void setBillingPer(String billingPer) {
		this.billingPer = billingPer;
	}

	public String getInvoiceFrequency() {
		return invoiceFrequency;
	}

	public void setInvoiceFrequency(String invoiceFrequency) {
		this.invoiceFrequency = invoiceFrequency;
	}

	public String getPaymentTerm() {
		return paymentTerm;
	}

	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public String getTaxType() {
		return taxType;
	}

	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}

	public RequirementSubmission getRequirementSubmission() {
		return requirementSubmission;
	}

	public void setRequirementSubmission(RequirementSubmission requirementSubmission) {
		this.requirementSubmission = requirementSubmission;
	}

	public String getHoursPerWeek() {
		return hoursPerWeek;
	}

	public void setHoursPerWeek(String hoursPerWeek) {
		this.hoursPerWeek = hoursPerWeek;
	}

	public String getOverTimeHours() {
		return overTimeHours;
	}

	public void setOverTimeHours(String overTimeHours) {
		this.overTimeHours = overTimeHours;
	}

	public String getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(String totalHours) {
		this.totalHours = totalHours;
	}

	public String getOvertimeRate() {
		return overtimeRate;
	}

	public void setOvertimeRate(String overtimeRate) {
		this.overtimeRate = overtimeRate;
	}

	public String getOvertimeRateType() {
		return overtimeRateType;
	}

	public void setOvertimeRateType(String overtimeRateType) {
		this.overtimeRateType = overtimeRateType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTemplateSubmissionStatus()
	{
		return templateSubmissionStatus;
	}

	public void setTemplateSubmissionStatus(String templateSubmissionStatus)
	{
		this.templateSubmissionStatus = templateSubmissionStatus;
	}

	public String getOwnerName()
	{
		return ownerName;
	}

	public void setOwnerName(String ownerName)
	{
		this.ownerName = ownerName;
	}

	public String getVendorContactName()
	{
		return vendorContactName;
	}

	public void setVendorContactName(String vendorContactName)
	{
		this.vendorContactName = vendorContactName;
	}

	public String getStartDateStr()
	{
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr)
	{
		this.startDateStr = startDateStr;
	}

	public String getEndDateStr()
	{
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr)
	{
		this.endDateStr = endDateStr;
	}

	public String getStateJurisdiction() {
		return stateJurisdiction;
	}

	public void setStateJurisdiction(String stateJurisdiction) {
		this.stateJurisdiction = stateJurisdiction;
	}

	public String getApproverUserName() {
		return approverUserName;
	}

	public void setApproverUserName(String approverUserName) {
		this.approverUserName = approverUserName;
	}

	public Integer getTotalDraftCount() {
		return totalDraftCount;
	}

	public void setTotalDraftCount(Integer totalDraftCount) {
		this.totalDraftCount = totalDraftCount;
	}

	public Integer getTotalWTACount() {
		return totalWTACount;
	}

	public void setTotalWTACount(Integer totalWTACount) {
		this.totalWTACount = totalWTACount;
	}

	public Integer getTotalWTSCount() {
		return totalWTSCount;
	}

	public void setTotalWTSCount(Integer totalWTSCount) {
		this.totalWTSCount = totalWTSCount;
	}

	public Integer getTotalWTVCount() {
		return totalWTVCount;
	}

	public void setTotalWTVCount(Integer totalWTVCount) {
		this.totalWTVCount = totalWTVCount;
	}

	public Integer getTotalFinalizedCount() {
		return totalFinalizedCount;
	}

	public void setTotalFinalizedCount(Integer totalFinalizedCount) {
		this.totalFinalizedCount = totalFinalizedCount;
	}

	public Integer getTotalExpiredCount() {
		return totalExpiredCount;
	}

	public void setTotalExpiredCount(Integer totalExpiredCount) {
		this.totalExpiredCount = totalExpiredCount;
	}

	public Integer getTotalRejectedCount() {
		return totalRejectedCount;
	}

	public void setTotalRejectedCount(Integer totalRejectedCount) {
		this.totalRejectedCount = totalRejectedCount;
	}

	public Integer getTotalDraftVendorCount() {
		return totalDraftVendorCount;
	}

	public void setTotalDraftVendorCount(Integer totalDraftVendorCount) {
		this.totalDraftVendorCount = totalDraftVendorCount;
	}

	public Integer getTotalDraftClientCount() {
		return totalDraftClientCount;
	}

	public void setTotalDraftClientCount(Integer totalDraftClientCount) {
		this.totalDraftClientCount = totalDraftClientCount;
	}

	public String getCreatedDateStr() {
		return createdDateStr;
	}

	public void setCreatedDateStr(String createdDateStr) {
		this.createdDateStr = createdDateStr;
	}

	public String getUpdatedDateStr() {
		return updatedDateStr;
	}

	public void setUpdatedDateStr(String updatedDateStr) {
		this.updatedDateStr = updatedDateStr;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getUpdatedByName() {
		return updatedByName;
	}

	public void setUpdatedByName(String updatedByName) {
		this.updatedByName = updatedByName;
	}

	public String getRequirementName() {
		return requirementName;
	}

	public void setRequirementName(String requirementName) {
		this.requirementName = requirementName;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getOfferValue() {
		return offerValue;
	}

	public void setOfferValue(String offerValue) {
		this.offerValue = offerValue;
	}

	public String getContractWith() {
		return contractWith;
	}

	public void setContractWith(String contractWith) {
		this.contractWith = contractWith;
	}

	public Long getApproverUserId()
	{
		return approverUserId;
	}

	public void setApproverUserId(Long approverUserId)
	{
		this.approverUserId = approverUserId;
	}

	public Long getOwnerUser()
	{
		return ownerUser;
	}

	public void setOwnerUser(Long ownerUser)
	{
		this.ownerUser = ownerUser;
	}

	public Candidate getCandidate()
	{
		return candidate;
	}

	public void setCandidate(Candidate candidate)
	{
		this.candidate = candidate;
	}

	public String getRejectedReason()
	{
		return rejectedReason;
	}

	public void setRejectedReason(String rejectedReason)
	{
		this.rejectedReason = rejectedReason;
	}

	public String getFileURL()
	{
		return fileURL;
	}

	public void setFileURL(String fileURL)
	{
		this.fileURL = fileURL;
	}

}
