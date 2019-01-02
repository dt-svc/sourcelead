package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateMarketPlace;
import com.bis.sourcelead.entity.CandidateMarketPlacePaymentTransactionHistory;
import com.bis.sourcelead.entity.OrgLocations;


public class MarketplaceWalletDTO extends SearchDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String transactionId;
	private Double amount;
	private String amountType;
	private String paymentStatus;
	private String walletUUID;
	private String description;
	private String transactionDate;
	private Long orgLocationId;
	private OrgLocations orgLocations;
	private Double totalBalanceAmount;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getAmountType() {
		return amountType;
	}
	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getWalletUUID() {
		return walletUUID;
	}
	public void setWalletUUID(String walletUUID) {
		this.walletUUID = walletUUID;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public Long getOrgLocationId() {
		return orgLocationId;
	}
	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}
	public OrgLocations getOrgLocations() {
		return orgLocations;
	}
	public void setOrgLocations(OrgLocations orgLocations) {
		this.orgLocations = orgLocations;
	}
	public Double getTotalBalanceAmount() {
		return totalBalanceAmount;
	}
	public void setTotalBalanceAmount(Double totalBalanceAmount) {
		this.totalBalanceAmount = totalBalanceAmount;
	}
	
}
