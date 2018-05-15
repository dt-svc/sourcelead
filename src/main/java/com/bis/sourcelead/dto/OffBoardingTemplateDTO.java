package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.annotations.Required;

/**
 * @author Laxman
 *
 */
@SuppressWarnings("serial")
public class OffBoardingTemplateDTO implements Serializable {
	
	 private String templateId;
	 
	 @Required(desc = "templateName")
	 private String templateName;
	 
	 @Required(desc="description")
	 private String description;
 
	 private String staticPath;
	 
	 private List<FileInfo> resignationFileInfo;
	 
	 private List<FileInfo> offboardPackFileInfo;

	public String getTemplateId() {
		return templateId;
	}

	public String getTemplateName() {
		return templateName;
	}

	public String getDescription() {
		return description;
	}

	public String getStaticPath() {
		return staticPath;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}

	public List<FileInfo> getResignationFileInfo() {
		return resignationFileInfo;
	}

	public List<FileInfo> getOffboardPackFileInfo() {
		return offboardPackFileInfo;
	}

	public void setResignationFileInfo(List<FileInfo> resignationFileInfo) {
		this.resignationFileInfo = resignationFileInfo;
	}

	public void setOffboardPackFileInfo(List<FileInfo> offboardPackFileInfo) {
		this.offboardPackFileInfo = offboardPackFileInfo;
	}
}