package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;

public class TicketCategoryAssignToDTO implements Serializable {

	/**
	 * @author VeeraRaghava
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private long userId;
	private String userName;
	private long ticketCategoryId;
	private String ticketCategory;
	private Long createdBy;
	private Date createdDate;
	private Long updatedBy;
	private Date updatedDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

}
