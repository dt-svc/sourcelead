package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.math.BigInteger;

public class PerformanceFeedbackTemplateDTO implements Serializable{
	
   private static final long serialVersionUID = 1L;
	 
	private String templateName;
	
	private String description;
	
	private String jsonString;
	
	private BigInteger templateId;
	
	private String feedbackTemplateUUID;
	
	private String submittedJsonString;
	
	private String submittedJsonStringValues;
	
	private String categoriesJsonString;
	
	private String categoryScore;
	
	private String feedbackTemplateId;
	
	private String categoriesTotalScore;
	
	private String submissionStage;
	
	

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

	public BigInteger getTemplateId() {
		return templateId;
	}

	public void setTemplateId(BigInteger templateId) {
		this.templateId = templateId;
	}

	public String getFeedbackTemplateUUID() {
		return feedbackTemplateUUID;
	}

	public void setFeedbackTemplateUUID(String feedbackTemplateUUID) {
		this.feedbackTemplateUUID = feedbackTemplateUUID;
	}

	public String getSubmittedJsonString() {
		return submittedJsonString;
	}

	public void setSubmittedJsonString(String submittedJsonString) {
		this.submittedJsonString = submittedJsonString;
	}

	public String getSubmittedJsonStringValues() {
		return submittedJsonStringValues;
	}

	public void setSubmittedJsonStringValues(String submittedJsonStringValues) {
		this.submittedJsonStringValues = submittedJsonStringValues;
	}

	public String getCategoriesJsonString() {
		return categoriesJsonString;
	}

	public void setCategoriesJsonString(String categoriesJsonString) {
		this.categoriesJsonString = categoriesJsonString;
	}

	public String getCategoryScore() {
		return categoryScore;
	}

	public void setCategoryScore(String categoryScore) {
		this.categoryScore = categoryScore;
	}

	public String getFeedbackTemplateId() {
		return feedbackTemplateId;
	}

	public void setFeedbackTemplateId(String feedbackTemplateId) {
		this.feedbackTemplateId = feedbackTemplateId;
	}

	public String getCategoriesTotalScore() {
		return categoriesTotalScore;
	}

	public void setCategoriesTotalScore(String categoriesTotalScore) {
		this.categoriesTotalScore = categoriesTotalScore;
	}

	public String getSubmissionStage() {
		return submissionStage;
	}

	public void setSubmissionStage(String submissionStage) {
		this.submissionStage = submissionStage;
	}
	

}
