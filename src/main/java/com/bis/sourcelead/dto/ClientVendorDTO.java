package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;
/**
 * DTO foe client and Vendor
 * Date :16-08-1989
 *
 */
public class ClientVendorDTO extends SearchDTO implements Serializable {
	private String orgName;
	private String orgLocationId;
	private String webSite;
	private String companyWebSite;
	private String location;
	private String orgLocation;
	private String orgCountry;
	private String orgState;
	private String orgCity;
	private String zipcode;
	private String orgZipcode;
	private String latitude;
	private String longitude;
	private String level;
	private String address;
	private String billingAddress;
	//private String PaymentTerm;
	private String paymentTerms;
	private String email;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String spocStatus;
	private List<String> owner;
	private String isMailSent;
	private String userRoleText;
	private String roleTypeId;
	private String responderType;
	private List<String> values;
	private List<String> orgNameAdvanced;
	private List<String> createdByAdvanced;
	private List<String> tierAdvanced;
	private List<String> clientIds;
	private List<String> status;
	private List<String> invitationStatus;

	private String UUID;
	private List<String> emailAdvanced;
	private List<String> contactNameAdvanced;

	private AddUserDTO addUserDTO;
	private List<String> phoneNumberAdvanced;
	private List<String> orgAdvanced;
	private List<String> ownerAdvanced;
	private List<String> statusAdvanced;
	private List<String> invitationAdvanced;
	
	public List<String> getOrgAdvanced() {
		return orgAdvanced;
	}

	public void setOrgAdvanced(List<String> orgAdvanced) {
		this.orgAdvanced = orgAdvanced;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(String orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getOrgLocation() {
		return orgLocation;
	}

	public void setOrgLocation(String orgLocation) {
		this.orgLocation = orgLocation;
	}

	public String getOrgCountry() {
		return orgCountry;
	}

	public void setOrgCountry(String orgCountry) {
		this.orgCountry = orgCountry;
	}

	public String getOrgState() {
		return orgState;
	}

	public void setOrgState(String orgState) {
		this.orgState = orgState;
	}

	public String getOrgCity() {
		return orgCity;
	}

	public void setOrgCity(String orgCity) {
		this.orgCity = orgCity;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getOrgZipcode() {
		return orgZipcode;
	}

	public void setOrgZipcode(String orgZipcode) {
		this.orgZipcode = orgZipcode;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	/*public String getPaymentTerm() {
		return PaymentTerm;
	}

	public void setPaymentTerm(String paymentTerm) {
		PaymentTerm = paymentTerm;
	}*/

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSpocStatus() {
		return spocStatus;
	}

	public void setSpocStatus(String spocStatus) {
		this.spocStatus = spocStatus;
	}

	public List<String> getOwner() {
		return owner;
	}

	public void setOwner(List<String> owner) {
		this.owner = owner;
	}

	public String getIsMailSent() {
		return isMailSent;
	}

	public void setIsMailSent(String isMailSent) {
		this.isMailSent = isMailSent;
	}

	public String getUserRoleText() {
		return userRoleText;
	}

	public void setUserRoleText(String userRoleText) {
		this.userRoleText = userRoleText;
	}

	public String getRoleTypeId() {
		return roleTypeId;
	}

	public void setRoleTypeId(String roleTypeId) {
		this.roleTypeId = roleTypeId;
	}

	public String getResponderType() {
		return responderType;
	}

	public void setResponderType(String responderType) {
		this.responderType = responderType;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}

	public List<String> getOrgNameAdvanced() {
		return orgNameAdvanced;
	}

	public void setOrgNameAdvanced(List<String> orgNameAdvanced) {
		this.orgNameAdvanced = orgNameAdvanced;
	}

	public List<String> getCreatedByAdvanced() {
		return createdByAdvanced;
	}

	public void setCreatedByAdvanced(List<String> createdByAdvanced) {
		this.createdByAdvanced = createdByAdvanced;
	}

	public List<String> getTierAdvanced() {
		return tierAdvanced;
	}

	public void setTierAdvanced(List<String> tierAdvanced) {
		this.tierAdvanced = tierAdvanced;
	}

	public List<String> getClientIds() {
		return clientIds;
	}

	public void setClientIds(List<String> clientIds) {
		this.clientIds = clientIds;
	}

	public List<String> getStatus() {
		return status;
	}

	public void setStatus(List<String> status) {
		this.status = status;
	}

	public List<String> getInvitationStatus() {
		return invitationStatus;
	}

	public void setInvitationStatus(List<String> invitationStatus) {
		this.invitationStatus = invitationStatus;
	}

	public AddUserDTO getAddUserDTO() {
		return addUserDTO;
	}

	public void setAddUserDTO(AddUserDTO addUserDTO) {
		this.addUserDTO = addUserDTO;
	}

	public String getCompanyWebSite() {
		return companyWebSite;
	}

	public void setCompanyWebSite(String companyWebSite) {
		this.companyWebSite = companyWebSite;
	}


	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}


	public List<String> getEmailAdvanced() {
		return emailAdvanced;
	}

	public void setEmailAdvanced(List<String> emailAdvanced) {
		this.emailAdvanced = emailAdvanced;
	}

	public List<String> getContactNameAdvanced() {
		return contactNameAdvanced;
	}

	public void setContactNameAdvanced(List<String> contactNameAdvanced) {
		this.contactNameAdvanced = contactNameAdvanced;
	}

	public List<String> getPhoneNumberAdvanced() {
		return phoneNumberAdvanced;
	}

	public void setPhoneNumberAdvanced(List<String> phoneNumberAdvanced) {
		this.phoneNumberAdvanced = phoneNumberAdvanced;
	}

	public List<String> getOwnerAdvanced() {
		return ownerAdvanced;
	}

	public void setOwnerAdvanced(List<String> ownerAdvanced) {
		this.ownerAdvanced = ownerAdvanced;
	}

	public List<String> getStatusAdvanced() {
		return statusAdvanced;
	}

	public void setStatusAdvanced(List<String> statusAdvanced) {
		this.statusAdvanced = statusAdvanced;
	}

	public List<String> getInvitationAdvanced() {
		return invitationAdvanced;
	}

	public void setInvitationAdvanced(List<String> invitationAdvanced) {
		this.invitationAdvanced = invitationAdvanced;
	}

	public String getPaymentTerms()
	{
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms)
	{
		this.paymentTerms = paymentTerms;
	}



}
