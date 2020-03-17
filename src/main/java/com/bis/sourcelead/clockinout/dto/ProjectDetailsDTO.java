package com.bis.sourcelead.clockinout.dto;

import java.io.Serializable;

public class ProjectDetailsDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String projectName;
	
	private String projectStartDate;
	
	private String projectEndDate;
	
	private String projectTimeSheetFrequency;
	
	private String client;
	
	private String managerLevel1;
	
	private String managerLevel2;
	
	private String managerLevel1ImgUrl;
	
	private String managerLevel2ImgUrl;
	
	private String displayManager;
	
	private String displayManagerImgUrl;
	
	private String projectStatus;


	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectStartDate() {
		return projectStartDate;
	}

	public void setProjectStartDate(String projectStartDate) {
		this.projectStartDate = projectStartDate;
	}

	public String getProjectEndDate() {
		return projectEndDate;
	}

	public void setProjectEndDate(String projectEndDate) {
		this.projectEndDate = projectEndDate;
	}

	public String getProjectTimeSheetFrequency() {
		return projectTimeSheetFrequency;
	}

	public void setProjectTimeSheetFrequency(String projectTimeSheetFrequency) {
		this.projectTimeSheetFrequency = projectTimeSheetFrequency;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getManagerLevel1() {
		return managerLevel1;
	}

	public void setManagerLevel1(String managerLevel1) {
		this.managerLevel1 = managerLevel1;
	}

	public String getManagerLevel2() {
		return managerLevel2;
	}

	public void setManagerLevel2(String managerLevel2) {
		this.managerLevel2 = managerLevel2;
	}

	public String getManagerLevel1ImgUrl() {
		return managerLevel1ImgUrl;
	}

	public void setManagerLevel1ImgUrl(String managerLevel1ImgUrl) {
		this.managerLevel1ImgUrl = managerLevel1ImgUrl;
	}

	public String getManagerLevel2ImgUrl() {
		return managerLevel2ImgUrl;
	}

	public void setManagerLevel2ImgUrl(String managerLevel2ImgUrl) {
		this.managerLevel2ImgUrl = managerLevel2ImgUrl;
	}

	public String getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}

	public String getDisplayManager() {
		return displayManager;
	}

	public void setDisplayManager(String displayManager) {
		this.displayManager = displayManager;
	}

	public String getDisplayManagerImgUrl() {
		return displayManagerImgUrl;
	}

	public void setDisplayManagerImgUrl(String displayManagerImgUrl) {
		this.displayManagerImgUrl = displayManagerImgUrl;
	}

}
