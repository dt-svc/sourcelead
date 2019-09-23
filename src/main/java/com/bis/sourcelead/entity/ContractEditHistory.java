package com.bis.sourcelead.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;
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

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Entity for CONTRACT_EDIT_HISTORY
 * 
 * @author Pavan Jampana
 *
 */
@Entity
@Table(name = "CONTRACT_EDIT_HISTORY")
public class ContractEditHistory {
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
	
	@Column(name = "TEMPLATE_ID")
	private Long contractsTemplate;
	
	@Column(name = "OWNER_USER_ID")
	private String ownerUserId;
	
	@Column(name = "START_DATE")
	private Date startDate;
	
	@Column(name = "END_DATE")
	private Date endDate;
	
	@Column(name = "JOIN_DATE")
	private Date joinDate;
	
	@Column(name = "WORK_LOCATION", columnDefinition = "LONGTEXT default NULL")
	private String workLocation;
	
	@Column(name = "INITIATOR_TYPE")
	private String initiatorType;
	
	@Column(name = "ORG_LOCATION_ID")
	private Long orgLocationId;
	
	@Column(name = "PARENT_CONTRACT_ID")
	private Long parentContractId;
	
	@Column(name = "CONTRACT_ORG_LOCATION_ID")
	private Long contractOrgLocationId;
	
	@Column(name = "CONTACT_USER_ID")
	private Long contactUser;
	
	@Column(name = "ORG_RELATION_ID")
	private Long orgRelations;

	@Column(name = "APPROVER_USER_ID")
	private Long approverUser;
	
	@Column(name = "CONTRACT_STATUS")
	private String contractStatus;
	
	@Column(name = "DOC_ID", columnDefinition = "TEXT default NULL")
	private String docId;
	
	@Column(name = "ENVELOP_ID", columnDefinition = "TEXT default NULL")
	private String envelopId;
	
	@Column(name = "DESCRIPTION", columnDefinition = "TEXT default NULL")
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
	
	@Column(name = "REQUIREMENT_SUBMISSION_ID")
	private Long requirementSubmission;
	
	@Column(name = "CANDIDATE_ID")
	private Long candidate;
	
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
	
	@Column(name = "FILE_TYPE")
	private String fileType;
	
	@Column(name = "FILE_URL")
	private String fileURL;
	
	@Column(name = "REJECTED_REASON", columnDefinition = "TEXT default NULL")
	private String rejectedReason;
	
	@Column(name = "REVISED_REASON", columnDefinition = "TEXT default NULL")
	private String revisedReason;
	
	@Column(name = "TEMPLATE_FILE_URL")
	private String templateFileURL;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getContractType()
	{
		return contractType;
	}

	public void setContractType(String contractType)
	{
		this.contractType = contractType;
	}

	public String getContractTitle()
	{
		return contractTitle;
	}

	public void setContractTitle(String contractTitle)
	{
		this.contractTitle = contractTitle;
	}

	public String getTypeOfContract()
	{
		return typeOfContract;
	}

	public void setTypeOfContract(String typeOfContract)
	{
		this.typeOfContract = typeOfContract;
	}

	public Long getContractsTemplate()
	{
		return contractsTemplate;
	}

	public void setContractsTemplate(Long contractsTemplate)
	{
		this.contractsTemplate = contractsTemplate;
	}

	public String getOwnerUserId()
	{
		return ownerUserId;
	}

	public void setOwnerUserId(String ownerUserId)
	{
		this.ownerUserId = ownerUserId;
	}

	public Date getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	public Date getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
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

	public String getInitiatorType()
	{
		return initiatorType;
	}

	public void setInitiatorType(String initiatorType)
	{
		this.initiatorType = initiatorType;
	}

	public Long getOrgLocationId()
	{
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId)
	{
		this.orgLocationId = orgLocationId;
	}

	public Long getParentContractId()
	{
		return parentContractId;
	}

	public void setParentContractId(Long parentContractId)
	{
		this.parentContractId = parentContractId;
	}

	public Long getContractOrgLocationId()
	{
		return contractOrgLocationId;
	}

	public void setContractOrgLocationId(Long contractOrgLocationId)
	{
		this.contractOrgLocationId = contractOrgLocationId;
	}

	public Long getContactUser()
	{
		return contactUser;
	}

	public void setContactUser(Long contactUser)
	{
		this.contactUser = contactUser;
	}

	public Long getOrgRelations()
	{
		return orgRelations;
	}

	public void setOrgRelations(Long orgRelations)
	{
		this.orgRelations = orgRelations;
	}

	public Long getApproverUser()
	{
		return approverUser;
	}

	public void setApproverUser(Long approverUser)
	{
		this.approverUser = approverUser;
	}

	public String getContractStatus()
	{
		return contractStatus;
	}

	public void setContractStatus(String contractStatus)
	{
		this.contractStatus = contractStatus;
	}

	public String getDocId()
	{
		return docId;
	}

	public void setDocId(String docId)
	{
		this.docId = docId;
	}

	public String getEnvelopId()
	{
		return envelopId;
	}

	public void setEnvelopId(String envelopId)
	{
		this.envelopId = envelopId;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getBillingAmount()
	{
		return billingAmount;
	}

	public void setBillingAmount(String billingAmount)
	{
		this.billingAmount = billingAmount;
	}

	public String getBillingRateType()
	{
		return billingRateType;
	}

	public void setBillingRateType(String billingRateType)
	{
		this.billingRateType = billingRateType;
	}

	public String getBillingRatePer()
	{
		return billingRatePer;
	}

	public void setBillingRatePer(String billingRatePer)
	{
		this.billingRatePer = billingRatePer;
	}

	public String getBillingPer()
	{
		return billingPer;
	}

	public void setBillingPer(String billingPer)
	{
		this.billingPer = billingPer;
	}

	public String getInvoiceFrequency()
	{
		return invoiceFrequency;
	}

	public void setInvoiceFrequency(String invoiceFrequency)
	{
		this.invoiceFrequency = invoiceFrequency;
	}

	public String getPaymentTerm()
	{
		return paymentTerm;
	}

	public void setPaymentTerm(String paymentTerm)
	{
		this.paymentTerm = paymentTerm;
	}

	public String getTax()
	{
		return tax;
	}

	public void setTax(String tax)
	{
		this.tax = tax;
	}

	public String getTaxType()
	{
		return taxType;
	}

	public void setTaxType(String taxType)
	{
		this.taxType = taxType;
	}

	public Long getRequirementSubmission()
	{
		return requirementSubmission;
	}

	public void setRequirementSubmission(Long requirementSubmission)
	{
		this.requirementSubmission = requirementSubmission;
	}

	public Long getCandidate()
	{
		return candidate;
	}

	public void setCandidate(Long candidate)
	{
		this.candidate = candidate;
	}

	public String getHoursPerWeek()
	{
		return hoursPerWeek;
	}

	public void setHoursPerWeek(String hoursPerWeek)
	{
		this.hoursPerWeek = hoursPerWeek;
	}

	public String getOverTimeHours()
	{
		return overTimeHours;
	}

	public void setOverTimeHours(String overTimeHours)
	{
		this.overTimeHours = overTimeHours;
	}

	public String getTotalHours()
	{
		return totalHours;
	}

	public void setTotalHours(String totalHours)
	{
		this.totalHours = totalHours;
	}

	public String getOvertimeRate()
	{
		return overtimeRate;
	}

	public void setOvertimeRate(String overtimeRate)
	{
		this.overtimeRate = overtimeRate;
	}

	public String getOvertimeRateType()
	{
		return overtimeRateType;
	}

	public void setOvertimeRateType(String overtimeRateType)
	{
		this.overtimeRateType = overtimeRateType;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
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

	public String getContractUUID()
	{
		return contractUUID;
	}

	public void setContractUUID(String contractUUID)
	{
		this.contractUUID = contractUUID;
	}

	public String getStateJurisdiction()
	{
		return stateJurisdiction;
	}

	public void setStateJurisdiction(String stateJurisdiction)
	{
		this.stateJurisdiction = stateJurisdiction;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public String getFileType()
	{
		return fileType;
	}

	public void setFileType(String fileType)
	{
		this.fileType = fileType;
	}

	public String getFileURL()
	{
		return fileURL;
	}

	public void setFileURL(String fileURL)
	{
		this.fileURL = fileURL;
	}

	public String getRejectedReason()
	{
		return rejectedReason;
	}

	public void setRejectedReason(String rejectedReason)
	{
		this.rejectedReason = rejectedReason;
	}

	public String getRevisedReason()
	{
		return revisedReason;
	}

	public void setRevisedReason(String revisedReason)
	{
		this.revisedReason = revisedReason;
	}

	public String getTemplateFileURL()
	{
		return templateFileURL;
	}

	public void setTemplateFileURL(String templateFileURL)
	{
		this.templateFileURL = templateFileURL;
	}
}