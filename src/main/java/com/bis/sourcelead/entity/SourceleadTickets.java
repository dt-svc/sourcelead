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
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "SOURCELEAD_TICKETS")
public class SourceleadTickets implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
    @Column(name = "TICKET_TITLE")
   	private String ticketTitle;
    
    @Column(name = "TICKET_GENERATED_ID")
   	private String ticketGeneratedId;
    
    @Column(name = "TICKET_TYPE")
   	private String ticketType;
    
    @Column(name = "TICKET_URL")
   	@Type(type="text")
   	private String ticketURL;
    
    @Column(name = "TICKET_DESCRIPTION")
	@Type(type="text")
	private String description;
    
    @Column(name = "STATUS")
   	private Integer status;
    
    @Column(name = "TICKET_STATUS")
   	private String ticketStatus;
    
    @Column(name = "TICKET_UUID")
   	private String ticketUUID;
    
    @ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "ORGLOCATION_ID", nullable = false)
   	private OrgLocations orgLocationId;
   		
    @ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "TAB_ID", nullable = false)
   	private Tabs tabId;
    
    @ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "USER_ID", nullable = false)
   	private User userId;
    
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
   	
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticketId")
    private Set<SourceleadTicketsAttachments> sourceleadTicketsAttachments;
   	
    @Transient
	@JsonProperty
	private String createDateStr;
    
	public Long getId() {
		return id;
	}

	public String getTicketTitle() {
		return ticketTitle;
	}

	public String getTicketType() {
		return ticketType;
	}

	public String getTicketURL() {
		return ticketURL;
	}

	public String getDescription() {
		return description;
	}

	public Integer getStatus() {
		return status;
	}

	public String getTicketStatus() {
		return ticketStatus;
	}

	public String getTicketUUID() {
		return ticketUUID;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public Tabs getTabId() {
		return tabId;
	}

	public User getUserId() {
		return userId;
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

	public void setTicketTitle(String ticketTitle) {
		this.ticketTitle = ticketTitle;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}

	public void setTicketURL(String ticketURL) {
		this.ticketURL = ticketURL;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setTicketStatus(String ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public void setTicketUUID(String ticketUUID) {
		this.ticketUUID = ticketUUID;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public void setTabId(Tabs tabId) {
		this.tabId = tabId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
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

	public Set<SourceleadTicketsAttachments> getSourceleadTicketsAttachments() {
		return sourceleadTicketsAttachments;
	}

	public void setSourceleadTicketsAttachments(Set<SourceleadTicketsAttachments> sourceleadTicketsAttachments) {
		this.sourceleadTicketsAttachments = sourceleadTicketsAttachments;
	}

	public String getCreateDateStr() {
		return createDateStr;
	}

	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}

	public String getTicketGeneratedId() {
		return ticketGeneratedId;
	}

	public void setTicketGeneratedId(String ticketGeneratedId) {
		this.ticketGeneratedId = ticketGeneratedId;
	}
}
