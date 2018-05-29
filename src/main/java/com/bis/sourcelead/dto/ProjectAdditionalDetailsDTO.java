package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

import com.bis.sourcelead.annotations.Required;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectAdditionalDetailsDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Required(desc="projectLocation")
	private String projectLocation;
	
	private String industry;
	
	private String otherIndustry;
	
	private String scope;
	
	private String otherScope;
	
	private List<String> category;
	
	private String teamSize;
	
	private List<String> frontEnd;
	
	private List<String> backEnd;
	
	private List<String> projectDatabase;
	
	private List<String> otherTechnologies;
	
	private List<String> testManagement;
	
	private String executionModel;
	
	private List<String> agileMethodologies;
	
	private String saveType;
	
	private String mailToTeamMembers;
	  
	private String mailToManagers;

	public String getProjectLocation() {
		return projectLocation;
	}

	public void setProjectLocation(String projectLocation) {
		this.projectLocation = projectLocation;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getOtherIndustry() {
		return otherIndustry;
	}

	public void setOtherIndustry(String otherIndustry) {
		this.otherIndustry = otherIndustry;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getOtherScope() {
		return otherScope;
	}

	public void setOtherScope(String otherScope) {
		this.otherScope = otherScope;
	}

	public List<String> getCategory() {
		return category;
	}

	public void setCategory(List<String> category) {
		this.category = category;
	}

	public String getTeamSize() {
		return teamSize;
	}

	public void setTeamSize(String teamSize) {
		this.teamSize = teamSize;
	}

	public List<String> getFrontEnd() {
		return frontEnd;
	}

	public void setFrontEnd(List<String> frontEnd) {
		this.frontEnd = frontEnd;
	}

	public List<String> getBackEnd() {
		return backEnd;
	}

	public void setBackEnd(List<String> backEnd) {
		this.backEnd = backEnd;
	}

	public List<String> getProjectDatabase() {
		return projectDatabase;
	}

	public void setProjectDatabase(List<String> projectDatabase) {
		this.projectDatabase = projectDatabase;
	}

	public List<String> getOtherTechnologies() {
		return otherTechnologies;
	}

	public void setOtherTechnologies(List<String> otherTechnologies) {
		this.otherTechnologies = otherTechnologies;
	}

	public List<String> getTestManagement() {
		return testManagement;
	}

	public void setTestManagement(List<String> testManagement) {
		this.testManagement = testManagement;
	}

	public String getExecutionModel() {
		return executionModel;
	}

	public void setExecutionModel(String executionModel) {
		this.executionModel = executionModel;
	}

	public List<String> getAgileMethodologies() {
		return agileMethodologies;
	}

	public void setAgileMethodologies(List<String> agileMethodologies) {
		this.agileMethodologies = agileMethodologies;
	}

	public String getSaveType() {
		return saveType;
	}

	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}

	public String getMailToTeamMembers() {
		return mailToTeamMembers;
	}

	public void setMailToTeamMembers(String mailToTeamMembers) {
		this.mailToTeamMembers = mailToTeamMembers;
	}

	public String getMailToManagers() {
		return mailToManagers;
	}

	public void setMailToManagers(String mailToManagers) {
		this.mailToManagers = mailToManagers;
	}
	
	
}
