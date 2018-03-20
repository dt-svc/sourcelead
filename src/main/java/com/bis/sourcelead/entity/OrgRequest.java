package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "ORG_REQUEST")
public class OrgRequest implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	@Column(name = "ORG_TYPE")
   	private String orgType;
	
    @Column(name = "FIRST_NAME")
   	private String firstName;
    
    @Column(name = "LAST_NAME")
   	private String lastName;
    
    @Column(name = "PHONE_NUMBER")
   	private String phoneNumber;
    
    @Column(name = "EMAIL")
   	private String email;
    
    @Column(name = "COMPANY_NAME")
   	private String companyName;
    
    @Column(name = "COMPANY_WEBSITE")
   	private String companywebsite;
    
    @Column(name = "COUNTRY")
   	private String country;
    
    @Column(name = "COMPANY_ADDRESS")
	@Type(type="text")
	private String companyAddress;
    
    @Column(name = "MODULES")
   	private String modules;
    
    @Column(name = "DEMO_DATE")
   	private Date demoDate;
    
    @Column(name = "DEMO_TIME")
   	private String demoTime;
    
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="DEMO_TIME_ZONE", nullable=false )
	private Timezone timezone;
		
    @Column(name = "STATUS")
   	private Integer status;
    
    @Column(name = "REQUEST_STATUS")
   	private String requestStatus;
   		
   	@Column(name = "CREATED_DATE")
   	private Date createdDate;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getCompanywebsite() {
		return companywebsite;
	}

	public String getCountry() {
		return country;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public String getModules() {
		return modules;
	}

	public Integer getStatus() {
		return status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setCompanywebsite(String companywebsite) {
		this.companywebsite = companywebsite;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public void setModules(String modules) {
		this.modules = modules;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getDemoDate() {
		return demoDate;
	}

	public String getDemoTime() {
		return demoTime;
	}

	public Timezone getTimezone() {
		return timezone;
	}

	public void setDemoDate(Date demoDate) {
		this.demoDate = demoDate;
	}

	public void setDemoTime(String demoTime) {
		this.demoTime = demoTime;
	}

	public void setTimezone(Timezone timezone) {
		this.timezone = timezone;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}
}
