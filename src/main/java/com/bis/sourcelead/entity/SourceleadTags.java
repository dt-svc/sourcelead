package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bis.sourcelead.enums.TagEntites;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.star.ucb.InteractiveNetworkGeneralException;

@Entity
@Table(name = "SOURCELEAD_TAGS")
public class SourceleadTags implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	@Column(name = "ENTITY_TYPE")
	@Enumerated(EnumType.STRING)
	private TagEntites entityType;
	
	@Column(name = "ENTITY_ID")
	private Long entityId;
	
	@Column(name = "TAG_NAME", columnDefinition = "TEXT default NULL")
	private String tagName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_LOCATION_ID")
    private OrgLocations orgLocationId;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_BY")
    private User createdBy;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UPDATED_BY")
    private User updatedBy;
	
    @Column(name = "CREATED_DATE")
	private Date createdDate;
	
    @Column(name = "UPDATED_DATE")
	private Date updatedDate;
    
    @Transient
    @JsonProperty
    private Integer candidateCount;
    
    @Transient
    @JsonProperty
    private Integer jobCount;
    
    @Transient
    @JsonProperty
    private Integer clientCount;
 
    @Transient
    @JsonProperty
    private Integer vendorCount;
    
    @Transient
    @JsonProperty
    private Integer projectCount;
    
    @Transient
    @JsonProperty
    private Integer associateCount;
    
    @Transient
    @JsonProperty
    private String entityName;
    
    @Transient
    @JsonProperty
    private Integer invoicetCount;
    
    
    @Transient
    @JsonProperty
    private Integer billsCount;
    
    
    @Transient
    @JsonProperty
    private Integer assetCount;
    
    @Transient
    @JsonProperty
    private Integer taskCount;
    

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TagEntites getEntityType() {
		return entityType;
	}

	public void setEntityType(TagEntites entityType) {
		this.entityType = entityType;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
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

	public Integer getCandidateCount() {
		return candidateCount;
	}

	public Integer getJobCount() {
		return jobCount;
	}

	public Integer getClientCount() {
		return clientCount;
	}

	public Integer getVendorCount() {
		return vendorCount;
	}

	public Integer getProjectCount() {
		return projectCount;
	}

	public void setCandidateCount(Integer candidateCount) {
		this.candidateCount = candidateCount;
	}

	public void setJobCount(Integer jobCount) {
		this.jobCount = jobCount;
	}

	public void setClientCount(Integer clientCount) {
		this.clientCount = clientCount;
	}

	public void setVendorCount(Integer vendorCount) {
		this.vendorCount = vendorCount;
	}

	public void setProjectCount(Integer projectCount) {
		this.projectCount = projectCount;
	}

	public Integer getAssociateCount() {
		return associateCount;
	}

	public void setAssociateCount(Integer associateCount) {
		this.associateCount = associateCount;
	}

	public Integer getInvoicetCount() {
		return invoicetCount;
	}

	public Integer getBillsCount() {
		return billsCount;
	}

	public Integer getAssetCount() {
		return assetCount;
	}

	public void setInvoicetCount(Integer invoicetCount) {
		this.invoicetCount = invoicetCount;
	}

	public void setBillsCount(Integer billsCount) {
		this.billsCount = billsCount;
	}

	public void setAssetCount(Integer assetCount) {
		this.assetCount = assetCount;
	}

	public Integer getTaskCount() {
		return taskCount;
	}

	public void setTaskCount(Integer taskCount) {
		this.taskCount = taskCount;
	}
}
