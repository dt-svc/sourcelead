package com.bis.sourcelead.dto;

import java.util.List;

public class RestrictDayDTO extends SearchDTO {

	private String restrictDayId;
	private String restrictName;
	private String startDate;
	private String endDate;
	private String reason;
	private String status;
	private String orgLocationId;
	private String userId;
	private String isApllicable;
	private List<String> rolesList;
	
	
	public String getRestrictDayId() {
		return restrictDayId;
	}
	public void setRestrictDayId(String restrictDayId) {
		this.restrictDayId = restrictDayId;
	}
	public String getRestrictName() {
		return restrictName;
	}
	public void setRestrictName(String restrictName) {
		this.restrictName = restrictName;
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
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOrgLocationId() {
		return orgLocationId;
	}
	public void setOrgLocationId(String orgLocationId) {
		this.orgLocationId = orgLocationId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getIsApllicable() {
		return isApllicable;
	}
	public void setIsApllicable(String isApllicable) {
		this.isApllicable = isApllicable;
	}
	public List<String> getRolesList() {
		return rolesList;
	}
	public void setRolesList(List<String> rolesList) {
		this.rolesList = rolesList;
	}
	
}
