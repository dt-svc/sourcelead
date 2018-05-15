package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import com.bis.sourcelead.annotations.CleanHtml;
import com.bis.sourcelead.entity.User;

public class OrgSpocDTO extends SearchDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private long id;
	
	private long orgRelations;
	
    private long vendorLocationId;
	
    private long clientLocationId;
	
	private long vendorUser;
	
	private long clientUser;
	
	private long ownerUser;
	
	private String invitationStatus;
	
	private String spocStatus;
	
	private String status;
	
	private Date createdDate;
	
	private String createdBy;
	
	private Date updatedDate;
	
	private String updatedBy;
	
	private String terminateReason;
	
	private String UUID;
	
	private String spocName;
	
	private Long spocUserId;
	
	private String ownerName;
	
	private String spocEmail;
	
	private String spocMobilePhone;
	
	private String orgName;

	private String ownerUserId;

	private List<User> userList;
	

	private String spocFirstName;
	
	private String spocLastName;

	private Long orgRelationId;

	private String userState;

	private Long userId;
	
	private Long spocLocationId;
	
	private Long tempOwnerId;
	
	private Long tempSpocId;
	
	private String releasedRequirement;
	
	private String clientCurrencyCode;
	
	private String clientRate;
	
	private String clientRateType;
	
	private String msg;
	
	private Integer reqReleasedCount;
	
	private Integer submittedCount;
	
	private Integer shortlistedCount;
	
	private Integer interviewedCount;
	
	@Transient
	private Integer offeredCount;
	
	private Integer rejectedCount;
	
	private Integer backoutCount;
	
	private String ownerUserName;
	
	@CleanHtml(escape=false)
	private String billingAddress;
	
	private String stringDate;


	private String paymentTerms;
	
	private String vendorCurrency;
	
	private String clientCurrency;
	
	private String requirementId;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public long getOrgRelations()
	{
		return orgRelations;
	}

	public void setOrgRelations(long orgRelations)
	{
		this.orgRelations = orgRelations;
	}

	public long getVendorLocationId()
	{
		return vendorLocationId;
	}

	public void setVendorLocationId(long vendorLocationId)
	{
		this.vendorLocationId = vendorLocationId;
	}

	public long getClientLocationId()
	{
		return clientLocationId;
	}

	public void setClientLocationId(long clientLocationId)
	{
		this.clientLocationId = clientLocationId;
	}

	public long getVendorUser()
	{
		return vendorUser;
	}

	public void setVendorUser(long vendorUser)
	{
		this.vendorUser = vendorUser;
	}

	public long getClientUser()
	{
		return clientUser;
	}

	public void setClientUser(long clientUser)
	{
		this.clientUser = clientUser;
	}

	public long getOwnerUser()
	{
		return ownerUser;
	}

	public void setOwnerUser(long ownerUser)
	{
		this.ownerUser = ownerUser;
	}

	public String getInvitationStatus()
	{
		return invitationStatus;
	}

	public void setInvitationStatus(String invitationStatus)
	{
		this.invitationStatus = invitationStatus;
	}

	public String getSpocStatus()
	{
		return spocStatus;
	}

	public void setSpocStatus(String spocStatus)
	{
		this.spocStatus = spocStatus;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public Date getCreatedDate()
	{
		return createdDate;
	}

	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}

	public String getCreatedBy()
	{
		return createdBy;
	}

	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate()
	{
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate)
	{
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy()
	{
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy)
	{
		this.updatedBy = updatedBy;
	}

	public String getTerminateReason()
	{
		return terminateReason;
	}

	public void setTerminateReason(String terminateReason)
	{
		this.terminateReason = terminateReason;
	}

	public String getUUID()
	{
		return UUID;
	}

	public void setUUID(String uUID)
	{
		UUID = uUID;
	}

	public String getSpocName()
	{
		return spocName;
	}

	public void setSpocName(String spocName)
	{
		this.spocName = spocName;
	}

	public Long getSpocUserId()
	{
		return spocUserId;
	}

	public void setSpocUserId(Long spocUserId)
	{
		this.spocUserId = spocUserId;
	}

	public String getOwnerName()
	{
		return ownerName;
	}

	public void setOwnerName(String ownerName)
	{
		this.ownerName = ownerName;
	}

	public String getSpocEmail()
	{
		return spocEmail;
	}

	public void setSpocEmail(String spocEmail)
	{
		this.spocEmail = spocEmail;
	}

	public String getSpocMobilePhone()
	{
		return spocMobilePhone;
	}

	public void setSpocMobilePhone(String spocMobilePhone)
	{
		this.spocMobilePhone = spocMobilePhone;
	}

	public String getOrgName()
	{
		return orgName;
	}

	public void setOrgName(String orgName)
	{
		this.orgName = orgName;
	}

	public String getOwnerUserId()
	{
		return ownerUserId;
	}

	public void setOwnerUserId(String ownerUserId)
	{
		this.ownerUserId = ownerUserId;
	}

	public List<User> getUserList()
	{
		return userList;
	}

	public void setUserList(List<User> userList)
	{
		this.userList = userList;
	}

	public String getSpocFirstName()
	{
		return spocFirstName;
	}

	public void setSpocFirstName(String spocFirstName)
	{
		this.spocFirstName = spocFirstName;
	}

	public String getSpocLastName()
	{
		return spocLastName;
	}

	public void setSpocLastName(String spocLastName)
	{
		this.spocLastName = spocLastName;
	}

	public Long getOrgRelationId()
	{
		return orgRelationId;
	}

	public void setOrgRelationId(Long orgRelationId)
	{
		this.orgRelationId = orgRelationId;
	}

	public String getUserState()
	{
		return userState;
	}

	public void setUserState(String userState)
	{
		this.userState = userState;
	}

	public Long getUserId()
	{
		return userId;
	}

	public void setUserId(Long userId)
	{
		this.userId = userId;
	}

	public Long getSpocLocationId()
	{
		return spocLocationId;
	}

	public void setSpocLocationId(Long spocLocationId)
	{
		this.spocLocationId = spocLocationId;
	}

	public Long getTempOwnerId()
	{
		return tempOwnerId;
	}

	public void setTempOwnerId(Long tempOwnerId)
	{
		this.tempOwnerId = tempOwnerId;
	}

	public Long getTempSpocId()
	{
		return tempSpocId;
	}

	public void setTempSpocId(Long tempSpocId)
	{
		this.tempSpocId = tempSpocId;
	}

	public String getReleasedRequirement()
	{
		return releasedRequirement;
	}

	public void setReleasedRequirement(String releasedRequirement)
	{
		this.releasedRequirement = releasedRequirement;
	}

	public String getClientCurrencyCode()
	{
		return clientCurrencyCode;
	}

	public void setClientCurrencyCode(String clientCurrencyCode)
	{
		this.clientCurrencyCode = clientCurrencyCode;
	}

	public String getClientRate()
	{
		return clientRate;
	}

	public void setClientRate(String clientRate)
	{
		this.clientRate = clientRate;
	}

	public String getClientRateType()
	{
		return clientRateType;
	}

	public void setClientRateType(String clientRateType)
	{
		this.clientRateType = clientRateType;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public Integer getReqReleasedCount()
	{
		return reqReleasedCount;
	}

	public void setReqReleasedCount(Integer reqReleasedCount)
	{
		this.reqReleasedCount = reqReleasedCount;
	}

	public Integer getSubmittedCount()
	{
		return submittedCount;
	}

	public void setSubmittedCount(Integer submittedCount)
	{
		this.submittedCount = submittedCount;
	}

	public Integer getShortlistedCount()
	{
		return shortlistedCount;
	}

	public void setShortlistedCount(Integer shortlistedCount)
	{
		this.shortlistedCount = shortlistedCount;
	}

	public Integer getInterviewedCount()
	{
		return interviewedCount;
	}

	public void setInterviewedCount(Integer interviewedCount)
	{
		this.interviewedCount = interviewedCount;
	}

	public Integer getOfferedCount()
	{
		return offeredCount;
	}

	public void setOfferedCount(Integer offeredCount)
	{
		this.offeredCount = offeredCount;
	}

	public Integer getRejectedCount()
	{
		return rejectedCount;
	}

	public void setRejectedCount(Integer rejectedCount)
	{
		this.rejectedCount = rejectedCount;
	}

	public Integer getBackoutCount()
	{
		return backoutCount;
	}

	public void setBackoutCount(Integer backoutCount)
	{
		this.backoutCount = backoutCount;
	}

	public String getOwnerUserName()
	{
		return ownerUserName;
	}

	public void setOwnerUserName(String ownerUserName)
	{
		this.ownerUserName = ownerUserName;
	}

	public String getBillingAddress()
	{
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress)
	{
		this.billingAddress = billingAddress;
	}

	public String getStringDate()
	{
		return stringDate;
	}

	public void setStringDate(String stringDate)
	{
		this.stringDate = stringDate;
	}

	public String getPaymentTerms()
	{
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms)
	{
		this.paymentTerms = paymentTerms;
	}

	public String getVendorCurrency()
	{
		return vendorCurrency;
	}

	public void setVendorCurrency(String vendorCurrency)
	{
		this.vendorCurrency = vendorCurrency;
	}

	public String getClientCurrency()
	{
		return clientCurrency;
	}

	public void setClientCurrency(String clientCurrency)
	{
		this.clientCurrency = clientCurrency;
	}

	public String getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(String requirementId) {
		this.requirementId = requirementId;
	}
}
