package com.bis.sourcelead.dto.performance;

import java.io.Serializable;

import com.bis.sourcelead.annotations.Required;

public class PerformanceTagCompetenciesDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long tagCompetenceId;
	
	private String competenceRole;
	
	@Required(desc="performanceCompetence")
	private String performanceCompetence;
	
	@Required(desc="jsonCompetenceString")
	private String jsonCompetenceString ;
	
	

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
	
	

}
