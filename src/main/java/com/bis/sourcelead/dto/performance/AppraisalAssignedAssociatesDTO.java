package com.bis.sourcelead.dto.performance;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class AppraisalAssignedAssociatesDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long orgLocationId;
	
	private Long orgId;
	
	private Long userId;
	
	private String appraisalTemplateUUId;
	
	private String associateUserIds;
	
	private String associateId;
	
	private String userImgUrl;
	
	private String associateName;
	
	private String associateEmail;
	
	private String associateRole;
	
	private String reportingManager;
	
	private String uuid;
	
	private int start;
	
	private int length;
	
	private String searchValue;
	
	private String sortColumn;
	
	private String sortOrder;
	
	private List<Map<String, String>> order;

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAppraisalTemplateUUId() {
		return appraisalTemplateUUId;
	}

	public void setAppraisalTemplateUUId(String appraisalTemplateUUId) {
		this.appraisalTemplateUUId = appraisalTemplateUUId;
	}

	public String getAssociateUserIds() {
		return associateUserIds;
	}

	public void setAssociateUserIds(String associateUserIds) {
		this.associateUserIds = associateUserIds;
	}

	public String getAssociateId() {
		return associateId;
	}

	public void setAssociateId(String associateId) {
		this.associateId = associateId;
	}

	public String getUserImgUrl() {
		return userImgUrl;
	}

	public void setUserImgUrl(String userImgUrl) {
		this.userImgUrl = userImgUrl;
	}

	public String getAssociateName() {
		return associateName;
	}

	public void setAssociateName(String associateName) {
		this.associateName = associateName;
	}

	public String getAssociateEmail() {
		return associateEmail;
	}

	public void setAssociateEmail(String associateEmail) {
		this.associateEmail = associateEmail;
	}

	public String getAssociateRole() {
		return associateRole;
	}

	public void setAssociateRole(String associateRole) {
		this.associateRole = associateRole;
	}

	public String getReportingManager() {
		return reportingManager;
	}

	public void setReportingManager(String reportingManager) {
		this.reportingManager = reportingManager;
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

	public List<Map<String, String>> getOrder() {
		return order;
	}

	public void setOrder(List<Map<String, String>> order) {
		this.order = order;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
}
