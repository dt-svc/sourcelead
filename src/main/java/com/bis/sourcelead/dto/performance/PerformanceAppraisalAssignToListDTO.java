package com.bis.sourcelead.dto.performance;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class PerformanceAppraisalAssignToListDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private BigInteger userId;
	
	private BigInteger userRoleId;
	
	private String associateId;
	
	private String associateName;
	
	private String associateEmail;
	
	private String associateRole;
	
	private String reportingManager;
	
	private String appraisalTemplateUUId;
	
	private BigInteger peersCount;
	
	private int start;
	
	private int length;
	
	private String searchValue;
	
	private String sortColumn;
	
	private String sortOrder;
	
	private List<Map<String, String>> order;
	
	

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}
	
	public BigInteger getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(BigInteger userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getAssociateId() {
		return associateId;
	}

	public void setAssociateId(String associateId) {
		this.associateId = associateId;
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

	public String getAppraisalTemplateUUId() {
		return appraisalTemplateUUId;
	}

	public void setAppraisalTemplateUUId(String appraisalTemplateUUId) {
		this.appraisalTemplateUUId = appraisalTemplateUUId;
	}

	public BigInteger getPeersCount() {
		return peersCount;
	}

	public void setPeersCount(BigInteger peersCount) {
		this.peersCount = peersCount;
	}
	
	

}
