package com.bis.sourcelead.userMarketplace.dto;

import java.sql.Date;

public class MarkerPlaceProductNegotiaitionHistoryDTO {
	
	private Long id;
	private String negotiatedPrice;
	private String negotiatedQuantity;
	private Long approvedUserId;
	private String approvedPrice;
	private Date approvedDate;
	private String uuid;
	private Long requestedUserId;
	private Date requestedDate;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNegotiatedPrice() {
		return negotiatedPrice;
	}
	public void setNegotiatedPrice(String negotiatedPrice) {
		this.negotiatedPrice = negotiatedPrice;
	}
	public String getNegotiatedQuantity() {
		return negotiatedQuantity;
	}
	public void setNegotiatedQuantity(String negotiatedQuantity) {
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
