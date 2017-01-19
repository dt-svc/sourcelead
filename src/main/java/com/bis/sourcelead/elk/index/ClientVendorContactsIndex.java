package com.bis.sourcelead.elk.index;

import java.io.Serializable;
import java.util.ArrayList;

public class ClientVendorContactsIndex implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String orgName;
	
	private String phoneNumber;
	
	private String contactEmail;
	
	private ArrayList<String> ownerName;
	
	private String invitationStatus;
	
	private String contactStatus;
	
	private Long orgRelationId;
	
	private Long orgLocationId;
	
	private Long spocUserId;
	
	private Long ownerUserId;
	
	private String  spocName;
	
	private Long tempOwnerId;
	
	private Long spocLocationId;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
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

	public String getContactStatus() {
		return contactStatus;
	}

	public void setContactStatus(String contactStatus) {
		this.contactStatus = contactStatus;
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

	public Long getSpocLocationId() {
		return spocLocationId;
	}

	public void setSpocLocationId(Long spocLocationId) {
		this.spocLocationId = spocLocationId;
	}


}
