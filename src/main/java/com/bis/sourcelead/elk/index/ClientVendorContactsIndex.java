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
	
	private Long initiatorLocationId;
	
	private Long spocUserId;
	
	private String  spocUserFirstName;
	
	private String  spocUserLastName;
	
	private String  spocUserEmail;
	
	private String  spocUserPhNo;
	
	private Long ownerUserId;
	
	private String  ownerUserFirstName;
	
	private String  ownerUserLastName;
	
	private String  ownerUserEmail;
	
	private String  ownerUserPhNo;
	
	private String  spocName;
	
	private String  ownerNames;
	
	private Long tempOwnerId;
	
	private Long responderLocationId;
	
	private Long userId;
	
	private String  initiatorOrgName;
	
	private String  responderOrgName;
    
	private String initiatorType;
	
	private String responderType;
	
	private String status;
	
	private String createdDate;
	
	private String updatedDate;
	
	//dummy
	private Long spocLocationId;
	
	
	
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

	public Long getSpocUserId() {
		return spocUserId;
	}

	public void setSpocUserId(Long spocUserId) {
		this.spocUserId = spocUserId;
	}

	public Long getOwnerUserId() {
		return ownerUserId;
	}

	public void setOwnerUserId(Long ownerUserId) {
		this.ownerUserId = ownerUserId;
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

	public String getSpocUserFirstName() {
		return spocUserFirstName;
	}

	public void setSpocUserFirstName(String spocUserFirstName) {
		this.spocUserFirstName = spocUserFirstName;
	}

	public String getSpocUserLastName() {
		return spocUserLastName;
	}

	public void setSpocUserLastName(String spocUserLastName) {
		this.spocUserLastName = spocUserLastName;
	}

	public String getOwnerUserFirstName() {
		return ownerUserFirstName;
	}

	public void setOwnerUserFirstName(String ownerUserFirstName) {
		this.ownerUserFirstName = ownerUserFirstName;
	}

	public String getOwnerUserLastName() {
		return ownerUserLastName;
	}

	public void setOwnerUserLastName(String ownerUserLastName) {
		this.ownerUserLastName = ownerUserLastName;
	}

	public String getOwnerNames() {
		return ownerNames;
	}

	public void setOwnerNames(String ownerNames) {
		this.ownerNames = ownerNames;
	}

	public String getSpocUserEmail() {
		return spocUserEmail;
	}

	public void setSpocUserEmail(String spocUserEmail) {
		this.spocUserEmail = spocUserEmail;
	}

	public String getSpocUserPhNo() {
		return spocUserPhNo;
	}

	public void setSpocUserPhNo(String spocUserPhNo) {
		this.spocUserPhNo = spocUserPhNo;
	}

	public String getOwnerUserEmail() {
		return ownerUserEmail;
	}

	public void setOwnerUserEmail(String ownerUserEmail) {
		this.ownerUserEmail = ownerUserEmail;
	}

	public String getOwnerUserPhNo() {
		return ownerUserPhNo;
	}

	public void setOwnerUserPhNo(String ownerUserPhNo) {
		this.ownerUserPhNo = ownerUserPhNo;
	}

	public String getInitiatorOrgName() {
		return initiatorOrgName;
	}

	public void setInitiatorOrgName(String initiatorOrgName) {
		this.initiatorOrgName = initiatorOrgName;
	}

	public String getResponderOrgName() {
		return responderOrgName;
	}

	public void setResponderOrgName(String responderOrgName) {
		this.responderOrgName = responderOrgName;
	}

	public Long getInitiatorLocationId() {
		return initiatorLocationId;
	}

	public void setInitiatorLocationId(Long initiatorLocationId) {
		this.initiatorLocationId = initiatorLocationId;
	}

	public Long getResponderLocationId() {
		return responderLocationId;
	}

	public void setResponderLocationId(Long responderLocationId) {
		this.responderLocationId = responderLocationId;
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

	
}
