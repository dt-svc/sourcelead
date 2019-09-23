package com.bis.sourcelead.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "CREDIT_DEBIT_LINE_ITEMS")
public class CreditDebitLineItems {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "INLINE_DESCRIPTION",columnDefinition = "TEXT default NULL")
	private String inlineDescription;
	
	@ManyToOne
	@JoinColumn(name = "ASSOCIATE_ID", nullable = false)
	private User associate;
	
	@ManyToOne
	@JoinColumn(name = "CREDIT_DEBIT_NOTE_ID", nullable = false)
	private CreditDebitNote creditDebitNote;
	
	@ManyToOne
	@JoinColumn(name = "ORG_LOCATIONS_ID", nullable = false)
	private OrgLocations orgLocation;
	
	@ManyToOne
    @JoinColumn(name="PAYMENT_ACCOUNT_ID", nullable=false )     
	private PaymentAccount paymentAccount;
	
	@Column(name = "PRICE")
	private BigDecimal price;
	
	@Column(name = "LINE_ITEM_DATE")
	private Date lineItemDate;
	
	@Column(name = "AMOUNT")
	private BigDecimal amount;
	
	@Column(name = "TOTAL_AMOUNT")
	private BigDecimal totalAmount;

	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "TAX_TYPE")
	private String taxType;
	
	@Column(name = "TAX_AMOUNT")
	private BigDecimal taxAmount;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@ManyToOne
	@JoinColumn(name = "CREATED_BY")
	private User createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@ManyToOne
	@JoinColumn(name = "UPDATED_BY")
	private User updatedBy;
	
	@Column(name = "QUANTITY")
	private String quantity;
	
	@Transient
	@JsonProperty
	private String lineItemDateStr;
	
	@Transient
	@JsonProperty
	private String associateName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInlineDescription() {
		return inlineDescription;
	}

	public void setInlineDescription(String inlineDescription) {
		this.inlineDescription = inlineDescription;
	}

	

	public User getAssociate() {
		return associate;
	}

	public void setAssociate(User associate) {
		this.associate = associate;
	}

	public CreditDebitNote getCreditDebitNote() {
		return creditDebitNote;
	}

	public void setCreditDebitNote(CreditDebitNote creditDebitNote) {
		this.creditDebitNote = creditDebitNote;
	}

	public OrgLocations getOrgLocation() {
		return orgLocation;
	}

	public void setOrgLocation(OrgLocations orgLocation) {
		this.orgLocation = orgLocation;
	}

	
	

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Date getLineItemDate() {
		return lineItemDate;
	}

	public void setLineItemDate(Date lineItemDate) {
		this.lineItemDate = lineItemDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getTaxType() {
		return taxType;
	}

	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}

	
	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getLineItemDateStr() {
		return lineItemDateStr;
	}

	public void setLineItemDateStr(String lineItemDateStr) {
		this.lineItemDateStr = lineItemDateStr;
	}

	public PaymentAccount getPaymentAccount() {
		return paymentAccount;
	}

	public void setPaymentAccount(PaymentAccount paymentAccount) {
		this.paymentAccount = paymentAccount;
	}

	public String getAssociateName() {
		return associateName;
	}

	public void setAssociateName(String associateName) {
		this.associateName = associateName;
	}

	
	
		
}
