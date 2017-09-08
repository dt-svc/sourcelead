package com.bis.sourcelead.dto;

import java.util.List;
import java.util.Map;

import com.bis.sourcelead.annotations.Required;

public class RequirementDTO {

	
	private String staticPath;

	private List<Map<String, String>> fileInfo;
	
	private List<FileInfo> uploadedFileInfo;
	
	private List<String> assignedTo;
	
	private String template;
	
	@Required(desc = "job title")
	private String jobTitle;

	private String jobType;
	
	private String jobId;
	
	private String otherJobType;
	
    private Long orgLocationId;
    
    private String projectDurationYears;
    
    private String projectDurationMonths;
	
    private String positions;
	
    private String education;	
	
    private String minExp;
	
	private String maxExp;
	
	private String industryName;
	
	private String currencyCode;
	
    private String rate;
	
	private String maxRate;
	
	private String rateValidation;
	
	private String rateType;
	
	private String travelAllowance;
	
	private String closedDate;
	
	private String isreceivingApplicants;
	
	@Required (desc ="location")
	private String location;
	
	private String countryReq;
	
	private String country;
	
	private String state;
	
	private String city;
	
	private String zipCode;
	
	private String skills;
	
	private List<String> visa;
	
	private String description;
	
	private List<String> noticePeriod;
	
	private String coolingOff;
	
	private String isTemplate;
	
	private String isClient;
	
	private String selectClient;
	
	private String clientCurrencyCode;
	
    private String clientRate;
	
	private String clientRateType;
	
	private String clientSubmissionForm;
	
	private String cd;
	
	private String approver;
	
	private List<String> parallelApprover;
	
    private String sequentialApprover1;
	
	private String sequentialApprover2;
	
	private String manager;
	
	
    /*private List<Map<String, String>> assinedToTeam;
	
	private List<AssinedTo> assinedTeamInfo;*/
	
	private String priority;
	
	private String applicationFrom;
	
	private String releaseToAllUsers;
	
	private String requirementId;
	
	private String templateId;
	
	private String saveType;
	
	private String selectContact;
	
	private String assignedToMaxCount;
	
	private String assignedTeamId;
	
	private List<String> assignUsers;
	
	private String jobOtherType;

	public String getStaticPath()
	{
		return staticPath;
	}

	public void setStaticPath(String staticPath)
	{
		this.staticPath = staticPath;
	}

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

	public String getTemplate()
	{
		return template;
	}

	public void setTemplate(String template)
	{
		this.template = template;
	}

	public String getJobTitle()
	{
		return jobTitle;
	}

	public void setJobTitle(String jobTitle)
	{
		this.jobTitle = jobTitle;
	}

	public String getJobType()
	{
		return jobType;
	}

	public void setJobType(String jobType)
	{
		this.jobType = jobType;
	}

	public String getJobId()
	{
		return jobId;
	}

	public void setJobId(String jobId)
	{
		this.jobId = jobId;
	}

	public String getOtherJobType()
	{
		return otherJobType;
	}

	public void setOtherJobType(String otherJobType)
	{
		this.otherJobType = otherJobType;
	}

	public Long getOrgLocationId()
	{
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId)
	{
		this.orgLocationId = orgLocationId;
	}

	public String getProjectDurationYears()
	{
		return projectDurationYears;
	}

	public void setProjectDurationYears(String projectDurationYears)
	{
		this.projectDurationYears = projectDurationYears;
	}

	public String getProjectDurationMonths()
	{
		return projectDurationMonths;
	}

	public void setProjectDurationMonths(String projectDurationMonths)
	{
		this.projectDurationMonths = projectDurationMonths;
	}

	public String getPositions()
	{
		return positions;
	}

	public void setPositions(String positions)
	{
		this.positions = positions;
	}

	public String getEducation()
	{
		return education;
	}

	public void setEducation(String education)
	{
		this.education = education;
	}

	public String getMinExp()
	{
		return minExp;
	}

	public void setMinExp(String minExp)
	{
		this.minExp = minExp;
	}

	public String getMaxExp()
	{
		return maxExp;
	}

	public void setMaxExp(String maxExp)
	{
		this.maxExp = maxExp;
	}

	public String getIndustryName()
	{
		return industryName;
	}

	public void setIndustryName(String industryName)
	{
		this.industryName = industryName;
	}

	public String getCurrencyCode()
	{
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode)
	{
		this.currencyCode = currencyCode;
	}

	public String getRate()
	{
		return rate;
	}

	public void setRate(String rate)
	{
		this.rate = rate;
	}

	public String getMaxRate()
	{
		return maxRate;
	}

	public void setMaxRate(String maxRate)
	{
		this.maxRate = maxRate;
	}

	public String getRateValidation()
	{
		return rateValidation;
	}

	public void setRateValidation(String rateValidation)
	{
		this.rateValidation = rateValidation;
	}

	public String getRateType()
	{
		return rateType;
	}

	public void setRateType(String rateType)
	{
		this.rateType = rateType;
	}

	public String getTravelAllowance()
	{
		return travelAllowance;
	}

	public void setTravelAllowance(String travelAllowance)
	{
		this.travelAllowance = travelAllowance;
	}


	public String getClosedDate()
	{
		return closedDate;
	}

	public void setClosedDate(String closedDate)
	{
		this.closedDate = closedDate;
	}

	public String getIsreceivingApplicants()
	{
		return isreceivingApplicants;
	}

	public void setIsreceivingApplicants(String isreceivingApplicants)
	{
		this.isreceivingApplicants = isreceivingApplicants;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public String getCountryReq()
	{
		return countryReq;
	}

	public void setCountryReq(String countryReq)
	{
		this.countryReq = countryReq;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getZipCode()
	{
		return zipCode;
	}

	public void setZipCode(String zipCode)
	{
		this.zipCode = zipCode;
	}

	

	public String getSkills()
	{
		return skills;
	}

	public void setSkills(String skills)
	{
		this.skills = skills;
	}

	public List<String> getVisa()
	{
		return visa;
	}

	public void setVisa(List<String> visa)
	{
		this.visa = visa;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public List<String> getNoticePeriod()
	{
		return noticePeriod;
	}

	public void setNoticePeriod(List<String> noticePeriod)
	{
		this.noticePeriod = noticePeriod;
	}

	public String getCoolingOff()
	{
		return coolingOff;
	}

	public void setCoolingOff(String coolingOff)
	{
		this.coolingOff = coolingOff;
	}

	public String getIsTemplate()
	{
		return isTemplate;
	}

	public void setIsTemplate(String isTemplate)
	{
		this.isTemplate = isTemplate;
	}

	public String getIsClient()
	{
		return isClient;
	}

	public void setIsClient(String isClient)
	{
		this.isClient = isClient;
	}

	public String getSelectClient()
	{
		return selectClient;
	}

	public void setSelectClient(String selectClient)
	{
		this.selectClient = selectClient;
	}

	public String getClientCurrencyCode()
	{
		return clientCurrencyCode;
	}

	public void setClientCurrencyCode(String clientCurrencyCode)
	{
		this.clientCurrencyCode = clientCurrencyCode;
	}

	public String getClientRate()
	{
		return clientRate;
	}

	public void setClientRate(String clientRate)
	{
		this.clientRate = clientRate;
	}

	public String getClientRateType()
	{
		return clientRateType;
	}

	public void setClientRateType(String clientRateType)
	{
		this.clientRateType = clientRateType;
	}

	public String getClientSubmissionForm()
	{
		return clientSubmissionForm;
	}

	public void setClientSubmissionForm(String clientSubmissionForm)
	{
		this.clientSubmissionForm = clientSubmissionForm;
	}

	public String getCd()
	{
		return cd;
	}

	public void setCd(String cd)
	{
		this.cd = cd;
	}

	public String getApprover()
	{
		return approver;
	}

	public void setApprover(String approver)
	{
		this.approver = approver;
	}

	public List<String> getParallelApprover()
	{
		return parallelApprover;
	}

	public void setParallelApprover(List<String> parallelApprover)
	{
		this.parallelApprover = parallelApprover;
	}

	public String getSequentialApprover1()
	{
		return sequentialApprover1;
	}

	public void setSequentialApprover1(String sequentialApprover1)
	{
		this.sequentialApprover1 = sequentialApprover1;
	}

	public String getSequentialApprover2()
	{
		return sequentialApprover2;
	}

	public void setSequentialApprover2(String sequentialApprover2)
	{
		this.sequentialApprover2 = sequentialApprover2;
	}

	public String getManager()
	{
		return manager;
	}

	public void setManager(String manager)
	{
		this.manager = manager;
	}

	

	

	public String getPriority()
	{
		return priority;
	}

	public void setPriority(String priority)
	{
		this.priority = priority;
	}

	public String getApplicationFrom()
	{
		return applicationFrom;
	}

	public void setApplicationFrom(String applicationFrom)
	{
		this.applicationFrom = applicationFrom;
	}

	public String getReleaseToAllUsers()
	{
		return releaseToAllUsers;
	}

	public void setReleaseToAllUsers(String releaseToAllUsers)
	{
		this.releaseToAllUsers = releaseToAllUsers;
	}

	public String getRequirementId()
	{
		return requirementId;
	}

	public void setRequirementId(String requirementId)
	{
		this.requirementId = requirementId;
	}

	public String getTemplateId()
	{
		return templateId;
	}

	public void setTemplateId(String templateId)
	{
		this.templateId = templateId;
	}

	public String getSaveType()
	{
		return saveType;
	}

	public void setSaveType(String saveType)
	{
		this.saveType = saveType;
	}

	public String getSelectContact()
	{
		return selectContact;
	}

	public void setSelectContact(String selectContact)
	{
		this.selectContact = selectContact;
	}

	public String getAssignedToMaxCount()
	{
		return assignedToMaxCount;
	}

	public void setAssignedToMaxCount(String assignedToMaxCount)
	{
		this.assignedToMaxCount = assignedToMaxCount;
	}

	public String getAssignedTeamId()
	{
		return assignedTeamId;
	}

	public void setAssignedTeamId(String assignedTeamId)
	{
		this.assignedTeamId = assignedTeamId;
	}

	
	public List<String> getAssignUsers()
	{
		return assignUsers;
	}

	public void setAssignUsers(List<String> assignUsers)
	{
		this.assignUsers = assignUsers;
	}

	public String getJobOtherType()
	{
		return jobOtherType;
	}

	public void setJobOtherType(String jobOtherType)
	{
		this.jobOtherType = jobOtherType;
	}

	public List<String> getAssignedTo()
	{
		return assignedTo;
	}

	public void setAssignedTo(List<String> assignedTo)
	{
		this.assignedTo = assignedTo;
	}

	
}
