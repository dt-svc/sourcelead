package com.bis.sourcelead.dto;

import java.util.List;

/**
 * @author Unifinte
 *
 */
public class ImmigrationTemplateDTO {

	private Long id;
	private String templateName;
	private String templateDescription;
	private List<ImmiTemplateDoc> immigrationDocList;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getTemplateDescription() {
		return templateDescription;
	}
	public void setTemplateDescription(String templateDescription) {
		this.templateDescription = templateDescription;
	}
	public List<ImmiTemplateDoc> getImmigrationDocList() {
		return immigrationDocList;
	}
	public void setImmigrationDocList(List<ImmiTemplateDoc> immigrationDocList) {
		this.immigrationDocList = immigrationDocList;
	}
	
}
