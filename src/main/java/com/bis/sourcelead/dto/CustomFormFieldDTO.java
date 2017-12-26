package com.bis.sourcelead.dto;

public class CustomFormFieldDTO {
	
	long entityId;
	String customFormFieldData;
	long customFormFieldId;
	
	public String getCustomFormFieldData() {
		return customFormFieldData;
	}

	public void setCustomFormFieldData(String customFormFieldData) {
		this.customFormFieldData = customFormFieldData;
	}

	public long getEntityId() {
		return entityId;
	}

	public void setEntityId(long entityId) {
		this.entityId = entityId;
	}

	public long getCustomFormFieldId() {
		return customFormFieldId;
	}

	public void setCustomFormFieldId(long customFormFieldId) {
		this.customFormFieldId = customFormFieldId;
	}
	
	
}
