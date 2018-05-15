package com.bis.sourcelead.dto;

import com.bis.sourcelead.annotations.Required;

public class CandidateEmployerDTO {

	private Long id;

	@Required(desc = "Employer Name")
	private String name;

	private String isCurrentPosition;

	private String designation;

	private String address;

	private String startMonth;

	private String endMonth;

	private String startDate;

	private String startYear;

	private String endYear;

	private String endDate;

	private String candidateEmployeeId;

	private long candidateId;

	private String candidateExperianceId;

	private String loginUserId;

	private String orgLocationId;

	private String candidateemployerId;

	public String getCandidateemployerId() {
		return candidateemployerId;
	}

	public void setCandidateemployerId(String candidateemployerId) {
		this.candidateemployerId = candidateemployerId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsCurrentPosition() {
		return isCurrentPosition;
	}

	public void setIsCurrentPosition(String isCurrentPosition) {
		this.isCurrentPosition = isCurrentPosition;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}

	public String getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartYear() {
		return startYear;
	}

	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}

	public String getEndYear() {
		return endYear;
	}

	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getCandidateEmployeeId() {
		return candidateEmployeeId;
	}

	public void setCandidateEmployeeId(String candidateEmployeeId) {
		this.candidateEmployeeId = candidateEmployeeId;
	}

	public long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(long candidateId) {
		this.candidateId = candidateId;
	}

	public String getCandidateExperianceId() {
		return candidateExperianceId;
	}

	public void setCandidateExperianceId(String candidateExperianceId) {
		this.candidateExperianceId = candidateExperianceId;
	}

	public String getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}

	public String getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(String orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

}
