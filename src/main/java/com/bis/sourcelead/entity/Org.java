package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

import com.bis.sourcelead.enums.Status;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author Rajashekar
 * @date 29th Mar 2016
 *
 */
@Entity
@Table(name="ORG")
public class Org implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID", nullable = false)
	private Long id;
	
	@Column(name = "ORG_NAME")
	@JsonProperty
	private String orgName;
	
	@Column(name = "WEBSITE")
	private String webSite;
	
	@Column(name = "ORG_TYPE")
	private String orgType;
	
	@Column(name = "ORG_CODE")
	private String orgCode;
	
	@Column(name = "STATUS")
	@Enumerated(EnumType.STRING)
	private Status status;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRIMARY_CONTACT_USER_ID")
    private User user;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
    private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "DOC_FILE",columnDefinition="mediumblob")
	@Lob
	private  byte[] docFile;
		
	@Column(name = "FILE_CONTENT_TYPE")
	private String fileContentType;
	
	@Column(name = "FILE_NAME")
	private String fileName;
	
	
	@Column(name = "FILE_URL")
	private String fileUrl;
	
	@Column(name = "ORG_DESCRIPTION")
	@Type(type="text")
	private String orgDescription;
	
	@Column(name = "ORG_ADDRESS")
	@Type(type="text")
	private String orgAddress;
	
	
	@Column(name = "ATS")
	@Enumerated(EnumType.STRING)
	private Status ats;
	
	@Column(name = "VMS")
	@Enumerated(EnumType.STRING)
	private Status vms;
	
	@Column(name = "TIMESHEET")
	@Enumerated(EnumType.STRING)
	private Status timeSheet;
	
	
	@OneToMany(mappedBy="org",cascade = CascadeType.ALL,fetch=FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    private Set<OrgLocations> orgLocationsList;
	
	@Column(name = "MODULES_ID")
	@Type(type="text")
	private String modulesId;
	
    @Column(name = "CLIENT_ADDRESS", columnDefinition = "LONGTEXT default NULL")
	private String clientAddress;
    
    @Column(name = "CLIENT_BILLING_ADDRESS", columnDefinition = "LONGTEXT default NULL")
	private String clientBillingAddress;
    
    @Column(name = "PAYMENT_TERM")
	private String PaymentTerm;
    
    @Column(name = "CAREER_PAGE_KEY")
    private String careerPageKey;
    
    @Column(name = "IS_CAREER_PAGE_KEY_ACTIVE")
    private Boolean careerPageKeyActive;
	
	@Transient
	private String email;
	
	@Transient
	private String mobilePhone;
	
	@Transient
	private Status userState;
	
	@Transient
	private Status isMailSent;
	
	@Transient
	private String location;
	
	@Transient
	private Long registeredUserId;
	
	@Transient
	private Long primaryOrgLocationId;
	
	@Transient
	@JsonProperty
	private int noOfDays;
	
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

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}


	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public byte[] getDocFile() {
		return docFile;
	}

	public void setDocFile(byte[] docFile) {
		this.docFile = docFile;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getOrgDescription() {
		return orgDescription;
	}

	public void setOrgDescription(String orgDescription) {
		this.orgDescription = orgDescription;
	}

	public Status getAts() {
		return ats;
	}

	public void setAts(Status ats) {
		this.ats = ats;
	}

	public Status getVms() {
		return vms;
	}

	public void setVms(Status vms) {
		this.vms = vms;
	}

	public Status getTimeSheet() {
		return timeSheet;
	}

	public void setTimeSheet(Status timeSheet) {
		this.timeSheet = timeSheet;
	}

	public Set<OrgLocations> getOrgLocationsList() {
		return orgLocationsList;
	}

	public void setOrgLocationsList(Set<OrgLocations> orgLocationsList) {
		this.orgLocationsList = orgLocationsList;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	
	public Status getUserState() {
		return userState;
	}

	public void setUserState(Status userState) {
		this.userState = userState;
	}

	public Status getIsMailSent() {
		return isMailSent;
	}

	public void setIsMailSent(Status isMailSent) {
		this.isMailSent = isMailSent;
	}

	public String getModulesId() {
		return modulesId;
	}

	public void setModulesId(String modulesId) {
		this.modulesId = modulesId;
	}

	public String getOrgAddress() {
		return orgAddress;
	}

	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getClientAddress() {
		return clientAddress;
	}

	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}

	public String getClientBillingAddress() {
		return clientBillingAddress;
	}

	public void setClientBillingAddress(String clientBillingAddress) {
		this.clientBillingAddress = clientBillingAddress;
	}

	public String getPaymentTerm() {
		return PaymentTerm;
	}

	public void setPaymentTerm(String paymentTerm) {
		PaymentTerm = paymentTerm;
	}

	public Long getRegisteredUserId() {
		return registeredUserId;
	}

	public void setRegisteredUserId(Long registeredUserId) {
		this.registeredUserId = registeredUserId;
	}

	public Long getPrimaryOrgLocationId()
	{
		return primaryOrgLocationId;
	}

	public void setPrimaryOrgLocationId(Long primaryOrgLocationId)
	{
		this.primaryOrgLocationId = primaryOrgLocationId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public int getNoOfDays()
	{
		return noOfDays;
	}

	public void setNoOfDays(int noOfDays)
	{
		this.noOfDays = noOfDays;
	}
	
	public String getCareerPageKey()
	{
		return careerPageKey;
	}

	public void setCareerPageKey(String careerPageKey)
	{
		this.careerPageKey = careerPageKey;
	}

	public Boolean getCareerPageKeyActive() {
		return careerPageKeyActive;
	}

	public void setCareerPageKeyActive(Boolean careerPageKeyActive) {
		this.careerPageKeyActive = careerPageKeyActive;
	}

	
}
