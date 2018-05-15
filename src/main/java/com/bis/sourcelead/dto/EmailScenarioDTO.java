package com.bis.sourcelead.dto;

import java.io.Serializable;
 
public class EmailScenarioDTO implements Serializable{
	
	private String id;
	
	private String entityType;
	
	private String uniqueScenarioName;
	
	private String description;
	
	private String subject;
	
	private String templateType;
	
	private String signature;
	
	private String body;
	
	private String scenarioName;
	
	public String getScenarioName() {
		return scenarioName;
	}

	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getUniqueScenarioName() {
		return uniqueScenarioName;
	}

	public void setUniqueScenarioName(String uniqueScenarioName) {
		this.uniqueScenarioName = uniqueScenarioName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	
}
