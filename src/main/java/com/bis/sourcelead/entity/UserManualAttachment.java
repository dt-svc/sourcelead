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

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "USER_MANUAL_ATTACHMENTS")
public class UserManualAttachment implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "USER_MANUAL_ID")
	private UserManualLevelThree userManualId;
	
	@Column(name = "ATTACHMENT_UUID")
	private String attachmentUUid;
	
	@Column(name = "FILE_NAME")
	private String fileName;
	
	@Column(name = "FILE_URL")
	@Type(type="text")
	private String fileUrl;
	
	@Column(name = "FILE_TYPE")
	private String fileType;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "CREATED_DATE")
    private Date createdDate;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
    
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
   	@JoinColumn(name = "CREATED_BY")
  	private User createdBy;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
   	@JoinColumn(name = "UPDATED_BY")
  	private User updatedBy;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public UserManualLevelThree getUserManualId() {
		return userManualId;
	}


	public void setUserManualId(UserManualLevelThree userManualId) {
		this.userManualId = userManualId;
	}


	public String getAttachmentUUid() {
		return attachmentUUid;
	}


	public void setAttachmentUUid(String attachmentUUid) {
		this.attachmentUUid = attachmentUUid;
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


	@Override
	public String toString() {
		return "UserManualAttachment [id=" + id + ", userManualId=" + userManualId + ", attachmentUUid="
				+ attachmentUUid + ", fileName=" + fileName + ", fileUrl=" + fileUrl + ", fileType=" + fileType
				+ ", status=" + status + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate
				+ ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + "]";
	}


}
