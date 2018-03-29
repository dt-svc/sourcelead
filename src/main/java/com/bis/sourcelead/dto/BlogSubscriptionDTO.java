package com.bis.sourcelead.dto;

import java.io.Serializable;

public class BlogSubscriptionDTO extends SearchDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long Id;
	
	private String email;
	
	private Integer status;
	
	private String createdDate;

	private String updatedDate;
	
	private String blogId;
	
	private String bodytext;
	
	private String subscriptionUUID;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getBlogId() {
		return blogId;
	}

	public void setBlogId(String blogId) {
		this.blogId = blogId;
	}

	public String getBodytext() {
		return bodytext;
	}

	public void setBodytext(String bodytext) {
		this.bodytext = bodytext;
	}

	public String getSubscriptionUUID() {
		return subscriptionUUID;
	}

	public void setSubscriptionUUID(String subscriptionUUID) {
		this.subscriptionUUID = subscriptionUUID;
	}
	
	
	
}