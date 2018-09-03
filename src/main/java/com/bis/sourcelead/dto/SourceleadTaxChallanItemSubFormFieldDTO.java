package com.bis.sourcelead.dto;

import java.io.Serializable;

import com.bis.sourcelead.entity.SourceleadTaxChallanItem;

public class SourceleadTaxChallanItemSubFormFieldDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
	private long sourceleadTaxChallanItemId;
	private String labelName;
	private String fieldName;
	private String fieldDesc;
	private Boolean active;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public long getSourceleadTaxChallanItemId() {
		return sourceleadTaxChallanItemId;
	}
	public void setSourceleadTaxChallanItemId(long sourceleadTaxChallanItemId) {
		this.sourceleadTaxChallanItemId = sourceleadTaxChallanItemId;
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
	public String getFieldDesc() {
		return fieldDesc;
	}
	public void setFieldDesc(String fieldDesc) {
		this.fieldDesc = fieldDesc;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	

}
