package com.bis.sourcelead.userMarketplace.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "MARKETPLACE_PRODUCT_NEGOTIAITION_HISTORY")
public class MarketPlaceProductNegotiaitionHistory {

@Id
@Column(name = "ID", nullable = false)
private Long id;

@ManyToOne
@JoinColumn(name="MARKETPLACE_PRODUCT_ID")        
private MarketPlaceProducts marketPlaceProductId;

@Column(name="NEGOTIAIED_PRICE")
private String negotiatedPrice;

@Column(name="NEGOTIAIED_QUANTITY")
private int negotiatedQuantity;

@Column(name="APPROVED_USER_ID")
private Long approvedUserId;

@Column(name="APPROVED_PRICE")
private String approvedPrice;

@Column(name="APPROVED_DATE")
private Date approvedDate;

@Column(name="UUID")
private String uuid;

@Column(name="REQUESTED_USER_ID")
private Long requestedUserId;

@Column(name="REQUESTED_DATE")
private Date requestedDate;

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public MarketPlaceProducts getMarketPlaceProductId() {
	return marketPlaceProductId;
}

public void setMarketPlaceProductId(MarketPlaceProducts marketPlaceProductId) {
	this.marketPlaceProductId = marketPlaceProductId;
}

public String getNegotiatedPrice() {
	return negotiatedPrice;
}

public void setNegotiatedPrice(String negotiatedPrice) {
	this.negotiatedPrice = negotiatedPrice;
}

public int getNegotiatedQuantity() {
	return negotiatedQuantity;
}

public void setNegotiatedQuantity(int negotiatedQuantity) {
	this.negotiatedQuantity = negotiatedQuantity;
}

public Long getApprovedUserId() {
	return approvedUserId;
}

public void setApprovedUserId(Long approvedUserId) {
	this.approvedUserId = approvedUserId;
}

public String getApprovedPrice() {
	return approvedPrice;
}

public void setApprovedPrice(String approvedPrice) {
	this.approvedPrice = approvedPrice;
}

public Date getApprovedDate() {
	return approvedDate;
}

public void setApprovedDate(Date approvedDate) {
	this.approvedDate = approvedDate;
}

public String getUuid() {
	return uuid;
}

public void setUuid(String uuid) {
	this.uuid = uuid;
}

public Long getRequestedUserId() {
	return requestedUserId;
}

public void setRequestedUserId(Long requestedUserId) {
	this.requestedUserId = requestedUserId;
}

public Date getRequestedDate() {
	return requestedDate;
}

public void setRequestedDate(Date requestedDate) {
	this.requestedDate = requestedDate;
}


}
