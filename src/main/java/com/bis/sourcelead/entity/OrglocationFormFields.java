package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "ORGLOCATION_FORM_FIELDS")
public class OrglocationFormFields implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="SECTION_ID", nullable=false )        
    private SourceleadFormSections sourceleadFormSections;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ORGLOCATION_ID", nullable=false )        
    private OrgLocations orgLocations;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ORG_ID", nullable=false )        
    private Org org;
	
	
	@Column(name = "LABEL_NAME")
	private String labelName;

	@Column(name = "FIELD_NAME")
	private String fieldName;
	
	@Column(name = "LABEL_INFO",columnDefinition = "TEXT default NULL")
	private String labelInfo;
	
	@Column(name = "FIELD_TYPE")
	private String fieldType;
	
	@Column(name = "FIELD_DESC",columnDefinition = "TEXT default NULL")
	private String fieldDesc;
	
	@Column(name = "IS_FIELD_REQUIRED")
	private int isFieldRequired;
	
	@Column(name = "PLACEHOLDER")
	private String placeHolder;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
    @JoinColumn(name="SL_DEPENDANT_FIELD", nullable=false )        
    private SourceleadFormFields sourceLeadDependantField;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
    @JoinColumn(name="CUSTOM_DEPENDANT_FIELD", nullable=false )        
    private OrglocationFormFields customDependantField;
	
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
	
	@Column(name = "STATUS")
	private int status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CREATED_BY", nullable=false )
	private User createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="UPDATED_BY", nullable=false )
	private User updatedBy;

	@Column(name="DATA_VALIDATION_TYPE",columnDefinition = "TEXT default NULL")
	private String dataValidationType;
	
	
	@Transient
	@JsonProperty
	private String dataValidationRange;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SourceleadFormSections getSourceleadFormSections() {
		return sourceleadFormSections;
	}

	public void setSourceleadFormSections(SourceleadFormSections sourceleadFormSections) {
		this.sourceleadFormSections = sourceleadFormSections;
	}

	public OrgLocations getOrgLocations() {
		return orgLocations;
	}

	public void setOrgLocations(OrgLocations orgLocations) {
		this.orgLocations = orgLocations;
	}

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
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

	public SourceleadFormFields getSourceLeadDependantField() {
		return sourceLeadDependantField;
	}

	public void setSourceLeadDependantField(SourceleadFormFields sourceLeadDependantField) {
		this.sourceLeadDependantField = sourceLeadDependantField;
	}

	public OrglocationFormFields getCustomDependantField() {
		return customDependantField;
	}

	public void setCustomDependantField(OrglocationFormFields customDependantField) {
		this.customDependantField = customDependantField;
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

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getPlaceHolder() {
		return placeHolder;
	}

	public void setPlaceHolder(String placeHolder) {
		this.placeHolder = placeHolder;
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
	
	
}
