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

import org.hibernate.annotations.Type;

@Entity
@Table(name = "IMMIGRATION_LIST_VIEW")
public class ImmigrationListView implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private Long id;
	
	
	@Column(name = "GENERATED_ID")
	private String generatedId;
	
	@Column(name = "CURRENT_USCIS_STATUS_ID ")
	private Long currUscisStatusId;
	
	@Column(name = "CURRENT_USCIS_STATUS ")
	private String currUscisStatus;
	
	@Column(name = "IMMIGRATION_TEMPLATE_ID")
	private Long immTemplateId;
	
	@Column(name = "CANDIDATE_ID")
	private Long candidateId;
	
	@Column(name = "COUNTRY")
	private String country;
	
	@Column(name = "ORG_LOCATION_ID")
	private Long orgLocationId;
	
	@Column(name = "ATTORNEY_LOCATION_ID")
	private Long attorneyOrgLocationId;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "IMMIGRATION_STATUS")
	private String immStatus;

	@Column(name = "PETITON_TYPE")
	private String petitionType;
	
	@Column(name = "OPEN_DATE")
	private Date openDate;

	@Column(name = "LCA_START_DATE")
	private Date lcaStartDate;
	
	@Column(name = "LCA_END_DATE")
	private Date lcaEndDate;
	
	
	@Column(name = "FIRS_TNAME")
	private String firstName;

	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "PHONE ")
	private String phone;

	@Column(name = "CLIENT ")
	private String client;
	
	@Column(name = "VENDOR ")
	private String vendor;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "PETITION_TYPE")
	private Long petitionTypeId;
	
	@Column(name = "SPOC_NAME_CLIENT")
	private String spocNameClient;
	
	@Column(name = "SPOC_NAME_VENDOR")
	private String spocNameVendor;
	
	@Column(name = "IMMIGRATION_UUID")
	private String immigrationUUID;
	
	@Column(name = "IMMI_TEMPLATE_UUID")
	private String immiTemplateUUID;
	
	@Column(name = "CANDIDATE_UUID")
	private String candidateUUID;
	
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

	public Date getLcaStartDate() {
		return lcaStartDate;
	}

	public void setLcaStartDate(Date lcaStartDate) {
		this.lcaStartDate = lcaStartDate;
	}

	public Date getLcaEndDate() {
		return lcaEndDate;
	}

	public void setLcaEndDate(Date lcaEndDate) {
		this.lcaEndDate = lcaEndDate;
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

	public Long getAttorneyOrgLocationId() {
		return attorneyOrgLocationId;
	}

	public void setAttorneyOrgLocationId(Long attorneyOrgLocationId) {
		this.attorneyOrgLocationId = attorneyOrgLocationId;
	}

	public Long getPetitionTypeId() {
		return petitionTypeId;
	}

	public void setPetitionTypeId(Long petitionTypeId) {
		this.petitionTypeId = petitionTypeId;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getSpocNameClient() {
		return spocNameClient;
	}

	public void setSpocNameClient(String spocNameClient) {
		this.spocNameClient = spocNameClient;
	}

	public String getSpocNameVendor() {
		return spocNameVendor;
	}

	public void setSpocNameVendor(String spocNameVendor) {
		this.spocNameVendor = spocNameVendor;
	}

	public String getImmigrationUUID() {
		return immigrationUUID;
	}

	public void setImmigrationUUID(String immigrationUUID) {
		this.immigrationUUID = immigrationUUID;
	}

	public String getImmiTemplateUUID() {
		return immiTemplateUUID;
	}

	public void setImmiTemplateUUID(String immiTemplateUUID) {
		this.immiTemplateUUID = immiTemplateUUID;
	}

	public String getCandidateUUID() {
		return candidateUUID;
	}

	public void setCandidateUUID(String candidateUUID) {
		this.candidateUUID = candidateUUID;
	}

}
