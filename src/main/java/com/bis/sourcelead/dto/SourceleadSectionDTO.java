package com.bis.sourcelead.dto;

import java.util.List;

import com.bis.sourcelead.entity.OrglocationFormFields;

public class SourceleadSectionDTO {

	private Long id;
	private Long tabID;
	private String tabName;
	private Long sourceleadActionId;
	private String sourceleadActionName;
	private String sectionName;
	private String sectionDescription;
	private int status;
	private List<SourceleadFormFieldsDTO> formFieldsList;
	private List<OrglocationFormFieldsDTO> customFieldsList;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTabID() {
		return tabID;
	}
	public void setTabID(Long tabID) {
		this.tabID = tabID;
	}
	public String getTabName() {
		return tabName;
	}
	public void setTabName(String tabName) {
		this.tabName = tabName;
	}
	public Long getSourceleadActionId() {
		return sourceleadActionId;
	}
	public void setSourceleadActionId(Long sourceleadActionId) {
		this.sourceleadActionId = sourceleadActionId;
	}
	public String getSourceleadActionName() {
		return sourceleadActionName;
	}
	public void setSourceleadActionName(String sourceleadActionName) {
		this.sourceleadActionName = sourceleadActionName;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public String getSectionDescription() {
		return sectionDescription;
	}
	public void setSectionDescription(String sectionDescription) {
		this.sectionDescription = sectionDescription;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public List<SourceleadFormFieldsDTO> getFormFieldsList() {
		return formFieldsList;
	}
	public void setFormFieldsList(List<SourceleadFormFieldsDTO> formFieldsList) {
		this.formFieldsList = formFieldsList;
	}
	public List<OrglocationFormFieldsDTO> getCustomFieldsList() {
		return customFieldsList;
	}
	public void setCustomFieldsList(List<OrglocationFormFieldsDTO> customFieldsList) {
		this.customFieldsList = customFieldsList;
	}
	
	
}
