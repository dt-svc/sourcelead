package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TicketDTO implements Serializable {

	/**
	 * VeeraRaghava
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String uuid;
	private String generatedId;
	private long ticketTypeId;
	private String ticketType;
	private long ticketCategoryId;
	private String ticketCategory;
	private long ticketPriorityId;
	private String ticketPriority;
	private long ticketStatusId;
	private String ticketStatus;
	private String description;
	private List<String> assignedTo;
	private String notifyTo;
	private int status;
	private Long orgLocationsId;
	private Long createdById;
	private Date createdDate;
	private Long updatedById;
	private Date updatedDate;

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

	public long getTicketTypeId() {
		return ticketTypeId;
	}

	public void setTicketTypeId(long ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}

	public String getTicketType() {
		return ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}

	public long getTicketCategoryId() {
		return ticketCategoryId;
	}

	public void setTicketCategoryId(long ticketCategoryId) {
		this.ticketCategoryId = ticketCategoryId;
	}

	public String getTicketCategory() {
		return ticketCategory;
	}

	public void setTicketCategory(String ticketCategory) {
		this.ticketCategory = ticketCategory;
	}

	public long getTicketPriorityId() {
		return ticketPriorityId;
	}

	public void setTicketPriorityId(long ticketPriorityId) {
		this.ticketPriorityId = ticketPriorityId;
	}

	public String getTicketPriority() {
		return ticketPriority;
	}

	public void setTicketPriority(String ticketPriority) {
		this.ticketPriority = ticketPriority;
	}

	public long getTicketStatusId() {
		return ticketStatusId;
	}

	public void setTicketStatusId(long ticketStatusId) {
		this.ticketStatusId = ticketStatusId;
	}

	public String getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(String ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(List<String> assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getNotifyTo() {
		return notifyTo;
	}

	public void setNotifyTo(String notifyTo) {
		this.notifyTo = notifyTo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getOrgLocationsId() {
		return orgLocationsId;
	}

	public void setOrgLocationsId(Long orgLocationsId) {
		this.orgLocationsId = orgLocationsId;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getUpdatedById() {
		return updatedById;
	}

	public void setUpdatedById(Long updatedById) {
		this.updatedById = updatedById;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
