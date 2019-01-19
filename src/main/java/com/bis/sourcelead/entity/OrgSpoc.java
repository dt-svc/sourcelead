package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "ORG_SPOC")
public class OrgSpoc implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_RELATION_ID")
	@JsonBackReference
	private OrgRelations orgRelations;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="VENDOR_LOCATION_ID") 
	@JsonBackReference
    private OrgLocations vendorLocationId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CLIENT_LOCATION_ID")        
    private OrgLocations clientLocationId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "VENDOR_USER_ID")
	private User vendorUser;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLIENT_USER_ID")
	private User clientUser;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OWNER_USER_ID")
	private User ownerUser;
	
	@Column(name = "INVITATION_STATUS")
	private String invitationStatus;
	
	@Column(name = "INVOICE_EMAIL_STATUS")
	private String invoiceEmailStatus;
	
	@Column(name = "BILLING_EMAIL_STATUS")
	private String billingEmailStatus;
	
	@Column(name = "AP_EMAIL_STATUS")
	private String apEmailStatus;
	
	@Column(name = "AR_EMAIL_STATUS")
	private String arEmailStatus;
	
	@Column(name = "SPOC_STATUS")
	private String spocStatus;
	
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
	
	@Column(name = "TERMINATE_REASON", columnDefinition = "TEXT default NULL")
	private String terminateReason;
	
	@Column(name="UUID")
	private String UUID;
	
	@Transient
	@JsonProperty
	private String spocName;
	
	@Transient
	@JsonProperty
	private Long spocUserId;
	
	@Transient
	@JsonProperty
	private String spocUserCode;
	
	@Transient
	@JsonProperty
	private Map<String,String> clientCustomFieldlabelNameAndValue;
	
	public String getVendorCurrency() {
		return vendorCurrency;
	}

	public void setVendorCurrency(String vendorCurrency) {
		this.vendorCurrency = vendorCurrency;
	}

	@Transient
	@JsonProperty
	private String ownerName;
	
	@Transient
	@JsonProperty
	private String spocEmail;
	
	@Transient
	@JsonProperty
	private String spocMobilePhone;
	
	@Transient
	@JsonProperty
	private String spocExtensionNumber;
	
	@Transient
	@JsonProperty
	private String orgName;

	@Transient
	@JsonProperty
	private String ownerUserId;

	@Transient
	@JsonProperty
	private List<User> userList;
	

	@Transient
	@JsonProperty
	private String spocFirstName;
	
	@Transient
	@JsonProperty
	private String spocLastName;

	@Transient
	@JsonProperty
	private Long orgRelationId;

	@Transient
	@JsonProperty
	private String userState;

	@Transient
	@JsonProperty
	private Long userId;
	
	@Transient
	@JsonProperty
	private Long spocLocationId;
	
	@Transient
	@JsonProperty
	private Long tempOwnerId;
	
	@Transient
	@JsonProperty
	private Long tempSpocId;
	
	@Transient
	@JsonProperty
	private String releasedRequirement;
	
	@Transient
	@JsonProperty
	private String clientCurrencyCode;
	
	@Transient
	@JsonProperty
	private String clientRate;
	
	@Transient
	@JsonProperty
	private String clientRateType;
	
	@Transient
	@JsonProperty
	private String msg;
	
	@Transient
	@JsonProperty
	private Integer reqReleasedCount;
	
	@Transient
	@JsonProperty
	private Integer submittedCount;
	
	@Transient
	@JsonProperty
	private Integer shortlistedCount;
	
	@Transient
	@JsonProperty
	private Integer interviewedCount;
	
	@Transient
	@JsonProperty
	private Integer offeredCount;
	
	@Transient
	@JsonProperty
	private Integer rejectedCount;
	
	@Transient
	@JsonProperty
	private Integer backoutCount;
	
	@Transient
	@JsonProperty
	private String ownerUserName;
	
	@Transient
	@JsonProperty
	private String billingAddress;
	
	@Transient
	@JsonProperty
	private String stringDate;


	@Transient
	@JsonProperty
	private String paymentTerms;
	
	@Transient
	@JsonProperty
	private String vendorCurrency;
	
	@Transient
	@JsonProperty
	private String clientCurrency;

	public Long getSpocLocationId() {
		return spocLocationId;
	}

	public void setSpocLocationId(Long spocLocationId) {
		this.spocLocationId = spocLocationId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrgRelations getOrgRelations() {
		return orgRelations;
	}

	public void setOrgRelations(OrgRelations orgRelations) {
		this.orgRelations = orgRelations;
	}

	public OrgLocations getVendorLocationId() {
		return vendorLocationId;
	}

	public void setVendorLocationId(OrgLocations vendorLocationId) {
		this.vendorLocationId = vendorLocationId;
	}

	public OrgLocations getClientLocationId() {
		return clientLocationId;
	}

	public void setClientLocationId(OrgLocations clientLocationId) {
		this.clientLocationId = clientLocationId;
	}

	public User getVendorUser() {
		return vendorUser;
	}

	public void setVendorUser(User vendorUser) {
		this.vendorUser = vendorUser;
	}

	public User getClientUser() {
		return clientUser;
	}

	public void setClientUser(User clientUser) {
		this.clientUser = clientUser;
	}

	public User getOwnerUser() {
		return ownerUser;
	}

	public void setOwnerUser(User ownerUser) {
		this.ownerUser = ownerUser;
	}

	public String getInvitationStatus() {
		return invitationStatus;
	}

	public void setInvitationStatus(String invitationStatus) {
		this.invitationStatus = invitationStatus;
	}

	public String getSpocStatus() {
		return spocStatus;
	}

	public void setSpocStatus(String spocStatus) {
		this.spocStatus = spocStatus;
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

	public String getSpocName() {
		return spocName;
	}

	public void setSpocName(String spocName) {
		this.spocName = spocName;
	}
	
	public Long getSpocUserId() {
		return spocUserId;
	}

	public void setSpocUserId(Long spocUserId) {
		this.spocUserId = spocUserId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getSpocEmail() {
		return spocEmail;
	}

	public void setSpocEmail(String spocEmail) {
		this.spocEmail = spocEmail;
	}

	public String getSpocMobilePhone() {
		return spocMobilePhone;
	}

	public void setSpocMobilePhone(String spocMobilePhone) {
		this.spocMobilePhone = spocMobilePhone;
	}

	public String getTerminateReason() {
		return terminateReason;
	}

	public void setTerminateReason(String terminateReason) {
		this.terminateReason = terminateReason;
	}

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}

	public String getOwnerUserId() {
		return ownerUserId;
	}

	public void setOwnerUserId(String ownerUserId) {
		this.ownerUserId = ownerUserId;
	}

	public String getSpocFirstName() {
		return spocFirstName;
	}

	public void setSpocFirstName(String spocFirstName) {
		this.spocFirstName = spocFirstName;
	}

	public String getSpocLastName() {
		return spocLastName;
	}

	public void setSpocLastName(String spocLastName) {
		this.spocLastName = spocLastName;
	}

	public Long getOrgRelationId() {
		return orgRelationId;
	}

	public void setOrgRelationId(Long orgRelationId) {
		this.orgRelationId = orgRelationId;
	}
	public String getUserState() {
		return userState;
	}

	public void setUserState(String userState) {
		this.userState = userState;
	}

	public Long getTempOwnerId() {
		return tempOwnerId;
	}

	public void setTempOwnerId(Long tempOwnerId) {
		this.tempOwnerId = tempOwnerId;
	}

	public Long getTempSpocId() {
		return tempSpocId;
	}

	public void setTempSpocId(Long tempSpocId) {
		this.tempSpocId = tempSpocId;
	}

	public String getReleasedRequirement() {
		return releasedRequirement;
	}

	public void setReleasedRequirement(String releasedRequirement) {
		this.releasedRequirement = releasedRequirement;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getClientCurrencyCode() {
		return clientCurrencyCode;
	}

	public void setClientCurrencyCode(String clientCurrencyCode) {
		this.clientCurrencyCode = clientCurrencyCode;
	}

	public String getClientRate() {
		return clientRate;
	}

	public void setClientRate(String clientRate) {
		this.clientRate = clientRate;
	}

	public String getClientRateType() {
		return clientRateType;
	}

	public void setClientRateType(String clientRateType) {
		this.clientRateType = clientRateType;
	}

	public Integer getReqReleasedCount() {
		return reqReleasedCount;
	}

	public void setReqReleasedCount(Integer reqReleasedCount) {
		this.reqReleasedCount = reqReleasedCount;
	}

	public Integer getSubmittedCount() {
		return submittedCount;
	}

	public void setSubmittedCount(Integer submittedCount) {
		this.submittedCount = submittedCount;
	}

	public Integer getShortlistedCount() {
		return shortlistedCount;
	}

	public void setShortlistedCount(Integer shortlistedCount) {
		this.shortlistedCount = shortlistedCount;
	}

	public Integer getInterviewedCount() {
		return interviewedCount;
	}

	public void setInterviewedCount(Integer interviewedCount) {
		this.interviewedCount = interviewedCount;
	}

	public Integer getOfferedCount() {
		return offeredCount;
	}

	public void setOfferedCount(Integer offeredCount) {
		this.offeredCount = offeredCount;
	}

	public Integer getRejectedCount() {
		return rejectedCount;
	}

	public void setRejectedCount(Integer rejectedCount) {
		this.rejectedCount = rejectedCount;
	}

	public Integer getBackoutCount() {
		return backoutCount;
	}

	public void setBackoutCount(Integer backoutCount) {
		this.backoutCount = backoutCount;
	}

	public String getOwnerUserName() {
		return ownerUserName;
	}

	public void setOwnerUserName(String ownerUserName) {
		this.ownerUserName = ownerUserName;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public String getStringDate()
	{
		return stringDate;
	}

	public void setStringDate(String stringDate)
	{
		this.stringDate = stringDate;
	}

	public String getClientCurrency() {
		return clientCurrency;
	}

	public void setClientCurrency(String clientCurrency) {
		this.clientCurrency = clientCurrency;
	}

	public String getSpocUserCode()
	{
		return spocUserCode;
	}

	public void setSpocUserCode(String spocUserCode)
	{
		this.spocUserCode = spocUserCode;
	}

	public String getSpocExtensionNumber()
	{
		return spocExtensionNumber;
	}

	public void setSpocExtensionNumber(String spocExtensionNumber)
	{
		this.spocExtensionNumber = spocExtensionNumber;
	}

	public Map<String, String> getClientCustomFieldlabelNameAndValue() {
		return clientCustomFieldlabelNameAndValue;
	}

	public void setClientCustomFieldlabelNameAndValue(Map<String, String> clientCustomFieldlabelNameAndValue) {
		this.clientCustomFieldlabelNameAndValue = clientCustomFieldlabelNameAndValue;
	}

	public String getInvoiceEmailStatus() {
		return invoiceEmailStatus;
	}

	public void setInvoiceEmailStatus(String invoiceEmailStatus) {
		this.invoiceEmailStatus = invoiceEmailStatus;
	}

	public String getBillingEmailStatus() {
		return billingEmailStatus;
	}

	public void setBillingEmailStatus(String billingEmailStatus) {
		this.billingEmailStatus = billingEmailStatus;
	}

	public String getApEmailStatus() {
		return apEmailStatus;
	}

	public void setApEmailStatus(String apEmailStatus) {
		this.apEmailStatus = apEmailStatus;
	}

	public String getArEmailStatus() {
		return arEmailStatus;
	}

	public void setArEmailStatus(String arEmailStatus) {
		this.arEmailStatus = arEmailStatus;
	}
	
}
