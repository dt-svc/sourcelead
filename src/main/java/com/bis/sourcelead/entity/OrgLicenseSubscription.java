package com.bis.sourcelead.entity;

import java.io.Serializable;
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

import com.bis.sourcelead.dto.TabsDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "ORG_LICENSE_SUBSCRIPTION")
public class OrgLicenseSubscription implements Serializable{


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_ID")
    private Org org;
	
	@Column(name = "COUNTRY")
	private String country;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUBSCRIPTION_TYPE")
    private OrgLicenseSubscriptionTypes subscriptionType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_SUBSCRIPTION_TYPE")
    private OrgLicenseSubscriptionTypes userSubscriptionType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STORAGE_SUBSCRIPTION_TYPE")
    private OrgLicenseSubscriptionTypes storageSubscriptionType;
	
	@Column(name = "CURRENCY")
	private String currency;
	
	@Column(name = "INVOICE_AMOUNT")
	private Double invoiceAmount;
	
	@Column(name = "SUBSCRIPTION_TABS", columnDefinition = "LONGTEXT default NULL")
	private String subscriptionTabs;
	
	@Column(name = "FROM_DATE")
	private Date fromDate;
	
	@Column(name = "TO_DATE")
	private Date toDate;
	
	@ManyToOne
	@JoinColumn(name = "TIMEZONE_ID")
    private Timezone timezone;
	
	@Column(name = "IS_INVOICE_AMOUNT_PAID")
	private String isInvoiceAmountPaid;
	
	
	@Column(name = "UUID")
	private String uuid;
	
	@Column(name = "STATUS")
	private Integer status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_BY")
    private User createdBy;
	
    @Column(name = "CREATED_DATE")
	private Date createdDate;
	
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UPDATED_BY")
    private User updatedBy;
    
    @Column(name = "UPDATED_DATE")
	private Date updatedDate;
    
    @Transient
    @JsonProperty
    private List<String> modulesList;
    
    @Transient
    @JsonProperty
    private String fromDateStr;
    
    @Transient
    @JsonProperty
    private String toDateStr;
    
    @Transient
    @JsonProperty
    private String showRenewal;
    
    
    @Transient
    @JsonProperty
    private List<Long> tabIdsList;
    
    @Transient
    @JsonProperty
    private List<TabsDTO> tabsDTOList;
    
    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}


	public OrgLicenseSubscriptionTypes getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(OrgLicenseSubscriptionTypes subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	public OrgLicenseSubscriptionTypes getUserSubscriptionType() {
		return userSubscriptionType;
	}

	public void setUserSubscriptionType(OrgLicenseSubscriptionTypes userSubscriptionType) {
		this.userSubscriptionType = userSubscriptionType;
	}

	public OrgLicenseSubscriptionTypes getStorageSubscriptionType() {
		return storageSubscriptionType;
	}

	public void setStorageSubscriptionType(OrgLicenseSubscriptionTypes storageSubscriptionType) {
		this.storageSubscriptionType = storageSubscriptionType;
	}

	public Double getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public String getSubscriptionTabs() {
		return subscriptionTabs;
	}

	public void setSubscriptionTabs(String subscriptionTabs) {
		this.subscriptionTabs = subscriptionTabs;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Timezone getTimezone() {
		return timezone;
	}

	public void setTimezone(Timezone timezone) {
		this.timezone = timezone;
	}

	public String getIsInvoiceAmountPaid() {
		return isInvoiceAmountPaid;
	}

	public void setIsInvoiceAmountPaid(String isInvoiceAmountPaid) {
		this.isInvoiceAmountPaid = isInvoiceAmountPaid;
	}

	public List<String> getModulesList() {
		return modulesList;
	}

	public void setModulesList(List<String> modulesList) {
		this.modulesList = modulesList;
	}

	public String getFromDateStr() {
		return fromDateStr;
	}

	public void setFromDateStr(String fromDateStr) {
		this.fromDateStr = fromDateStr;
	}

	public String getToDateStr() {
		return toDateStr;
	}

	public void setToDateStr(String toDateStr) {
		this.toDateStr = toDateStr;
	}

	public String getShowRenewal() {
		return showRenewal;
	}

	public void setShowRenewal(String showRenewal) {
		this.showRenewal = showRenewal;
	}

	public List<Long> getTabIdsList() {
		return tabIdsList;
	}

	public void setTabIdsList(List<Long> tabIdsList) {
		this.tabIdsList = tabIdsList;
	}

	public List<TabsDTO> getTabsDTOList() {
		return tabsDTOList;
	}

	public void setTabsDTOList(List<TabsDTO> tabsDTOList) {
		this.tabsDTOList = tabsDTOList;
	}

	
}
