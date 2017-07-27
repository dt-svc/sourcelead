package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.bis.sourcelead.enums.PaymentModes;

public class ExpensesEntriesDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private ExpensesDTO expensesId;
	
	private ExpensesCategoryDTO expensesCategoryId;

	private PaymentModes paymentMode;
	
	private List<ExpensesEntryDocumentsDTO> expensesEntryDocuments;

	private String receiptIssuedBy;

	private String currencyType;
	
	private String expenseDate;

	private Float amount;

	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ExpensesDTO getExpensesId() {
		return expensesId;
	}

	public void setExpensesId(ExpensesDTO expensesId) {
		this.expensesId = expensesId;
	}

	public ExpensesCategoryDTO getExpensesCategoryId() {
		return expensesCategoryId;
	}

	public void setExpensesCategoryId(ExpensesCategoryDTO expensesCategoryId) {
		this.expensesCategoryId = expensesCategoryId;
	}
	public String getExpenseDate() {
		return expenseDate;
	}

	public void setExpenseDate(String expenseDate) {
		this.expenseDate = expenseDate;
	}

	public PaymentModes getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(PaymentModes paymentMode) {
		this.paymentMode = paymentMode;
	}

	public List<ExpensesEntryDocumentsDTO> getExpensesEntryDocuments() {
		return expensesEntryDocuments;
	}

	public void setExpensesEntryDocuments(List<ExpensesEntryDocumentsDTO> expensesEntryDocuments) {
		this.expensesEntryDocuments = expensesEntryDocuments;
	}

	public String getReceiptIssuedBy() {
		return receiptIssuedBy;
	}

	public void setReceiptIssuedBy(String receiptIssuedBy) {
		this.receiptIssuedBy = receiptIssuedBy;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	

}
