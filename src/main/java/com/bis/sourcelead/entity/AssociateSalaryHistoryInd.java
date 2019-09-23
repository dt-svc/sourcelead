package com.bis.sourcelead.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "ASSOCIATE_SALARY_HISTORY_IND")
public class AssociateSalaryHistoryInd {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	@ManyToOne
	@JoinColumn(name = "ASSOCIATE_SALARY_IND")
	private AssociateSalaryInd associateSalaryInd;
	
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
	
	@Column( name ="INCREMENT_PERCENTAGE")
	private String incrementPercentage;
	
	@Column( name ="EFFECTIVE_DATE")
	private Date effectiveDate;
	
	@Column( name ="CHANGE_REASON",columnDefinition = "TEXT default NULL")
	private String changeReason;

	@Column( name ="SALARY_TYPE")
	private String salaryType;
	
	@Column( name ="STOP_SALARY")
	private String stopSalary;
	
	@ManyToOne
	@JoinColumn(name = "PREVIOUS_GROUP_ID")
	private UserGroup previousGroupId;
	
	@Column(name = "PREVIOUS_PAY_TYPE")
	private String previousPayType;
	
	@Column(name = "PREVIOUS_PAY_FREQUENCY")
	private String previousPayFrequency;
	
	@Column(name = "PREVIOUS_STANDARD_HOURS")
	private String previousStandardHours;
	
	@Column(name = "PREVIOUS_ANNUAL_SALARY")
	private BigDecimal previousAnnualSalary;
	
	@Column(name = "PREVIOUS_GROSS_SALARY")
	private BigDecimal previousGrossSalary;
	
	@Column(name = "PREVIOUS_HOURLY_RATE")
	private BigDecimal previousHourlyRate;
	
	@Column( name ="PREVIOUS_INCREMENT_PERCENTAGE")
	private String previousIncrementPercentage;
	
	@Column( name ="PREVIOUS_EFFECTIVE_DATE")
	private Date previousEffectiveDate;
	
	@Column( name ="PREVIOUS_CHANGE_REASON",columnDefinition = "TEXT default NULL")
	private String previousChangeReason;

	@Column( name ="PREVIOUS_SALARY_TYPE")
	private String previousSalaryType;
	
	@Column( name ="PREVIOUS_STOP_SALARY")
	private String previousStopSalary;
	
	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "PREVIOUS_CREATED_BY")
  	private User previousCreatedBy;
    
    @Column(name = "PREVIOUS_CREATED_DATE")
  	private Date previousCreatedDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "PREVIOUS_UPDATED_BY")
  	private User previousUpdatedBy;
    
    @Column(name = "PREVIOUS_UPDATED_DATE")
  	private Date previousUpdatedDate;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "UUID")
	private String uuid;
    
    @ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "CREATED_BY")
  	private User createdBy;
    
    @Column(name = "CREATED_DATE")
  	private Date createdDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "UPDATED_BY")
  	private User updatedBy;
    
    @Column(name = "UPDATED_DATE")
  	private Date updatedDate;
    
    @NotFound(action = NotFoundAction.IGNORE)
	@OneToMany(mappedBy = "associateSalaryHistoryInd", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Fetch(FetchMode.SELECT)
	private Set<AssociateSalaryPayheadHistoryInd> associateSalaryPayheadHistoryIndList = new HashSet<AssociateSalaryPayheadHistoryInd>(0);
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserGroup getGroupId() {
		return groupId;
	}

	public void setGroupId(UserGroup groupId) {
		this.groupId = groupId;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayFrequency() {
		return payFrequency;
	}

	public void setPayFrequency(String payFrequency) {
		this.payFrequency = payFrequency;
	}

	public String getStandardHours() {
		return standardHours;
	}

	public void setStandardHours(String standardHours) {
		this.standardHours = standardHours;
	}

	public BigDecimal getAnnualSalary() {
		return annualSalary;
	}

	public void setAnnualSalary(BigDecimal annualSalary) {
		this.annualSalary = annualSalary;
	}

	public BigDecimal getGrossSalary() {
		return grossSalary;
	}

	public void setGrossSalary(BigDecimal grossSalary) {
		this.grossSalary = grossSalary;
	}

	public BigDecimal getHourlyRate() {
		return hourlyRate;
	}

	public void setHourlyRate(BigDecimal hourlyRate) {
		this.hourlyRate = hourlyRate;
	}

	public String getIncrementPercentage() {
		return incrementPercentage;
	}

	public void setIncrementPercentage(String incrementPercentage) {
		this.incrementPercentage = incrementPercentage;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}

	public String getSalaryType() {
		return salaryType;
	}

	public void setSalaryType(String salaryType) {
		this.salaryType = salaryType;
	}

	public String getStopSalary() {
		return stopSalary;
	}

	public void setStopSalary(String stopSalary) {
		this.stopSalary = stopSalary;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public AssociateSalaryInd getAssociateSalaryInd() {
		return associateSalaryInd;
	}

	public void setAssociateSalaryInd(AssociateSalaryInd associateSalaryInd) {
		this.associateSalaryInd = associateSalaryInd;
	}

	public UserGroup getPreviousGroupId() {
		return previousGroupId;
	}

	public void setPreviousGroupId(UserGroup previousGroupId) {
		this.previousGroupId = previousGroupId;
	}

	public String getPreviousPayType() {
		return previousPayType;
	}

	public void setPreviousPayType(String previousPayType) {
		this.previousPayType = previousPayType;
	}

	public String getPreviousPayFrequency() {
		return previousPayFrequency;
	}

	public void setPreviousPayFrequency(String previousPayFrequency) {
		this.previousPayFrequency = previousPayFrequency;
	}

	public String getPreviousStandardHours() {
		return previousStandardHours;
	}

	public void setPreviousStandardHours(String previousStandardHours) {
		this.previousStandardHours = previousStandardHours;
	}

	public BigDecimal getPreviousAnnualSalary() {
		return previousAnnualSalary;
	}

	public void setPreviousAnnualSalary(BigDecimal previousAnnualSalary) {
		this.previousAnnualSalary = previousAnnualSalary;
	}

	public BigDecimal getPreviousGrossSalary() {
		return previousGrossSalary;
	}

	public void setPreviousGrossSalary(BigDecimal previousGrossSalary) {
		this.previousGrossSalary = previousGrossSalary;
	}

	public BigDecimal getPreviousHourlyRate() {
		return previousHourlyRate;
	}

	public void setPreviousHourlyRate(BigDecimal previousHourlyRate) {
		this.previousHourlyRate = previousHourlyRate;
	}

	public String getPreviousIncrementPercentage() {
		return previousIncrementPercentage;
	}

	public void setPreviousIncrementPercentage(String previousIncrementPercentage) {
		this.previousIncrementPercentage = previousIncrementPercentage;
	}

	public Date getPreviousEffectiveDate() {
		return previousEffectiveDate;
	}

	public void setPreviousEffectiveDate(Date previousEffectiveDate) {
		this.previousEffectiveDate = previousEffectiveDate;
	}

	public String getPreviousChangeReason() {
		return previousChangeReason;
	}

	public void setPreviousChangeReason(String previousChangeReason) {
		this.previousChangeReason = previousChangeReason;
	}

	public String getPreviousSalaryType() {
		return previousSalaryType;
	}

	public void setPreviousSalaryType(String previousSalaryType) {
		this.previousSalaryType = previousSalaryType;
	}

	public String getPreviousStopSalary() {
		return previousStopSalary;
	}

	public void setPreviousStopSalary(String previousStopSalary) {
		this.previousStopSalary = previousStopSalary;
	}

	public User getPreviousCreatedBy() {
		return previousCreatedBy;
	}

	public void setPreviousCreatedBy(User previousCreatedBy) {
		this.previousCreatedBy = previousCreatedBy;
	}

	public Date getPreviousCreatedDate() {
		return previousCreatedDate;
	}

	public void setPreviousCreatedDate(Date previousCreatedDate) {
		this.previousCreatedDate = previousCreatedDate;
	}

	public User getPreviousUpdatedBy() {
		return previousUpdatedBy;
	}

	public void setPreviousUpdatedBy(User previousUpdatedBy) {
		this.previousUpdatedBy = previousUpdatedBy;
	}

	public Date getPreviousUpdatedDate() {
		return previousUpdatedDate;
	}

	public void setPreviousUpdatedDate(Date previousUpdatedDate) {
		this.previousUpdatedDate = previousUpdatedDate;
	}

	public Set<AssociateSalaryPayheadHistoryInd> getAssociateSalaryPayheadHistoryIndList() {
		return associateSalaryPayheadHistoryIndList;
	}

	public void setAssociateSalaryPayheadHistoryIndList(
			Set<AssociateSalaryPayheadHistoryInd> associateSalaryPayheadHistoryIndList) {
		this.associateSalaryPayheadHistoryIndList = associateSalaryPayheadHistoryIndList;
	}
}
