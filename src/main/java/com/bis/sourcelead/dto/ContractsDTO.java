package com.bis.sourcelead.dto;

import java.util.List;
import java.util.Map;

public class ContractsDTO {
	private String contractType;
	private String contractTitle;
	private String template;
	private String requirementsList;
	private String submissionOffersList;
	private String owner;
	private String startDate;
	private String endDate;
	private String orgRelationId;
	private String vendorContact;
	private String clientOrgRelationId;
	private String clientContact;
	private String approver;
	private String candiRateType;
	private String candiFlatReward;
	private String candiRatePer;
	private String description;
	private String workLocation;
	private String joinDate;
	private String jurisdiction;
	private String hoursPerWeek;
	private String overTimeHours;
	private String totalHoursEstimated;
	private String overtimeRate;
	private String billingRateType;
	private String billingRatePer;
	private String flatReward;
	private String invoiceFrequency;
	private String paymentFrequency;
	private String taxRewardType;
	private String taxPercentageReward;
	private String taxFlatReward;
	private String taxFlatRateType;
	private List<Map<String, String>> fileInfo;
	private List<FileInfo> uploadedFileInfo;
	
	public List<Map<String, String>> getFileInfo()
	{
		return fileInfo;
	}
	
	public void setFileInfo(List<Map<String, String>> fileInfo)
	{
		this.fileInfo = fileInfo;
	}
	
	public List<FileInfo> getUploadedFileInfo()
	{
		return uploadedFileInfo;
	}
	
	public void setUploadedFileInfo(List<FileInfo> uploadedFileInfo)
	{
		this.uploadedFileInfo = uploadedFileInfo;
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
	
	public String getTemplate()
	{
		return template;
	}
	
	public void setTemplate(String template)
	{
		this.template = template;
	}
	
	public String getRequirementsList()
	{
		return requirementsList;
	}
	
	public void setRequirementsList(String requirementsList)
	{
		this.requirementsList = requirementsList;
	}
	
	public String getSubmissionOffersList()
	{
		return submissionOffersList;
	}
	
	public void setSubmissionOffersList(String submissionOffersList)
	{
		this.submissionOffersList = submissionOffersList;
	}
	
	public String getOwner()
	{
		return owner;
	}
	
	public void setOwner(String owner)
	{
		this.owner = owner;
	}
	
	public String getStartDate()
	{
		return startDate;
	}
	
	public void setStartDate(String startDate)
	{
		this.startDate = startDate;
	}
	
	public String getEndDate()
	{
		return endDate;
	}
	
	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}
	
	public String getOrgRelationId()
	{
		return orgRelationId;
	}
	
	public void setOrgRelationId(String orgRelationId)
	{
		this.orgRelationId = orgRelationId;
	}
	
	public String getVendorContact()
	{
		return vendorContact;
	}
	
	public void setVendorContact(String vendorContact)
	{
		this.vendorContact = vendorContact;
	}
	
	public String getClientOrgRelationId()
	{
		return clientOrgRelationId;
	}
	
	public void setClientOrgRelationId(String clientOrgRelationId)
	{
		this.clientOrgRelationId = clientOrgRelationId;
	}
	
	public String getClientContact()
	{
		return clientContact;
	}
	
	public void setClientContact(String clientContact)
	{
		this.clientContact = clientContact;
	}
	
	public String getApprover()
	{
		return approver;
	}
	
	public void setApprover(String approver)
	{
		this.approver = approver;
	}
	
	public String getCandiRateType()
	{
		return candiRateType;
	}
	
	public void setCandiRateType(String candiRateType)
	{
		this.candiRateType = candiRateType;
	}
	
	public String getCandiFlatReward()
	{
		return candiFlatReward;
	}
	
	public void setCandiFlatReward(String candiFlatReward)
	{
		this.candiFlatReward = candiFlatReward;
	}
	
	public String getCandiRatePer()
	{
		return candiRatePer;
	}
	
	public void setCandiRatePer(String candiRatePer)
	{
		this.candiRatePer = candiRatePer;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public String getWorkLocation()
	{
		return workLocation;
	}
	
	public void setWorkLocation(String workLocation)
	{
		this.workLocation = workLocation;
	}
	
	public String getJoinDate()
	{
		return joinDate;
	}
	
	public void setJoinDate(String joinDate)
	{
		this.joinDate = joinDate;
	}
	
	public String getJurisdiction()
	{
		return jurisdiction;
	}
	
	public void setJurisdiction(String jurisdiction)
	{
		this.jurisdiction = jurisdiction;
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
	
	public String getTotalHoursEstimated()
	{
		return totalHoursEstimated;
	}
	
	public void setTotalHoursEstimated(String totalHoursEstimated)
	{
		this.totalHoursEstimated = totalHoursEstimated;
	}
	
	public String getOvertimeRate()
	{
		return overtimeRate;
	}
	
	public void setOvertimeRate(String overtimeRate)
	{
		this.overtimeRate = overtimeRate;
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
	
	public String getFlatReward()
	{
		return flatReward;
	}
	
	public void setFlatReward(String flatReward)
	{
		this.flatReward = flatReward;
	}
	
	public String getInvoiceFrequency()
	{
		return invoiceFrequency;
	}
	
	public void setInvoiceFrequency(String invoiceFrequency)
	{
		this.invoiceFrequency = invoiceFrequency;
	}
	
	public String getPaymentFrequency()
	{
		return paymentFrequency;
	}
	
	public void setPaymentFrequency(String paymentFrequency)
	{
		this.paymentFrequency = paymentFrequency;
	}
	
	public String getTaxRewardType()
	{
		return taxRewardType;
	}
	
	public void setTaxRewardType(String taxRewardType)
	{
		this.taxRewardType = taxRewardType;
	}
	
	public String getTaxPercentageReward()
	{
		return taxPercentageReward;
	}
	
	public void setTaxPercentageReward(String taxPercentageReward)
	{
		this.taxPercentageReward = taxPercentageReward;
	}
	
	public String getTaxFlatReward()
	{
		return taxFlatReward;
	}
	
	public void setTaxFlatReward(String taxFlatReward)
	{
		this.taxFlatReward = taxFlatReward;
	}
	
	public String getTaxFlatRateType()
	{
		return taxFlatRateType;
	}
	
	public void setTaxFlatRateType(String taxFlatRateType)
	{
		this.taxFlatRateType = taxFlatRateType;
	}
}
