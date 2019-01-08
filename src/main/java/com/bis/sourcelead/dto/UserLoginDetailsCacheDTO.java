package com.bis.sourcelead.dto;

import java.io.Serializable;

public class UserLoginDetailsCacheDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long orgLocationId;
	
	private Long userId;
	
	private String dateFormat;
	
	

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
}
