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
	
	private Long orgLocationId;
	
	private Long spocUserId;
	
	private Long ownerUserId;
	
	private String  spocName;
	
	private Long tempOwnerId;
	
	private Long spocLocationId;
	
	private Long userId;
	
	
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

}
