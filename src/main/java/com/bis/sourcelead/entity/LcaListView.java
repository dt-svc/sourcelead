package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LCA_LIST_VIEW")
public class LcaListView implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "ORG_LOCATION_ID")
	private Long orgLocationId;

	@Column(name = "LCA_NO")
	private String lcaNo;
	
	@Column(name = "LCA_Title")
	private String lcaTitle;
	
	@Column(name = "LCA_STATUS")
	private String lcaStatus;
	
	@Column(name = "LCA_UUID ")
	private String lcaUuid;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "START_DATE")
	private Date startDate;
	
	@Column(name = "END_DATE")
	private Date endDate;
	
	@Column(name = "JOB_TITLE")
	private String jobTitle;
	
	@Column(name = "WAGES_LEVEL")
	private String wagesLevel;
	
	@Column(name = "PREVAILING_WAGE")
	private String prevailingWage;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "LOCATION" ,columnDefinition = "TEXT default NULL")
	private String location;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLcaNo() {
		return lcaNo;
	}

	public void setLcaNo(String lcaNo) {
		this.lcaNo = lcaNo;
	}

	public String getLcaTitle() {
		return lcaTitle;
	}

	public void setLcaTitle(String lcaTitle) {
		this.lcaTitle = lcaTitle;
	}

	public String getLcaStatus() {
		return lcaStatus;
	}

	public void setLcaStatus(String lcaStatus) {
		this.lcaStatus = lcaStatus;
	}

	public String getLcaUuid() {
		return lcaUuid;
	}

	public void setLcaUuid(String lcaUuid) {
		this.lcaUuid = lcaUuid;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getWagesLevel() {
		return wagesLevel;
	}

	public void setWagesLevel(String wagesLevel) {
		this.wagesLevel = wagesLevel;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getPrevailingWage() {
		return prevailingWage;
	}

	public void setPrevailingWage(String prevailingWage) {
		this.prevailingWage = prevailingWage;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
   
	
}
