package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("serial")
public class CandidateOffBoardingDTO implements Serializable {
	
	private String savedoffboardId;
	
	private String offboardUser;
	
	private String firstName;
	
	private String lastName;
	
	private String mobileNumber;
	
	private String email;
	
	private String dateOfJoin;
	
	private String role;
	
	private String employmentType;
	
	private String endDate;
	
	private String reason;
	
	private String explanation;
	
	private String noticePeriod;
	
	private String workInNoticePeriod;
	
	private String effectiveDate;
	
	private String offboardingTemplate;
	
	private String dateOfJoinStr;
	
	private String endDateStr;
	
	private String effectiveDateStr;
	
	private String candidateName;
	
	private String haveToWorkInNoticePeriod;
	
	private String offboardTemplateId;
	
	private String id;
	
	private long offboardUserId;
	
	private String offboardUUID;
	
	private String offBoardStatus;
	
	private String offboardSubmitted;
	
	private Integer rating;

	private int start;
	
	private int length;
	
	private String searchValue;
	
	private Map<String, String> search;
	
	private List<Map<String, String>> order;
	
	private String sortColumn;
	
	private String sortOrder;
	
	public String getOffboardSubmitted() {
		return offboardSubmitted;
	}

	public void setOffboardSubmitted(String offboardSubmitted) {
		this.offboardSubmitted = offboardSubmitted;
	}

	public String getOffBoardStatus() {
		return offBoardStatus;
	}

	public void setOffBoardStatus(String offBoardStatus) {
		this.offBoardStatus = offBoardStatus;
	}

	public String getOffboardUUID() {
		return offboardUUID;
	}

	public void setOffboardUUID(String offboardUUID) {
		this.offboardUUID = offboardUUID;
	}

	public long getOffboardUserId() {
		return offboardUserId;
	}

	public void setOffboardUserId(long offboardUserId) {
		this.offboardUserId = offboardUserId;
	}

	public String getOffboardTemplateId() {
		return offboardTemplateId;
	}

	public void setOffboardTemplateId(String offboardTemplateId) {
		this.offboardTemplateId = offboardTemplateId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHaveToWorkInNoticePeriod() {
		return haveToWorkInNoticePeriod;
	}

	public void setHaveToWorkInNoticePeriod(String haveToWorkInNoticePeriod) {
		this.haveToWorkInNoticePeriod = haveToWorkInNoticePeriod;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getEffectiveDateStr() {
		return effectiveDateStr;
	}

	public void setEffectiveDateStr(String effectiveDateStr) {
		this.effectiveDateStr = effectiveDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public String getDateOfJoinStr() {
		return dateOfJoinStr;
	}

	public void setDateOfJoinStr(String dateOfJoinStr) {
		this.dateOfJoinStr = dateOfJoinStr;
	}

	public String getSavedoffboardId() {
		return savedoffboardId;
	}

	public String getOffboardUser() {
		return offboardUser;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public String getDateOfJoin() {
		return dateOfJoin;
	}

	public String getRole() {
		return role;
	}

	public String getEmploymentType() {
		return employmentType;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getReason() {
		return reason;
	}

	public String getExplanation() {
		return explanation;
	}

	public String getNoticePeriod() {
		return noticePeriod;
	}

	public String getWorkInNoticePeriod() {
		return workInNoticePeriod;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public String getOffboardingTemplate() {
		return offboardingTemplate;
	}

	public void setSavedoffboardId(String savedoffboardId) {
		this.savedoffboardId = savedoffboardId;
	}

	public void setOffboardUser(String offboardUser) {
		this.offboardUser = offboardUser;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setDateOfJoin(String dateOfJoin) {
		this.dateOfJoin = dateOfJoin;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public void setNoticePeriod(String noticePeriod) {
		this.noticePeriod = noticePeriod;
	}

	public void setWorkInNoticePeriod(String workInNoticePeriod) {
		this.workInNoticePeriod = workInNoticePeriod;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public void setOffboardingTemplate(String offboardingTemplate) {
		this.offboardingTemplate = offboardingTemplate;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
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
	
	
}
