package com.bis.sourcelead.dto.performance;

import java.io.Serializable;

import com.bis.sourcelead.annotations.Required;

public class PerformanceTagCompetenciesDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long tagCompetenceId;
	
	private String competenceRole;
	
	private String createdDate;
	
	@Required(desc="performanceCompetence")
	private String performanceCompetence;
	
	@Required(desc="jsonCompetenceString")
	private String jsonCompetenceString ;
	
	private String competenceRoleNames;
	
	private Integer competenceCount;
	
	private String uuid;
	
	private Integer totalWeightage;
	
	private String competenceIds;
	

	public Long getTagCompetenceId() {
		return tagCompetenceId;
	}

	public void setTagCompetenceId(Long tagCompetenceId) {
		this.tagCompetenceId = tagCompetenceId;
	}

	public String getCompetenceRole() {
		return competenceRole;
	}

	public void setCompetenceRole(String competenceRole) {
		this.competenceRole = competenceRole;
	}

	public String getPerformanceCompetence() {
		return performanceCompetence;
	}

	public void setPerformanceCompetence(String performanceCompetence) {
		this.performanceCompetence = performanceCompetence;
	}

	public String getJsonCompetenceString() {
		return jsonCompetenceString;
	}

	public void setJsonCompetenceString(String jsonCompetenceString) {
		this.jsonCompetenceString = jsonCompetenceString;
	}

	public String getCompetenceRoleNames() {
		return competenceRoleNames;
	}

	public void setCompetenceRoleNames(String competenceRoleNames) {
		this.competenceRoleNames = competenceRoleNames;
	}

	public Integer getCompetenceCount() {
		return competenceCount;
	}

	public void setCompetenceCount(Integer competenceCount) {
		this.competenceCount = competenceCount;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getTotalWeightage() {
		return totalWeightage;
	}

	public void setTotalWeightage(Integer totalWeightage) {
		this.totalWeightage = totalWeightage;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getCompetenceIds() {
		return competenceIds;
	}

	public void setCompetenceIds(String competenceIds) {
		this.competenceIds = competenceIds;
	}

}
