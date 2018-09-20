package com.bis.sourcelead.dto;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.ContractRequestDocuments;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.User;

public class FullTimeContractsDTO {
	private Long id;
	private String contractType;
	private String contractTitle;
	private String typeOfContract;
	private Long contractsTemplate;
	private Long ownerUserId;
	private Date startDate;
	private Date endDate;
	private Date joinDate;
	private String workLocation;
	private String initiatorType;
	private Long orgLocationId;
	private Long parentContractId;
	private Long contractOrgLocationId;
	private Long contactUser;
	private Long orgRelations;
	private Long approverUser;
	private String contractStatus;
	private String docId;
	private String envelopId;
	private String description;
	private String billingAmount;
	private String billingRateType;
	private String billingRatePer;
	private String billingPer;
	private String invoiceFrequency;
	private String paymentTerm;
	private String tax;
	private String taxType;
	private Long requirementSubmission;
	private Long candidate;
	private String hoursPerWeek;
	private String overTimeHours;
	private String totalHours;
	private String overtimeRate;
	private String overtimeRateType;
	private String status;
	private String templateSubmissionStatus;
	private Date createdDate;
	private String createdBy;
	private Date updatedDate;
	private String updatedBy;
	private String contractUUID;
	private String stateJurisdiction;
	private String fileName;
	private String fileType;
	private String fileURL;
	private String rejectedReason;
	private String ownerName;
	private String vendorContactName;
	private String startDateStr;
	private String endDateStr;
	private String parentId;
	private Integer reqDocCount;
	List<ContractRequestDocuments> contractRequestDocumentsList;
	private String tempEnvId;
	private String tempdocId;
	private String approverUserName;
	private String contactUserName;
	private Integer totalDraftCount;
	private Integer totalWTACount;
	private Integer totalWTSCount;
	private Integer totalWTVCount;
	private Integer totalFinalizedCount;
	private Integer totalExpiredCount;
	private Integer totalRejectedCount;
	private Integer totalDraftVendorCount;
	private Integer totalDraftClientCount;
	private String createdDateStr;
	private String updatedDateStr;
	private String joinedDateStr;
	private String createdByName;
	private String updatedByName;
	private String requirementName;
	private String applicantName;
	private String templateName;
	private String offerValue;
	private String contractWith;
	private String empName;
	private String tempUUID;
	private Long approverUserId;
	private Long ownerUser;
	private String templateId;
    private String orgRelationUUID;
	private String spocUUID;
	private String isOwner;
	
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
	
	public Long getOwnerUserId()
	{
		return ownerUserId;
	}
	
	public void setOwnerUserId(Long ownerUserId)
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
	
	public String getParentId()
	{
		return parentId;
	}
	
	public void setParentId(String parentId)
	{
		this.parentId = parentId;
	}
	
	public Integer getReqDocCount()
	{
		return reqDocCount;
	}
	
	public void setReqDocCount(Integer reqDocCount)
	{
		this.reqDocCount = reqDocCount;
	}
	
	public List<ContractRequestDocuments> getContractRequestDocumentsList()
	{
		return contractRequestDocumentsList;
	}
	
	public void setContractRequestDocumentsList(List<ContractRequestDocuments> contractRequestDocumentsList)
	{
		this.contractRequestDocumentsList = contractRequestDocumentsList;
	}
	
	public String getTempEnvId()
	{
		return tempEnvId;
	}
	
	public void setTempEnvId(String tempEnvId)
	{
		this.tempEnvId = tempEnvId;
	}
	
	public String getTempdocId()
	{
		return tempdocId;
	}
	
	public void setTempdocId(String tempdocId)
	{
		this.tempdocId = tempdocId;
	}
	
	public String getApproverUserName()
	{
		return approverUserName;
	}
	
	public void setApproverUserName(String approverUserName)
	{
		this.approverUserName = approverUserName;
	}
	
	public String getContactUserName()
	{
		return contactUserName;
	}
	
	public void setContactUserName(String contactUserName)
	{
		this.contactUserName = contactUserName;
	}
	
	public Integer getTotalDraftCount()
	{
		return totalDraftCount;
	}
	
	public void setTotalDraftCount(Integer totalDraftCount)
	{
		this.totalDraftCount = totalDraftCount;
	}
	
	public Integer getTotalWTACount()
	{
		return totalWTACount;
	}
	
	public void setTotalWTACount(Integer totalWTACount)
	{
		this.totalWTACount = totalWTACount;
	}
	
	public Integer getTotalWTSCount()
	{
		return totalWTSCount;
	}
	
	public void setTotalWTSCount(Integer totalWTSCount)
	{
		this.totalWTSCount = totalWTSCount;
	}
	
	public Integer getTotalWTVCount()
	{
		return totalWTVCount;
	}
	
	public void setTotalWTVCount(Integer totalWTVCount)
	{
		this.totalWTVCount = totalWTVCount;
	}
	
	public Integer getTotalFinalizedCount()
	{
		return totalFinalizedCount;
	}
	
	public void setTotalFinalizedCount(Integer totalFinalizedCount)
	{
		this.totalFinalizedCount = totalFinalizedCount;
	}
	
	public Integer getTotalExpiredCount()
	{
		return totalExpiredCount;
	}
	
	public void setTotalExpiredCount(Integer totalExpiredCount)
	{
		this.totalExpiredCount = totalExpiredCount;
	}
	
	public Integer getTotalRejectedCount()
	{
		return totalRejectedCount;
	}
	
	public void setTotalRejectedCount(Integer totalRejectedCount)
	{
		this.totalRejectedCount = totalRejectedCount;
	}
	
	public Integer getTotalDraftVendorCount()
	{
		return totalDraftVendorCount;
	}
	
	public void setTotalDraftVendorCount(Integer totalDraftVendorCount)
	{
		this.totalDraftVendorCount = totalDraftVendorCount;
	}
	
	public Integer getTotalDraftClientCount()
	{
		return totalDraftClientCount;
	}
	
	public void setTotalDraftClientCount(Integer totalDraftClientCount)
	{
		this.totalDraftClientCount = totalDraftClientCount;
	}
	
	public String getCreatedDateStr()
	{
		return createdDateStr;
	}
	
	public void setCreatedDateStr(String createdDateStr)
	{
		this.createdDateStr = createdDateStr;
	}
	
	public String getUpdatedDateStr()
	{
		return updatedDateStr;
	}
	
	public void setUpdatedDateStr(String updatedDateStr)
	{
		this.updatedDateStr = updatedDateStr;
	}
	
	public String getJoinedDateStr()
	{
		return joinedDateStr;
	}
	
	public void setJoinedDateStr(String joinedDateStr)
	{
		this.joinedDateStr = joinedDateStr;
	}
	
	public String getCreatedByName()
	{
		return createdByName;
	}
	
	public void setCreatedByName(String createdByName)
	{
		this.createdByName = createdByName;
	}
	
	public String getUpdatedByName()
	{
		return updatedByName;
	}
	
	public void setUpdatedByName(String updatedByName)
	{
		this.updatedByName = updatedByName;
	}
	
	public String getRequirementName()
	{
		return requirementName;
	}
	
	public void setRequirementName(String requirementName)
	{
		this.requirementName = requirementName;
	}
	
	public String getApplicantName()
	{
		return applicantName;
	}
	
	public void setApplicantName(String applicantName)
	{
		this.applicantName = applicantName;
	}
	
	public String getTemplateName()
	{
		return templateName;
	}
	
	public void setTemplateName(String templateName)
	{
		this.templateName = templateName;
	}
	
	public String getOfferValue()
	{
		return offerValue;
	}
	
	public void setOfferValue(String offerValue)
	{
		this.offerValue = offerValue;
	}
	
	public String getContractWith()
	{
		return contractWith;
	}
	
	public void setContractWith(String contractWith)
	{
		this.contractWith = contractWith;
	}
	
	public String getEmpName()
	{
		return empName;
	}
	
	public void setEmpName(String empName)
	{
		this.empName = empName;
	}
	
	public String getTempUUID()
	{
		return tempUUID;
	}
	
	public void setTempUUID(String tempUUID)
	{
		this.tempUUID = tempUUID;
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

	public String getTemplateId()
	{
		return templateId;
	}

	public void setTemplateId(String templateId)
	{
		this.templateId = templateId;
	}

	public String getOrgRelationUUID()
	{
		return orgRelationUUID;
	}

	public void setOrgRelationUUID(String orgRelationUUID)
	{
		this.orgRelationUUID = orgRelationUUID;
	}

	public String getSpocUUID()
	{
		return spocUUID;
	}

	public void setSpocUUID(String spocUUID)
	{
		this.spocUUID = spocUUID;
	}

	public String getIsOwner()
	{
		return isOwner;
	}

	public void setIsOwner(String isOwner)
	{
		this.isOwner = isOwner;
	}
}
