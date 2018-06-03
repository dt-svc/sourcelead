package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;




@Entity
@Table(name = "SOURCELEAD_TAX_CHALLAN_ITEM", catalog = "sourcelead")
public class SourceleadTaxChallanItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "FIELD_TYPE")
	private String fieldType;
	
	@Column(name = "LABEL_NAME")
	private String labelName;
	
	@Column(name = "DISCRIPTION")
	private String discription;
	
	
	@Column(name = "IS_FIELD_REQUIRED")
	private int isFieldRequired;
	
	@Column(name = "PLACEHOLDER")
	private String placeHolder;
	
	@Column(name = "MIN_LENGTH")
	private int minLength;
	
	@Column(name = "MAX_LENGTH")
	private int maxLength;
	
	@Column(name = "MIN_DATE")
	private String minDate;
	
	@Column(name = "MAX_DATE")
	private String maxDate;
	
	@Column(name = "MIN_NUMBER")
	private int minNumber;
	
	@Column(name = "MAX_NUMBER")
	private int maxNumber;
	
	@Column(name = "DATAVALIDATION_TYPE")
	private String dataValidationType;
	
	@Column(name = "DATAVALIDATION_RANGE")
	private String dataValidationRange;
	
	@Column(name = "STATUS")
	private int status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "UPDATED_DATE")
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

	public int getIsFieldRequired() {
		return isFieldRequired;
	}

	public void setIsFieldRequired(int isFieldRequired) {
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

	public String getMinDate() {
		return minDate;
	}

	public void setMinDate(String minDate) {
		this.minDate = minDate;
	}

	public String getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(String maxDate) {
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
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

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
