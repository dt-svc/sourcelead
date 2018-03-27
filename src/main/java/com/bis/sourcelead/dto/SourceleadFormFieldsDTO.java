package com.bis.sourcelead.dto;

public class SourceleadFormFieldsDTO {

	private Long Id;
	private Long sectionId;
	private String labelName;
	private String fieldName;
	private String labelInfo;
	private String placeHolder;
	private String fieldType;
	private String fieldDesc;
	private int isFieldRequired;
	private Long sourceLeadlDependantField;
	private int fieldstatus;
	private String imagePathUrl;
	
	
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public Long getSectionId() {
		return sectionId;
	}
	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getLabelInfo() {
		return labelInfo;
	}
	public void setLabelInfo(String labelInfo) {
		this.labelInfo = labelInfo;
	}
	public String getPlaceHolder() {
		return placeHolder;
	}
	public void setPlaceHolder(String placeHolder) {
		this.placeHolder = placeHolder;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public String getFieldDesc() {
		return fieldDesc;
	}
	public void setFieldDesc(String fieldDesc) {
		this.fieldDesc = fieldDesc;
	}
	public int getIsFieldRequired() {
		return isFieldRequired;
	}
	public void setIsFieldRequired(int isFieldRequired) {
		this.isFieldRequired = isFieldRequired;
	}
	public Long getSourceLeadlDependantField() {
		return sourceLeadlDependantField;
	}
	public void setSourceLeadlDependantField(Long sourceLeadlDependantField) {
		this.sourceLeadlDependantField = sourceLeadlDependantField;
	}
	public int getFieldstatus() {
		return fieldstatus;
	}
	public void setFieldstatus(int fieldstatus) {
		this.fieldstatus = fieldstatus;
	}
	public String getImagePathUrl() {
		return imagePathUrl;
	}
	public void setImagePathUrl(String imagePathUrl) {
		this.imagePathUrl = imagePathUrl;
	}
	
	
}
