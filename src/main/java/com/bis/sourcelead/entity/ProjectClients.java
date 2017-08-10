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

@Entity
@Table(name = "PROJECT_CLIENTS")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL,region="projectClients")
public class ProjectClients implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
    @ManyToOne
    @JoinColumn(name = "PROJECT_ID")
    private OrgRelations projectId;
	
    @ManyToOne
    @JoinColumn(name = "ORG_RELATION_ID")
    private OrgRelations orgRelationId;
    
    @ManyToOne
    @JoinColumn(name = "ORG_LOCATIONS_ID")
    private OrgLocations orgLocationsId;
    
    @Column(name = "UUID")
    private String uuid;
    
    @Column(name = "STATUS")
    private Integer status;
    
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "CREATED_BY")
  	private User createdBy;
	
	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "UPDATED_BY")
  	private User updatedBy;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrgRelations getProjectId() {
		return projectId;
	}

	public void setProjectId(OrgRelations projectId) {
		this.projectId = projectId;
	}

	public OrgRelations getOrgRelationId() {
		return orgRelationId;
	}

	public void setOrgRelationId(OrgRelations orgRelationId) {
		this.orgRelationId = orgRelationId;
	}

	public OrgLocations getOrgLocationsId() {
		return orgLocationsId;
	}

	public void setOrgLocationsId(OrgLocations orgLocationsId) {
		this.orgLocationsId = orgLocationsId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
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
}
