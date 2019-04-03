package com.bis.sourcelead.dto;

import java.io.Serializable;

public class TicketCommentDTO implements Serializable {

	/**
	 * @author VeeraRaghava
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	private long ticketId;
	private String comment;
	private Integer status;
	private long createdById;
	private String createdByName;
	private String createdByImageURL;
	private String createdDate;
	private String createdDateString;
	private long updatedById;
	private String updatedByName;
	private String updatedDate;
	private String mentionedUserIds;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTicketId() {
		return ticketId;
	}

	public void setTicketId(long ticketId) {
		this.ticketId = ticketId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(long createdById) {
		this.createdById = createdById;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getCreatedByImageURL() {
		return createdByImageURL;
	}

	public void setCreatedByImageURL(String createdByImageURL) {
		this.createdByImageURL = createdByImageURL;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedDateString() {
		return createdDateString;
	}

	public void setCreatedDateString(String createdDateString) {
		this.createdDateString = createdDateString;
	}

	public long getUpdatedById() {
		return updatedById;
	}

	public void setUpdatedById(long updatedById) {
		this.updatedById = updatedById;
	}

	public String getUpdatedByName() {
		return updatedByName;
	}

	public void setUpdatedByName(String updatedByName) {
		this.updatedByName = updatedByName;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getMentionedUserIds() {
		return mentionedUserIds;
	}

	public void setMentionedUserIds(String mentionedUserIds) {
		this.mentionedUserIds = mentionedUserIds;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
