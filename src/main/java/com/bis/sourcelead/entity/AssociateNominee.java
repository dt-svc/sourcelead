package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "ASSOCIATE_NOMINEE")
public class AssociateNominee implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "ORG_LOCATION_ID")
	private Long orgLocationId;
	
	@ManyToOne
	@JoinColumn(name = "CANDIDATE_ID")
	private Candidate candidateId;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "DATE_OF_BIRTH")
	private Date dateOfBirth;
	
	@Column(name = "GENDER")
	private String gender;
	
	@Column(name = "BLOOD_GROUP")
	private String bloodGroup;
	
	@Column(name = "RELATION")
	private String relation;
	
	@Column(name = "NOMINATION_FOR")
	private String nominationFor;
	
	@Column(name = "NOMINATION_PERCENTAGE")
	private int nominationPercentage;
	
	@Column(name = "ADDRESS", columnDefinition = "TEXT default NULL")
	private String address;
	
	@Column(name = "ILLNESS_GUARDIAN_NAME")
	private String illnessGuardianName;
	
	@Column(name = "ILLNESS_GUARDIAN_RELATION_NAME")
	private String illnessGuardianRelationName;
	
	@Column(name = "ILLNESS_GUARDIAN_ADDRESS", columnDefinition = "TEXT default NULL")
	private String illnessGuardianAddress;
	
	@Column(name = "ILLNESS_GUARDIAN_PHONE_NUMBER")
	private String illnessGuardianPhoneNumber;
	
	@Column(name = "ILLNESS_GUARDIAN_EMAIL")
	private String illnessGuardianEmail;
	
	@Column(name = "MINOR_GUARDIAN_NAME")
	private String minorGuardianName;
	
	@Column(name = "MINOR_GUARDIAN_RELATION_NAME")
	private String minorGuardianRelationName;
	
	@Column(name = "MINOR_GUARDIAN_ADDRESS", columnDefinition = "TEXT default NULL")
	private String minorGuardianAddress;
	
	@Column(name = "MINOR_GUARDIAN_PHONE_NUMBER")
	private String minorGuardianPhoneNumber;
	
	@Column(name = "MINOR_GUARDIAN_EMAIL")
	private String minorGuardianEmail;
	
	@Column(name = "STATUS")
	private int status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "UPDATED_DATE")
  	private Date updatedDate;
	
	@Column(name = "CREATED_BY")
    private Long createdBy;
	
	@Column(name = "UPDATED_BY")
    private Long updatedBy;
	
	@Transient
	@JsonProperty
	private String dob;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public String getName() {
		return name;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public String getRelation() {
		return relation;
	}

	public String getNominationFor() {
		return nominationFor;
	}

	public int getNominationPercentage() {
		return nominationPercentage;
	}

	public String getAddress() {
		return address;
	}

	public String getIllnessGuardianName() {
		return illnessGuardianName;
	}

	public String getIllnessGuardianRelationName() {
		return illnessGuardianRelationName;
	}

	public String getIllnessGuardianAddress() {
		return illnessGuardianAddress;
	}

	public String getIllnessGuardianPhoneNumber() {
		return illnessGuardianPhoneNumber;
	}

	public String getIllnessGuardianEmail() {
		return illnessGuardianEmail;
	}

	public String getMinorGuardianName() {
		return minorGuardianName;
	}

	public String getMinorGuardianRelationName() {
		return minorGuardianRelationName;
	}

	public String getMinorGuardianAddress() {
		return minorGuardianAddress;
	}

	public String getMinorGuardianPhoneNumber() {
		return minorGuardianPhoneNumber;
	}

	public String getMinorGuardianEmail() {
		return minorGuardianEmail;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public void setNominationFor(String nominationFor) {
		this.nominationFor = nominationFor;
	}

	public void setNominationPercentage(int nominationPercentage) {
		this.nominationPercentage = nominationPercentage;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setIllnessGuardianName(String illnessGuardianName) {
		this.illnessGuardianName = illnessGuardianName;
	}

	public void setIllnessGuardianRelationName(String illnessGuardianRelationName) {
		this.illnessGuardianRelationName = illnessGuardianRelationName;
	}

	public void setIllnessGuardianAddress(String illnessGuardianAddress) {
		this.illnessGuardianAddress = illnessGuardianAddress;
	}

	public void setIllnessGuardianPhoneNumber(String illnessGuardianPhoneNumber) {
		this.illnessGuardianPhoneNumber = illnessGuardianPhoneNumber;
	}

	public void setIllnessGuardianEmail(String illnessGuardianEmail) {
		this.illnessGuardianEmail = illnessGuardianEmail;
	}

	public void setMinorGuardianName(String minorGuardianName) {
		this.minorGuardianName = minorGuardianName;
	}

	public void setMinorGuardianRelationName(String minorGuardianRelationName) {
		this.minorGuardianRelationName = minorGuardianRelationName;
	}

	public void setMinorGuardianAddress(String minorGuardianAddress) {
		this.minorGuardianAddress = minorGuardianAddress;
	}

	public void setMinorGuardianPhoneNumber(String minorGuardianPhoneNumber) {
		this.minorGuardianPhoneNumber = minorGuardianPhoneNumber;
	}

	public void setMinorGuardianEmail(String minorGuardianEmail) {
		this.minorGuardianEmail = minorGuardianEmail;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Candidate getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Candidate candidateId) {
		this.candidateId = candidateId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}
}
