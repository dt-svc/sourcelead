package com.bis.sourcelead.entity;

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
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "ORGLOCATION_INFORMATION_DETAILS")
public class OrgLocationInformationDetails {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "ORGLOCATION_ID")
    private OrgLocations orgLocationId;
	
	@Column(name = "CIN")
	private String cin;
	
	@Column(name = "GSTIN")
	private String gstin;
	
	@Column(name = "INCORPORATION_DATE")
	private Date incorporationDate;
	
	@Column(name = "PAN")
	private String pan;
	
	@Column(name = "TAN")
	private String tan;
	
	@Column(name = "TDS")
	private String tds;
	
	@Column(name = "PF_NUMBER")
	private String pfNumber;
	
	@Column(name = "PF_REGISTRATION_DATE")
	private Date pfRegistrationDate;
	
	@Column(name = "ESI_NUMBER")
	private String esiNumber;
	
	@Column(name = "ESI_REGISTRATION_DATE")
	private Date esiRegistrationDate;
	
	@Column(name = "PT_NUMBER")
	private String ptNumber;
	
	@Column(name = "PT_REGISTRATION_DATE")
	private Date ptRegistrationDate;
	
	@Column(name = "UUID")
	private String uuid;
	
    @ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "CREATED_BY")
  	private User createdBy;
    
	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "UPDATED_BY")
  	private User updatedBy;
    
    @Column(name = "CREATED_DATE")
  	private Date createdDate;
    
    @Column(name = "UPDATED_DATE")
   	private Date updatedDate;
    
    @Transient
    @JsonProperty
    private String incorporationDateStr;
    
    @Transient
    @JsonProperty
    private String pfRegistrationDateStr;
    
    @Transient
    @JsonProperty
    private String esiRegistrationDateStr;
    
    @Transient
    @JsonProperty
    private String ptRegistrationDateStr;
    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public Date getIncorporationDate() {
		return incorporationDate;
	}

	public void setIncorporationDate(Date incorporationDate) {
		this.incorporationDate = incorporationDate;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getTan() {
		return tan;
	}

	public void setTan(String tan) {
		this.tan = tan;
	}

	public String getTds() {
		return tds;
	}

	public void setTds(String tds) {
		this.tds = tds;
	}

	public String getPfNumber() {
		return pfNumber;
	}

	public void setPfNumber(String pfNumber) {
		this.pfNumber = pfNumber;
	}

	public Date getPfRegistrationDate() {
		return pfRegistrationDate;
	}

	public void setPfRegistrationDate(Date pfRegistrationDate) {
		this.pfRegistrationDate = pfRegistrationDate;
	}

	public String getEsiNumber() {
		return esiNumber;
	}

	public void setEsiNumber(String esiNumber) {
		this.esiNumber = esiNumber;
	}

	public Date getEsiRegistrationDate() {
		return esiRegistrationDate;
	}

	public void setEsiRegistrationDate(Date esiRegistrationDate) {
		this.esiRegistrationDate = esiRegistrationDate;
	}

	public String getPtNumber() {
		return ptNumber;
	}

	public void setPtNumber(String ptNumber) {
		this.ptNumber = ptNumber;
	}

	public Date getPtRegistrationDate() {
		return ptRegistrationDate;
	}

	public void setPtRegistrationDate(Date ptRegistrationDate) {
		this.ptRegistrationDate = ptRegistrationDate;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
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

	public String getIncorporationDateStr() {
		return incorporationDateStr;
	}

	public void setIncorporationDateStr(String incorporationDateStr) {
		this.incorporationDateStr = incorporationDateStr;
	}

	public String getPfRegistrationDateStr() {
		return pfRegistrationDateStr;
	}

	public void setPfRegistrationDateStr(String pfRegistrationDateStr) {
		this.pfRegistrationDateStr = pfRegistrationDateStr;
	}

	public String getEsiRegistrationDateStr() {
		return esiRegistrationDateStr;
	}

	public void setEsiRegistrationDateStr(String esiRegistrationDateStr) {
		this.esiRegistrationDateStr = esiRegistrationDateStr;
	}

	public String getPtRegistrationDateStr() {
		return ptRegistrationDateStr;
	}

	public void setPtRegistrationDateStr(String ptRegistrationDateStr) {
		this.ptRegistrationDateStr = ptRegistrationDateStr;
	}

}
