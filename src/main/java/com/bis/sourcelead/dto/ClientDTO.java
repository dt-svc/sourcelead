package com.bis.sourcelead.dto;

import java.util.List;

import com.bis.sourcelead.annotations.CleanHtml;
import com.bis.sourcelead.annotations.Email;
import com.bis.sourcelead.annotations.Required;

public class ClientDTO {
	
	@Required(desc = "client name")
	private String orgName;
	
	private String orgLocationId;
	
	private String webSite;

	private String companyWebsite;
	
	@Required(desc = "location")
	private String location;
	
	private String orgLocation;
	
	private String orgCountry;
	
	private String country;
	
	private String orgState;
	
	private String state;
	
	private String orgCity;
	
	private String city;
	
	private String zipcode;
	
	private String orgZipcode;
	
	private String latitude;
	
	private String longitude;
	
	private String level;
	
	private String address;
	
	@CleanHtml(escape=false)
	private String billingAddress;
	
	private String paymentTerms;
	
	private String type;
	
	@Required(desc = "email")
	@Email
	private String email;
	
	@Required(desc = "first name")
	private String firstName;
	
	private String lastName;
	
	private String phoneNumber;
	
	private String extensionNumber;
	
	private String spocStatus;
	
	private List<String> owner;
	
	private List<String> templateId;
	
	private String OwnerUserId;
	
	private String existingSpocUserId;
	
	private String isMailSent;
	
	private String invoiceOrBillSent;
	
	private String apOrArSent;
	
	private String userRoleText;
	
	private String roleTypeId;
	
	private String responderType;
	
	private List<String> values;
	
	private List<String> orgNameAdvanced;
	
	private List<String> createdByAdvanced;
	
	private List<String> tierAdvanced;
	
	private List<String> clientIds;
	
	private String status;
	
	private List<String> invitationStatus;
	
	private String vendorName;
	
	private String reportingManager;
	
	private AddUserDTO addUserDTO;
	
	private CandidateDTO candidateDTO;
	
	private String isLawFirm;
	
	private String isAccountingFirm;
	
	private String generateBill;
	
	private String generateInvoice;
	
	public String getClientSaveFromType()
	{
		return clientSaveFromType;
	}
	public void setClientSaveFromType(String clientSaveFromType)
	{
		this.clientSaveFromType = clientSaveFromType;
	}
	private String UUID;
	
	private String clientSaveFromType;
	
	private List<CustomFormFieldDTO> customFormData;
	
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
	public String getCompanyWebsite() {
		return companyWebsite;
	}
	public void setCompanyWebsite(String companyWebsite) {
		this.companyWebsite = companyWebsite;
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getOrgState() {
		return orgState;
	}
	public void setOrgState(String orgState) {
		this.orgState = orgState;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getOrgCity() {
		return orgCity;
	}
	public void setOrgCity(String orgCity) {
		this.orgCity = orgCity;
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

	public String getPaymentTerms()
	{
		return paymentTerms;
	}
	public void setPaymentTerms(String paymentTerms)
	{
		this.paymentTerms = paymentTerms;
	}
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
	
	public String getExtensionNumber()
	{
		return extensionNumber;
	}
	public void setExtensionNumber(String extensionNumber)
	{
		this.extensionNumber = extensionNumber;
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
	public String getOwnerUserId() {
		return OwnerUserId;
	}
	public void setOwnerUserId(String ownerUserId) {
		OwnerUserId = ownerUserId;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<String> getInvitationStatus() {
		return invitationStatus;
	}
	public void setInvitationStatus(List<String> invitationStatus) {
		this.invitationStatus = invitationStatus;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getReportingManager() {
		return reportingManager;
	}
	public void setReportingManager(String reportingManager) {
		this.reportingManager = reportingManager;
	}
	public AddUserDTO getAddUserDTO() {
		return addUserDTO;
	}
	public void setAddUserDTO(AddUserDTO addUserDTO) {
		this.addUserDTO = addUserDTO;
	}
	public CandidateDTO getCandidateDTO() {
		return candidateDTO;
	}
	public void setCandidateDTO(CandidateDTO candidateDTO) {
		this.candidateDTO = candidateDTO;
	}
	public String getExistingSpocUserId() {
		return existingSpocUserId;
	}
	public void setExistingSpocUserId(String existingSpocUserId) {
		this.existingSpocUserId = existingSpocUserId;
	}
	public List<String> getTemplateId() {
		return templateId;
	}
	public void setTemplateId(List<String> templateId) {
		this.templateId = templateId;
	}
	public String getUUID() {
		return UUID;
	}
	public void setUUID(String uUID) {
		UUID = uUID;
	}
	public List<CustomFormFieldDTO> getCustomFormData() {
		return customFormData;
	}
	public void setCustomFormData(List<CustomFormFieldDTO> customFormData) {
		this.customFormData = customFormData;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIsLawFirm() {
		return isLawFirm;
	}
	public void setIsLawFirm(String isLawFirm) {
		this.isLawFirm = isLawFirm;
	}
	public String getIsAccountingFirm() {
		return isAccountingFirm;
	}
	public void setIsAccountingFirm(String isAccountingFirm) {
		this.isAccountingFirm = isAccountingFirm;
	}
	public String getInvoiceOrBillSent() {
		return invoiceOrBillSent;
	}
	public void setInvoiceOrBillSent(String invoiceOrBillSent) {
		this.invoiceOrBillSent = invoiceOrBillSent;
	}
	public String getApOrArSent() {
		return apOrArSent;
	}
	public void setApOrArSent(String apOrArSent) {
		this.apOrArSent = apOrArSent;
	}
	public String getGenerateBill() {
		return generateBill;
	}
	public void setGenerateBill(String generateBill) {
		this.generateBill = generateBill;
	}
	public String getGenerateInvoice() {
		return generateInvoice;
	}
	public void setGenerateInvoice(String generateInvoice) {
		this.generateInvoice = generateInvoice;
	}
	
	
}
