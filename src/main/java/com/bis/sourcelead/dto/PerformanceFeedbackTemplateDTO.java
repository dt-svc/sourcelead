package com.bis.sourcelead.dto;

import java.io.Serializable;

public class PerformanceFeedbackTemplateDTO implements Serializable{
	
   private static final long serialVersionUID = 1L;
	 
	private String templateName;
	
	private String description;
	
	private String jsonString;

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}
	
	

}
