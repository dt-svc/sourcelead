package com.bis.sourcelead.dto;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.annotations.Email;
import com.bis.sourcelead.annotations.Phone;
import com.bis.sourcelead.annotations.Required;

public class ImmigrationDTO extends SearchDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	@Required(desc = "candidate id")
	private String candidateId;

	private String orgloationId;

	private String fileUrl;

	private String fileName;

	private String fileContentType;

	private List<FileInfo> resumeInfo;

	private String candidateType;
	
	@Required(desc = "Petition type")
	private Long petitionType;
	
	private String receiptNo;

	@Required(desc = "Current USCIS Status")
	private Long currUscisStatus;

	@Required(desc = "Petition open date")
	private Date openDate;
	
	private Date arrivalDate;
	
	private String entryPlace;

	private Long TemplateId;

	private Long attorneyId;

	private Long attorneyContact;

	@Required(desc = "first name")
	private String firstName;

	private String middleName;

	private String lastName;

	private String gender;

	@Required(desc = "email")
	@Email
	private String email;

	@Phone
	private String mobile;

	@Phone
	private String alternateMobile;

	@Required(desc = "Date of Birth")
	private Date birthDate;

	private String birthPlace;

	private String nationality;

	private String maritalStatus;

	private String ssnNumber;

	private String i_94_number;
	private String i_20_number;
	private String eadNumber;

	private String prevLcaNumber;

	private Date prevLcaStartDate;

	private Date prevLcaEndDate;

	private String experienceYears;

	private String experienceMonths;

	private Long jobId;

	private String employmentLevel;

	private String employer;

	private String jobLocation;

	private Date jobStartDate;

	private Date jobEndDate;

	private String lcaNumber;

	private String lcaStartDate;

	private String lcaEndDate;

	private String lcaCurrentStatus;

	@Required(desc = "country")
	private String country;

	private String state;

	private String city;

	private String address;

	private String location;

	private String zipcode;

	private String isCurrResidence;

	private Date residenceStartDate;

	private Date residenceEndtDate;
	
	private String militaryServiceCountry;
	
	private String militaryRank;
	
	private String militartBranch;

	private Date militaryServiceStartDate;

	private Date militaryServiceEndtDate;

	private String status;

	private String createdDate;

	private String createdBy;

	private String updatedDate;

	private String updatedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getPetitionType() {
		return petitionType;
	}

	public void setPetitionType(Long petitionType) {
		this.petitionType = petitionType;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public Long getCurrUscisStatus() {
		return currUscisStatus;
	}

	public void setCurrUscisStatus(Long currUscisStatus) {
		this.currUscisStatus = currUscisStatus;
	}

	

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public String getEntryPlace() {
		return entryPlace;
	}

	public void setEntryPlace(String entryPlace) {
		this.entryPlace = entryPlace;
	}

	public Long getTemplateId() {
		return TemplateId;
	}

	public void setTemplateId(Long templateId) {
		TemplateId = templateId;
	}

	public Long getAttorneyId() {
		return attorneyId;
	}

	public void setAttorneyId(Long attorneyId) {
		this.attorneyId = attorneyId;
	}

	

	public Long getAttorneyContact() {
		return attorneyContact;
	}

	public void setAttorneyContact(Long attorneyContact) {
		this.attorneyContact = attorneyContact;
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


	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
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

	public Date getPrevLcaStartDate() {
		return prevLcaStartDate;
	}

	public void setPrevLcaStartDate(Date prevLcaStartDate) {
		this.prevLcaStartDate = prevLcaStartDate;
	}

	public Date getPrevLcaEndDate() {
		return prevLcaEndDate;
	}

	public void setPrevLcaEndDate(Date prevLcaEndDate) {
		this.prevLcaEndDate = prevLcaEndDate;
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

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
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

	public Date getJobStartDate() {
		return jobStartDate;
	}

	public void setJobStartDate(Date jobStartDate) {
		this.jobStartDate = jobStartDate;
	}

	public Date getJobEndDate() {
		return jobEndDate;
	}

	public void setJobEndDate(Date jobEndDate) {
		this.jobEndDate = jobEndDate;
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

	public String getLcaCurrentStatus() {
		return lcaCurrentStatus;
	}

	public void setLcaCurrentStatus(String lcaCurrentStatus) {
		this.lcaCurrentStatus = lcaCurrentStatus;
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

	public Date getResidenceStartDate() {
		return residenceStartDate;
	}

	public void setResidenceStartDate(Date residenceStartDate) {
		this.residenceStartDate = residenceStartDate;
	}

	public Date getResidenceEndtDate() {
		return residenceEndtDate;
	}

	public void setResidenceEndtDate(Date residenceEndtDate) {
		this.residenceEndtDate = residenceEndtDate;
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

	public Date getMilitaryServiceStartDate() {
		return militaryServiceStartDate;
	}

	public void setMilitaryServiceStartDate(Date militaryServiceStartDate) {
		this.militaryServiceStartDate = militaryServiceStartDate;
	}

	public Date getMilitaryServiceEndtDate() {
		return militaryServiceEndtDate;
	}

	public void setMilitaryServiceEndtDate(Date militaryServiceEndtDate) {
		this.militaryServiceEndtDate = militaryServiceEndtDate;
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
	

}
