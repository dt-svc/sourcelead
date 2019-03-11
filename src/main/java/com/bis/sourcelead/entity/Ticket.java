package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

@Entity
@Table(name = "TICKET")
public class Ticket implements Serializable {

	/**
	 * VeeraRaghava
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "UUID")
	private String uuid;

	@Column(name = "GENERATED_ID")
	private String generatedId;
	
	@Column(name = "TICKET_TITLE")
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TICKET_TYPE_ID", referencedColumnName = "ID")
	private TicketType ticketType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TICKET_CATEGORY_ID", referencedColumnName = "ID")
	private TicketCategory ticketCategory;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TICKET_PRIORITY_ID", referencedColumnName = "ID")
	private TicketPriority ticketPriority;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TICKET_STATUS_ID", referencedColumnName = "ID")
	private TicketStatus ticketStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_LOCATION_ID", referencedColumnName = "ID")
	private OrgLocations orgLocations;

	@Column(name = "DESCRIPTION",columnDefinition = "LONGTEXT default NULL")
	private String description;

	@Column(name = "NOTIFY_TO", columnDefinition = "LONGTEXT default NULL")
	private String notifyTo;

	@Column(name = "STATUS")
	private int status;
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_BY", referencedColumnName = "ID")
	private User createdBy;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UPDATED_BY", referencedColumnName = "ID")
	private User updatedBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@OneToMany(mappedBy = "ticket", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<TicketAssignTo> ticketAssignToList;
	
	@OneToMany(mappedBy = "ticket", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<TicketAttachment> ticketAttachmentList;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getGeneratedId() {
		return generatedId;
	}

	public void setGeneratedId(String generatedId) {
		this.generatedId = generatedId;
	}

	public TicketType getTicketType() {
		return ticketType;
	}

	public void setTicketType(TicketType ticketType) {
		this.ticketType = ticketType;
	}

	public TicketCategory getTicketCategory() {
		return ticketCategory;
	}

	public void setTicketCategory(TicketCategory ticketCategory) {
		this.ticketCategory = ticketCategory;
	}

	public TicketPriority getTicketPriority() {
		return ticketPriority;
	}

	public void setTicketPriority(TicketPriority ticketPriority) {
		this.ticketPriority = ticketPriority;
	}

	public TicketStatus getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(TicketStatus ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public OrgLocations getOrgLocations() {
		return orgLocations;
	}

	public void setOrgLocations(OrgLocations orgLocations) {
		this.orgLocations = orgLocations;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNotifyTo() {
		return notifyTo;
	}

	public void setNotifyTo(String notifyTo) {
		this.notifyTo = notifyTo;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public List<TicketAssignTo> getTicketAssignToList() {
		return ticketAssignToList;
	}

	public void setTicketAssignToList(List<TicketAssignTo> ticketAssignToList) {
		this.ticketAssignToList = ticketAssignToList;
	}

	public List<TicketAttachment> getTicketAttachmentList() {
		return ticketAttachmentList;
	}

	public void setTicketAttachmentList(List<TicketAttachment> ticketAttachmentList) {
		this.ticketAttachmentList = ticketAttachmentList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
