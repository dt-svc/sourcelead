package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

public class TicketDTO implements Serializable {

	/**
	 * VeeraRaghava
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String uuid;
	private String generatedId;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	private String title;
	private long ticketTypeId;
	private String ticketType;
	private long ticketCategoryId;
	private String ticketCategory;
	private long ticketPriorityId;
	private String ticketPriority;
	private String ticketPriorityTextColor;
	private String ticketPriorityImage;
	private long ticketStatusId;
	private String ticketStatus;
	private int status;
	private String description;
	private List<String> assignedTo;
	private List<TicketAssignToDTO> ticketAssignToList;
	private String notifyTo;
	private Long orgLocationsId;
	private Long createdById;
	private String createdBy;
	private String createdDate;
	private Long updatedById;
	private String updatedDate;
	private List<FileInfo> fileInfo;
	private List<TicketAttachmentDTO> attachmentList;

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

	public String getTicketPriorityTextColor() {
		return ticketPriorityTextColor;
	}

	public void setTicketPriorityTextColor(String ticketPriorityTextColor) {
		this.ticketPriorityTextColor = ticketPriorityTextColor;
	}

	public String getTicketPriorityImage() {
		return ticketPriorityImage;
	}

	public void setTicketPriorityImage(String ticketPriorityImage) {
		this.ticketPriorityImage = ticketPriorityImage;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public List<TicketAssignToDTO> getTicketAssignToList() {
		return ticketAssignToList;
	}

	public void setTicketAssignToList(List<TicketAssignToDTO> ticketAssignToList) {
		this.ticketAssignToList = ticketAssignToList;
	}

	public String getNotifyTo() {
		return notifyTo;
	}

	public void setNotifyTo(String notifyTo) {
		this.notifyTo = notifyTo;
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

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Long getUpdatedById() {
		return updatedById;
	}

	public void setUpdatedById(Long updatedById) {
		this.updatedById = updatedById;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<FileInfo> getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(List<FileInfo> attachmentList) {
		this.fileInfo = attachmentList;
	}

	public List<TicketAttachmentDTO> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<TicketAttachmentDTO> attachmentList) {
		this.attachmentList = attachmentList;
	}

}
