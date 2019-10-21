package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.annotations.CleanHtml;

public class OrgRelationDTO extends SearchDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Long userId;
	
	private Long orgLocationId;
	
	private String webSite;
	
	private String orgName;
	
	private String location;
	
	private String country;
	
	private String state;
	
	private String city;
	
	private String zipcode;
	
	private String PaymentTerm;
	
	@CleanHtml(escape=false)
	private String billingAddress;
	
	private String address;
	
	private Long vendorlocationId;
	
	private String vendorOrgLocation;
	
	private String vendorContactFirstName;
	
	private String email;
	
	private String phone;
	
	private String phoneNumber;
	
	private String spocStatus;
	
	private String clientContactIds;
	
	private String firstName;
	
	private String lastName;
	
	private String vendorOnboardTemplateId;
	
	private String level;
	
	private String extensionNumber;
	
	private Long orgRelationId;
	
	
    private String sortColumn;
	
	private String sortOrder;
	 
	private List<Map<String, String>> order;
	 
	private String searchValue;
	
	private String jobCampaignType;
	
	private int start;
	 
	private int length;
	
	private String requirementId;
	
	private String vendorStatus;
	
	private String initiatorLevel;
	
	private String  noOfContatcs;

	private String  stringDate;
	
	private String isLawFirm;
	
//	private String isBackgroundCheck;
	
	private String isAccountingFirm;
	
	private String noOfNonReleasedContacts;
	
	private BigInteger clientLocationId;
	
	private BigInteger vendorLocationId;
	
	private String clientName;
	
	private BigInteger orgRelId;
	
	private String vendorName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getPaymentTerm() {
		return PaymentTerm;
	}

	public void setPaymentTerm(String paymentTerm) {
		PaymentTerm = paymentTerm;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getVendorlocationId() {
		return vendorlocationId;
	}

	public void setVendorlocationId(Long vendorlocationId) {
		this.vendorlocationId = vendorlocationId;
	}

	public String getVendorOrgLocation() {
		return vendorOrgLocation;
	}

	public void setVendorOrgLocation(String vendorOrgLocation) {
		this.vendorOrgLocation = vendorOrgLocation;
	}

	public String getVendorContactFirstName() {
		return vendorContactFirstName;
	}

	public void setVendorContactFirstName(String vendorContactFirstName) {
		this.vendorContactFirstName = vendorContactFirstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getClientContactIds() {
		return clientContactIds;
	}

	public void setClientContactIds(String clientContactIds) {
		this.clientContactIds = clientContactIds;
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

	public String getVendorOnboardTemplateId() {
		return vendorOnboardTemplateId;
	}

	public void setVendorOnboardTemplateId(String vendorOnboardTemplateId) {
		this.vendorOnboardTemplateId = vendorOnboardTemplateId;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getExtensionNumber() {
		return extensionNumber;
	}

	public void setExtensionNumber(String extensionNumber) {
		this.extensionNumber = extensionNumber;
	}

	public Long getOrgRelationId() {
		return orgRelationId;
	}

	public void setOrgRelationId(Long orgRelationId) {
		this.orgRelationId = orgRelationId;
	}


	public String getSortColumn()
	{
		return sortColumn;
	}

	public void setSortColumn(String sortColumn)
	{
		this.sortColumn = sortColumn;
	}

	public String getSortOrder()
	{
		return sortOrder;
	}

	public void setSortOrder(String sortOrder)
	{
		this.sortOrder = sortOrder;
	}

	public List<Map<String, String>> getOrder()
	{
		return order;
	}

	public void setOrder(List<Map<String, String>> order)
	{
		this.order = order;
	}

	public String getSearchValue()
	{
		return searchValue;
	}

	public void setSearchValue(String searchValue)
	{
		this.searchValue = searchValue;
	}

	public String getJobCampaignType()
	{
		return jobCampaignType;
	}

	public void setJobCampaignType(String jobCampaignType)
	{
		this.jobCampaignType = jobCampaignType;
	}

	public int getStart()
	{
		return start;
	}

	public void setStart(int start)
	{
		this.start = start;
	}

	public int getLength()
	{
		return length;
	}

	public void setLength(int length)
	{
		this.length = length;
	}
	
	public String getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(String requirementId) {
		this.requirementId = requirementId;
	}

	public String getVendorStatus() {
		return vendorStatus;
	}

	public void setVendorStatus(String vendorStatus) {
		this.vendorStatus = vendorStatus;
	}

	public String getInitiatorLevel() {
		return initiatorLevel;
	}

	public void setInitiatorLevel(String initiatorLevel) {
		this.initiatorLevel = initiatorLevel;
	}

	public String getNoOfContatcs() {
		return noOfContatcs;
	}

	public void setNoOfContatcs(String noOfContatcs) {
		this.noOfContatcs = noOfContatcs;
	}

	public String getStringDate() {
		return stringDate;
	}

	public void setStringDate(String stringDate) {
		this.stringDate = stringDate;
	}

	public String getNoOfNonReleasedContacts() {
		return noOfNonReleasedContacts;
	}

	public void setNoOfNonReleasedContacts(String noOfNonReleasedContacts) {
		this.noOfNonReleasedContacts = noOfNonReleasedContacts;
	}

	public String getIsLawFirm() {
		return isLawFirm;
	}

	public void setIsLawFirm(String isLawFirm) {
		this.isLawFirm = isLawFirm;
	}

//	public String getIsBackgroundCheck() {
//		return isBackgroundCheck;
//	}
//
//	public void setIsBackgroundCheck(String isBackgroundCheck) {
//		this.isBackgroundCheck = isBackgroundCheck;
//	}

	public String getIsAccountingFirm() {
		return isAccountingFirm;
	}

	public void setIsAccountingFirm(String isAccountingFirm) {
		this.isAccountingFirm = isAccountingFirm;
	}

	public BigInteger getClientLocationId() {
		return clientLocationId;
	}

	public void setClientLocationId(BigInteger clientLocationId) {
		this.clientLocationId = clientLocationId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public BigInteger getOrgRelId() {
		return orgRelId;
	}

	public void setOrgRelId(BigInteger orgRelId) {
		this.orgRelId = orgRelId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public BigInteger getVendorLocationId() {
		return vendorLocationId;
	}

	public void setVendorLocationId(BigInteger vendorLocationId) {
		this.vendorLocationId = vendorLocationId;
	}

}
