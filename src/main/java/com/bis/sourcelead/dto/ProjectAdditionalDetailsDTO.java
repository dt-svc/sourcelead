package com.bis.sourcelead.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectAdditionalDetailsDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String projectLocation;
	
	private String industry;
	
	private String otherIndustry;
	
	private String scope;
	
	private String[] category;
	
	private Double timeSize;
	
	private String[] frontEnd;
	
	private String[] backEnd;
	
	private String[] database;
	
	private String[] otherTechnologies;
	
	private String[] testManagement;
	
	private String executionModel;
	
	

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


	public String[] getCategory() {
		return category;
	}

	public void setCategory(String[] category) {
		this.category = category;
	}

	public Double getTimeSize() {
		return timeSize;
	}

	public void setTimeSize(Double timeSize) {
		this.timeSize = timeSize;
	}


	public String[] getFrontEnd() {
		return frontEnd;
	}

	public void setFrontEnd(String[] frontEnd) {
		this.frontEnd = frontEnd;
	}

	public String[] getBackEnd() {
		return backEnd;
	}

	public void setBackEnd(String[] backEnd) {
		this.backEnd = backEnd;
	}

	public String[] getDatabase() {
		return database;
	}

	public void setDatabase(String[] database) {
		this.database = database;
	}

	public String[] getOtherTechnologies() {
		return otherTechnologies;
	}

	public void setOtherTechnologies(String[] otherTechnologies) {
		this.otherTechnologies = otherTechnologies;
	}

	public String[] getTestManagement() {
		return testManagement;
	}

	public void setTestManagement(String[] testManagement) {
		this.testManagement = testManagement;
	}

	public String getExecutionModel() {
		return executionModel;
	}

	public void setExecutionModel(String executionModel) {
		this.executionModel = executionModel;
	}
	
	
	
	
	
	
}
