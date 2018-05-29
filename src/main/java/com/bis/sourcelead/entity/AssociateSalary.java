package com.bis.sourcelead.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bis.sourcelead.payroll.entity.PayItem;

@Entity
@Table(name = "ASSOCIATE_SALARY")
public class AssociateSalary {
	
   private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "ORG_LOCATION_ID")
	private OrgLocations orgLocations;
	
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User userId;
	
	@Column(name = "PAY_TYPE")
	private String payType;
	
	@Column(name = "PAY_FREQUENCY")
	private String payFrequency;
	
	@Column(name = "STANDARD_HOURS")
	private String standardHours;
	
	@Column(name = "ANNUAL_SALARY")
	private BigDecimal annualSalary;
	
	@Column(name = "GROSS_SALARY")
	private BigDecimal grossSalary;
	
	@Column(name = "HOURLY_RATE")
	private BigDecimal hourlyRate;
	
	@Column( name ="ALLOW_OVER_TIME_PAY")
	private String allowOverTimePay;
	
	@Column(name = "ASSOCIATE_SALARY_UUID")
	private String associateSalaryUuid;
	
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "associateSalary")
	private Set<AssociatePayItem> associatePayItem;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public OrgLocations getOrgLocations()
	{
		return orgLocations;
	}

	public void setOrgLocations(OrgLocations orgLocations)
	{
		this.orgLocations = orgLocations;
	}

	public User getUserId()
	{
		return userId;
	}

	public void setUserId(User userId)
	{
		this.userId = userId;
	}

	public String getPayType()
	{
		return payType;
	}

	public void setPayType(String payType)
	{
		this.payType = payType;
	}

	public String getPayFrequency()
	{
		return payFrequency;
	}

	public void setPayFrequency(String payFrequency)
	{
		this.payFrequency = payFrequency;
	}


	public BigDecimal getAnnualSalary()
	{
		return annualSalary;
	}

	public void setAnnualSalary(BigDecimal annualSalary)
	{
		this.annualSalary = annualSalary;
	}

	public String getStandardHours()
	{
		return standardHours;
	}

	public void setStandardHours(String standardHours)
	{
		this.standardHours = standardHours;
	}

	public BigDecimal getGrossSalary()
	{
		return grossSalary;
	}

	public void setGrossSalary(BigDecimal grossSalary)
	{
		this.grossSalary = grossSalary;
	}

	public BigDecimal getHourly()
	{
		return hourly;
	}

	public void setHourly(BigDecimal hourly)
	{
		this.hourly = hourly;
	}

	public String getAssociateSalaryUuid()
	{
		return associateSalaryUuid;
	}

	public void setAssociateSalaryUuid(String associateSalaryUuid)
	{
		this.associateSalaryUuid = associateSalaryUuid;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public Date getCreatedDate()
	{
		return createdDate;
	}

	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}

	public String getCreatedBy()
	{
		return createdBy;
	}

	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate()
	{
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate)
	{
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy()
	{
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy)
	{
		this.updatedBy = updatedBy;
	}

	public Set<AssociatePayItem> getAssociatePayItem()
	{
		return associatePayItem;
	}

	public void setAssociatePayItem(Set<AssociatePayItem> associatePayItem)
	{
		this.associatePayItem = associatePayItem;
	}

	
}
