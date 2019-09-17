package com.bis.sourcelead.dto;


import java.io.Serializable;
import java.util.List;

import com.bis.sourcelead.entity.LookUpData;

import com.bis.sourcelead.annotations.Required;


public class CandidateEmployerDTO implements Serializable {

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

	private String staticPath;
	
	private List<FileInfo> fileInfo;
	
	private String taxId;
	
	private String jobTitle;
	
	private String employmentType;


	private long candidateId;

	private String candidateExperianceId;


	private List<LookUpData> candidateEmpLookup; 



	private String loginUserId;

	private String orgLocationId;

	private String candidateemployerId;
	
	private String candidateType;

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


	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
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


	public List<LookUpData> getCandidateEmpLookup() {
		return candidateEmpLookup;
	}

	public void setCandidateEmpLookup(List<LookUpData> candidateEmpLookup) {
		this.candidateEmpLookup = candidateEmpLookup;
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

	public long getCandidateId() {
		return candidateId;
	}

	public String getLoginUserId() {
		return loginUserId;
	}

	public String getStaticPath() {
		return staticPath;
	}

	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}

	public List<FileInfo> getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(List<FileInfo> fileInfo) {
		this.fileInfo = fileInfo;
	}

	public String getCandidateType() {
		return candidateType;
	}

	public void setCandidateType(String candidateType) {
		this.candidateType = candidateType;
	}


}
