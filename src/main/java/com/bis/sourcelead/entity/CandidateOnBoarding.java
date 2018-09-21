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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "CANDIDATE_ON_BOARDING")
public class CandidateOnBoarding implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	
	@ManyToOne
	@JoinColumn(name = "CANDIDATE_ID")
	private Candidate candidateId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OFFER_ID")
	private RequirementOffers offerId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BOARDING_USER_ID")
	private User onboardingUser;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "ADDRESS_1")
	@Type(type="text")
	private String address1;
	
	@Column(name = "ADDRESS_2")
	@Type(type="text")
	private String address2;
	
	@Column(name = "LOCATION")
	@Type(type="text")
	private String location;
	
	@Column(name = "COUNTRY", columnDefinition = "LONGTEXT default NULL")
	private String country;
	
	@Column(name = "STATE", columnDefinition = "LONGTEXT default NULL")
	private String state;
	
	@Column(name = "CITY", columnDefinition = "LONGTEXT default NULL")
	private String city;
	
	@Column(name = "ZIPCODE")
	private String zipCode;
	
	@Column(name = "MOBILE_NUMBER")
	private String mobileNumber;
	
	@Column(name = "PERSONAL_EMAIL")
	private String personalEmail;
	
	@Column(name = "COMPANY_EMAIL")
	private String companyEmail;
	
	@Column(name = "GENDER")
	private String gender;
	
	@Column(name = "DATE_OF_BIRTH")
	private String dateOfBirth;
	
	@Column(name = "EMPLOYMENT_ELIGIBILITY")
	private String employmentEligibility;

	@Column(name = "USCIS_ALIEN")
	private String uscisAlien;
	
	@Column(name = "AUTHORIZED_WORK_UNTIL")
	private String authorizedWorkUntil;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEPARTMENT_ID")
	private Department departmentId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SHIFT_ID")
	private Shift shift;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_ID")
	private Roles roleId;
	
	@Column(name = "START_DATE")
	private Date startDate;
	
	@Column(name = "END_DATE")
	private Date endDate;
	
	@Column(name = "SALARY_RATE")
	private String salaryRate;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MANAGER_USER_ID")
	private User managerUserId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TEAM_ID")
	private Team teamId;
	
	@Column(name = "EMPLOYMENT_TYPE")
	private String employmentType;
	
	@Column(name = "EMPLOYMENT_OTHER_TYPE")
	private String employmentOtherType;
	
	@Column(name = "PROBATION_NOTICE")
	private String probationNotice;
	
	@Column(name = "TERMINATION_NOTICE")
	private String terminationNotice;
	
	@Column(name = "DUE_DATE")
	private Date dueDate;
	
	@Column(name = "SPECIAL_CONDITIONS")
	@Type(type="text")
	private String specialConditions;
	
	@Column(name = "ONBOARD_TEMPLATE_ID")
	@Type(type="text")
	private String onboardTemplateId;
	
	
	@ManyToOne
	@JoinColumn(name = "ORG_LOCATION_ID")
	private OrgLocations orgLocationId;
	
	@Column(name = "CANDIDATE_ONBOARD_UUID")
	private String onboardCandidateUUID;
	
	@Column(name = "CANDIDATE_ONBOARD_STATUS")
	private String candidateOnBoardStatus;
	
	@Column(name = "REJECTED_REASON")
	@Type(type="text")
	private String rejectedReason;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "ONBOARD_SUBMITTED")
	private String onboardSubmitted;
		
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "PAYMENT_TYPE")
	private String paymentType;
	
	
	@Column(name = "PAYMENT_MODE")
	private String paymentMode;
	
	@Transient
	private String joiningDateStr;
	
	@Transient
	private String candidateName;
	
	@Transient
	private Long onBoardCandidateId;
	
	@Transient
	private Long onBoardOfferId;
	
	@Transient
	private Long onBoardDepartmentId;
	
	@Transient
	private Long onBoardRoleId;
	
	@Transient
	private Long onBoardManagerId;
	
	@Transient
	@JsonProperty
	private Long onBoardTeamId;
	
	@Transient
	private String endDateStr;
	
	@Transient
	private String dueDateStr;
	
	@Transient
	private String jobTitle;
	
	@Transient
	private String roleName;
	
	@Transient
	private String managerName;
	
	@Transient
	private String teamName;
	
	@Transient
	private String templateName;
	
	@Transient
	private Integer documentsCount;
	
	@Transient
	private Integer documentsAcceptedCount;
	
	@Transient
	private String userEmail;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getOnboardingUser() {
		return onboardingUser;
	}

	public void setOnboardingUser(User onboardingUser) {
		this.onboardingUser = onboardingUser;
	}

	public Candidate getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Candidate candidateId) {
		this.candidateId = candidateId;
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

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPersonalEmail() {
		return personalEmail;
	}

	public void setPersonalEmail(String personalEmail) {
		this.personalEmail = personalEmail;
	}

	public String getCompanyEmail() {
		return companyEmail;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	public String getGender() {
		return gender;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public String getEmploymentEligibility() {
		return employmentEligibility;
	}

	public String getUscisAlien() {
		return uscisAlien;
	}

	public String getAuthorizedWorkUntil() {
		return authorizedWorkUntil;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setEmploymentEligibility(String employmentEligibility) {
		this.employmentEligibility = employmentEligibility;
	}

	public void setUscisAlien(String uscisAlien) {
		this.uscisAlien = uscisAlien;
	}

	public void setAuthorizedWorkUntil(String authorizedWorkUntil) {
		this.authorizedWorkUntil = authorizedWorkUntil;
	}

	public Department getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Department departmentId) {
		this.departmentId = departmentId;
	}

	public Shift getShift() {
		return shift;
	}

	public void setShift(Shift shift) {
		this.shift = shift;
	}

	public Roles getRoleId() {
		return roleId;
	}

	public void setRoleId(Roles roleId) {
		this.roleId = roleId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getSalaryRate() {
		return salaryRate;
	}

	public void setSalaryRate(String salaryRate) {
		this.salaryRate = salaryRate;
	}

	public User getManagerUserId() {
		return managerUserId;
	}

	public void setManagerUserId(User managerUserId) {
		this.managerUserId = managerUserId;
	}

	public Team getTeamId() {
		return teamId;
	}

	public void setTeamId(Team teamId) {
		this.teamId = teamId;
	}

	public String getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}

	public String getProbationNotice() {
		return probationNotice;
	}

	public void setProbationNotice(String probationNotice) {
		this.probationNotice = probationNotice;
	}

	public String getTerminationNotice() {
		return terminationNotice;
	}

	public void setTerminationNotice(String terminationNotice) {
		this.terminationNotice = terminationNotice;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getSpecialConditions() {
		return specialConditions;
	}

	public void setSpecialConditions(String specialConditions) {
		this.specialConditions = specialConditions;
	}

	public String getOnboardTemplateId() {
		return onboardTemplateId;
	}

	public void setOnboardTemplateId(String onboardTemplateId) {
		this.onboardTemplateId = onboardTemplateId;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getOnboardCandidateUUID() {
		return onboardCandidateUUID;
	}

	public void setOnboardCandidateUUID(String onboardCandidateUUID) {
		this.onboardCandidateUUID = onboardCandidateUUID;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getOnboardSubmitted() {
		return onboardSubmitted;
	}

	public void setOnboardSubmitted(String onboardSubmitted) {
		this.onboardSubmitted = onboardSubmitted;
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

	public RequirementOffers getOfferId() {
		return offerId;
	}

	public void setOfferId(RequirementOffers offerId) {
		this.offerId = offerId;
	}

	public String getCandidateOnBoardStatus() {
		return candidateOnBoardStatus;
	}

	public void setCandidateOnBoardStatus(String candidateOnBoardStatus) {
		this.candidateOnBoardStatus = candidateOnBoardStatus;
	}

	public String getJoiningDateStr() {
		return joiningDateStr;
	}

	public void setJoiningDateStr(String joiningDateStr) {
		this.joiningDateStr = joiningDateStr;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public Long getOnBoardCandidateId() {
		return onBoardCandidateId;
	}

	public void setOnBoardCandidateId(Long onBoardCandidateId) {
		this.onBoardCandidateId = onBoardCandidateId;
	}

	public Long getOnBoardOfferId() {
		return onBoardOfferId;
	}

	public void setOnBoardOfferId(Long onBoardOfferId) {
		this.onBoardOfferId = onBoardOfferId;
	}

	public Long getOnBoardDepartmentId() {
		return onBoardDepartmentId;
	}

	public void setOnBoardDepartmentId(Long onBoardDepartmentId) {
		this.onBoardDepartmentId = onBoardDepartmentId;
	}

	public Long getOnBoardRoleId() {
		return onBoardRoleId;
	}

	public void setOnBoardRoleId(Long onBoardRoleId) {
		this.onBoardRoleId = onBoardRoleId;
	}

	public Long getOnBoardManagerId() {
		return onBoardManagerId;
	}

	public void setOnBoardManagerId(Long onBoardManagerId) {
		this.onBoardManagerId = onBoardManagerId;
	}

	public Long getOnBoardTeamId() {
		if(teamId != null){
			onBoardTeamId = teamId.getId();
		}
		return onBoardTeamId;
	}

	public void setOnBoardTeamId(Long onBoardTeamId) {
		this.onBoardTeamId = onBoardTeamId;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public String getDueDateStr() {
		return dueDateStr;
	}

	public void setDueDateStr(String dueDateStr) {
		this.dueDateStr = dueDateStr;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public Integer getDocumentsCount() {
		return documentsCount;
	}

	public void setDocumentsCount(Integer documentsCount) {
		this.documentsCount = documentsCount;
	}

	public Integer getDocumentsAcceptedCount() {
		return documentsAcceptedCount;
	}

	public void setDocumentsAcceptedCount(Integer documentsAcceptedCount) {
		this.documentsAcceptedCount = documentsAcceptedCount;
	}

	public String getRejectedReason() {
		return rejectedReason;
	}

	public void setRejectedReason(String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getEmploymentOtherType() {
		return employmentOtherType;
	}

	public void setEmploymentOtherType(String employmentOtherType) {
		this.employmentOtherType = employmentOtherType;
	}

	public String getPaymentType()
	{
		return paymentType;
	}

	public void setPaymentType(String paymentType)
	{
		this.paymentType = paymentType;
	}

	public String getPaymentMode()
	{
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode)
	{
		this.paymentMode = paymentMode;
	}
	
	
}
