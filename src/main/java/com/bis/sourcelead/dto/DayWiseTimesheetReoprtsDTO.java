package com.bis.sourcelead.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DayWiseTimesheetReoprtsDTO extends SearchDTO {

	private static final long serialVersionUID = -3365114820091971186L;

	private String startDate;
	
	private String endDate;
	
	private Long locationsId;
	
	private Long loginUserId;
	
	private List<String> employmentType;
	
	private String typeOfProject;
	
	private List<Long> projectIds;
	
	private String exportType;
	
	private List<Long> projectMemberListId;
	
	private String templateType;

	private int pageNo;
	
	private int pageSize;
	
	private String associateStatus;
	
	private String loginUserRoleType;
	
	public String getAssociateStatus() {
		return associateStatus;
	}

	public void setAssociateStatus(String associateStatus) {
		this.associateStatus = associateStatus;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Long getLocationsId() {
		return locationsId;
	}

	public void setLocationsId(Long locationsId) {
		this.locationsId = locationsId;
	}

	public Long getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(Long loginUserId) {
		this.loginUserId = loginUserId;
	}
	
	public List<String> getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(List<String> employmentType) {
		this.employmentType = employmentType;
	}

	public List<Long> getProjectIds() {
		return projectIds;
	}

	public void setProjectIds(List<Long> projectIds) {
		this.projectIds = projectIds;
	}

	public String getTypeOfProject() {
		return typeOfProject;
	}

	public void setTypeOfProject(String typeOfProject) {
		this.typeOfProject = typeOfProject;
	}

	public String getExportType() {
		return exportType;
	}

	public void setExportType(String exportType) {
		this.exportType = exportType;
	}

	public List<Long> getProjectMemberListId() {
		return projectMemberListId;
	}

	public void setProjectMemberListId(List<Long> projectMemberListId) {
		this.projectMemberListId = projectMemberListId;
	}

	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getLoginUserRoleType() {
		return loginUserRoleType;
	}

	public void setLoginUserRoleType(String loginUserRoleType) {
		this.loginUserRoleType = loginUserRoleType;
	}
	
	
}
