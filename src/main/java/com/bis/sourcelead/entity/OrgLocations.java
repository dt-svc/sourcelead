package com.bis.sourcelead.entity;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import com.bis.sourcelead.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ORG_LOCATIONS")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL,region="orgLocation" )
public class OrgLocations {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "ORG_ID")
    private Org org;
	
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
	
	
	@Column(name = "STATUS")
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Column(name = "PHONE")
	private String phone;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "FAX")
	private String fax;
	
	
	@Column(name = "LOCATION_CODE")
	private String locationCode;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
    @Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
    @Column(name = "UPDATED_DATE")
	private Date updatedDate;

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

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
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
	
	

	
}
