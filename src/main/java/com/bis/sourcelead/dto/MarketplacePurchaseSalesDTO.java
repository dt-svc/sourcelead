package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;

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
	private Date orderDate;
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
