package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateMarketPlace;

public class MarketplacePurchaseSalesDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String orderNo;
	private String companyName;
	private String paymentStatus;
	private Double totalPrice;
	private Double grandTotal;
	private String discount;
	private String serviceFee;
	private String tax;
	private String totalresume;
	private String paymentId;
	private String candidateName;
	private String createdDate;
	private String updatedDate;	
	private String purchaseUuid;
	private String salesUuid;
	
	
	
	public String getCandidateName() {
		return candidateName;
	}
	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
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
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getPurchaseUuid() {
		return purchaseUuid;
	}
	public void setPurchaseUuid(String purchaseUuid) {
		this.purchaseUuid = purchaseUuid;
	}
	public String getSalesUuid() {
		return salesUuid;
	}
	public void setSalesUuid(String salesUuid) {
		this.salesUuid = salesUuid;
	}
   
	
}
