package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateMarketPlace;


public class MarketplacePurchaseSalesDTO extends SearchDTO implements Serializable{
	
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
	private String purchaseUuid;
	private String salesUuid;
	private String orderDate;
	private String description;

	private Long orglocationId;
	private String exportType;
	
	private Double sumOfOrdersAmount;
	private Long orgLocationId;
	
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Long getOrglocationId() {
		return orglocationId;
	}
	public void setOrglocationId(Long orglocationId) {
		this.orglocationId = orglocationId;
	}

	public Double getSumOfOrdersAmount() {
		return sumOfOrdersAmount;
	}
	public void setSumOfOrdersAmount(Double sumOfOrdersAmount) {
		this.sumOfOrdersAmount = sumOfOrdersAmount;
	}
	public Long getOrgLocationId() {
		return orgLocationId;
	}
	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;

	}
	public String getExportType() {
		return exportType;
	}
	public void setExportType(String exportType) {
		this.exportType = exportType;
	}
	
	
}
