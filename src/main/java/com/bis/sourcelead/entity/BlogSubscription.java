package com.bis.sourcelead.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "BLOG_SUBSCRIPTION")
public class BlogSubscription {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "STATUS")
	private String status;
	
	@DateTimeFormat(pattern="dd/MM/yyyy  HH:mm:ss")
    @Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;
	
	@Column(name = "SUBSCRIPTION_UUID")
	private String subscriptionUUID;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getSubscriptionUUID() {
		return subscriptionUUID;
	}

	public void setSubscriptionUUID(String subscriptionUUID) {
		this.subscriptionUUID = subscriptionUUID;
	}
	
}
