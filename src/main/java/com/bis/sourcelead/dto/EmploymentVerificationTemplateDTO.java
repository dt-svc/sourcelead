package com.bis.sourcelead.dto;

import java.io.Serializable;

public class EmploymentVerificationTemplateDTO extends SearchDTO implements Serializable {
	
	private String templateId;
	private String templateName;
	private String signatory;
	private String orgLocationId;
	private String letterHead;
	private String description;
	private String templateContent;
	private String templateUUID;
	private String templateStatus;
	private String createdBy;
	private String action;
	public String getTemplateId()
	{
		return templateId;
	}
	public void setTemplateId(String templateId)
	{
		this.templateId = templateId;
	}
	public String getTemplateName()
	{
		return templateName;
	}
	public void setTemplateName(String templateName)
	{
		this.templateName = templateName;
	}
	public String getSignatory() {
		return signatory;
	}
	public void setSignatory(String signatory) {
		this.signatory = signatory;
	}
	public String getOrgLocationId()
	{
		return orgLocationId;
	}
	public void setOrgLocationId(String orgLocationId)
	{
		this.orgLocationId = orgLocationId;
	}
	public String getLetterHead()
	{
		return letterHead;
	}
	public void setLetterHead(String letterHead)
	{
		this.letterHead = letterHead;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public String getTemplateContent()
	{
		return templateContent;
	}
	public void setTemplateContent(String templateContent)
	{
		this.templateContent = templateContent;
	}
	public String getTemplateUUID()
	{
		return templateUUID;
	}
	public void setTemplateUUID(String templateUUID)
	{
		this.templateUUID = templateUUID;
	}
	public String getTemplateStatus()
	{
		return templateStatus;
	}
	public void setTemplateStatus(String templateStatus)
	{
		this.templateStatus = templateStatus;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getAction()
	{
		return action;
	}
	public void setAction(String action)
	{
		this.action = action;
	}
	
	
	
}
