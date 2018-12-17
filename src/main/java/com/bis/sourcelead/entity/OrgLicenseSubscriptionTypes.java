package com.bis.sourcelead.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ORG_LICENSE_SUBSCRIPTION_TYPES")
public class OrgLicenseSubscriptionTypes {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	
	@Column(name = "NAME")
	private String name;
	
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	
	@Column(name = "CATEGORY")
	private String category;
	
	
	@Column(name = "UNIQUE_KEY")
	private String uniqueKey;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "SUBSCRIPTION_TYPE_ORDER")
	private Integer subscriptionTypeOrder;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}


	public String getUniqueKey() {
		return uniqueKey;
	}

	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSubscriptionTypeOrder() {
		return subscriptionTypeOrder;
	}

	public void setSubscriptionTypeOrder(Integer subscriptionTypeOrder) {
		this.subscriptionTypeOrder = subscriptionTypeOrder;
	}
	
}
