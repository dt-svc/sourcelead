package com.bis.sourcelead.entity;


import java.util.ArrayList;
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

/**
 * @author Venkat Kesav
 *
 */

@Entity
@Table(name = "TEAM", catalog = "sourcelead")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "team")

public class Team {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "NAME")
	private String name ;
	
	@ManyToOne
    @JoinColumn(name="OWNER_USER_ID", nullable=false )        
    private User ownerUserId;
	
	@Column(name = "DESCRIPTION ")
	private String description ;
	
	@Column(name = "CATEGORY")
	private String category;
	
	@Column(name = "STATUS ")
	private String status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = " UPDATED_DATE ")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORG_LOCATION_ID")
    private OrgLocations orgLocationsId;
	
	
	@Transient
	private ArrayList<String> teamMembers;
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getOwnerUserId() {
		return ownerUserId;
	}

	public void setOwnerUserId(User ownerUserId) {
		this.ownerUserId = ownerUserId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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

	public OrgLocations getOrgLocationsId() {
		return orgLocationsId;
	}

	public void setOrgLocationsId(OrgLocations orgLocationsId) {
		this.orgLocationsId = orgLocationsId;
	}

	public ArrayList<String> getTeamMembers() {
		return teamMembers;
	}

	public void setTeamMembers(ArrayList<String> teamMembers) {
		this.teamMembers = teamMembers;
	}

	
}
