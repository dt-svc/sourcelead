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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Entity
@Table(name = "PAYROLL_LOAN_AND_ADVANCES")
public class PayrollLoanAndAdvances implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "ORGLOCATION_ID")
    private OrgLocations orgLocationId;
	
	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "MIN_AMOUNT")
	private Double minAmount;
	
	@Column(name = "MAX_AMOUNT")
	private Double maxAmount;
	
	@Column(name = "INTEREST_RATE")
	private Double interestRate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "PAY_HEAD_ID")
    private PayrollPayhead payHeadId;
	
	@Column(name = "APPLICABLE_FOR")
	private String applicableFor;
	
	@Column(name = "EXPERIENCE_YEARS")
	private Integer experienceYears;
	
	@Column(name = "EXPERIENCE_MONTHS")
	private Integer experienceMonths;
	
	@Column(name = "DESCRIPTION", columnDefinition = "TEXT default NULL")
	private String description;
	
	@Column(name = "UUID")
	private String uuid;
	
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(Double minAmount) {
		this.minAmount = minAmount;
	}

	public Double getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(Double maxAmount) {
		this.maxAmount = maxAmount;
	}

	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	public PayrollPayhead getPayHeadId() {
		return payHeadId;
	}

	public void setPayHeadId(PayrollPayhead payHeadId) {
		this.payHeadId = payHeadId;
	}

	public String getApplicableFor() {
		return applicableFor;
	}

	public void setApplicableFor(String applicableFor) {
		this.applicableFor = applicableFor;
	}

	public Integer getExperienceYears() {
		return experienceYears;
	}

	public void setExperienceYears(Integer experienceYears) {
		this.experienceYears = experienceYears;
	}

	public Integer getExperienceMonths() {
		return experienceMonths;
	}

	public void setExperienceMonths(Integer experienceMonths) {
		this.experienceMonths = experienceMonths;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

}
