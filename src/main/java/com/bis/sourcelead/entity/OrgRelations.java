package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="ORG_RELATIONS")
//@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "orgRelations")
public class OrgRelations implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID",nullable = false)
	private Long id;
	
	
	@ManyToOne
    @JoinColumn(name="RESPONDER_LOCATION_ID", nullable=false )   
	@JsonBackReference
    private OrgLocations responderLocationId;
	
	
	@ManyToOne
    @JoinColumn(name="LOCATION_ID", nullable=false )  
	@JsonBackReference
    private OrgLocations locationId;
	
	@Transient
	private Long responderJsonLocationId;
	
	@Transient
	private Long locationJsonId;
    
	@Column(name="INITIATOR_TYPE")
	private String initiatorType;
	
	@Column(name="RESPONDER_TYPE")
	private String responderType;
	
	@Column(name="INITIATOR_LEVEL")
	private String initiatorLevel;
	
	@Column(name="RESPONDER_LEVEL")
	private String responderLevel;
	
	@Column(name = "PAYMENT_TERM")
	private String PaymentTerm;
	
	@Column(name="VENDOR_ONBOARD_TEMPLATE_ID")
	@Type(type="text")
	private String vendorOnboardTemplateId;
	
	@Column(name="VENDOR_RELATION_STATUS")
	private String vendorRelationStatus;
	
	@Column(name="INDUSTRY_NAME")
	private String industryName;
	
	@Column(name="IS_VENDOR_DETAILS_FILLED")
	private String isVendorDetailsFilled;
	
	
	@Column(name="IS_VENDOR_DOCUMENTS_FILLED")
	private String isVendorDocumentsFilled;
	
	
	@Column(name="IS_VENDOR_TASKS_FILLED")
	private String isVendorTasksFilled;
	
	@Column(name="CURRENCY")
	private String currency;
	
	@Column(name="SIC_CODE")
	private String sicCode;
	
	@Column(name="NAICS_CODE")
	private String naicsCode;
	
	@Column(name="EIN_CODE")
	private String einCode;
	
	@Column(name="TAX_REGISTRATION_NUMBER")
	private String taxRegistrationNumber;
	
	@Column(name="DUNS_NUMBER")
	private String dunsNumber;
	
	
    @ManyToOne
    @JoinColumn(name = "BANK_DETAILS_ID")
    private OrgBankDetails bankDetailsId;
	
	@Column(name="UUID")
	private String UUID;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="CREATED_DATE")
	private Date createdDate;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name="UPDATED_BY")
	private String updatedBy;
	

	@Column(name = "IS_LAW_FIRM")
	private String isLawFirm;
	

//	@Column(name = "IS_BACKGROUND_CHECK_FIRM")
//	private String isBackgroundCheck;
	
	
	@Column(name = "IS_ACCOUNTING_FIRM")
	private String isAccountingFirm;

	@Column(name="IS_OFFSHORE_RELATION")
	private String isOffShoreRelation;
	
	@Column(name="GENERATE_INVOICE")
	private String generateInvoice;
	
	@Column(name="GENERATE_BILL")
	private String generateBill;

	
	@Transient
	@JsonProperty
    private String orgName;
	
	@Transient
	@JsonProperty
    private Long immigrationRelatedId;
	
	@Transient
	@JsonProperty
    private String vendorOrgLocation;
	
	@Transient
    private String orgType;
	
	@Transient
	@JsonProperty
    private Long orgid;
	
	@Transient
	@JsonProperty
	private String webSite;
	
	@Transient
	@JsonProperty
	private String country;
	
	@Transient
	@JsonProperty
	private String state;
	
	@Transient
	@JsonProperty
	private String city;
	
	@Transient
	@JsonProperty
	private String location;
	
	@Transient
	@JsonProperty
	private String zipCode;
	
	
	@Transient
	@JsonProperty
	private String phone;
	
	
	@Transient
	@JsonProperty
	private String phoneNumber;
	
	@Transient
	@JsonProperty
	private String email;
	
	@Transient
	@JsonProperty
	private String vendorContactFirstName;
	
	
	@Transient
	@JsonProperty
	private String vendorContactLastName;
	
	@Transient
	@JsonProperty
	private String spocStatus;
	
	@Transient
	private Long projectsCont;
	
	
	@Transient
	@JsonProperty
	private String vendorCreateduserName;
	
	@Transient
	@JsonProperty
	private String poEmail;
	
	@Transient
	@JsonProperty
	private String paymentRemittanceEmail;
	
	@Transient
	@JsonProperty
	private Integer noOfContatcs;
	
	@Transient
	@JsonProperty
	private Integer noOfContatcsForCampaign;
	
	@Transient
	@JsonProperty
	private Integer noOfReleasedJobs;
	
	@Transient
	@JsonProperty
	private Integer noOfNonReleasedContacts;
	
	@Transient
	@JsonProperty
	private String locationOrgname;
	
	@Transient
	@JsonProperty
	private String locationWebsite;
	
	@Transient
	@JsonProperty
	private String locationCountry;
	
	@Transient
	@JsonProperty
	private String locationState;
	
	@Transient
	@JsonProperty
	private String locationCity;
	
	@Transient
	@JsonProperty
	private String responderId;
	
	@Transient
	@JsonProperty
	private String locationID;
	
	@Transient
	@JsonProperty
	private String level;
	
	@Transient
	private String msg;
	
	@Transient
	private List<OrgRelations> matchedDomainClientList;
	
	@Transient
	private List<OrgRelations> matchedClientNameList;
	
	@Transient
	private Integer reqReleasedCount;
	
	@Transient
	private Integer submittedCount;
	
	@Transient
	private Integer shortlistedCount;
	
	@Transient
	private Integer interviewedCount;
	
	@Transient
	private Integer offeredCount;
	
	@Transient
	private Integer rejectedCount;
	
	@Transient
	private Integer backoutCount;
	
	@Transient
	private String vendorName;
	
	@Transient
	@JsonProperty
	private String address;
	
	@Transient
	@JsonProperty
	private String billingAddress;
	
	@Transient
	@JsonProperty
	private String paymentTerms;
	
	@Transient
	private String stringDate;
	
	@Transient
    private Long orgLocationId;
	
	@Transient
	@JsonProperty
	private String clientName;
	

	@Transient
	@JsonProperty
	private String clientContactIds;
	
	
	@JsonBackReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "orgRelations")
	private Set<OrgSpoc> orgSpocList;
	
	 @Transient
	 @JsonProperty
	 private String clientCurrency;
	 
	 
    @Transient
	@JsonProperty
	private String isVendorOnboardSubmitted;
    
    @Transient
	@JsonProperty
    private String accountName;
    
    @Transient
	@JsonProperty
    private String accountNumber;
    
    @Transient
	@JsonProperty
    private String bankName;
    
    @Transient
	@JsonProperty
    private String branchNumber;
	
    @Transient
	@JsonProperty
    private String bankAddress;
    
    @Transient
	@JsonProperty
    private String bankIdentifierCode;
    
    @Transient
	@JsonProperty
    private String vendorNameForList;
    
	public String getStringDate()
	{
		return stringDate;
	}

	public void setStringDate(String stringDate)
	{
		this.stringDate = stringDate;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Long getVendorlocationId() {
		return vendorlocationId;
	}

	public void setVendorlocationId(Long vendorlocationId) {
		this.vendorlocationId = vendorlocationId;
	}

	@Transient
	@JsonProperty
	private Long vendorlocationId;
	
	
	public String getVendorCreateduserName() {
		return vendorCreateduserName;
	}

	public void setVendorCreateduserName(String vendorCreateduserName) {
		this.vendorCreateduserName = vendorCreateduserName;
	}

	public Long getProjectsCont() {
		return projectsCont;
	}

	public void setProjectsCont(Long projectsCont) {
		this.projectsCont = projectsCont;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrgLocations getResponderLocationId() {
		return responderLocationId;
	}

	public void setResponderLocationId(OrgLocations responderLocationId) {
		this.responderLocationId = responderLocationId;
	}

	public OrgLocations getLocationId() {
		return locationId;
	}

	public void setLocationId(OrgLocations locationId) {
		this.locationId = locationId;
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

	public String getOrgName() {
		return orgName;
	}
	
	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}

	public String getClientName() {
		 if("Vendor".equalsIgnoreCase(initiatorType) && responderLocationId != null){
			 clientName = responderLocationId.getOrgLocationName();
		 }else if("Client".equalsIgnoreCase(initiatorType) && locationId != null){
			 clientName = locationId.getOrgLocationName();
		 }
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
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

	

	public String getInitiatorLevel() {
		return initiatorLevel;
	}

	public void setInitiatorLevel(String initiatorLevel) {
		this.initiatorLevel = initiatorLevel;
	}

	public String getResponderLevel() {
		return responderLevel;
	}

	public void setResponderLevel(String responderLevel) {
		this.responderLevel = responderLevel;
	}

	public Integer getNoOfContatcs() {
		return noOfContatcs;
	}

	public void setNoOfContatcs(Integer noOfContatcs) {
		this.noOfContatcs = noOfContatcs;
	}

	public Integer getNoOfNonReleasedContacts() {
		return noOfNonReleasedContacts;
	}

	public void setNoOfNonReleasedContacts(Integer noOfNonReleasedContacts) {
		this.noOfNonReleasedContacts = noOfNonReleasedContacts;
	}

	public String getLocationOrgname() {
		return locationOrgname;
	}

	public void setLocationOrgname(String locationOrgname) {
		this.locationOrgname = locationOrgname;
	}

	public String getLocationWebsite() {
		return locationWebsite;
	}

	public void setLocationWebsite(String locationWebsite) {
		this.locationWebsite = locationWebsite;
	}

	public String getLocationCountry() {
		return locationCountry;
	}

	public void setLocationCountry(String locationCountry) {
		this.locationCountry = locationCountry;
	}

	public String getLocationState() {
		return locationState;
	}

	public Integer getNoOfContatcsForCampaign() {
		return noOfContatcsForCampaign;
	}

	public void setNoOfContatcsForCampaign(Integer noOfContatcsForCampaign) {
		this.noOfContatcsForCampaign = noOfContatcsForCampaign;
	}

	public void setLocationState(String locationState) {
		this.locationState = locationState;
	}

	public String getLocationCity() {
		return locationCity;
	}

	public void setLocationCity(String locationCity) {
		this.locationCity = locationCity;
	}

	public String getResponderId() {
		return responderId;
	}

	public void setResponderId(String responderId) {
		this.responderId = responderId;
	}

	public String getLocationID() {
		return locationID;
	}

	public void setLocationID(String locationID) {
		this.locationID = locationID;
	}

	public Integer getNoOfReleasedJobs() {
		return noOfReleasedJobs;
	}

	public void setNoOfReleasedJobs(Integer noOfReleasedJobs) {
		this.noOfReleasedJobs = noOfReleasedJobs;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<OrgRelations> getMatchedDomainClientList()
	{
		return matchedDomainClientList;
	}

	public void setMatchedDomainClientList(List<OrgRelations> matchedDomainClientList)
	{
		this.matchedDomainClientList = matchedDomainClientList;
	}

	public List<OrgRelations> getMatchedClientNameList()
	{
		return matchedClientNameList;
	}

	public void setMatchedClientNameList(List<OrgRelations> matchedClientNameList)
	{
		this.matchedClientNameList = matchedClientNameList;
	}

	public Integer getReqReleasedCount() {
		return reqReleasedCount;
	}

	public void setReqReleasedCount(Integer reqReleasedCount) {
		this.reqReleasedCount = reqReleasedCount;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
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

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public Long getResponderJsonLocationId() {
		if(responderLocationId != null && responderLocationId.getId() != null){
			responderJsonLocationId = responderLocationId.getId();
		}
		return responderJsonLocationId;
	}

	public Long getLocationJsonId() {
		if(locationId != null && locationId.getId() != null){
			locationJsonId = locationId.getId();
		}
		return locationJsonId;
	}

	public Long getOrgLocationId()
	{
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId)
	{
		this.orgLocationId = orgLocationId;
	}

	public Set<OrgSpoc> getOrgSpocList() {
		return orgSpocList;
	}

	public void setOrgSpocList(Set<OrgSpoc> orgSpocList) {
		this.orgSpocList = orgSpocList;
	}

	public String getClientCurrency() {
		return clientCurrency;
	}

	public void setClientCurrency(String clientCurrency) {
		this.clientCurrency = clientCurrency;
	}

	

	public String getVendorOnboardTemplateId() {
		return vendorOnboardTemplateId;
	}

	public void setVendorOnboardTemplateId(String vendorOnboardTemplateId) {
		this.vendorOnboardTemplateId = vendorOnboardTemplateId;
	}

	public String getVendorRelationStatus() {
		return vendorRelationStatus;
	}

	public void setVendorRelationStatus(String vendorRelationStatus) {
		this.vendorRelationStatus = vendorRelationStatus;
	}

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}

	public String getIsVendorDetailsFilled() {
		return isVendorDetailsFilled;
	}

	public void setIsVendorDetailsFilled(String isVendorDetailsFilled) {
		this.isVendorDetailsFilled = isVendorDetailsFilled;
	}

	public String getIsVendorDocumentsFilled() {
		return isVendorDocumentsFilled;
	}

	public void setIsVendorDocumentsFilled(String isVendorDocumentsFilled) {
		this.isVendorDocumentsFilled = isVendorDocumentsFilled;
	}

	public String getIsVendorTasksFilled() {
		return isVendorTasksFilled;
	}

	public void setIsVendorTasksFilled(String isVendorTasksFilled) {
		this.isVendorTasksFilled = isVendorTasksFilled;
	}

	public String getVendorOrgLocation() {
		return vendorOrgLocation;
	}

	public void setVendorOrgLocation(String vendorOrgLocation) {
		this.vendorOrgLocation = vendorOrgLocation;
	}

	public String getClientContactIds() {
		return clientContactIds;
	}

	public void setClientContactIds(String clientContactIds) {
		this.clientContactIds = clientContactIds;
	}

	public String getVendorContactFirstName() {
		return vendorContactFirstName;
	}

	public void setVendorContactFirstName(String vendorContactFirstName) {
		this.vendorContactFirstName = vendorContactFirstName;
	}

	public String getVendorContactLastName() {
		return vendorContactLastName;
	}

	public void setVendorContactLastName(String vendorContactLastName) {
		this.vendorContactLastName = vendorContactLastName;
	}

	public String getSpocStatus() {
		return spocStatus;
	}

	public void setSpocStatus(String spocStatus) {
		this.spocStatus = spocStatus;
	}

	public String getPaymentTerm() {
		return PaymentTerm;
	}

	public void setPaymentTerm(String paymentTerm) {
		PaymentTerm = paymentTerm;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getSicCode() {
		return sicCode;
	}

	public void setSicCode(String sicCode) {
		this.sicCode = sicCode;
	}

	public String getTaxRegistrationNumber() {
		return taxRegistrationNumber;
	}

	public void setTaxRegistrationNumber(String taxRegistrationNumber) {
		this.taxRegistrationNumber = taxRegistrationNumber;
	}

	public String getDunsNumber() {
		return dunsNumber;
	}

	public void setDunsNumber(String dunsNumber) {
		this.dunsNumber = dunsNumber;
	}

	public OrgBankDetails getBankDetailsId() {
		return bankDetailsId;
	}

	public void setBankDetailsId(OrgBankDetails bankDetailsId) {
		this.bankDetailsId = bankDetailsId;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getNaicsCode() {
		return naicsCode;
	}

	public void setNaicsCode(String naicsCode) {
		this.naicsCode = naicsCode;
	}

	public String getEinCode() {
		return einCode;
	}

	public void setEinCode(String einCode) {
		this.einCode = einCode;
	}

	public String getIsVendorOnboardSubmitted() {
		return isVendorOnboardSubmitted;
	}

	public void setIsVendorOnboardSubmitted(String isVendorOnboardSubmitted) {
		this.isVendorOnboardSubmitted = isVendorOnboardSubmitted;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchNumber() {
		return branchNumber;
	}

	public void setBranchNumber(String branchNumber) {
		this.branchNumber = branchNumber;
	}

	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	public String getBankIdentifierCode() {
		return bankIdentifierCode;
	}

	public void setBankIdentifierCode(String bankIdentifierCode) {
		this.bankIdentifierCode = bankIdentifierCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPoEmail() {
		return poEmail;
	}

	public void setPoEmail(String poEmail) {
		this.poEmail = poEmail;
	}

	public String getPaymentRemittanceEmail() {
		return paymentRemittanceEmail;
	}

	public void setPaymentRemittanceEmail(String paymentRemittanceEmail) {
		this.paymentRemittanceEmail = paymentRemittanceEmail;
	}

	public String getVendorNameForList()
	{
		return vendorNameForList;
	}

	public void setVendorNameForList(String vendorNameForList)
	{
		this.vendorNameForList = vendorNameForList;
	}

	public void setResponderJsonLocationId(Long responderJsonLocationId)
	{
		this.responderJsonLocationId = responderJsonLocationId;
	}

	public void setLocationJsonId(Long locationJsonId)
	{
		this.locationJsonId = locationJsonId;
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

	public Long getImmigrationRelatedId() {
		return immigrationRelatedId;
	}

	public void setImmigrationRelatedId(Long immigrationRelatedId) {
		this.immigrationRelatedId = immigrationRelatedId;
	}
	public String getIsOffShoreRelation() {
		return isOffShoreRelation;
	}

	public void setIsOffShoreRelation(String isOffShoreRelation) {
		this.isOffShoreRelation = isOffShoreRelation;

	}

	public String getGenerateInvoice() {
		return generateInvoice;
	}

	public void setGenerateInvoice(String generateInvoice) {
		this.generateInvoice = generateInvoice;
	}

	public String getGenerateBill() {
		return generateBill;
	}

	public void setGenerateBill(String generateBill) {
		this.generateBill = generateBill;
	}

	

}