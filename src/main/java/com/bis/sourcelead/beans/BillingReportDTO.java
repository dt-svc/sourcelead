package com.bis.sourcelead.beans;

import java.util.List;

public class BillingReportDTO {
   
	private String receivedAmount;
	private String payableAmount;
	private String netAmount;
	private List<BillingGraphBean> billingGraphBean;
	
	public String getReceivedAmount() {
		return receivedAmount;
	}
	public void setReceivedAmount(String receivedAmount) {
		this.receivedAmount = receivedAmount;
	}
	public String getPayableAmount() {
		return payableAmount;
	}
	public void setPayableAmount(String payableAmount) {
		this.payableAmount = payableAmount;
	}
	public String getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(String netAmount) {
		this.netAmount = netAmount;
	}
	public List<BillingGraphBean> getBillingGraphBean() {
		return billingGraphBean;
	}
	public void setBillingGraphBean(List<BillingGraphBean> billingGraphBean) {
		this.billingGraphBean = billingGraphBean;
	}
	
}
