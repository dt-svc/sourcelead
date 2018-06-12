package com.bis.sourcelead.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "SUBMISSION_REQUIREMENT_LIST_VIEW")
public class SubmissionRequirementListView {

	@Id
    @Column(name = "REQ_ID")
	private Long id;
	
	@Column(name = "GENERATED_ID")
	private String generatedId;
	
	@Column(name = "JOB_TITLE")
	private String jobTitle;
	
	@Column(name = "CREATED_DATE")
	private String createdDate;
	
	@Column(name = "SKILLS")
	@Type(type="text")
	private String skills;
	
	@Column(name = "NOTICE_PERIOD")
	@Type(type="text")
	private String noticePeriod;
	
	@Column(name = "ORG_LOCATION_ID")
	private Long orgLocationId;
	
	@Column(name = "ORG_ID")
	private Long orgId;
	
	@Column(name = "ORG_RELATION_ID")
	private Long orgRelationId;
		
	@Column(name = "USER_ID")
	private Long userId;
	
	@Column(name = "LOCATION")
	@Type(type="text")
	private String location;
	
	@Column(name = "CITY")
	@Type(type="text")
	private String city;

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGeneratedId() {
		return generatedId;
	}

	public void setGeneratedId(String generatedId) {
		this.generatedId = generatedId;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getNoticePeriod() {
		return noticePeriod;
	}

	public void setNoticePeriod(String noticePeriod) {
		this.noticePeriod = noticePeriod;
	}

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getOrgRelationId() {
		return orgRelationId;
	}

	public void setOrgRelationId(Long orgRelationId) {
		this.orgRelationId = orgRelationId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	
	
}
