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
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "ORGLOCATION_DEPOSIT_TAXES")
public class OrglocationDepositTaxes implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "ORG_LOCATION_ID")
    private OrgLocations orgLocationId;
	
	@Column(name = "FILLER_TYPE")
	private String fillerType;
	
	@Column(name = "FEDERAL_DEPOSIT")
	private String federalDeposit;
	
	@Column(name = "TAX_DEPOSIT_SCHEDULE")
	private String taxDepositSchedule;
	
	@Column(name = "STATUS")
	private Integer status;
	
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public String getFillerType() {
		return fillerType;
	}

	public String getFederalDeposit() {
		return federalDeposit;
	}

	public String getTaxDepositSchedule() {
		return taxDepositSchedule;
	}

	public Integer getStatus() {
		return status;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public void setFillerType(String fillerType) {
		this.fillerType = fillerType;
	}

	public void setFederalDeposit(String federalDeposit) {
		this.federalDeposit = federalDeposit;
	}

	public void setTaxDepositSchedule(String taxDepositSchedule) {
		this.taxDepositSchedule = taxDepositSchedule;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}


}
