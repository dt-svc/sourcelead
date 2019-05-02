package com.bis.sourcelead.dto;

import java.util.HashMap;
import java.util.List;

import com.bis.sourcelead.annotations.Email;
import com.bis.sourcelead.annotations.Phone;
import com.bis.sourcelead.annotations.Required;
import com.bis.sourcelead.entity.LookUpData;

public class ImmigrationDTO extends SearchDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String generatedId; 
	
	@Required(desc="candidateId")
	private String candidateId;

	private String orgloationId;
	
	private String staticPath;
	
	private String fileUrl;

	private String fileName;

	private String fileContentType;

	private List<FileInfo> resumeInfo;

	private String candidateType;
	
	@Required(desc = "petition type")
	private String petitionType;
	
	@Required(desc = "process type")
	private String processType;
	
	private String receiptNo;

	private String currUscisStatus;

	@Required(desc = "petition open date")
	private String openDate;
	
	private String arrivalDate;
	
	@Required(desc="entry place")
	private String entryPlace;

	private String templateId;
	
	private String templateName;

	@Required(desc="attorney name")
	private String attorneyId;
	
	private String attorneyName;

	@Required(desc="attorney contact")
	private String attorneyContact;
	
	private String associateName;
	
	private String attorneyEmail;
	
	private String attorneyFax;
	
	private String attorneyPhone;
	
	private String attorneyLawFirm;
	
	private String editMode;

	@Required(desc = "first name")
	private String firstName;

	private String middleName;
	
	@Required(desc = "last name")
	private String lastName;

	private String gender;

	@Required(desc = "email")
	@Email
	private String email;

	@Phone
	private String mobile;

	private String alternateMobile;

	@Required(desc = "date of birth")
	private String birthDate;

	private String birthPlace;

	private String nationality;

	private String maritalStatus;

	private String ssnNumber;

	private String i_94_number;
	private String i_20_number;
	private String eadNumber;

	private String prevLcaNumber;

	private String prevLcaStartDate;

	private String prevLcaEndDate;

	private String experienceYears;

	private String experienceMonths;
	
	private String experience;

	@Required(desc = "Job Id")
	private String jobId;
	
	private String jobTitle;
	
	private String employmentLevel;
	
	private String employmentLevelName;

	private String employer;

	private String jobLocation;
	
	private String rate;
	
	private String rateCurrency;
	
	private String rateType;
	
	private String jobStartDate;

	private String jobEndDate;

	private String lcaNumber;

	private String lcaStartDate;

	private String lcaEndDate;

	private String country;

	private String state;

	private String city;

	private String address;
	
	@Required(desc = "location")
	private String location;

	private String zipcode;

	private String isCurrResidence;
	
	@Required(desc = "residence start date")
	private String residenceStartDate;

	private String residenceEndDate;
	
	private String militaryServiceCountry;
	
	private String militaryRank;
	
	private String militartBranch;

	private String militaryServiceStartDate;

	private String militaryServiceEndtDate;

	private String status;

	private String createdDate;

	private String createdBy;

	private String updatedDate;

	private String updatedBy;
	
    private List<ImmigrationResidenceDTO> residenceList;
    
    private String immigrationId;
    
    private String immigrationUUID;
    
    private String[] removedResidenceIds;
    
    private Long currUscisStatusId;
    
    private String orgName;
    
    private String attorneyContactName;
    
    private String isOwnTemplateId;
    
    private String candidateNameEmail;
   
	private String lcaId;
	
	private String prevLcaId;
	
	private String lcaCurrLocation;
	
    private HashMap<String, Integer> tabRecordCountMap;
	
	private String myImmigrationPage;
	
	List<LookUpData> uscisStatusList;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGeneratedId() {
		return generatedId;
	}

	public void setGeneratedId(String generatedId) {
		this.generatedId = generatedId;
	}

	public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	public String getOrgloationId() {
		return orgloationId;
	}

	public void setOrgloationId(String orgloationId) {
		this.orgloationId = orgloationId;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getStaticPath() {
		return staticPath;
	}

	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public List<FileInfo> getResumeInfo() {
		return resumeInfo;
	}

	public void setResumeInfo(List<FileInfo> resumeInfo) {
		this.resumeInfo = resumeInfo;
	}

	public String getCandidateType() {
		return candidateType;
	}

	public void setCandidateType(String candidateType) {
		this.candidateType = candidateType;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getEntryPlace() {
		return entryPlace;
	}

	public void setEntryPlace(String entryPlace) {
		this.entryPlace = entryPlace;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAlternateMobile() {
		return alternateMobile;
	}

	public void setAlternateMobile(String alternateMobile) {
		this.alternateMobile = alternateMobile;
	}


	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getSsnNumber() {
		return ssnNumber;
	}

	public void setSsnNumber(String ssnNumber) {
		this.ssnNumber = ssnNumber;
	}

	public String getI_94_number() {
		return i_94_number;
	}

	public void setI_94_number(String i_94_number) {
		this.i_94_number = i_94_number;
	}

	public String getI_20_number() {
		return i_20_number;
	}

	public void setI_20_number(String i_20_number) {
		this.i_20_number = i_20_number;
	}

	public String getEadNumber() {
		return eadNumber;
	}

	public void setEadNumber(String eadNumber) {
		this.eadNumber = eadNumber;
	}

	public String getPrevLcaNumber() {
		return prevLcaNumber;
	}

	public void setPrevLcaNumber(String prevLcaNumber) {
		this.prevLcaNumber = prevLcaNumber;
	}

	public String getExperienceYears() {
		return experienceYears;
	}

	public void setExperienceYears(String experienceYears) {
		this.experienceYears = experienceYears;
	}

	public String getExperienceMonths() {
		return experienceMonths;
	}

	public void setExperienceMonths(String experienceMonths) {
		this.experienceMonths = experienceMonths;
	}

	public String getEmploymentLevel() {
		return employmentLevel;
	}

	public void setEmploymentLevel(String employmentLevel) {
		this.employmentLevel = employmentLevel;
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public String getJobLocation() {
		return jobLocation;
	}

	public void setJobLocation(String jobLocation) {
		this.jobLocation = jobLocation;
	}

	public String getLcaNumber() {
		return lcaNumber;
	}

	public void setLcaNumber(String lcaNumber) {
		this.lcaNumber = lcaNumber;
	}

	public String getLcaStartDate() {
		return lcaStartDate;
	}

	public void setLcaStartDate(String lcaStartDate) {
		this.lcaStartDate = lcaStartDate;
	}

	public String getLcaEndDate() {
		return lcaEndDate;
	}

	public void setLcaEndDate(String lcaEndDate) {
		this.lcaEndDate = lcaEndDate;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}



	public String getIsCurrResidence() {
		return isCurrResidence;
	}

	public void setIsCurrResidence(String isCurrResidence) {
		this.isCurrResidence = isCurrResidence;
	}

	public String getMilitaryServiceCountry() {
		return militaryServiceCountry;
	}

	public void setMilitaryServiceCountry(String militaryServiceCountry) {
		this.militaryServiceCountry = militaryServiceCountry;
	}

	public String getMilitaryRank() {
		return militaryRank;
	}

	public void setMilitaryRank(String militaryRank) {
		this.militaryRank = militaryRank;
	}

	public String getMilitartBranch() {
		return militartBranch;
	}

	public void setMilitartBranch(String militartBranch) {
		this.militartBranch = militartBranch;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getRateCurrency() {
		return rateCurrency;
	}

	public void setRateCurrency(String rateCurrency) {
		this.rateCurrency = rateCurrency;
	}

	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public String getPetitionType() {
		return petitionType;
	}

	public void setPetitionType(String petitionType) {
		this.petitionType = petitionType;
	}

	public String getCurrUscisStatus() {
		return currUscisStatus;
	}

	public void setCurrUscisStatus(String currUscisStatus) {
		this.currUscisStatus = currUscisStatus;
	}

	public String getOpenDate() {
		return openDate;
	}

	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}

	public String getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getAttorneyId() {
		return attorneyId;
	}

	public void setAttorneyId(String attorneyId) {
		this.attorneyId = attorneyId;
	}

	public String getAttorneyContact() {
		return attorneyContact;
	}

	public void setAttorneyContact(String attorneyContact) {
		this.attorneyContact = attorneyContact;
	}

	public String getPrevLcaStartDate() {
		return prevLcaStartDate;
	}

	public void setPrevLcaStartDate(String prevLcaStartDate) {
		this.prevLcaStartDate = prevLcaStartDate;
	}

	public String getPrevLcaEndDate() {
		return prevLcaEndDate;
	}

	public void setPrevLcaEndDate(String prevLcaEndDate) {
		this.prevLcaEndDate = prevLcaEndDate;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getJobStartDate() {
		return jobStartDate;
	}

	public void setJobStartDate(String jobStartDate) {
		this.jobStartDate = jobStartDate;
	}

	public String getJobEndDate() {
		return jobEndDate;
	}

	public void setJobEndDate(String jobEndDate) {
		this.jobEndDate = jobEndDate;
	}

	public String getResidenceStartDate() {
		return residenceStartDate;
	}

	public void setResidenceStartDate(String residenceStartDate) {
		this.residenceStartDate = residenceStartDate;
	}

	public String getResidenceEndDate() {
		return residenceEndDate;
	}

	public void setResidenceEndDate(String residenceEndDate) {
		this.residenceEndDate = residenceEndDate;
	}

	public String getMilitaryServiceStartDate() {
		return militaryServiceStartDate;
	}

	public void setMilitaryServiceStartDate(String militaryServiceStartDate) {
		this.militaryServiceStartDate = militaryServiceStartDate;
	}

	public String getMilitaryServiceEndtDate() {
		return militaryServiceEndtDate;
	}

	public void setMilitaryServiceEndtDate(String militaryServiceEndtDate) {
		this.militaryServiceEndtDate = militaryServiceEndtDate;
	}

	public List<ImmigrationResidenceDTO> getResidenceList() {
		return residenceList;
	}

	public void setResidenceList(List<ImmigrationResidenceDTO> residenceList) {
		this.residenceList = residenceList;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getAttorneyName() {
		return attorneyName;
	}

	public void setAttorneyName(String attorneyName) {
		this.attorneyName = attorneyName;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getEmploymentLevelName() {
		return employmentLevelName;
	}

	public void setEmploymentLevelName(String employmentLevelName) {
		this.employmentLevelName = employmentLevelName;
	}

	public String getAssociateName() {
		return associateName;
	}

	public void setAssociateName(String associateName) {
		this.associateName = associateName;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getAttorneyEmail() {
		return attorneyEmail;
	}

	public void setAttorneyEmail(String attorneyEmail) {
		this.attorneyEmail = attorneyEmail;
	}

	public String getAttorneyFax() {
		return attorneyFax;
	}

	public void setAttorneyFax(String attorneyFax) {
		this.attorneyFax = attorneyFax;
	}

	public String getAttorneyPhone() {
		return attorneyPhone;
	}

	public void setAttorneyPhone(String attorneyPhone) {
		this.attorneyPhone = attorneyPhone;
	}

	public String getAttorneyLawFirm() {
		return attorneyLawFirm;
	}

	public void setAttorneyLawFirm(String attorneyLawFirm) {
		this.attorneyLawFirm = attorneyLawFirm;
	}

	public String getImmigrationId() {
		return immigrationId;
	}

	public void setImmigrationId(String immigrationId) {
		this.immigrationId = immigrationId;
	}

	public String[] getRemovedResidenceIds() {
		return removedResidenceIds;
	}

	public void setRemovedResidenceIds(String[] removedResidenceIds) {
		this.removedResidenceIds = removedResidenceIds;
	}

	public Long getCurrUscisStatusId() {
		return currUscisStatusId;
	}

	public void setCurrUscisStatusId(Long currUscisStatusId) {
		this.currUscisStatusId = currUscisStatusId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getAttorneyContactName() {
		return attorneyContactName;
	}

	public void setAttorneyContactName(String attorneyContactName) {
		this.attorneyContactName = attorneyContactName;
	}

	public String getEditMode() {
		return editMode;
	}

	public void setEditMode(String editMode) {
		this.editMode = editMode;
	}

	public String getIsOwnTemplateId() {
		return isOwnTemplateId;
	}

	public void setIsOwnTemplateId(String isOwnTemplateId) {
		this.isOwnTemplateId = isOwnTemplateId;
	}

	public String getImmigrationUUID() {
		return immigrationUUID;
	}

	public void setImmigrationUUID(String immigrationUUID) {
		this.immigrationUUID = immigrationUUID;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public String getCandidateNameEmail() {
		return candidateNameEmail;
	}

	public void setCandidateNameEmail(String candidateNameEmail) {
		this.candidateNameEmail = candidateNameEmail;
	}

	public String getLcaId() {
		return lcaId;
	}

	public void setLcaId(String lcaId) {
		this.lcaId = lcaId;
	}

	public String getLcaCurrLocation() {
		return lcaCurrLocation;
	}

	public void setLcaCurrLocation(String lcaCurrLocation) {
		this.lcaCurrLocation = lcaCurrLocation;
	}

	public String getPrevLcaId() {
		return prevLcaId;
	}

	public void setPrevLcaId(String prevLcaId) {
		this.prevLcaId = prevLcaId;
	}

	public HashMap<String, Integer> getTabRecordCountMap() {
		return tabRecordCountMap;
	}

	public void setTabRecordCountMap(HashMap<String, Integer> tabRecordCountMap) {
		this.tabRecordCountMap = tabRecordCountMap;
	}

	public String getMyImmigrationPage() {
		return myImmigrationPage;
	}

	public void setMyImmigrationPage(String myImmigrationPage) {
		this.myImmigrationPage = myImmigrationPage;
	}

	public List<LookUpData> getUscisStatusList() {
		return uscisStatusList;
	}

	public void setUscisStatusList(List<LookUpData> uscisStatusList) {
		this.uscisStatusList = uscisStatusList;
	}
	
}