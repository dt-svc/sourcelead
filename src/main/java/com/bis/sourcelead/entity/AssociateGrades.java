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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "ASSOCIATE_GRADES")
public class AssociateGrades {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "ORGLOCATION_ID")
    private OrgLocations orgLocationId;
	
	@Column(name = "GRADE_NAME")
	private String gradeName;
	
	@Column(name = "DESIGNATION")
	private String designation;
	
	@Column(name = "MIN_SALARY")
	private Double minSalary;
	
	@Column(name = "MIN_SALARY_RATE_TYPE")
	private String minSalaryRateType;
	
	@Column(name = "MIN_SALARY_RATE_PER")
	private String minSalaryRatePer;
	
	@Column(name = "MID_SALARY")
	private Double midSalary;
	
	@Column(name = "MID_SALARY_RATE_TYPE")
	private String midSalaryRateType;
	
	@Column(name = "MID_SALARY_RATE_PER")
	private String midSalaryRatePer;
	
	@Column(name = "MAX_SALARY")
	private Double maxSalary;
	
	@Column(name = "MAX_SALARY_RATE_TYPE")
	private String maxSalaryRateType;
	
	@Column(name = "MAX_SALARY_RATE_PER")
	private String maxSalaryRatePer;
	
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

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Double getMinSalary() {
		return minSalary;
	}

	public void setMinSalary(Double minSalary) {
		this.minSalary = minSalary;
	}

	public String getMinSalaryRateType() {
		return minSalaryRateType;
	}

	public void setMinSalaryRateType(String minSalaryRateType) {
		this.minSalaryRateType = minSalaryRateType;
	}

	public String getMinSalaryRatePer() {
		return minSalaryRatePer;
	}

	public void setMinSalaryRatePer(String minSalaryRatePer) {
		this.minSalaryRatePer = minSalaryRatePer;
	}

	public Double getMidSalary() {
		return midSalary;
	}

	public void setMidSalary(Double midSalary) {
		this.midSalary = midSalary;
	}

	public String getMidSalaryRateType() {
		return midSalaryRateType;
	}

	public void setMidSalaryRateType(String midSalaryRateType) {
		this.midSalaryRateType = midSalaryRateType;
	}

	public String getMidSalaryRatePer() {
		return midSalaryRatePer;
	}

	public void setMidSalaryRatePer(String midSalaryRatePer) {
		this.midSalaryRatePer = midSalaryRatePer;
	}

	public Double getMaxSalary() {
		return maxSalary;
	}

	public void setMaxSalary(Double maxSalary) {
		this.maxSalary = maxSalary;
	}

	public String getMaxSalaryRateType() {
		return maxSalaryRateType;
	}

	public void setMaxSalaryRateType(String maxSalaryRateType) {
		this.maxSalaryRateType = maxSalaryRateType;
	}

	public String getMaxSalaryRatePer() {
		return maxSalaryRatePer;
	}

	public void setMaxSalaryRatePer(String maxSalaryRatePer) {
		this.maxSalaryRatePer = maxSalaryRatePer;
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
