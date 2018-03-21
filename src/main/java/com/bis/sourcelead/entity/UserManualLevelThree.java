package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "USER_MANUAL_LEVEL_THREE")
public class UserManualLevelThree implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "LEVEL_THREE_UUID")
	private String levelThreeUUID;
	
	@Column(name = "TITLE")
	private String title;
	
	
	@Column(name = "FILE_NAME")
	private String fileName;
	
	@Column(name = "FILE_URL")
	@Type(type="text")
	private String fileUrl;
	
	@Column(name = "FILE_TYPE")
	private String fileType;
	
	@Column(name = "UPLOAD_URL")
	@Type(type="text")
	private String uploadUrl;
	
	@Column(name = "DESCRIPTION")
	@Type(type="text")
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "USER_MANUAL_LEVEL_TWO_ID")
	private UserManualLevelTwo UserManualLevelTwoId;
	
	@Column(name = "ORDER_THREE")
	private Integer order;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "CREATED_DATE")
    private Date createdDate;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "PUBLISH_ON")
	private Date publishOn;
    
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
   	@JoinColumn(name = "CREATED_BY")
  	private User createdBy;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
   	@JoinColumn(name = "UPDATED_BY")
  	private User updatedBy;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "userManualId")
	private Set<UserManualAttachment> userManualAttachment;
	

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getFileUrl() {
		return fileUrl;
	}


	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}


	

	public String getFileType() {
		return fileType;
	}


	public void setFileType(String fileType) {
		this.fileType = fileType;
	}


	public String getUploadUrl() {
		return uploadUrl;
	}


	public void setUploadUrl(String uploadUrl) {
		this.uploadUrl = uploadUrl;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public UserManualLevelTwo getUserManualLevelTwoId() {
		return UserManualLevelTwoId;
	}


	public void setUserManualLevelTwoId(UserManualLevelTwo userManualLevelTwoId) {
		UserManualLevelTwoId = userManualLevelTwoId;
	}


	public Integer getOrder() {
		return order;
	}


	public void setOrder(Integer order) {
		this.order = order;
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


	public Date getPublishOn() {
		return publishOn;
	}


	public void setPublishOn(Date publishOn) {
		this.publishOn = publishOn;
	}


	public Set<UserManualAttachment> getUserManualAttachment() {
		return userManualAttachment;
	}


	public void setUserManualAttachment(Set<UserManualAttachment> userManualAttachment) {
		this.userManualAttachment = userManualAttachment;
	}


	public String getLevelThreeUUID() {
		return levelThreeUUID;
	}


	public void setLevelThreeUUID(String levelThreeUUID) {
		this.levelThreeUUID = levelThreeUUID;
	}


}
