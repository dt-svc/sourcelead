package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "EXPENSES_ENTRIES", catalog = "sourcelead")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "ExpensesEntries")
public class ExpensesEntries implements Serializable {
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name="EXPENSESID", nullable=false )        
    private Expenses expensesId;
	
	@ManyToOne
    @JoinColumn(name="EXPENSES_CATEGORY_ID", nullable=false )        
    private ExpensesCategory expensesCategoryId;
	
	@Column(name = "EXPENSE_DATE")
	private Date expenseDate;
	
	@Column(name = "PAYMENT_MODE")
	private String paymentMode;
	
	@Column(name = "RECEIPT_ISSUED_BY")
	private String receiptIssuedBy;
	
	@Column(name = "AMOUNT")
	private float amount;
	
	@Column(name = "DESCRIPTION", columnDefinition = "TEXT default NULL")
	private String description;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Expenses getExpensesId() {
		return expensesId;
	}

	public void setExpensesId(Expenses expensesId) {
		this.expensesId = expensesId;
	}

	public ExpensesCategory getExpensesCategoryId() {
		return expensesCategoryId;
	}

	public void setExpensesCategoryId(ExpensesCategory expensesCategoryId) {
		this.expensesCategoryId = expensesCategoryId;
	}

	public Date getExpenseDate() {
		return expenseDate;
	}

	public void setExpenseDate(Date expenseDate) {
		this.expenseDate = expenseDate;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getReceiptIssuedBy() {
		return receiptIssuedBy;
	}

	public void setReceiptIssuedBy(String receiptIssuedBy) {
		this.receiptIssuedBy = receiptIssuedBy;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	}
