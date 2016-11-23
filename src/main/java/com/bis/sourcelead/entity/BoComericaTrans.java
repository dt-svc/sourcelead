package com.bis.sourcelead.entity;

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
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "BO_COMERICA_TRANS", catalog = "sourcelead")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "boComericaTrans")
public class BoComericaTrans {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "TRANS_DATE")
	private Date transDate;
	
	@Column(name = "REFERENCE_NO")
	private String referenceNo;
	
	@Column(name = "TRANS_DESC")
	@Type(type="text")
	private String transDesc;
	
	@Column(name = "AMOUNT")
	private Float amount;
	
	@Column(name = "TRANS_TYPE")
	private String transType;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PAYMENT_TYPE_ID")
	private BoPaymentTypes paymentTypeId;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Transient
	private String financialInstituteName;
	
	@Transient
	private Float creditAmount;
	
	@Transient
	private Float debitAmount;
	
	@Transient
	private Float balanceAmount;
	
	@Transient
	private String transDateStr;
	
	@Transient
	private String identity;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getTransDesc() {
		return transDesc;
	}

	public void setTransDesc(String transDesc) {
		this.transDesc = transDesc;
	}


	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public BoPaymentTypes getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(BoPaymentTypes paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
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

	public String getFinancialInstituteName() {
		return financialInstituteName;
	}

	public void setFinancialInstituteName(String financialInstituteName) {
		this.financialInstituteName = financialInstituteName;
	}

	public Float getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(Float creditAmount) {
		this.creditAmount = creditAmount;
	}

	public Float getDebitAmount() {
		return debitAmount;
	}

	public void setDebitAmount(Float debitAmount) {
		this.debitAmount = debitAmount;
	}

	public Float getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(Float balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public String getTransDateStr() {
		return transDateStr;
	}

	public void setTransDateStr(String transDateStr) {
		this.transDateStr = transDateStr;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}
	
	

}
