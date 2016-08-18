package com.bis.sourcelead.entity;

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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import com.bis.sourcelead.enums.Status;

/**
 * 
 * @author Rajashekar
 * @date 29th Mar 2016
 *
 */
@Entity
@Table(name="ORG")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL,region="org")
public class Org {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID", nullable = false)
	private Long id;
	
	@Column(name = "ORG_NAME")
	private String orgName;
	
	@Column(name = "WEBSITE")
	private String webSite;
	
	@Column(name = "ORG_TYPE")
	private String orgType;
	
	@Column(name = "ORG_CODE")
	private String orgCode;
	
	@Column(name = "STATUS")
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PRIMARY_CONTACT_USER_ID")
    private User user;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
    private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "DOC_FILE",columnDefinition="mediumblob")
	@Lob
	private  byte[] docFile;
		
	@Column(name = "FILE_CONTENT_TYPE")
	private String fileContentType;
	
	@Column(name = "ORG_DESCRIPTION")
	@Type(type="text")
	private String orgDescription;
	
	
	@Column(name = "ATS")
	@Enumerated(EnumType.STRING)
	private Status ats;
	
	@Column(name = "VMS")
	@Enumerated(EnumType.STRING)
	private Status vms;
	
	@Column(name = "TIMESHEET")
	@Enumerated(EnumType.STRING)
	private Status timeSheet;

	
	
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


	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public byte[] getDocFile() {
		return docFile;
	}

	public void setDocFile(byte[] docFile) {
		this.docFile = docFile;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getOrgDescription() {
		return orgDescription;
	}

	public void setOrgDescription(String orgDescription) {
		this.orgDescription = orgDescription;
	}

	public Status getAts() {
		return ats;
	}

	public void setAts(Status ats) {
		this.ats = ats;
	}

	public Status getVms() {
		return vms;
	}

	public void setVms(Status vms) {
		this.vms = vms;
	}

	public Status getTimeSheet() {
		return timeSheet;
	}

	public void setTimeSheet(Status timeSheet) {
		this.timeSheet = timeSheet;
	}
	

}
