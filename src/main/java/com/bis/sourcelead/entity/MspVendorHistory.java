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

@Entity
@Table(name = "MSP_VENDOR_HISTORY")
public class MspVendorHistory implements Serializable{
	
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
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORG_RELATION_ID", nullable = false)
    private OrgRelations orgRelationId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MSP_ORG_RELATION_ID", nullable = false)
    private OrgRelations mspOrgRelationId;

    @Column(name = "STATUS")
   	private Integer status;
   		
   	@Column(name = "ASSIGN_DATE")
   	private Date createdDate;
   	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ASSIGN_BY", nullable = false)
   	private User createdBy;
   	
   	@Column(name = "TERMINATE_DATE")
   	private Date updatedDate;
   	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TERMINATE_BY", nullable = false)
   	private User updatedBy;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public OrgLocations getMspOrgLocationId() {
		return mspOrgLocationId;
	}

	public OrgRelations getMspRelationId() {
		return mspRelationId;
	}

	public Integer getStatus() {
		return status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMspOrgLocationId(OrgLocations mspOrgLocationId) {
		this.mspOrgLocationId = mspOrgLocationId;
	}

	public void setMspRelationId(OrgRelations mspRelationId) {
		this.mspRelationId = mspRelationId;
	}

	public OrgRelations getOrgRelationId() {
		return orgRelationId;
	}

	public void setOrgRelationId(OrgRelations orgRelationId) {
		this.orgRelationId = orgRelationId;
	}

	public OrgRelations getMspOrgRelationId() {
		return mspOrgRelationId;
	}

	public void setMspOrgRelationId(OrgRelations mspOrgRelationId) {
		this.mspOrgRelationId = mspOrgRelationId;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}
}
