package com.bis.sourcelead.elk.index;

import java.io.Serializable;
import java.util.ArrayList;

public class ClientVendorContactsIndex implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String orgName;
	
	private String spocMobilePhone;
	
	private String spocEmail;
	
	private ArrayList<String> ownerName;
	
	private String invitationStatus;
	
	private String spocStatus;
	
	private Long orgRelationId;
	
	private Long vendorLocationId;
	
	private Long spocUserId;
	
	private Long ownerUserId;
	
	private String  clientUserName;
	
	private String  clientUserFirstName;
	
	private String  clientUserLastName;
	
	private String  clientUserEmail;
	
	private Long  clientUserId;
	
	private String  clientUserPhNo;
	
	private String  vendorUserName;
	
	private String  vendorUserFirstName;
	
	private String  vendorUserLastName;
	
	private String  vendorUserEmail;
	
	private Long  vendorUserId;
	
	private String  vendorUserPhNo;
	
	private String  spocName;
	
	private String  ownerNames;
	
	private Long tempOwnerId;
	
	private Long clientLocationId;
	
	private Long userId;
	
	private String  vendorOrgName;
	
	private String  clientOrgName;
    
	private String initiatorType;
	
	private String responderType;
	
	private String status;
	
	private String createdDate;
	
	private String updatedDate;
	
	//dummy
	private Long spocLocationId;
	
	private String action;
	
	private String onboardUUID;
	
	private String notificationUUID;
	

	private String orgSpocUUUID;
	
	private String checkbox;
	
	
	public Long getOwnerUserId() { 

		return ownerUserId;
	}

	public void setOwnerUserId(Long ownerUserId) {
		this.ownerUserId = ownerUserId;
	}

	public String getClientUserPhNo() {
		return clientUserPhNo;
	}

	public void setClientUserPhNo(String clientUserPhNo) {
		this.clientUserPhNo = clientUserPhNo;
	}

	public String getVendorUserPhNo() {
		return vendorUserPhNo;
	}

	public void setVendorUserPhNo(String vendorUserPhNo) {
		this.vendorUserPhNo = vendorUserPhNo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getSpocMobilePhone() {
		return spocMobilePhone;
	}

	public void setSpocMobilePhone(String spocMobilePhone) {
		this.spocMobilePhone = spocMobilePhone;
	}

	public String getSpocEmail() {
		return spocEmail;
	}

	public void setSpocEmail(String spocEmail) {
		this.spocEmail = spocEmail;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ArrayList<String> getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(ArrayList<String> ownerName) {
		this.ownerName = ownerName;
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

	public Long getOrgRelationId() {
		return orgRelationId;
	}

	public void setOrgRelationId(Long orgRelationId) {
		this.orgRelationId = orgRelationId;
	}


	public Long getVendorLocationId() {
		return vendorLocationId;
	}

	public void setVendorLocationId(Long vendorLocationId) {
		this.vendorLocationId = vendorLocationId;
	}

	public Long getSpocUserId() {
		return spocUserId;
	}

	public void setSpocUserId(Long spocUserId) {
		this.spocUserId = spocUserId;
	}

	public Long getClientLocationId() {
		return clientLocationId;
	}

	public void setClientLocationId(Long clientLocationId) {
		this.clientLocationId = clientLocationId;
	}

	public String getSpocName() {
		return spocName;
	}

	public void setSpocName(String spocName) {
		this.spocName = spocName;
	}

	public Long getTempOwnerId() {
		return tempOwnerId;
	}

	public void setTempOwnerId(Long tempOwnerId) {
		this.tempOwnerId = tempOwnerId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getOwnerNames() {
		return ownerNames;
	}

	public void setOwnerNames(String ownerNames) {
		this.ownerNames = ownerNames;
	}

	public String getClientUserFirstName() {
		return clientUserFirstName;
	}

	public void setClientUserFirstName(String clientUserFirstName) {
		this.clientUserFirstName = clientUserFirstName;
	}

	public String getClientUserLastName() {
		return clientUserLastName;
	}

	public void setClientUserLastName(String clientUserLastName) {
		this.clientUserLastName = clientUserLastName;
	}

	public String getClientUserEmail() {
		return clientUserEmail;
	}

	public void setClientUserEmail(String clientUserEmail) {
		this.clientUserEmail = clientUserEmail;
	}

	public Long getClientUserId() {
		return clientUserId;
	}

	public void setClientUserId(Long clientUserId) {
		this.clientUserId = clientUserId;
	}

	public String getVendorUserFirstName() {
		return vendorUserFirstName;
	}

	public void setVendorUserFirstName(String vendorUserFirstName) {
		this.vendorUserFirstName = vendorUserFirstName;
	}

	public String getVendorUserLastName() {
		return vendorUserLastName;
	}

	public void setVendorUserLastName(String vendorUserLastName) {
		this.vendorUserLastName = vendorUserLastName;
	}

	public String getVendorUserEmail() {
		return vendorUserEmail;
	}

	public void setVendorUserEmail(String vendorUserEmail) {
		this.vendorUserEmail = vendorUserEmail;
	}

	public Long getVendorUserId() {
		return vendorUserId;
	}

	public void setVendorUserId(Long vendorUserId) {
		this.vendorUserId = vendorUserId;
	}

	public String getVendorOrgName() {
		return vendorOrgName;
	}

	public void setVendorOrgName(String vendorOrgName) {
		this.vendorOrgName = vendorOrgName;
	}

	public String getClientOrgName() {
		return clientOrgName;
	}

	public void setClientOrgName(String clientOrgName) {
		this.clientOrgName = clientOrgName;
	}

	public String getInitiatorType() {
		return initiatorType;
	}

	public void setInitiatorType(String initiatorType) {
		this.initiatorType = initiatorType;
	}

	public String getResponderType() {
		return responderType;
	}

	public void setResponderType(String responderType) {
		this.responderType = responderType;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getSpocLocationId() {
		return spocLocationId;
	}

	public void setSpocLocationId(Long spocLocationId) {
		this.spocLocationId = spocLocationId;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	

	public String getOnboardUUID() {
		return onboardUUID;
	}

	public void setOnboardUUID(String onboardUUID) {
		this.onboardUUID = onboardUUID;
	}

	public String getNotificationUUID() {
		return notificationUUID;
	}

	public void setNotificationUUID(String notificationUUID) {
		this.notificationUUID = notificationUUID;
	}


	public String getOrgSpocUUUID()
	{
		return orgSpocUUUID;
	}

	public void setOrgSpocUUUID(String orgSpocUUUID)
	{
		this.orgSpocUUUID = orgSpocUUUID;
	}
	
	

	public String getCheckbox()
	{
		return checkbox;
	}

	public void setCheckbox(String checkbox)
	{
		this.checkbox = checkbox;
	}

	public String getClientUserName() {
		return clientUserName;
	}

	public void setClientUserName(String clientUserName) {
		this.clientUserName = clientUserName;
	}

	public String getVendorUserName() {
		return vendorUserName;
	}

	public void setVendorUserName(String vendorUserName) {
		this.vendorUserName = vendorUserName;
	}

	
}
