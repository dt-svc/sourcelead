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

import org.hibernate.annotations.Type;

@Entity
@Table(name = "IMMIGRATION_TEMPLATE_VIEW")
public class ImmigrationTemplateView implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME ")
	@Type(type = "text")
	private String name;

	@Column(name = "DESCRIPTION")
	@Type(type = "text")
	private String description;
	
	@Column(name="ORG_LOCATION_ID") 
	private Long orgLocationId;

	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "UPDATED_DATE")
	private Date updateDate;
	
	@Column(name = "DOCS_COUNT ")
	private Long docsCount;

	@Column(name = "FORMS_COUNT ")
	private Long formsCount;

	@Column(name = "TASKS_COUNT ")
	private Long tasksCount;
	
	@Column(name = "PETITION_TYPE")
	private String petitionType;
	
	@Column(name = "COUNTRY")
	private String country;
	
	@Column(name = "REJECT_REASON")
	@Type(type = "text")
	private String rejectReason;
	
	@Column(name = "TEMPLATEUUID ")
	private String templateUUID;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Long getDocsCount() {
		return docsCount;
	}

	public void setDocsCount(Long docsCount) {
		this.docsCount = docsCount;
	}

	public Long getFormsCount() {
		return formsCount;
	}

	public void setFormsCount(Long formsCount) {
		this.formsCount = formsCount;
	}

	public Long getTasksCount() {
		return tasksCount;
	}

	public void setTasksCount(Long tasksCount) {
		this.tasksCount = tasksCount;
	}

	public String getPetitionType() {
		return petitionType;
	}

	public void setPetitionType(String petitionType) {
		this.petitionType = petitionType;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public String getTemplateUUID() {
		return templateUUID;
	}

	public void setTemplateUUID(String templateUUID) {
		this.templateUUID = templateUUID;
	}

	
}
