package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.bis.sourcelead.enums.PaymentModes;

@Entity
@Table(name = "EXPENSES_ENTRIES")
public class ExpensesEntries implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EXPENSESID", nullable = false)
	private Expenses expensesId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EXPENSE_CATEGORY_ID", nullable = false)
	private ExpensesCategory expensesCategoryId;

	@Column(name = "EXPENSE_DATE")
	private Date expenseDate;

	@Column(name = "PAYMENT_MODE")
	@Enumerated(EnumType.STRING)
	private PaymentModes paymentMode;

	
	@OneToMany(mappedBy = "expensesEntriesId")
	//@Fetch(FetchMode.SELECT)
	private Set<ExpensesEntryDocuments> expensesEntryDocuments;

	@Column(name = "RECEIPT_ISSUED_BY")
	private String receiptIssuedBy;

	@Column(name = "CURRENCY_TYPE")
	private String currencyType;

	@Column(name = "APPROVED_AMOUNT")
	private BigDecimal amount;
	
	@Column(name = "SUBMITTED_AMOUNT")
	private BigDecimal submittedamount;
	
	@Column(name = "AMOUNT_CHANGE_REASON")
	private String amountchangereason;
	
	@Column(name = "PAID_BY")
	private String paidBy;

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
	
	@Column(name = "BILLABLE")
	private Boolean billable;

	@Transient
	private String expenseDateStr;

	@Transient
	private List<Expenses> expenseIndList;

	@Transient
	private String expenseDateString;  
	
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

	public PaymentModes getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(PaymentModes paymentMode) {
		this.paymentMode = paymentMode;
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
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

	public Set<ExpensesEntryDocuments> getExpensesEntryDocuments() {
		return expensesEntryDocuments;
	}

	public void setExpensesEntryDocuments(Set<ExpensesEntryDocuments> expensesEntryDocuments) {
		this.expensesEntryDocuments = expensesEntryDocuments;
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

	public String getExpenseDateStr() {
			if(expenseDate != null){
				SimpleDateFormat dateFormat = expensesId.getOrgLocationId().getLocFullFormDateFormatter();
				expenseDateStr = dateFormat.format(expenseDate);	
			}
		return expenseDateStr;
	}

	public void setExpenseDateStr(String expenseDateStr) {
		this.expenseDateStr = expenseDateStr;
	}

	public List<Expenses> getExpenseIndList() {
		return expenseIndList;
	}
	
	public String getExpenseDateString() {
		return expenseDateString;
	}

	public void setExpenseDateString(String expenseDateString) {
		this.expenseDateString = expenseDateString;
	}

	public void setExpenseIndList(List<Expenses> expenseIndList) {
		this.expenseIndList = expenseIndList;
	}

	public String getPaidBy() {
		return paidBy;
	}

	public void setPaidBy(String paidBy) {
		this.paidBy = paidBy;
	}

	public Boolean getBillable() {
		return billable;
	}

	public void setBillable(Boolean billable) {
		this.billable = billable;
	}

	public BigDecimal getSubmittedamount() {
		return submittedamount;
	}

	public void setSubmittedamount(BigDecimal submittedamount) {
		this.submittedamount = submittedamount;
	}

	public String getAmountchangereason() {
		return amountchangereason;
	}

	public void setAmountchangereason(String amountchangereason) {
		this.amountchangereason = amountchangereason;
	}

	
	
	
	
}
