package com.bis.sourcelead.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	
	@Column(name = "HOURLY")
	private BigDecimal hourly;
	
	/*@ManyToOne
	@JoinColumn(name = "PAY_ITEM_ID")
	private PayItem payItemId;*/
	
	/*@Column(name = "OVERRIDE")
	private String overRide;*/
	
	/*@Column(name = "EFFECTIVE_DATE")
	private Date effectiveDate;
	
	@Column(name = "END_DATE")
	private Date endDate;*/
	
	/*@Column(name = "PAY_ITEM_AMOUNT")
	private BigDecimal payItemAmount;*/
	
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

	/*public PayItem getPayItemId()
	{
		return payItemId;
	}

	public void setPayItemId(PayItem payItemId)
	{
		this.payItemId = payItemId;
	}

	public String getOverRide()
	{
		return overRide;
	}

	public void setOverRide(String overRide)
	{
		this.overRide = overRide;
	}

	public Date getEffectiveDate()
	{
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate)
	{
		this.effectiveDate = effectiveDate;
	}

	public Date getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}

	public BigDecimal getPayItemAmount()
	{
		return payItemAmount;
	}

	public void setPayItemAmount(BigDecimal payItemAmount)
	{
		this.payItemAmount = payItemAmount;
	}*/

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

	
}
