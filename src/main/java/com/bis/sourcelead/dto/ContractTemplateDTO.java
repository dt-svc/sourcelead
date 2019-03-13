package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

import com.bis.sourcelead.annotations.CleanHtml;
import com.bis.sourcelead.entity.OrgLocations;

public class ContractTemplateDTO extends SearchDTO implements Serializable {
	
	private String templateId;
	private String templateName;
	private String orgLocationId;
	private List<String> templateType;
	private List<String> contractType;
	private String letterHead;
	
	@CleanHtml(escape=false)
	private String description;
	
	private String templateContent;
	private String templateUUID;
	private String templateStatus;
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
	public String getOrgLocationId()
	{
		return orgLocationId;
	}
	public void setOrgLocationId(String orgLocationId)
	{
		this.orgLocationId = orgLocationId;
	}
	public List<String> getTemplateType()
	{
		return templateType;
	}
	public void setTemplateType(List<String> templateType)
	{
		this.templateType = templateType;
	}
	public List<String> getContractType()
	{
		return contractType;
	}
	public void setContractType(List<String> contractType)
	{
		this.contractType = contractType;
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
	public String getAction()
	{
		return action;
	}
	public void setAction(String action)
	{
		this.action = action;
	}
	
	
	
}
