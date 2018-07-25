package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

import com.bis.sourcelead.annotations.Required;

/**
 * @author
 *
 */
public class ImmigrationTemplateDTO extends SearchDTO implements Serializable {

	private Long id;

	private Long templateId;
	
	@Required(desc = "Template Name")
	private String templateName;

	@Required(desc = "Template Description")
	private String templateDescription;

	private Long immigrationDocDeletedListIds[];

	private Long immigrationFormDeletedListIds[];

	private List<ImmigrationTemplateDocDTO> immigrationDocList;

	private List<ImmigrationTemplateFormDTO> immigrationFormList;
	
	private String staticPath;

	@Required(desc ="Country")
	private String country;
	
	@Required(desc ="Petition Type")
	private String petitionType;
	
	private String uuid;
	
	private String documentCategory;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
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

	

	public Long[] getImmigrationDocDeletedListIds() {
		return immigrationDocDeletedListIds;
	}

	public void setImmigrationDocDeletedListIds(Long[] immigrationDocDeletedListIds) {
		this.immigrationDocDeletedListIds = immigrationDocDeletedListIds;
	}

	

	public Long[] getImmigrationFormDeletedListIds() {
		return immigrationFormDeletedListIds;
	}

	public void setImmigrationFormDeletedListIds(Long[] immigrationFormDeletedListIds) {
		this.immigrationFormDeletedListIds = immigrationFormDeletedListIds;
	}

	public List<ImmigrationTemplateDocDTO> getImmigrationDocList() {
		return immigrationDocList;
	}

	public void setImmigrationDocList(List<ImmigrationTemplateDocDTO> immigrationDocList) {
		this.immigrationDocList = immigrationDocList;
	}

	public List<ImmigrationTemplateFormDTO> getImmigrationFormList() {
		return immigrationFormList;
	}

	public void setImmigrationFormList(List<ImmigrationTemplateFormDTO> immigrationFormList) {
		this.immigrationFormList = immigrationFormList;
	}

	public String getStaticPath() {
		return staticPath;
	}

	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPetitionType() {
		return petitionType;
	}

	public void setPetitionType(String petitionType) {
		this.petitionType = petitionType;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getDocumentCategory() {
		return documentCategory;
	}

	public void setDocumentCategory(String documentCategory) {
		this.documentCategory = documentCategory;
	}
	
}
