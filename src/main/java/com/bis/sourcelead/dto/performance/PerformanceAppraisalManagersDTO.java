package com.bis.sourcelead.dto.performance;

import java.io.Serializable;

public class PerformanceAppraisalManagersDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String userCode;
	
	private String managerName;
	
	private String managerImgUrl;
	
	private String managerType;

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerImgUrl() {
		return managerImgUrl;
	}

	public void setManagerImgUrl(String managerImgUrl) {
		this.managerImgUrl = managerImgUrl;
	}

	public String getManagerType() {
		return managerType;
	}

	public void setManagerType(String managerType) {
		this.managerType = managerType;
	}
	
	
}
