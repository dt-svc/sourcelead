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
import org.hibernate.annotations.Type;

@Entity
@Table(name = "MSP_VENDOR")
public class MspVendor implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_LOCATION_ID", nullable = false)
	private OrgLocations orgLocationId;
    
    @ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "MSP_VENDOR_ORG_LOCATION_ID", nullable = false)
   	private OrgLocations mspOrgLocationId;
    
    @ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "MSP_RELATION_ID", nullable = false)
   	private OrgRelations mspRelationId;
    
    @Column(name = "ORG_RELATION_IDS" , columnDefinition = "TEXT default NULL")
    private String orgRelationIds;
    
    @Column(name = "MSP_ORG_RELATION_IDS" , columnDefinition = "TEXT default NULL")
    private String mspOrgRelationIds;

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public OrgLocations getMspOrgLocationId() {
		return mspOrgLocationId;
	}

	public OrgRelations getMspRelationId() {
		return mspRelationId;
	}

	public String getOrgRelationIds() {
		return orgRelationIds;
	}

	public String getMspOrgRelationIds() {
		return mspOrgRelationIds;
	}

	public Integer getStatus() {
		return status;
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

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public void setMspOrgLocationId(OrgLocations mspOrgLocationId) {
		this.mspOrgLocationId = mspOrgLocationId;
	}

	public void setMspRelationId(OrgRelations mspRelationId) {
		this.mspRelationId = mspRelationId;
	}

	public void setOrgRelationIds(String orgRelationIds) {
		this.orgRelationIds = orgRelationIds;
	}

	public void setMspOrgRelationIds(String mspOrgRelationIds) {
		this.mspOrgRelationIds = mspOrgRelationIds;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
