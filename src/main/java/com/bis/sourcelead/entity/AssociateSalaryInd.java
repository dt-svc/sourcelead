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
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "ASSOCIATE_SALARY_IND")
public class AssociateSalaryInd {
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
	@OneToMany(mappedBy = "associateSalaryInd", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Fetch(FetchMode.SELECT)
	private Set<AssociateSalaryPayheadInd> associateSalaryPayheadIndList = new HashSet<AssociateSalaryPayheadInd>(0);
    
    @Transient
    private String effectiveDateStr;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrgLocations getOrgLocations() {
		return orgLocations;
	}

	public void setOrgLocations(OrgLocations orgLocations) {
		this.orgLocations = orgLocations;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
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

	public Set<AssociateSalaryPayheadInd> getAssociateSalaryPayheadIndList() {
		return associateSalaryPayheadIndList;
	}

	public void setAssociateSalaryPayheadIndList(Set<AssociateSalaryPayheadInd> associateSalaryPayheadIndList) {
		this.associateSalaryPayheadIndList = associateSalaryPayheadIndList;
	}

	public String getEffectiveDateStr() {
		return effectiveDateStr;
	}

	public void setEffectiveDateStr(String effectiveDateStr) {
		this.effectiveDateStr = effectiveDateStr;
	}
}
