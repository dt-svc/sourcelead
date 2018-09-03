package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Laxman
 *
 */
@Entity
@Table(name = "REQUIREMENT_SUBMISSION_TEMPLATE_FIELDS")
public class RequirementSubmissionTemplateFields implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
 	
	@JsonBackReference
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REQUIREMENT_SUBMISSION_ID")
   	private RequirementSubmission requirementSubmission;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "PHONE_NUMBER")
	private String phoneNumber;
	
	@Column(name = "ALTERNATE_NUMBER")
	private String alternateNumber;
	
	@Column(name = "EMAIL_ID")
	private String emailId;
	
	@Column(name = "COUNTRY")
	private String country;
	
	@Column(name = "STATE")
	private String state;
	
	@Column(name = "CITY")
	private String city;
	
	@Column(name = "LOCATION")
	private String location;
	
	@Column(name = "ZIPCODE")
	private String zipCode;

	@Column(name = "DOB")
	private Date dateOfBirth;
	
	@Column(name = "CURRENT_OR_LAST_CAOMPANY")
	private String currentORLastCompany;
	
	@Column(name = "DESIGNATION")
	private String designation;
	
	@Column(name = "EDUCATION")
	@Type(type="text")
	private String education;
	
	@Column(name = "WORK_EXPERIENCE")
	private String workExperience;
	
	@Column(name = "CURRENT_CTC")
	private String currentCTC;

	@Column(name = "EXPECTED_CTC")
	private String expectedCTC;
	
	@Column(name = "NOTICE_PERIOD")
	private String noticePeriod;
	
	@Column(name = "STATUS")
    private Integer status;
	
    @Column(name = "CREATED_DATE")
    private Date createdDate;
    
    @Column(name = "CREATED_BY")
    private String createdBy;
    
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;
    
    @Column(name = "UPDATED_BY")
    private String updatedBy;

    
    @Column(name = "CURRENT_RATE_TYPE")
	private String currentRateType;
    
    @Column(name = "CURRENT_RATE_PER")
	private String currentRatePer;
    
    
    @Column(name = "EXPECTED_RATE_TYPE")
	private String expectedRateType;
    
    
    @Column(name = "EXPECTED_RATE_PER")
	private String expectedRatePer;
    
    @Transient
    @JsonProperty
    private String dob;
    
    @Transient
    @JsonProperty
    private Integer candidateWorkExperienceYears;
    
    @Transient
    @JsonProperty
    private Integer candidateWorkExperienceMonths;
    
    @Transient
    @JsonProperty
    private String submissionJsonStringValue;
    
    @Transient
    @JsonProperty
    private String submissionJsonString;
    
    @Transient
    @JsonProperty
    private String externalInterviewerUUID;;



    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RequirementSubmission getRequirementSubmission() {
		return requirementSubmission;
	}

	public void setRequirementSubmission(RequirementSubmission requirementSubmission) {
		this.requirementSubmission = requirementSubmission;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAlternateNumber() {
		return alternateNumber;
	}

	public void setAlternateNumber(String alternateNumber) {
		this.alternateNumber = alternateNumber;
	}


	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
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
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getCurrentORLastCompany() {
		return currentORLastCompany;
	}

	public void setCurrentORLastCompany(String currentORLastCompany) {
		this.currentORLastCompany = currentORLastCompany;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getWorkExperience() {
		return workExperience;
	}

	public void setWorkExperience(String workExperience) {
		this.workExperience = workExperience;
	}

	public String getCurrentCTC() {
		return currentCTC;
	}

	public void setCurrentCTC(String currentCTC) {
		this.currentCTC = currentCTC;
	}

	public String getExpectedCTC() {
		return expectedCTC;
	}

	public void setExpectedCTC(String expectedCTC) {
		this.expectedCTC = expectedCTC;
	}

	public String getNoticePeriod() {
		return noticePeriod;
	}

	public void setNoticePeriod(String noticePeriod) {
		this.noticePeriod = noticePeriod;
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

	public String getCurrentRateType() {
		return currentRateType;
	}

	public void setCurrentRateType(String currentRateType) {
		this.currentRateType = currentRateType;
	}

	public String getCurrentRatePer() {
		return currentRatePer;
	}

	public void setCurrentRatePer(String currentRatePer) {
		this.currentRatePer = currentRatePer;
	}

	public String getExpectedRateType() {
		return expectedRateType;
	}

	public void setExpectedRateType(String expectedRateType) {
		this.expectedRateType = expectedRateType;
	}

	public String getExpectedRatePer() {
		return expectedRatePer;
	}

	public void setExpectedRatePer(String expectedRatePer) {
		this.expectedRatePer = expectedRatePer;
	}

	public String getDob() {
		if(dateOfBirth != null){
			SimpleDateFormat dateFormat = requirementSubmission.getOrgLocations().getLocFullFormDateFormatter();
			dob = dateFormat.format(dateOfBirth);	
		}
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public Integer getCandidateWorkExperienceYears() {
		if(workExperience != null && !workExperience.isEmpty()){
			if(workExperience.contains(".")){
				String[] exp = workExperience.split("\\.");
				candidateWorkExperienceYears = Integer.parseInt(exp[0]);
			}else{
				candidateWorkExperienceYears = Integer.parseInt(workExperience);
			}
			
		}
		return candidateWorkExperienceYears;
	}

	public void setCandidateWorkExperienceYears(Integer candidateWorkExperienceYears) {
		this.candidateWorkExperienceYears = candidateWorkExperienceYears;
	}

	public Integer getCandidateWorkExperienceMonths() {
		if(workExperience != null && !workExperience.isEmpty()){
			if(workExperience.contains(".")){
				String[] exp = workExperience.split("\\.");
				if(exp.length > 1){
					candidateWorkExperienceMonths = Integer.parseInt(exp[1]);
				}
			}
			
		}
		return candidateWorkExperienceMonths;
	}

	public void setCandidateWorkExperienceMonths(Integer candidateWorkExperienceMonths) {
		this.candidateWorkExperienceMonths = candidateWorkExperienceMonths;
	}

	public String getSubmissionJsonStringValue() {
		return submissionJsonStringValue;
	}

	public void setSubmissionJsonStringValue(String submissionJsonStringValue) {
		this.submissionJsonStringValue = submissionJsonStringValue;
	}

	public String getSubmissionJsonString() {
		return submissionJsonString;
	}

	public void setSubmissionJsonString(String submissionJsonString) {
		this.submissionJsonString = submissionJsonString;
	}

	public String getExternalInterviewerUUID() {
		return externalInterviewerUUID;
	}

	public void setExternalInterviewerUUID(String externalInterviewerUUID) {
		this.externalInterviewerUUID = externalInterviewerUUID;
	}
}
