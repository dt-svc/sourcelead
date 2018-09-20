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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "SOURCELEAD_FORM_FIELDS")
public class SourceleadFormFields implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="SECTION_ID", nullable=false )        
    private SourceleadFormSections sourceleadFormSections;
	
	@Column(name = "LABEL_NAME")
	private String labelName;

	@Column(name = "FIELD_NAME")
	private String fieldName;
	
	@Column(name = "LABEL_INFO",columnDefinition = "TEXT default NULL")
	private String labelInfo;
	
	@Column(name = "PLACE_HOLDER",columnDefinition = "TEXT default NULL")
	private String placeHolder;
	
	@Column(name = "FIELD_TYPE")
	private String fieldType;
	
	@Column(name = "FIELD_DESC",columnDefinition = "TEXT default NULL")
	private String fieldDesc;
	
	@Column(name = "IS_FIELD_REQUIRED")
	private Integer isFieldRequired;
	
	@Column(name = "IS_IMPORT_FIELD")
	private int isImportField;
	
	@Column(name = "SL_DEPENDANT_FIELD")
	private Long slDependantField;
	
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

	public Integer getIsFieldRequired() {
		return isFieldRequired;
	}

	public void setIsFieldRequired(Integer isFieldRequired) {
		this.isFieldRequired = isFieldRequired;
	}

	public int getIsImportField()
	{
		return isImportField;
	}

	public void setIsImportField(int isImportField)
	{
		this.isImportField = isImportField;
	}

	public Long getSlDependantField() {
		return slDependantField;
	}

	public void setSlDependantField(Long slDependantField) {
		this.slDependantField = slDependantField;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
