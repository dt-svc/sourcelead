package com.bis.sourcelead.bgc.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateGlobal;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.User;

/**
 * @author Srinivas.Sabbu
 *
 */
@Entity
@Table(name = "BGC_TEMPLATE")
public class BgcTemplate implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "ORG_LOCATION_ID")
	private OrgLocations orgLocationId;
	
	@Column(name = "NAME ")
	private String name;
	
	@Column(name = "UUID ")
	private String uuid;
	
	@Column(name = "BGC_STATUS_STRING ")
	private String  bgcStatusString;

	@Column(name = "BGC_COMPONENT_STRING ")
	private String  bgcComponentString;
	
	@Column(name = "DESCRIPTION ", columnDefinition = "TEXT default NULL")
	private String description;
	
	@Column(name = "CONFIGURE_TYPE ")
	private String configureType;
	
	@Column(name = "STATUS ")
	private Integer status;
	
	@Column(name = "CREATED_DATE ")
	private Date createdDate;
	
	@ManyToOne
	@JoinColumn(name = "CREATED_BY")
	private User createdBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@ManyToOne
	@JoinColumn(name = "UPDATED_BY")
	private User updatedBy;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	

	public String getBgcStatusString() {
		return bgcStatusString;
	}

	public void setBgcStatusString(String bgcStatusString) {
		this.bgcStatusString = bgcStatusString;
	}

	public String getBgcComponentString() {
		return bgcComponentString;
	}

	public void setBgcComponentString(String bgcComponentString) {
		this.bgcComponentString = bgcComponentString;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getConfigureType() {
		return configureType;
	}

	public void setConfigureType(String configureType) {
		this.configureType = configureType;
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

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	

	
}
