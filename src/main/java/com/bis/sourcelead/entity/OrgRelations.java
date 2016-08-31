package com.bis.sourcelead.entity;

import java.util.Date;

import javax.persistence.CascadeType;
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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="ORG_RELATIONS")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL,region="orgRelation")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrgRelations {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID",nullable = false)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name="RESPONDER_LOCATION_ID", nullable=false )        
    private OrgLocations responderLocationId;
	
	@ManyToOne
    @JoinColumn(name="LOCATION_ID", nullable=false )        
    private OrgLocations locationId;
    
    /*@Column(name="RELATION_TYPE")
	private String relationType;*/
	
	@Column(name="INITIATOR_TYPE")
	private String initiatorType;
	
	@Column(name="RESPONDER_TYPE")
	private String responderType;
	
	@Column(name="LEVEL")
	private String level;
	
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
	
	@Transient
    private String orgName;
	
	@Transient
    private Long orgid;
	
	@Transient
	private String webSite;
	
	@Transient
	private String country;
	
	@Transient
	private String state;
	
	@Transient
	private String city;
	
	@Transient
	private String phone;
	
	@Transient
	private String email;
	
	@Transient
	private Long projectsCont;
	
	@Transient
	private String clientName;
	
	@Transient
	private String vendorCreateduserName;
	
	@Transient
	private Integer noOfContatcs;
	
	
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

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Integer getNoOfContatcs() {
		return noOfContatcs;
	}

	public void setNoOfContatcs(Integer noOfContatcs) {
		this.noOfContatcs = noOfContatcs;
	}
}