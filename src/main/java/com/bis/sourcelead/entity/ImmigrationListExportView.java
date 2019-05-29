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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "IMMIGRATION_LIST_EXPORT_VIEW")
public class ImmigrationListExportView implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private Long id;
	
	
	@Column(name = "GENERATED_ID")
	private String generatedId;
	
	@Column(name = "CURRENT_USCIS_STATUS_ID ")
	private Long currUscisStatusId;
	
	@Column(name = "CURRENT_USCIS_STATUS ")
	private String currUscisStatus;
	
	@Column(name = "IMMIGRATION_TEMPLATE_ID")
	private Long immTemplateId;
	
	@Column(name = "CANDIDATE_ID")
	private Long candidateId;
	
	@Column(name = "COUNTRY")
	private String country;
	
	@Column(name = "ORG_LOCATION_ID")
	private Long orgLocationId;
	
	@Column(name = "ATTORNEY_LOCATION_ID")
	private Long attorneyOrgLocationId;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "IMMIGRATION_STATUS")
	private String immStatus;

	@Column(name = "PETITON_TYPE")
	private String petitionType;
	
	@Column(name = "OPEN_DATE")
	private Date openDate;

	@Column(name = "LCA_START_DATE")
	private Date lcaStartDate;
	
	@Column(name = "LCA_END_DATE")	
	private Date lcaEndDate;
	
	@Column(name = "LCA_TITLE")	
	private String lcaTitle;
	
	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "PHONE ")
	private String phone;

	@Column(name = "CLIENT ")
	private String client;
	
	@Column(name = "VENDOR ")
	private String vendor;
	
	@Column(name = "PETITION_TYPE")
	private Long petitionTypeId;
	
	@Column(name = "SPOC_NAME_CLIENT")
	private String spocNameClient;
	
	@Column(name = "SPOC_NAME_VENDOR")
	private String spocNameVendor;
	
	@Column(name = "DATE_OF_ARRIVAL")
	private Date arrivalDate;
	
	@Column(name = "RECEIPT_NO")
	private String receiptNo;
	
	@Column(name = "SSN_NUMBER")
	private String ssnNumber;
	
	@Column(name = "TEMPLATE_NAME")
	@Type(type="text")
	private String templateName;
	
	@Column(name = "VISA_ID_NUMBER")
	private String visaIdNumber;
	
	@Column(name = "VISA_VALID_FROM_DATE")
	private Date visaValidFromDate;
	
	@Column(name = "VISA_VALID_TO_DATE")
	private Date visaValidToDate;
	
	@Column(name = "LCA_NUMBER")
	private String lcaNumber;
	
	@Column(name = "LCA_RATE")
	private String lcaRate;
	
	@Column(name = "EMPLOYER_LOCATION", columnDefinition = "LONGTEXT default NULL")
	private String employerLocation;
	
	@Column(name = "PREVIOUS_LCA_START_DATE")
	private Date previousLcaStartDate;
	
	@Column(name = "PREVIOUS_LCA_END_DATE")
	private Date previousLcaEndDate;
	
	@Column(name = "PREVIOUS_LCA_NUMBER")
	private String previousLcaNumber;
	
	@Column(name = "PREVIOUS_LCA_RATE")
	private String previousLcaRate;
	
	/*@Column(name = "PREVIOUS_EMPLOYER_LOCATION")
	private String previousEmployerLocation;*/
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "SPOC_EMAIL_CLIENT")
	private String spocEmailClient;
	
	@Column(name = "SPOC_EMAIL_VENDOR")
	private String spocEmailVendor;
	
	@Column(name = "EXPERIENCE_YEARS")
	private String experienceYears;
	
	@Column(name = "EXPERIENCE_MONTHS")
	private String experienceMonths;
	
	@Column(name = "I_94_NO")
	private String i94No;
	
	@Column(name = "EAD_NO")
	private String eadNo;
	
	@Column(name = "I_20_SEVIS_NO")
	private String i20SevisNo;
	
	@Column(name = "DATE_OF_BIRTH")
	private Date dateOfBirth;
	
	@Column(name = "MARITAL_STATUS")
	private String maritalStatus;
	
	@Column(name = "JOB_NAME")
	private String jobName;
	
	@Column(name = "JOB_ID")
	private String jobId;
	
	@Column(name = "LOCATION", columnDefinition = "LONGTEXT default NULL")
	private String location;
	
	@Transient
	private String visaStatus;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCurrUscisStatus() {
		return currUscisStatus;
	}

	public void setCurrUscisStatus(String currUscisStatus) {
		this.currUscisStatus = currUscisStatus;
	}

	public String getPetitionType() {
		return petitionType;
	}

	public void setPetitionType(String petitionType) {
		this.petitionType = petitionType;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public Date getLcaStartDate() {
		return lcaStartDate;
	}

	public void setLcaStartDate(Date lcaStartDate) {
		this.lcaStartDate = lcaStartDate;
	}

	public Date getLcaEndDate() {
		return lcaEndDate;
	}

	public void setLcaEndDate(Date lcaEndDate) {
		this.lcaEndDate = lcaEndDate;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getImmTemplateId() {
		return immTemplateId;
	}

	public void setImmTemplateId(Long immTemplateId) {
		this.immTemplateId = immTemplateId;
	}

	public Long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getImmStatus() {
		return immStatus;
	}

	public void setImmStatus(String immStatus) {
		this.immStatus = immStatus;
	}

	public Long getCurrUscisStatusId() {
		return currUscisStatusId;
	}

	public void setCurrUscisStatusId(Long currUscisStatusId) {
		this.currUscisStatusId = currUscisStatusId;
	}

	public String getGeneratedId() {
		return generatedId;
	}

	public void setGeneratedId(String generatedId) {
		this.generatedId = generatedId;
	}

	public Long getAttorneyOrgLocationId() {
		return attorneyOrgLocationId;
	}

	public void setAttorneyOrgLocationId(Long attorneyOrgLocationId) {
		this.attorneyOrgLocationId = attorneyOrgLocationId;
	}

	public Long getPetitionTypeId() {
		return petitionTypeId;
	}

	public void setPetitionTypeId(Long petitionTypeId) {
		this.petitionTypeId = petitionTypeId;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getSpocNameClient() {
		return spocNameClient;
	}

	public void setSpocNameClient(String spocNameClient) {
		this.spocNameClient = spocNameClient;
	}

	public String getSpocNameVendor() {
		return spocNameVendor;
	}

	public void setSpocNameVendor(String spocNameVendor) {
		this.spocNameVendor = spocNameVendor;
	}
	
	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getSsnNumber() {
		return ssnNumber;
	}

	public void setSsnNumber(String ssnNumber) {
		this.ssnNumber = ssnNumber;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getVisaIdNumber() {
		return visaIdNumber;
	}

	public void setVisaIdNumber(String visaIdNumber) {
		this.visaIdNumber = visaIdNumber;
	}

	public Date getVisaValidFromDate() {
		return visaValidFromDate;
	}

	public void setVisaValidFromDate(Date visaValidFromDate) {
		this.visaValidFromDate = visaValidFromDate;
	}

	public Date getVisaValidToDate() {
		return visaValidToDate;
	}

	public void setVisaValidToDate(Date visaValidToDate) {
		this.visaValidToDate = visaValidToDate;
	}

	public String getLcaNumber() {
		return lcaNumber;
	}

	public void setLcaNumber(String lcaNumber) {
		this.lcaNumber = lcaNumber;
	}

	public String getLcaRate() {
		return lcaRate;
	}

	public void setLcaRate(String lcaRate) {
		this.lcaRate = lcaRate;
	}

	public String getEmployerLocation() {
		return employerLocation;
	}

	public void setEmployerLocation(String employerLocation) {
		this.employerLocation = employerLocation;
	}

	public Date getPreviousLcaStartDate() {
		return previousLcaStartDate;
	}

	public void setPreviousLcaStartDate(Date previousLcaStartDate) {
		this.previousLcaStartDate = previousLcaStartDate;
	}

	public Date getPreviousLcaEndDate() {
		return previousLcaEndDate;
	}

	public void setPreviousLcaEndDate(Date previousLcaEndDate) {
		this.previousLcaEndDate = previousLcaEndDate;
	}

	public String getPreviousLcaNumber() {
		return previousLcaNumber;
	}

	public void setPreviousLcaNumber(String previousLcaNumber) {
		this.previousLcaNumber = previousLcaNumber;
	}

	public String getPreviousLcaRate() {
		return previousLcaRate;
	}

	public void setPreviousLcaRate(String previousLcaRate) {
		this.previousLcaRate = previousLcaRate;
	}

	/*public String getPreviousEmployerLocation() {
		return previousEmployerLocation;
	}

	public void setPreviousEmployerLocation(String previousEmployerLocation) {
		this.previousEmployerLocation = previousEmployerLocation;
	}*/

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSpocEmailClient() {
		return spocEmailClient;
	}

	public void setSpocEmailClient(String spocEmailClient) {
		this.spocEmailClient = spocEmailClient;
	}

	public String getSpocEmailVendor() {
		return spocEmailVendor;
	}

	public void setSpocEmailVendor(String spocEmailVendor) {
		this.spocEmailVendor = spocEmailVendor;
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

	public String getI94No() {
		return i94No;
	}

	public void setI94No(String i94No) {
		this.i94No = i94No;
	}

	public String getEadNo() {
		return eadNo;
	}

	public void setEadNo(String eadNo) {
		this.eadNo = eadNo;
	}

	public String getI20SevisNo() {
		return i20SevisNo;
	}

	public void setI20SevisNo(String i20SevisNo) {
		this.i20SevisNo = i20SevisNo;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	
	public String getVisaStatus() {
		return visaStatus;
	}

	public void setVisaStatus(String visaStatus) {
		this.visaStatus = visaStatus;
	}

	public String getLcaTitle() {
		return lcaTitle;
	}

	public void setLcaTitle(String lcaTitle) {
		this.lcaTitle = lcaTitle;
	}


}
