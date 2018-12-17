package com.bis.sourcelead.payroll.entity;

import java.io.Serializable;
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

import com.bis.sourcelead.entity.OrgLocations;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "INVOICE_DATA")
public class InvoiceData implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_LOCATION_ID", nullable = false)
	private OrgLocations orgLocationId;
	
	@Column(name = "SKU")
	private String sku;
	
	@Column(name = "PRODUCT_SERVICE")
	private String productService;
	
	@Column(name = "EMP_NAME")
	private String empName;
	
	@Column(name = "NUMBER")
	private String number;
	
	@Column(name = "MEMO_DESCRIPTION", columnDefinition = "TEXT default NULL")
	private String memoDescription;
	
	@Column(name = "DATE")
	private Date date;
	
	@Column(name = "LAST_MODIFIED")
	private Date lastmodified;
	
	@Column(name = "DUE_DATE")
	private Date dueDate;
	
	@Column(name = "RATE")
	private String rate;
	
	@Column(name = "QTY")
	private String qty;
	
	@Column(name = "AMOUNT")
	private String amount;
	
	@Column(name = "AR_PAID")
	private String arPaid;
	
	@Column(name = "OLD_NEW")
	private String oldNew;
	
	@Column(name = "FROM_DATE")
	private Date fromDate;
	
	@Column(name = "TO_DATE")
	private Date 	toDate;

	@Column(name = "BATCH_NUMBER")
	private String batchNumber;
	
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
	
	@Column(name = "VERSION")
	private int version;
	
	@Column(name = "PAY_RATE")
	private String payRate;
	
	@Column(name = "UUID")
	private String uuid;
	
	@Transient
	@JsonProperty
	private String dateStr;
	
	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public String getLastModifiedStr() {
		return lastModifiedStr;
	}

	public void setLastModifiedStr(String lastModifiedStr) {
		this.lastModifiedStr = lastModifiedStr;
	}

	public String getDueDateStr() {
		return dueDateStr;
	}

	public void setDueDateStr(String dueDateStr) {
		this.dueDateStr = dueDateStr;
	}

	@Transient
	@JsonProperty
	private String lastModifiedStr;
	
	@Transient
	@JsonProperty
	private String dueDateStr;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getProductService() {
		return productService;
	}

	public void setProductService(String productService) {
		this.productService = productService;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getMemoDescription() {
		return memoDescription;
	}

	public void setMemoDescription(String memoDescription) {
		this.memoDescription = memoDescription;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getLastmodified() {
		return lastmodified;
	}

	public void setLastmodified(Date lastmodified) {
		this.lastmodified = lastmodified;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getArPaid() {
		return arPaid;
	}

	public void setArPaid(String arPaid) {
		this.arPaid = arPaid;
	}

	public String getOldNew() {
		return oldNew;
	}

	public void setOldNew(String oldNew) {
		this.oldNew = oldNew;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getVersion()
	{
		return version;
	}

	public void setVersion(int version)
	{
		this.version = version;
	}

	public String getPayRate()
	{
		return payRate;
	}

	public void setPayRate(String payRate)
	{
		this.payRate = payRate;
	}

	public String getUuid()
	{
		return uuid;
	}

	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}

}
