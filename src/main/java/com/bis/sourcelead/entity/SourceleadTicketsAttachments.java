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
@Table(name = "SOURCELEAD_TICKETS_ATTACHMENTS")
public class SourceleadTicketsAttachments implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
    @Column(name = "FILE_URL")
   	@Type(type="text")
   	private String fileURL;
    
    @Column(name = "FILE_NAME")
   	private String fileName;
    
    @Column(name = "FILE_TYPE")
   	private String fileType;
    
    @Column(name = "STATUS")
   	private Integer status;
    
    @Column(name = "ATTACHMENT_UUID")
   	private String attachmentUUID;
   		
    @ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "TICKET_ID", nullable = false)
   	private SourceleadTickets ticketId;
    
    @Column(name = "CREATED_DATE")
   	private Date createdDate;
   	
    @ManyToOne
   	@JoinColumn(name = "CREATED_BY", nullable = false)
   	private User createdBy;
   	
   	@Column(name = "UPDATED_DATE")
   	private Date updatedDate;
   	
   	@ManyToOne
   	@JoinColumn(name = "UPDATED_BY", nullable = false)
   	private User updatedBy;

	public Long getId() {
		return id;
	}

	public String getFileURL() {
		return fileURL;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public Integer getStatus() {
		return status;
	}

	public String getAttachmentUUID() {
		return attachmentUUID;
	}

	public SourceleadTickets getTicketId() {
		return ticketId;
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

	public void setFileURL(String fileURL) {
		this.fileURL = fileURL;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setAttachmentUUID(String attachmentUUID) {
		this.attachmentUUID = attachmentUUID;
	}

	public void setTicketId(SourceleadTickets ticketId) {
		this.ticketId = ticketId;
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
}
