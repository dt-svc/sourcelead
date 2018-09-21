package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "SOURCELEAD_TAX_CHALLAN_ITEM")
public class SourceleadTaxChallanItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SOURCELEAD_TAX_CHALLAN_ID", nullable = false)
	private SourceleadTaxChallan sourceleadtaxchallan;

	@Column(name = "FIELD_TYPE")
	private String fieldType;

	@Column(name = "LABEL_NAME")
	private String labelName;

	@Column(name = "DISCRIPTION")
	private String discription;

	@Column(name = "IS_FIELD_REQUIRED")
	private Boolean isFieldRequired;

	@Column(name = "PLACEHOLDER")
	private String placeHolder;

	@Column(name = "MIN_LENGTH")
	private int minLength;

	@Column(name = "MAX_LENGTH")
	private int maxLength;

	@Column(name = "MIN_DATE")
	private Date minDate;

	@Column(name = "MAX_DATE")
	private Date maxDate;

	@Column(name = "MIN_NUMBER")
	private int minNumber;

	@Column(name = "MAX_NUMBER")
	private int maxNumber;

	@Column(name = "DATAVALIDATION_TYPE")
	private String dataValidationType;

	@Column(name = "DATAVALIDATION_RANGE")
	private String dataValidationRange;


	@Column(name = "HELPTEXT")
	private String helptext;
/*	@Column(name = "MAX_LIMIT")
	private BigDecimal maxLimit;*/

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "ACTIVE")
	private boolean active;

	@OneToMany(mappedBy = "sourceleadTaxChallanItem", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<SourceleadTaxChallanItemSubFormField> sourceleadTaxChallanItemSubFormFieldList;

	public List<SourceleadTaxChallanItemSubFormField> getSourceleadTaxChallanItemSubFormFieldList() {
		return sourceleadTaxChallanItemSubFormFieldList;
	}

	public void setSourceleadTaxChallanItemSubFormFieldList(
			List<SourceleadTaxChallanItemSubFormField> sourceleadTaxChallanItemSubFormFieldList) {
		this.sourceleadTaxChallanItemSubFormFieldList = sourceleadTaxChallanItemSubFormFieldList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SourceleadTaxChallan getSourceleadtaxchallan() {
		return sourceleadtaxchallan;
	}

	public void setSourceleadtaxchallan(SourceleadTaxChallan sourceleadtaxchallan) {
		this.sourceleadtaxchallan = sourceleadtaxchallan;
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

	public Boolean getIsFieldRequired() {
		return isFieldRequired;
	}

	public void setIsFieldRequired(Boolean isFieldRequired) {
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getHelptext() {
		return helptext;
	}

	public void setHelptext(String helptext) {
		this.helptext = helptext;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

/*	public BigDecimal getMaxLimit() {
		return maxLimit;
	}

	public void setMaxLimit(BigDecimal maxLimit) {
		this.maxLimit = maxLimit;
	}*/



}
