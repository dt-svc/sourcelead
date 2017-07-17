package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;

import com.bis.sourcelead.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ORG_LOCATIONS")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL,region="orgLocation" )
public class OrgLocations implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "ORG_ID")
    private Org org;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "TIMEZONE_ID")
    private Timezone timezone;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRIMARY_CONTACT_USER_ID")
    private User user;
	
	@OneToMany(mappedBy="responderLocationId",cascade = CascadeType.ALL,fetch=FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    private Set<OrgRelations> orgRelationsList;
	 
	
	@Column(name = "COUNTRY")
	private String country;
	
	@Column(name = "STATE")
	private String state;
	
	@Column(name = "ZIP_CODE")
	private String zipCode;
	
	@Column(name = "CITY")
	private String city;
	
	@Column(name = "LOCATION")
	private String location;
	
	@Column(name = "LATITUDE")
	private String latitude;
	
	@Column(name = "LONGITUDE")
	private String longitude;
	
	@Column(name = "STATUS")
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Column(name = "FROM_EMAIL")
	private String fromEmail;
	
	@Column(name = "FROM_EMAIL_PASSWORD")
	private String fromEmailPassword;
	
	@Column(name = "PHONE")
	private String phone;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "FAX")
	private String fax;
	
	
	@Column(name = "LOCATION_CODE")
	private String locationCode;
	
	@Column(name = "COOLINGOFF_PERIOD")
	private String coolingOffPeriod;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
    @Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
    @Column(name = "UPDATED_DATE")
	private Date updatedDate;
    
    @Column(name = "MODULES_ID")
	@Type(type="text")
	private String modulesId;
    
    @Column(name = "ORG_NAME")
	private String orgLocationName;
    
    @Column(name = "ORG_LOCATION_ADDRESS")
   	@Type(type="text")
   	private String orgLocationAddress;
    
    @Column(name = "WEB_SITE")
	private String website;
    
    @Column(name = "ABOUT_COMPANY")
	@Type(type="text")
	private String orgDescription;
    
    @Column(name = "IS_PRIMARY")
	private String isPrimary;
    
    @Column(name = "GENERATED_USER_ID")
	private String generatedUserId;
    
    
    @Column(name = "SYSTEM_GENERATED_ID")
	private String systemGeneratedId;
    
    @Transient
    private String orgName;
    
    @Transient
    private String orglocationUserName;
    
    @Transient
    private String orglocationUserEmail;
    
    @Transient
    private String orglocationUserPhone;
    
    @Transient
    private String orglocationUserStatus;
    
    @Transient
    private Long orglocationUserId;
    
    @Transient
    private String orgCode;
    
    @Transient
    private List<Modules> modulesList;
    
    @Transient
    private List<Modules> subModulesList;
    
    @Transient
    private String timeZoneName;
    
    @Transient
    private String createdDateStr;
    
    @Transient
    private String orgLocationUserFirstName;
    
    @Transient
    private String orgLocationUserLastName;
    

	public Timezone getTimezone() {
		return timezone;
	}

	public void setTimezone(Timezone timezone) {
		this.timezone = timezone;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}


	public String getCountry() {
		return country;
	}
	
	@JsonIgnore
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getFromEmail() {
		return fromEmail;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public String getFromEmailPassword() {
		return fromEmailPassword;
	}

	public void setFromEmailPassword(String fromEmailPassword) {
		this.fromEmailPassword = fromEmailPassword;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getCoolingOffPeriod() {
		return coolingOffPeriod;
	}

	public void setCoolingOffPeriod(String coolingOffPeriod) {
		this.coolingOffPeriod = coolingOffPeriod;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public Set<OrgRelations> getOrgRelationsList() {
		return orgRelationsList;
	}

	public void setOrgRelationsList(Set<OrgRelations> orgRelationsList) {
		this.orgRelationsList = orgRelationsList;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getModulesId()
	{
		return modulesId;
	}

	public void setModulesId(String modulesId)
	{
		this.modulesId = modulesId;
	}

	public String getOrgLocationName()
	{
		return orgLocationName;
	}

	public void setOrgLocationName(String orgLocationName)
	{
		this.orgLocationName = orgLocationName;
	}

	public String getOrgLocationAddress()
	{
		return orgLocationAddress;
	}

	public void setOrgLocationAddress(String orgLocationAddress)
	{
		this.orgLocationAddress = orgLocationAddress;
	}

	public String getWebsite()
	{
		return website;
	}

	public void setWebsite(String website)
	{
		this.website = website;
	}

	public String getOrgDescription()
	{
		return orgDescription;
	}

	public void setOrgDescription(String orgDescription)
	{
		this.orgDescription = orgDescription;
	}

	public String getOrglocationUserName()
	{
		return orglocationUserName;
	}

	public void setOrglocationUserName(String orglocationUserName)
	{
		this.orglocationUserName = orglocationUserName;
	}

	public String getOrglocationUserEmail()
	{
		return orglocationUserEmail;
	}

	public void setOrglocationUserEmail(String orglocationUserEmail)
	{
		this.orglocationUserEmail = orglocationUserEmail;
	}

	public String getOrglocationUserPhone()
	{
		return orglocationUserPhone;
	}

	public void setOrglocationUserPhone(String orglocationUserPhone)
	{
		this.orglocationUserPhone = orglocationUserPhone;
	}

	public String getOrglocationUserStatus()
	{
		return orglocationUserStatus;
	}

	public void setOrglocationUserStatus(String orglocationUserStatus)
	{
		this.orglocationUserStatus = orglocationUserStatus;
	}

	public Long getOrglocationUserId()
	{
		return orglocationUserId;
	}

	public void setOrglocationUserId(Long orglocationUserId)
	{
		this.orglocationUserId = orglocationUserId;
	}

	public String getOrgCode()
	{
		return orgCode;
	}

	public void setOrgCode(String orgCode)
	{
		this.orgCode = orgCode;
	}

	public List<Modules> getModulesList()
	{
		return modulesList;
	}

	public void setModulesList(List<Modules> modulesList)
	{
		this.modulesList = modulesList;
	}

	public List<Modules> getSubModulesList()
	{
		return subModulesList;
	}

	public void setSubModulesList(List<Modules> subModulesList)
	{
		this.subModulesList = subModulesList;
	}

	public String getTimeZoneName()
	{
		return timeZoneName;
	}

	public void setTimeZoneName(String timeZoneName)
	{
		this.timeZoneName = timeZoneName;
	}

	public String getCreatedDateStr()
	{
		return createdDateStr;
	}

	public void setCreatedDateStr(String createdDateStr)
	{
		this.createdDateStr = createdDateStr;
	}

	public String getIsPrimary()
	{
		return isPrimary;
	}

	public void setIsPrimary(String isPrimary)
	{
		this.isPrimary = isPrimary;
	}

	public String getOrgLocationUserFirstName()
	{
		return orgLocationUserFirstName;
	}

	public void setOrgLocationUserFirstName(String orgLocationUserFirstName)
	{
		this.orgLocationUserFirstName = orgLocationUserFirstName;
	}

	public String getOrgLocationUserLastName()
	{
		return orgLocationUserLastName;
	}

	public void setOrgLocationUserLastName(String orgLocationUserLastName)
	{
		this.orgLocationUserLastName = orgLocationUserLastName;
	}

	public String getGeneratedUserId()
	{
		return generatedUserId;
	}

	public void setGeneratedUserId(String generatedUserId)
	{
		this.generatedUserId = generatedUserId;
	}

	public String getSystemGeneratedId()
	{
		return systemGeneratedId;
	}

	public void setSystemGeneratedId(String systemGeneratedId)
	{
		this.systemGeneratedId = systemGeneratedId;
	}
	
	
}
