package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TicketCategoryDTO implements Serializable {

	/**
	 * VeeraRaghava
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	private String uuid;
	private String name;
	private String description;
	private List<String> assignedTo;
	private List<TicketCategoryAssignToDTO> ticketCategoryAssignToDTOList;
	private List<String> deleteAssignedToIdList;
	private String notifyTo;
	private int status;
	private Long orgLocationsId;
	private Long createdById;
	private Date createdDate;
	private Long updatedById;
	private Date updatedDate;

	public List<TicketCategoryAssignToDTO> getTicketCategoryAssignToDTOList() {
		return ticketCategoryAssignToDTOList;
	}

	public void setTicketCategoryAssignToDTOList(List<TicketCategoryAssignToDTO> ticketCategoryAssignToDTOList) {
		this.ticketCategoryAssignToDTOList = ticketCategoryAssignToDTOList;
	}

	public List<String> getDeleteAssignedToIdList() {
		return deleteAssignedToIdList;
	}

	public void setDeleteAssignedToIdList(List<String> deleteAssignedToIdList) {
		this.deleteAssignedToIdList = deleteAssignedToIdList;
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
