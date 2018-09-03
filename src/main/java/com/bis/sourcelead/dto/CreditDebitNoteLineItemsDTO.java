package com.bis.sourcelead.dto;

public class CreditDebitNoteLineItemsDTO {
	
	private String inlineDescription;
	private String price;
	private String totalAmount;
	private String rowTotal;
	private String rowTotalAmount;
	private String taxAmount;
	private String taxType;
	private String quantity;
    private String account;
    private String lineItemDate;
    private String associates;
    private String lineItemId;
    
    
	public String getInlineDescription() {
		return inlineDescription;
	}
	public void setInlineDescription(String inlineDescription) {
		this.inlineDescription = inlineDescription;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	
	public String getRowTotal() {
		return rowTotal;
	}
	public void setRowTotal(String rowTotal) {
		this.rowTotal = rowTotal;
	}
	public String getRowTotalAmount() {
		return rowTotalAmount;
	}
	public void setRowTotalAmount(String rowTotalAmount) {
		this.rowTotalAmount = rowTotalAmount;
	}
	public String getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(String taxAmount) {
		this.taxAmount = taxAmount;
	}
	public String getTaxType() {
		return taxType;
	}
	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getLineItemDate() {
		return lineItemDate;
	}
	public void setLineItemDate(String lineItemDate) {
		this.lineItemDate = lineItemDate;
	}
	public String getAssociates() {
		return associates;
	}
	public void setAssociates(String associates) {
		this.associates = associates;
	}
	public String getLineItemId() {
		return lineItemId;
	}
	public void setLineItemId(String lineItemId) {
		this.lineItemId = lineItemId;
	}
    
    
	}
