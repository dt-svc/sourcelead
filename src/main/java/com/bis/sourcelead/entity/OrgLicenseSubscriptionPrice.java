package com.bis.sourcelead.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ORG_LICENSE_SUBSCRIPTION_PRICE")
public class OrgLicenseSubscriptionPrice implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUBSCRIPTION_TYPE_ID")
    private LookUpData subscriptionType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_SUBSCRIPTION_TYPE_ID")
    private LookUpData userSubscriptionType;
	
	@Column(name = "SUBSCRIPTION_RATE")
	private Integer subscriptionRate;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LookUpData getUserSubscriptionType() {
		return userSubscriptionType;
	}

	public void setUserSubscriptionType(LookUpData userSubscriptionType) {
		this.userSubscriptionType = userSubscriptionType;
	}


	public LookUpData getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(LookUpData subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	public Integer getSubscriptionRate() {
		return subscriptionRate;
	}

	public void setSubscriptionRate(Integer subscriptionRate) {
		this.subscriptionRate = subscriptionRate;
	}

	

}
