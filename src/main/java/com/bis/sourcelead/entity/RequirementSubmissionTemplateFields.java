package com.bis.sourcelead.entity;

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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author Laxman
 *
 */
@Entity
@Table(name = "REQUIRMENT_SUBMISSION_TEMPLATE_FIELDS")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL,region="requirementSubmissionTemplateFields")
public class RequirementSubmissionTemplateFields {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
 	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REQUIRMENT_SUBMISSION_ID")
   	private RequirementSubmission requirementSubmission;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "PHONE_NUMBER")
	private String phonenUmber;
	
	@Column(name = "ALTERNATE_NUMBER")
	private String alternateNumber;
	
	@Column(name = "EMAIL_ID")
	private String eamilId;
	
	@Column(name = "COUNTRY")
	private String country;
	
	@Column(name = "STATE")
	private String state;
	
	@Column(name = "CITY")
	private String city;
	

	@Column(name = "DOB")
	private Date dateOfBirth;
	
	@Column(name = "CURRENT_OR_LAST_CAOMPANY")
	private String currentORLastCompany;
	
	@Column(name = "DESIGNATION")
	private String designation;
	
	@Column(name = "EDUCATION")
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

	public String getPhonenUmber() {
		return phonenUmber;
	}

	public void setPhonenUmber(String phonenUmber) {
		this.phonenUmber = phonenUmber;
	}

	public String getAlternateNumber() {
		return alternateNumber;
	}

	public void setAlternateNumber(String alternateNumber) {
		this.alternateNumber = alternateNumber;
	}

	public String getEamilId() {
		return eamilId;
	}

	public void setEamilId(String eamilId) {
		this.eamilId = eamilId;
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
}
