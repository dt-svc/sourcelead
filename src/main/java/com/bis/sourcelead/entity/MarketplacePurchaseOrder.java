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
@Table(name = "CANDIDATE_MARKET_PLACE_PURCHASE_ORDER")
public class MarketplacePurchaseOrder implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_LOCATION_ID", nullable = false)
	private OrgLocations orgLocationId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="MARKET_PLACE_ID", nullable = false)
	private  CandidateMarketPlace  candidateMarketplace;
	


	@Column(name = "ORDER_NO", columnDefinition = "TEXT default NULL")
	private String orderNo;
	

	@Column(name = "COMPANY_NAME" )
	private String companyName;
	
	@Column(name = "PAYMENT_STATUS" )
	private String paymentStatus;
	
	@Column(name = "TOTAL_PRICE")
	private Double totalPrice;
	
	@Column(name = "GRAND_TOTAL")
	private Double grandTotal;
	
	@Column(name = "DISCOUNT")
	private String discount;
	
	@Column(name = "SERVICE_FEE")
	private String serviceFee;
	
	@Column(name = "TAX ")
	private String tax;
	
	@Column(name = "TOTAL_RESUME")
	private String totalresume;
	
	@Column(name = "PAYMENT_ID", columnDefinition = "TEXT default NULL")
	private String paymentId;
	
	@Column(name = "ORDER_DATE" )
	private Date orderDate;
	
	
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

	
	public CandidateMarketPlace getCandidateMarketplace() {
		return candidateMarketplace;
	}

	public void setCandidateMarketplace(CandidateMarketPlace candidateMarketplace) {
		this.candidateMarketplace = candidateMarketplace;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(Double grandTotal) {
		this.grandTotal = grandTotal;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(String serviceFee) {
		this.serviceFee = serviceFee;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public String getTotalresume() {
		return totalresume;
	}

	public void setTotalresume(String totalresume) {
		this.totalresume = totalresume;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}