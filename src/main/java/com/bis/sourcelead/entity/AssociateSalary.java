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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.bis.sourcelead.payroll.entity.PayItem;
import com.fasterxml.jackson.annotation.JsonProperty;

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
	@JoinColumn(name = "CANDIDATE_ID")
	private Candidate candidate;
	
	@ManyToOne
	@JoinColumn(name = "GROUP_ID")
	private UserGroup groupId;
	
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
	
	@Column( name ="INCREMENT_PERCENTAGE")
	private String incrementPercentage;
	
	@Column( name ="EFFECTIVE_DATE")
	private Date effectiveDate;
	
	@Column( name ="CHANGE_REASON",columnDefinition = "TEXT default NULL")
	private String changeReason;
	

	@Column( name ="SALARY_TYPE")
	private String salaryType; 
	
	@Column(name = "ASSOCIATE_SALARY_UUID")
	private String associateSalaryUuid;
	
	
	@Column(name = "STATUS")
	private Integer status;
	
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private Long createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private Long updatedBy;
	
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "associateSalary")
	private Set<AssociatePayItem> associatePayItem;
	
	@Transient
	@JsonProperty
	private Long user;

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


	public Candidate getCandidate()
	{
		return candidate;
	}

	public void setCandidate(Candidate candidate)
	{
		this.candidate = candidate;
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


	public BigDecimal getHourlyRate()
	{
		return hourlyRate;
	}

	public String getAllowOverTimePay()
	{
		return allowOverTimePay;
	}

	public void setHourlyRate(BigDecimal hourlyRate)
	{
		this.hourlyRate = hourlyRate;
	}

	public void setAllowOverTimePay(String allowOverTimePay)
	{
		this.allowOverTimePay = allowOverTimePay;
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


	public Date getUpdatedDate()
	{
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate)
	{
		this.updatedDate = updatedDate;
	}


	public Set<AssociatePayItem> getAssociatePayItem()
	{
		return associatePayItem;
	}

	public void setAssociatePayItem(Set<AssociatePayItem> associatePayItem)
	{
		this.associatePayItem = associatePayItem;
	}

	public String getIncrementPercentage()
	{
		return incrementPercentage;
	}

	public void setIncrementPercentage(String incrementPercentage)
	{
		this.incrementPercentage = incrementPercentage;
	}

	public Date getEffectiveDate()
	{
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate)
	{
		this.effectiveDate = effectiveDate;
	}

	public String getChangeReason()
	{
		return changeReason;
	}

	public void setChangeReason(String changeReason)
	{
		this.changeReason = changeReason;
	}

	public String getSalaryType()
	{
		return salaryType;
	}

	public void setSalaryType(String salaryType)
	{
		this.salaryType = salaryType;
	}

	public Long getCreatedBy()
	{
		return createdBy;
	}

	public Long getUpdatedBy()
	{
		return updatedBy;
	}

	public void setCreatedBy(Long createdBy)
	{
		this.createdBy = createdBy;
	}

	public void setUpdatedBy(Long updatedBy)
	{
		this.updatedBy = updatedBy;
	}

	public UserGroup getGroupId() {
		return groupId;
	}

	public void setGroupId(UserGroup groupId) {
		this.groupId = groupId;
	}

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
