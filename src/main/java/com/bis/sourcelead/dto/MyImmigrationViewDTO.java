package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.LookUpData;

public class MyImmigrationViewDTO extends SearchDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long id;
    private String currUscisStatus;
    private Long immTemplateId;
    private Long candidateId;
    private Long candidateUserId;
    private String country;
    private Long orgLocationId;
    private Integer status;
    private String immStatus;
    private String petitionType;
    private Date openDate;
    private String firstName;
    private String email;
    private String phone;
    private String orgName;
    private Date createdDate;
    private Date updatedDate;
    private String openDateStr;
    private String lcaStartDateStr;
    private String lcaEndDateStr;
	private Long currUscisStatusId;
	private String generatedId;
	private String immigrationUUID;
	private String candidateUUID;
	private String immiTemplateUUID;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCurrUscisStatus() {
		return currUscisStatus;
	}
	public void setCurrUscisStatus(String currUscisStatus) {
		this.currUscisStatus = currUscisStatus;
	}
	public Long getImmTemplateId() {
		return immTemplateId;
	}
	public void setImmTemplateId(Long immTemplateId) {
		this.immTemplateId = immTemplateId;
	}
	public Long getCandidateId() {
		return candidateId;
	}
	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}
	public Long getCandidateUserId() {
		return candidateUserId;
	}
	public void setCandidateUserId(Long candidateUserId) {
		this.candidateUserId = candidateUserId;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Long getOrgLocationId() {
		return orgLocationId;
	}
	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getImmStatus() {
		return immStatus;
	}
	public void setImmStatus(String immStatus) {
		this.immStatus = immStatus;
	}
	public String getPetitionType() {
		return petitionType;
	}
	public void setPetitionType(String petitionType) {
		this.petitionType = petitionType;
	}
	public Date getOpenDate() {
		return openDate;
	}
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
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
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
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
	public String getOpenDateStr() {
		return openDateStr;
	}
	public void setOpenDateStr(String openDateStr) {
		this.openDateStr = openDateStr;
	}
	public String getLcaStartDateStr() {
		return lcaStartDateStr;
	}
	public void setLcaStartDateStr(String lcaStartDateStr) {
		this.lcaStartDateStr = lcaStartDateStr;
	}
	public String getLcaEndDateStr() {
		return lcaEndDateStr;
	}
	public void setLcaEndDateStr(String lcaEndDateStr) {
		this.lcaEndDateStr = lcaEndDateStr;
	}
	public Long getCurrUscisStatusId() {
		return currUscisStatusId;
	}
	public void setCurrUscisStatusId(Long currUscisStatusId) {
		this.currUscisStatusId = currUscisStatusId;
	}
	public String getGeneratedId() {
		return generatedId;
	}
	public void setGeneratedId(String generatedId) {
		this.generatedId = generatedId;
	}
	public String getImmigrationUUID() {
		return immigrationUUID;
	}
	public void setImmigrationUUID(String immigrationUUID) {
		this.immigrationUUID = immigrationUUID;
	}
	public String getCandidateUUID() {
		return candidateUUID;
	}
	public void setCandidateUUID(String candidateUUID) {
		this.candidateUUID = candidateUUID;
	}
	public String getImmiTemplateUUID() {
		return immiTemplateUUID;
	}
	public void setImmiTemplateUUID(String immiTemplateUUID) {
		this.immiTemplateUUID = immiTemplateUUID;
	}
  
}
