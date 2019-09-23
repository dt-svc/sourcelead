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
@Table(name = "JOB_ALERTS")
public class JobAlert implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User userId;
    
    @Column(name = "ALERT_TITLE")
   	private String alertTitle;
    
    @Column(name = "SKILLS_NAME")
	@Type(type="text")
	private String skillsName;
    
    @Column(name = "COUNTRY", columnDefinition = "LONGTEXT default NULL")
	private String country;
	
	@Column(name = "STATE", columnDefinition = "LONGTEXT default NULL")
	private String state;
	
	@Column(name = "CITY", columnDefinition = "LONGTEXT default NULL")
	private String city;
	
	@Column(name = "LOCATION", columnDefinition = "LONGTEXT default NULL")
	private String location;
	
	@Column(name = "ZIPCODE")
	private String zipCode;
	
	@Column(name = "EXPERIENCE")
	private String experience;
	
	@Column(name = "RATE")
	private String rate;
	
	@Column(name = "RATE_TYPE")
	private String rateType;
	
	@Column(name = "RATE_CURRENCY")
	private String rateCurrency;
	
	@Column(name = "INDUSTRY_NAME")
	private String industryName;
	
	@Column(name = "DURATION")
	private String duration;
	
	@Column(name = "EMAIL")
	private String email;
	
    @Column(name = "STATUS")
   	private Integer status;
    
	@Column(name = "JOB_ALERT_UUID")
	private String jobAlertUUID;
   		
   	@Column(name = "CREATED_DATE")
   	private Date createdDate;
   	
   	@Column(name = "CREATED_BY")
   	private String createdBy;
   	
   	@Column(name = "UPDATED_DATE")
   	private Date updatedDate;
   	
   	@Column(name = "UPDATED_BY")
   	private String updatedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public String getAlertTitle() {
		return alertTitle;
	}

	public void setAlertTitle(String alertTitle) {
		this.alertTitle = alertTitle;
	}

	public String getSkillsName() {
		return skillsName;
	}

	public void setSkillsName(String skillsName) {
		this.skillsName = skillsName;
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

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public String getRateCurrency() {
		return rateCurrency;
	}

	public void setRateCurrency(String rateCurrency) {
		this.rateCurrency = rateCurrency;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	
	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
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

	public String getJobAlertUUID()
	{
		return jobAlertUUID;
	}

	public void setJobAlertUUID(String jobAlertUUID)
	{
		this.jobAlertUUID = jobAlertUUID;
	}

	
   	
}
