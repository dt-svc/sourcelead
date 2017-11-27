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
@Table(name = "SOURCELEAD_FORM_FIELDS", catalog = "sourcelead")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "sourceleadFormFields")
public class SourceleadFormFields implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="SECTION_ID", nullable=false )        
    private SourceleadFormSections sourceleadFormSections;
	
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
	
	@Column(name = "STATUS")
	private int status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CREATED_BY", nullable=false )
	private User createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="UPDATED_BY", nullable=false )
	private User updatedBy;
	
}
