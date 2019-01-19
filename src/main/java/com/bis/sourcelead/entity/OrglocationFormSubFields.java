package com.bis.sourcelead.entity;

import java.io.Serializable;

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
@Table(name = "ORGLOCATION_FORM_SUB_FIELDS")
public class OrglocationFormSubFields implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="FORM_FIELD_ID", nullable=false )        
    private OrglocationFormFields formField;
	
	@Column(name = "LABEL_NAME")
	private String labelName;

	@Column(name = "FIELD_NAME")
	private String fieldName;
	
	@Column(name = "FIELD_DESC",columnDefinition = "TEXT default NULL")
	private String fieldDesc;
	
	@Column(name = "STATUS")
	private int status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrglocationFormFields getFormField() {
		return formField;
	}

	public void setFormField(OrglocationFormFields formField) {
		this.formField = formField;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	
	
}
