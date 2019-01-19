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
@Table(name = "CANDIDATE_MARKET_PLACE_PAYMENT_TRANSACTION_HISTORY")
public class CandidateMarketPlacePaymentTransactionHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_LOCATION_ID", nullable = false)
	private OrgLocations orgLocationId;
	
	@Column(name = "MARKETPLACE_CART_IDS" , columnDefinition = "TEXT default NULL")
	private String marketPlaceCartIds;
	
	@Column(name = "CURRENCY")
	private String currency;
	
	@Column(name = "INVOICE_AMOUNT")
	private Double invoiceAmount;
	
	@Column(name = "PAYMENT_ID", columnDefinition = "TEXT default NULL")
	private String paymentId;
	
	@Column(name = "ORDER_ID", columnDefinition = "TEXT default NULL")
	private String orderId;
	
	@Column(name = "RECEPIT_ID", columnDefinition = "TEXT default NULL")
	private String receiptId;
	
	@Column(name = "INVOICE_NUMBER", columnDefinition = "TEXT default NULL")
	private String invoiceNumber;
	
	@Column(name = "TRANSACTION_ID", columnDefinition = "TEXT default NULL")
	private String transactionId;
	
	@Column(name = "TRANSACTION_PAYER_ID", columnDefinition = "TEXT default NULL")
	private String transactionPayerId;
	
	@Column(name = "TRANSACTION_USER", columnDefinition = "TEXT default NULL")
	private String transactionUser;
	
	@Column(name = "TRANSACTION_USER_EMAIL", columnDefinition = "TEXT default NULL")
	private String transactionUserEmail;
	
	@Column(name = "TRANSACTION_DATE")
	private Date transactionDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getMarketPlaceCartIds() {
		return marketPlaceCartIds;
	}

	public void setMarketPlaceCartIds(String marketPlaceCartIds) {
		this.marketPlaceCartIds = marketPlaceCartIds;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionPayerId() {
		return transactionPayerId;
	}

	public void setTransactionPayerId(String transactionPayerId) {
		this.transactionPayerId = transactionPayerId;
	}

	public String getTransactionUser() {
		return transactionUser;
	}

	public void setTransactionUser(String transactionUser) {
		this.transactionUser = transactionUser;
	}

	public String getTransactionUserEmail() {
		return transactionUserEmail;
	}

	public void setTransactionUserEmail(String transactionUserEmail) {
		this.transactionUserEmail = transactionUserEmail;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	
	
}
