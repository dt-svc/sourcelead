package com.bis.sourcelead.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Rajashekar
 * @date 29th Mar 2016
 *
 */
@Entity
@Table(name="ORG")
public class Org {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID", nullable = false)
	private Long id;
	
	@Column(name = "ORG_NAME", nullable = false)
	private String orgName;
	
	@Column(name = "WEBSITE", nullable = false)
	private String webSite;
	
	@Column(name = "ORG_TYPE", nullable = false)
	private String orgType;
	
	@Column(name = "ORG_CODE",nullable = false)
	private String orgCode;
	
	@Column(name = "STATUS",nullable = false)
	private String status;
	
	@Column(name = "CREATED_DATE",nullable = false)
	private Date createdDate;
	
	@Column(name = "CREATED_BY",nullable = false)
    private String createdBy;
	
	@Column(name = "UPDATED_DATE",nullable = false)
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY",nullable = false)
	private String updatedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
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
	
	

}
