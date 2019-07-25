package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Transient;
import com.bis.sourcelead.annotations.CleanHtml;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ContractsDTO extends SearchDTO implements Serializable {
	private String staticPath;
	private String contrId;
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
	@CleanHtml(escape=false)
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
	private String overtimeRateType;
//	private List<Map<String, String>> fileInfo;
	private List<FileInfo> fileInfo;
	private List<ContractDocumentsDTO> document;
	private String percentageReward;
	private String flatRateType;
	
	private List<VendorContractsDTO> vendorcontractsList;
	private List<ClientContractsDTO> clientcontractsList;
	private List<FullTimeContractsDTO> fullTimeoffercontractsList;
	
	private List<FileInfo> reqDocs;
	
	private String orgRelationUUID;
	
	private String spocUUID;
	
	private List<CustomFormFieldDTO> customFormData;
	
	private String joinedDateStr;
	
	private String ownersType;
	
	private Long sequentialOwner1;
	
	private Long sequentialOwner2;
	
	private String saveType;
	
	private List<Long> parallelOwner;
	
	private String contractUUID;
	
	private String revisedReason;
	
	private String typeOfContract;
	
	private String contractSubmissionType;
	
	private String tabType;
	
	private String contractsType;
	
	private String contractStatus;
	
	private String userRoleName;
	
	private String contractAnotherStage;
	
	public String getStaticPath()
	{
		return staticPath;
	}
	
	public void setStaticPath(String staticPath)
	{
		this.staticPath = staticPath;
	}
	
	public String getContrId()
	{
		return contrId;
	}
	
	public void setContrId(String contrId)
	{
		this.contrId = contrId;
	}
	
	/*public List<Map<String, String>> getFileInfo()
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
	}*/
	
	public String getContractType()
	{
		return contractType;
	}
	
	public List<FileInfo> getFileInfo()
	{
		return fileInfo;
	}

	public void setFileInfo(List<FileInfo> fileInfo)
	{
		this.fileInfo = fileInfo;
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
	
	public String getOvertimeRateType()
	{
		return overtimeRateType;
	}
	
	public void setOvertimeRateType(String overtimeRateType)
	{
		this.overtimeRateType = overtimeRateType;
	}
	
	public List<ContractDocumentsDTO> getDocument()
	{
		return document;
	}
	
	public void setDocument(List<ContractDocumentsDTO> document)
	{
		this.document = document;
	}

	public String getPercentageReward() {
		return percentageReward;
	}

	public void setPercentageReward(String percentageReward) {
		this.percentageReward = percentageReward;
	}

	public String getFlatRateType() {
		return flatRateType;
	}

	public void setFlatRateType(String flatRateType) {
		this.flatRateType = flatRateType;
	}

	public List<FileInfo> getReqDocs() {
		return reqDocs;
	}

	public void setReqDocs(List<FileInfo> reqDocs) {
		this.reqDocs = reqDocs;
	}

	public List<VendorContractsDTO> getVendorcontractsList()
	{
		return vendorcontractsList;
	}

	public void setVendorcontractsList(List<VendorContractsDTO> vendorcontractsList)
	{
		this.vendorcontractsList = vendorcontractsList;
	}

	public List<ClientContractsDTO> getClientcontractsList()
	{
		return clientcontractsList;
	}

	public void setClientcontractsList(List<ClientContractsDTO> clientcontractsList)
	{
		this.clientcontractsList = clientcontractsList;
	}

	public List<FullTimeContractsDTO> getFullTimeoffercontractsList()
	{
		return fullTimeoffercontractsList;
	}

	public void setFullTimeoffercontractsList(List<FullTimeContractsDTO> fullTimeoffercontractsList)
	{
		this.fullTimeoffercontractsList = fullTimeoffercontractsList;
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

	public List<CustomFormFieldDTO> getCustomFormData() {
		return customFormData;
	}

	public void setCustomFormData(List<CustomFormFieldDTO> customFormData) {
		this.customFormData = customFormData;
	}

	public String getJoinedDateStr()
	{
		return joinedDateStr;
	}

	public void setJoinedDateStr(String joinedDateStr)
	{
		this.joinedDateStr = joinedDateStr;
	}

	public Long getSequentialOwner1()
	{
		return sequentialOwner1;
	}

	public void setSequentialOwner1(Long sequentialOwner1)
	{
		this.sequentialOwner1 = sequentialOwner1;
	}

	public Long getSequentialOwner2()
	{
		return sequentialOwner2;
	}

	public void setSequentialOwner2(Long sequentialOwner2)
	{
		this.sequentialOwner2 = sequentialOwner2;
	}

	public List<Long> getParallelOwner()
	{
		return parallelOwner;
	}

	public void setParallelOwner(List<Long> parallelOwner)
	{
		this.parallelOwner = parallelOwner;
	}

	public String getOwnersType()
	{
		return ownersType;
	}

	public void setOwnersType(String ownersType)
	{
		this.ownersType = ownersType;
	}

	public String getSaveType()
	{
		return saveType;
	}

	public void setSaveType(String saveType)
	{
		this.saveType = saveType;
	}

	public String getContractUUID()
	{
		return contractUUID;
	}

	public void setContractUUID(String contractUUID)
	{
		this.contractUUID = contractUUID;
	}

	public String getRevisedReason()
	{
		return revisedReason;
	}

	public void setRevisedReason(String revisedReason)
	{
		this.revisedReason = revisedReason;
	}

	public String getTypeOfContract()
	{
		return typeOfContract;
	}

	public void setTypeOfContract(String typeOfContract)
	{
		this.typeOfContract = typeOfContract;
	}

	public String getContractSubmissionType()
	{
		return contractSubmissionType;
	}

	public void setContractSubmissionType(String contractSubmissionType)
	{
		this.contractSubmissionType = contractSubmissionType;
	}

	public String getTabType()
	{
		return tabType;
	}

	public void setTabType(String tabType)
	{
		this.tabType = tabType;
	}

	public String getContractsType()
	{
		return contractsType;
	}

	public void setContractsType(String contractsType)
	{
		this.contractsType = contractsType;
	}

	public String getContractStatus()
	{
		return contractStatus;
	}

	public void setContractStatus(String contractStatus)
	{
		this.contractStatus = contractStatus;
	}

	public String getUserRoleName()
	{
		return userRoleName;
	}

	public void setUserRoleName(String userRoleName)
	{
		this.userRoleName = userRoleName;
	}

	public String getContractAnotherStage() {
		return contractAnotherStage;
	}

	public void setContractAnotherStage(String contractAnotherStage) {
		this.contractAnotherStage = contractAnotherStage;
	}
	
}
