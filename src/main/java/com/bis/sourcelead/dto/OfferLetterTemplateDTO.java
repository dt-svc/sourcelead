package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

import com.bis.sourcelead.annotations.CleanHtml;
import com.bis.sourcelead.entity.OrgLocations;

@SuppressWarnings("serial")
public class OfferLetterTemplateDTO extends SearchDTO implements Serializable {
	
	private Long templateId;
	private String templateName;
	private Long orgLocationId;
	private String letterHead;
	@CleanHtml(escape=false)
	private String description;
	private String templateContent;
	private String templateUUID;
	private String templateStatus;
	private String action;
	private String userCode;
	
	public Long getTemplateId() {
		return templateId;
	}
	public String getTemplateName() {
		return templateName;
	}
	public Long getOrgLocationId() {
		return orgLocationId;
	}
	 
	public String getDescription() {
		return description;
	}
	public String getTemplateContent() {
		return templateContent;
	}
	public String getTemplateUUID() {
		return templateUUID;
	}
	public String getTemplateStatus() {
		return templateStatus;
	}
	public String getAction() {
		return action;
	}
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}
	 
	public void setDescription(String description) {
		this.description = description;
	}
	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}
	public void setTemplateUUID(String templateUUID) {
		this.templateUUID = templateUUID;
	}
	public void setTemplateStatus(String templateStatus) {
		this.templateStatus = templateStatus;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getLetterHead() {
		return letterHead;
	}
	public void setLetterHead(String letterHead) {
		this.letterHead = letterHead;
	}
	
	
}
