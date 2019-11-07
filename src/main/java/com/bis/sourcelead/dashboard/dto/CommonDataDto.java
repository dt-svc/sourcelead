package com.bis.sourcelead.dashboard.dto;

import com.bis.sourcelead.entity.OrgLocations;

public class CommonDataDto {
	
	
	private OrgLocations orgLocation;
	
	private Long userId;
	
	private String loginUserRole;
	
	private Long orgId;
	
	public OrgLocations getOrgLocation() {
		return orgLocation;
	}
	public void setOrgLocation(OrgLocations orgLocation) {
		this.orgLocation = orgLocation;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getLoginUserRole() {
		return loginUserRole;
	}
	public void setLoginUserRole(String loginUserRole) {
		this.loginUserRole = loginUserRole;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	
	

}
