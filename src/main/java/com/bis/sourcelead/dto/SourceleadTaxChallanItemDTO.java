package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;

public class SourceleadTaxChallanItemDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
    private Long id;
	private String fieldType;
	private String labelName;
	private String discription;
	private boolean isFieldRequired;
	private String placeHolder;
	private int minLength;
	private int maxLength;
	private Date minDate;
	private Date maxDate;
	private int minNumber;
	private int maxNumber;
	private String dataValidationType;
	private String dataValidationRange;
	private boolean status;
	private Date createdDate;
	private Date updatedDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public String getDiscription() {
		return discription;
	}
	public void setDiscription(String discription) {
		this.discription = discription;
	}
	public boolean isFieldRequired() {
		return isFieldRequired;
	}
	public void setFieldRequired(boolean isFieldRequired) {
		this.isFieldRequired = isFieldRequired;
	}
	public String getPlaceHolder() {
		return placeHolder;
	}
	public void setPlaceHolder(String placeHolder) {
		this.placeHolder = placeHolder;
	}
	public int getMinLength() {
		return minLength;
	}
	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}
	public int getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}
	public Date getMinDate() {
		return minDate;
	}
	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}
	public Date getMaxDate() {
		return maxDate;
	}
	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}
	public int getMinNumber() {
		return minNumber;
	}
	public void setMinNumber(int minNumber) {
		this.minNumber = minNumber;
	}
	public int getMaxNumber() {
		return maxNumber;
	}
	public void setMaxNumber(int maxNumber) {
		this.maxNumber = maxNumber;
	}
	public String getDataValidationType() {
		return dataValidationType;
	}
	public void setDataValidationType(String dataValidationType) {
		this.dataValidationType = dataValidationType;
	}
	public String getDataValidationRange() {
		return dataValidationRange;
	}
	public void setDataValidationRange(String dataValidationRange) {
		this.dataValidationRange = dataValidationRange;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	
}
