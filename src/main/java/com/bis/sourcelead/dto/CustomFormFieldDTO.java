package com.bis.sourcelead.dto;

public class CustomFormFieldDTO {
	
	Long entityId;
	String customFormFieldData;
	long customFormFieldId;
	
	public String getCustomFormFieldData() {
		return customFormFieldData;
	}

	public void setCustomFormFieldData(String customFormFieldData) {
		this.customFormFieldData = customFormFieldData;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public long getCustomFormFieldId() {
		return customFormFieldId;
	}

	public void setCustomFormFieldId(long customFormFieldId) {
		this.customFormFieldId = customFormFieldId;
	}
	
	
}
