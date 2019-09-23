package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("serial")
public class CandidateOnBoardingDTO implements Serializable {
	
	private String savedonboardId;
	
	private String id;
	
	private String OnBoardRoleId;
	
	private String onboardCandiate;
	
	private String onBoardOffer;
	
	private String firstName;
	
	private String lastName;
	
	private String address1;
	
	private String address2;
	
	private String location;
	
	private String country;
	
	private String state;
	
	private String city;
	
	private String zipCode;
	
	private String mobileNumber;
	
	private String personalEmail;
	
	private String companyEmail;
	
	private String gender;
	
	private String dateOfBirth;
	
	private String employmentEligibility;
	
	private String uscisAlien;
	
	private String authorizedWorkUntil;
	
	private String onboardCandidateRole;
	
	private String startDate;
	
	private String salaryRate;
	
	private String paymentType;
	
	private String paymentMode;
	
	private String reportingManager;
	
	private String team;
	
	private String shift;
	
	private String shiftName;
	
	private String employmentType;
	
	private String employmentOtherType;
	
	private String probationNotice;
	
	private String terminationNotice;

	private String dueDate;
	
	private String specialConditions;
	
	private String onboardingTemplate;
	
	private String jobTitle;
	
	private String managerName;
	
	private String teamName;
	
	private String roleName;
	
	private String onBoardManagerId;
	
	private String onBoardTeamId;
	
	private String joiningDateStr;
	
	private String endDateStr;
	
	private String endDate;
	
	private String dueDateStr;
	
	private String candidateName;
	
	private String onboardTemplateId;
	
	private long onBoardCandidateId;
	
	private long onBoardOfferId;
	
	private String userEmail;
	
	private long documentsCount;
	
	private long documentsAcceptedCount;
	
	private String candidateOnBoardStatus;
	
	private String onboardCandidateUUID;
	
	private String onboardSubmitted;
	
	private String rejectedReason;
	
	private int start;
	
	private int length;
	
	private String searchValue;
	
	private Map<String, String> search;
	
	private List<Map<String, String>> order;
	
	private String sortColumn;
	
	private String sortOrder;
	
	private String vendors;
	
	private String vendorContact;
	
	public String getCandidateOnBoardStatus() {
		return candidateOnBoardStatus;
	}

	public void setCandidateOnBoardStatus(String candidateOnBoardStatus) {
		this.candidateOnBoardStatus = candidateOnBoardStatus;
	}

	public String getOnboardCandidateUUID() {
		return onboardCandidateUUID;
	}

	public void setOnboardCandidateUUID(String onboardCandidateUUID) {
		this.onboardCandidateUUID = onboardCandidateUUID;
	}

	public String getOnboardSubmitted() {
		return onboardSubmitted;
	}

	public void setOnboardSubmitted(String onboardSubmitted) {
		this.onboardSubmitted = onboardSubmitted;
	}

	public long getDocumentsCount() {
		return documentsCount;
	}

	public void setDocumentsCount(long documentsCount) {
		this.documentsCount = documentsCount;
	}

	public long getDocumentsAcceptedCount() {
		return documentsAcceptedCount;
	}

	public void setDocumentsAcceptedCount(long documentsAcceptedCount) {
		this.documentsAcceptedCount = documentsAcceptedCount;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public long getOnBoardCandidateId() {
		return onBoardCandidateId;
	}

	public void setOnBoardCandidateId(long onBoardCandidateId) {
		this.onBoardCandidateId = onBoardCandidateId;
	}

	public long getOnBoardOfferId() {
		return onBoardOfferId;
	}

	public void setOnBoardOfferId(long onBoardOfferId) {
		this.onBoardOfferId = onBoardOfferId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOnboardTemplateId() {
		return onboardTemplateId;
	}

	public void setOnboardTemplateId(String onboardTemplateId) {
		this.onboardTemplateId = onboardTemplateId;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getDueDateStr() {
		return dueDateStr;
	}

	public void setDueDateStr(String dueDateStr) {
		this.dueDateStr = dueDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public String getJoiningDateStr() {
		return joiningDateStr;
	}

	public void setJoiningDateStr(String joiningDateStr) {
		this.joiningDateStr = joiningDateStr;
	}

	public String getOnBoardTeamId() {
		return onBoardTeamId;
	}

	public void setOnBoardTeamId(String onBoardTeamId) {
		this.onBoardTeamId = onBoardTeamId;
	}

	public String getOnBoardManagerId() {
		return onBoardManagerId;
	}

	public void setOnBoardManagerId(String onBoardManagerId) {
		this.onBoardManagerId = onBoardManagerId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getOnBoardRoleId() {
		return OnBoardRoleId;
	}

	public void setOnBoardRoleId(String onBoardRoleId) {
		OnBoardRoleId = onBoardRoleId;
	}
	
	
	public String getOnBoardOffer() {
		return onBoardOffer;
	}

	public void setOnBoardOffer(String onBoardOffer) {
		this.onBoardOffer = onBoardOffer;
	}

	public String getSavedonboardId() {
		return savedonboardId;
	}

	public void setSavedonboardId(String savedonboardId) {
		this.savedonboardId = savedonboardId;
	}

	public String getOnboardCandiate() {
		return onboardCandiate;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getAddress1() {
		return address1;
	}

	public String getAddress2() {
		return address2;
	}

	public String getLocation() {
		return location;
	}

	public String getCountry() {
		return country;
	}

	public String getState() {
		return state;
	}

	public String getCity() {
		return city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public String getPersonalEmail() {
		return personalEmail;
	}

	public String getCompanyEmail() {
		return companyEmail;
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

	public String getOnboardCandidateRole() {
		return onboardCandidateRole;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getSalaryRate() {
		return salaryRate;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public String getReportingManager() {
		return reportingManager;
	}

	public String getTeam() {
		return team;
	}

	public String getEmploymentType() {
		return employmentType;
	}

	public String getEmploymentOtherType() {
		return employmentOtherType;
	}

	public String getProbationNotice() {
		return probationNotice;
	}

	public String getTerminationNotice() {
		return terminationNotice;
	}

	public String getDueDate() {
		return dueDate;
	}

	public String getSpecialConditions() {
		return specialConditions;
	}

	 
	public void setOnboardCandiate(String onboardCandiate) {
		this.onboardCandiate = onboardCandiate;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public void setPersonalEmail(String personalEmail) {
		this.personalEmail = personalEmail;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	public void setOnboardCandidateRole(String onboardCandidateRole) {
		this.onboardCandidateRole = onboardCandidateRole;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setSalaryRate(String salaryRate) {
		this.salaryRate = salaryRate;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public void setReportingManager(String reportingManager) {
		this.reportingManager = reportingManager;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}

	public void setEmploymentOtherType(String employmentOtherType) {
		this.employmentOtherType = employmentOtherType;
	}

	public void setProbationNotice(String probationNotice) {
		this.probationNotice = probationNotice;
	}

	public void setTerminationNotice(String terminationNotice) {
		this.terminationNotice = terminationNotice;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public void setSpecialConditions(String specialConditions) {
		this.specialConditions = specialConditions;
	}

	public String getOnboardingTemplate() {
		return onboardingTemplate;
	}

	public void setOnboardingTemplate(String onboardingTemplate) {
		this.onboardingTemplate = onboardingTemplate;
	}

	public String getRejectedReason()
	{
		return rejectedReason;
	}

	public void setRejectedReason(String rejectedReason)
	{
		this.rejectedReason = rejectedReason;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public Map<String, String> getSearch() {
		return search;
	}

	public void setSearch(Map<String, String> search) {
		this.search = search;
	}

	public List<Map<String, String>> getOrder() {
		return order;
	}

	public void setOrder(List<Map<String, String>> order) {
		this.order = order;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getVendors() {
		return vendors;
	}

	public void setVendors(String vendors) {
		this.vendors = vendors;
	}

	public String getVendorContact() {
		return vendorContact;
	}

	public void setVendorContact(String vendorContact) {
		this.vendorContact = vendorContact;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public String getShiftName() {
		return shiftName;
	}

	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}
}
