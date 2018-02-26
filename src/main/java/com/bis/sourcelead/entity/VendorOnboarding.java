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
@Table(name = "VENDOR_ON_BOARDING")
public class VendorOnboarding implements Serializable{

private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORG_RELATION_ID")
    private OrgRelations orgRelationId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORG_LOCATIONS_ID")
    private OrgLocations orgLocationsId;
    
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VENDOR_LOCATION_ID")
    private OrgLocations vendorLocationId;
	
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SPOC_USER_ID")
    private User spocUserId;
	
	@Column(name = "VENDOR_ON_BOARD_STATUS")
	private String vendorOnboardStatus;
	
	@Column(name = "ON_BOARD_SUBMITTED")
	private String onboardSubmitted;
	
	@Column(name = "ON_BOARD_UUID")
	private String onboardUUID;
	
	@Column(name = "REJECTED_REASON")
	@Type(type="text")
	private String RejectedReason;
	
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

	public OrgLocations getVendorLocationId() {
		return vendorLocationId;
	}

	public void setVendorLocationId(OrgLocations vendorLocationId) {
		this.vendorLocationId = vendorLocationId;
	}

	public User getSpocUserId() {
		return spocUserId;
	}

	public void setSpocUserId(User spocUserId) {
		this.spocUserId = spocUserId;
	}

	public String getVendorOnboardStatus() {
		return vendorOnboardStatus;
	}

	public void setVendorOnboardStatus(String vendorOnboardStatus) {
		this.vendorOnboardStatus = vendorOnboardStatus;
	}

	public String getOnboardSubmitted() {
		return onboardSubmitted;
	}

	public void setOnboardSubmitted(String onboardSubmitted) {
		this.onboardSubmitted = onboardSubmitted;
	}

	public String getOnboardUUID() {
		return onboardUUID;
	}

	public void setOnboardUUID(String onboardUUID) {
		this.onboardUUID = onboardUUID;
	}

	public String getRejectedReason() {
		return RejectedReason;
	}

	public void setRejectedReason(String rejectedReason) {
		RejectedReason = rejectedReason;
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
