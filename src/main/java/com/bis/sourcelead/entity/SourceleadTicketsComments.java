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
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "SOURCELEAD_TICKETS_COMMENTS")
public class SourceleadTicketsComments implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
    @Column(name = "TICKET_COMMENT_DESCRIPTION")
   	@Type(type="text")
   	private String description;
    
    @Column(name = "STATUS")
   	private Integer status;
   		
    @ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "TICKET_ID", nullable = false)
   	private SourceleadTickets ticketId;
    
    @Column(name = "CREATED_DATE")
   	private Date createdDate;
   	
    @ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "CREATED_BY", nullable = false)
   	private User createdBy;
   	
   	@Column(name = "UPDATED_DATE")
   	private Date updatedDate;
   	
   	@ManyToOne
   	@JoinColumn(name = "UPDATED_BY", nullable = false)
   	private User updatedBy;
   	
   	@Transient
   	@JsonProperty
	private String createdDateStr;
   	
   	@Transient
   	@JsonProperty
	private String createdDateTime;
   	
	@Transient
	@JsonProperty
	private String userName;
	
	
	@Transient
	@JsonProperty
	private String userImage;

	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public Integer getStatus() {
		return status;
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

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getCreatedDateStr() {
		return createdDateStr;
	}

	public String getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateStr(String createdDateStr) {
		this.createdDateStr = createdDateStr;
	}

	public void setCreatedDateTime(String createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}
}
