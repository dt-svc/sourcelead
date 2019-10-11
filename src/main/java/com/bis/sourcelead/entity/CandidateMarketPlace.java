package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "CANDIDATE_MARKET_PLACE")
//@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "candidateMarketPlace")
public class CandidateMarketPlace implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CANDIDATE_ID", nullable = false)
	private CandidateGlobal candidateId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_LOCATION_ID", nullable = false)
	private OrgLocations orgLocationId;

	@Column(name = "MARKET_PRICE")
	private String marketPrice;

	@Column(name = "MARKET_RATE_CURRENCY")
	private String marketRateCurrencey;
	
	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@ManyToOne
	@JoinColumn(name = "CREATED_BY", nullable = false)
	private User createdBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@ManyToOne
	@JoinColumn(name = "UPDATED_BY", nullable = false)
	private User updatedBy;

	@Column(name = "CANDIDATE_UUID")
	private String candidateUUID;

	@Column(name = "CAN_MARKET_STATUS")
	private String candidateMarketStatus;
	
	@Column(name = "SHOW_COMPANY_NAME")
	private String showCompanyName;
	
	@Column(name = "COMPANY_NAME")
	private String companyName;
	
	
	@Column(name = "WITHDRAW_REASON")
	private String withdrawReason;
	
	@Column(name = "UUID")
	private String marketplaceUUID;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CandidateGlobal getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(CandidateGlobal candidateId) {
		this.candidateId = candidateId;
	}

	public String getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getMarketRateCurrencey() {
		return marketRateCurrencey;
	}

	public void setMarketRateCurrencey(String marketRateCurrencey) {
		this.marketRateCurrencey = marketRateCurrencey;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getCandidateUUID() {
		return candidateUUID;
	}

	public void setCandidateUUID(String candidateUUID) {
		this.candidateUUID = candidateUUID;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getCandidateMarketStatus() {
		return candidateMarketStatus;
	}

	public void setCandidateMarketStatus(String candidateMarketStatus) {
		this.candidateMarketStatus = candidateMarketStatus;
	}

	public String getShowCompanyName() {
		return showCompanyName;
	}

	public void setShowCompanyName(String showCompanyName) {
		this.showCompanyName = showCompanyName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getWithdrawReason() {
		return withdrawReason;
	}

	public void setWithdrawReason(String withdrawReason) {
		this.withdrawReason = withdrawReason;
	}

	public String getMarketplaceUUID() {
		return marketplaceUUID;
	}

	public void setMarketplaceUUID(String marketplaceUUID) {
		this.marketplaceUUID = marketplaceUUID;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

}
