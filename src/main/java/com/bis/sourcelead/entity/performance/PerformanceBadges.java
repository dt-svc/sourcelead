package com.bis.sourcelead.entity.performance;

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

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

@Entity
@Table(name = "PERFORMANCE_BADGES")
public class PerformanceBadges implements Serializable{
	
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "FILE_URL", columnDefinition = "TEXT default NULL")
	private String fileURL;
	
	@Column(name = "FILE_CONTENT_TYPE", columnDefinition = "TEXT default NULL")
	private String fileContentType;
	
	@Column(name = "FILE_NAME", columnDefinition = "TEXT default NULL")
	private String fileName;
	
	@Column(name = "DESCRIPTION", columnDefinition = "LONGTEXT default NULL")
	private String description ;
		
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ORG_LOCATION_ID", nullable=false )        
    private OrgLocations orgLocationId;
	
	@Column(name = "UUID")
	private String uuid;
	
	@Column(name = "STATUS ")
	private Integer status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CREATED_BY", nullable=false )        
    private User createdBy;
	
	@Column(name = " UPDATED_DATE ")
	private Date updatedDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="UPDATED_BY", nullable=false )        
    private User updatedBy;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFileURL() {
		return fileURL;
	}

	public void setFileURL(String fileURL) {
		this.fileURL = fileURL;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	

}
