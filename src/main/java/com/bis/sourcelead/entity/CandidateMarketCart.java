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
@Table(name = "CANDIDATE_MARKET_CART")
//@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "candidateMarketCart")
public class CandidateMarketCart implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CANDIDATE_ID", nullable = false)
	private CandidateGlobal candidateId;
	
	@Column(name = "EMAIL")
	private String Email;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MARKETPLACE_ID", nullable = false)
	private CandidateMarketPlace marketplaceId;

	@Column(name = "CANDIDATE_UUID")
	private String candidateUUID;
	
	@Column(name = "UUID")
	private String marketCartUUID;
	
	@Column(name = "MARKETPLACE_UUID")
	private String marketplaceUUID;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SOURCE_ORG_ID", nullable = false)
	private Org sourceOrgId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TARGET_ORG_ID", nullable = false)
	private Org targetOrgId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SOURCE_ORG_LOCATION_ID", nullable = false)
	private OrgLocations sourceOrgLocationId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TARGET_ORG_LOCATION_ID", nullable = false)
	private OrgLocations targetOrgLocationId;
	
	@Column(name = "CURRENCY")
	private String currency;
	
	@Column(name = "AMOUNT")
	private Double amount ;
	
	@Column(name = "CART_STATUS")
	private String cartStatus;
	
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
	
	public String getEmail() {
		return Email;
	}

	public void setEmail(String Email) {
		this.Email = Email;
	}

	public CandidateMarketPlace getMarketplaceId() {
		return marketplaceId;
	}

	public void setMarketplaceId(CandidateMarketPlace marketplaceId) {
		this.marketplaceId = marketplaceId;
	}

	public String getCandidateUUID() {
		return candidateUUID;
	}

	public void setCandidateUUID(String candidateUUID) {
		this.candidateUUID = candidateUUID;
	}

	public String getMarketCartUUID() {
		return marketCartUUID;
	}

	public void setMarketCartUUID(String marketCartUUID) {
		this.marketCartUUID = marketCartUUID;
	}

	public String getMarketplaceUUID() {
		return marketplaceUUID;
	}

	public void setMarketplaceUUID(String marketplaceUUID) {
		this.marketplaceUUID = marketplaceUUID;
	}

	public Org getSourceOrgId() {
		return sourceOrgId;
	}

	public void setSourceOrgId(Org sourceOrgId) {
		this.sourceOrgId = sourceOrgId;
	}

	public Org getTargetOrgId() {
		return targetOrgId;
	}

	public void setTargetOrgId(Org targetOrgId) {
		this.targetOrgId = targetOrgId;
	}

	public OrgLocations getSourceOrgLocationId() {
		return sourceOrgLocationId;
	}

	public void setSourceOrgLocationId(OrgLocations sourceOrgLocationId) {
		this.sourceOrgLocationId = sourceOrgLocationId;
	}

	public OrgLocations getTargetOrgLocationId() {
		return targetOrgLocationId;
	}

	public void setTargetOrgLocationId(OrgLocations targetOrgLocationId) {
		this.targetOrgLocationId = targetOrgLocationId;
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

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getCartStatus() {
		return cartStatus;
	}

	public void setCartStatus(String cartStatus) {
		this.cartStatus = cartStatus;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	
}
