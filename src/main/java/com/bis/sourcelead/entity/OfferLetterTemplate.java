package com.bis.sourcelead.entity;

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
 

@Entity
@Table(name = "OFFER_LETTER_TEMPLATE")
public class OfferLetterTemplate {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "TEMPLATE_NAME")
	private String templateName;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ORGLOCATION_ID", nullable=false )     
	private OrgLocations orgLocationId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "LETTER_HEAD_ID", nullable=false)
	private LetterHead letterHeadId;
	
	@Column(name = "DESCRIPTION",columnDefinition = "TEXT default NULL")
	private String description;
	
	@Column(name = "TEMPLATE_CONTENT",columnDefinition = "LONGTEXT default NULL")
	private String templateContent;
	
	@Column(name = "TEMPLATE_UUID")
	private String templateUUID;
	
	@Column(name = "TEMPLATE_STATUS")
	private Integer templateStatus;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;

	public Long getId() {
		return id;
	}

	public String getTemplateName() {
		return templateName;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public LetterHead getLetterHeadId() {
		return letterHeadId;
	}

	public String getDescription() {
		return description;
	}

	public String getTemplateContent() {
		return templateContent;
	}

	public String getTemplateUUID() {
		return templateUUID;
	}

	public Integer getTemplateStatus() {
		return templateStatus;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public void setLetterHeadId(LetterHead letterHeadId) {
		this.letterHeadId = letterHeadId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}

	public void setTemplateUUID(String templateUUID) {
		this.templateUUID = templateUUID;
	}

	public void setTemplateStatus(Integer templateStatus) {
		this.templateStatus = templateStatus;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
}
