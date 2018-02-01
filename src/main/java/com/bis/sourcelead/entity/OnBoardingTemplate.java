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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "ON_BOARDING_TEMPLATE")
public class OnBoardingTemplate implements Serializable{


	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_LOCATION_ID", nullable = false)
	private OrgLocations orgLocationId;
    
    @Column(name = "TEMPLATE_NAME")
	private String templateName;
    
    @Column(name = "TEMPLATE_UUID")
   	private String templateUUID;
    
    @Column(name = "TEMPLATE_STATUS")
   	private String templateStatus;
	
	@Column(name = "DESCRIPTION")
	@Type(type="text")
	private String description;
	
    @Column(name = "CREATED_USER")
	private String createdUser;
	
    @Column(name = "STATUS")
	private Integer status;
		
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Transient
	private String templateCreatedDate;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}
	
	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateUUID() {
		return templateUUID;
	}

	public void setTemplateUUID(String templateUUID) {
		this.templateUUID = templateUUID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public String getTemplateCreatedDate() {
		return templateCreatedDate;
	}

	public void setTemplateCreatedDate(String templateCreatedDate) {
		this.templateCreatedDate = templateCreatedDate;
	}

	public String getTemplateStatus() {
		return templateStatus;
	}

	public void setTemplateStatus(String templateStatus) {
		this.templateStatus = templateStatus;
	}

	
		
}
