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

@Entity
@Table(name = "ASSOCIATE_SAL_HISTORY")
public class AssociateSalaryHistory {
	
	
	 private static final long serialVersionUID = 1L;
		
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "ID", nullable = false)
		private Long id;
		
		@ManyToOne
		@JoinColumn(name = "ASSOCIATE_SALARY_ID")
		private AssociateSalary associateSalary;
		
		@Column( name = "PAY_TYPE")
		private String payType;
		
		@Column( name = "PAY_FREQUENCY")
		private String payFrequency;
		
		@Column( name = "STANDARD_HOURS")
		private String standardHours;
		
		@Column( name = "ANNUAL_SALARY")
		private BigDecimal annualSalary;
		
		@Column( name = "GROSS_SALARY")
		private BigDecimal grossSalary;
		
		@Column( name = "HOURLY_RATE")
		private BigDecimal hourlyRate;
		
		
		@Column( name = "PREVIOUS_PAY_TYPE")
		private String previousPayType;
		
		@Column( name = "PREVIOUS_PAY_FREQUENCY")
		private String previousPayFrequency;
		
		@Column( name = "PREVIOUS_STANDARD_HOURS")
		private String previousStandardHours;
		
		@Column( name = "PREVIOUS_ANNUAL_SALARY")
		private BigDecimal previousAnnualSalary;
		
		@Column( name = "PREVIOUS_GROSS_SALARY")
		private BigDecimal previousGrossSalary;
		
		@Column( name = "PREVIOUS_HOURLY_RATE")
		private BigDecimal previousHourlyRate;
		
		@Column( name = "EFFECTIVE_DATE")
		private Date effectiveDate;
		
		@Column( name = "ASSOCIATE_SAL_HISTORY_UUID")
		private String associateSalHistoryUUID;
		
		@Column( name = "INCREASE_PERCENTAGE")
		private String increasePercentage;
		
		@Column(name = "CHANGE_REASON", columnDefinition = "TEXT default NULL")
		private String changeReason;
		

		@Column( name ="SALARY_TYPE")
		private String salaryType; 
		
		@Column( name = "STATUS")
		private Integer status;
		
		
		@Column( name = "CREATED_DATE")
		private Date createdDate;
		
		@Column( name = "CREATED_BY")
		private Long createdBy;
		
		@Column( name = "UPDATED_DATE")
		private Date updatedDate;
		
		@Column( name = "UPDATED_BY")
		private Long updatedBy;

		
		@Column(name = "PREVIOUS_CREATED_DATE")
		private Date previousCreatedDate;
		
		@Column(name = "PREVIOUS_CREATED_BY")
		private Long previousCreatedBy;
		
		@Column(name = "PREVIOUS_UPDATED_DATE")
		private Date previousUpdatedDate;
		
		@Column(name = "PREVIOUS_UPDATED_BY")
		private Long previousUpdatedBy;
		
		
		public Long getId()
		{
			return id;
		}

		public void setId(Long id)
		{
			this.id = id;
		}

		public AssociateSalary getAssociateSalary()
		{
			return associateSalary;
		}

		public void setAssociateSalary(AssociateSalary associateSalary)
		{
			this.associateSalary = associateSalary;
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

		public String getStandardHours()
		{
			return standardHours;
		}

		public void setStandardHours(String standardHours)
		{
			this.standardHours = standardHours;
		}

		public BigDecimal getAnnualSalary()
		{
			return annualSalary;
		}

		public void setAnnualSalary(BigDecimal annualSalary)
		{
			this.annualSalary = annualSalary;
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

		public void setHourlyRate(BigDecimal hourlyRate)
		{
			this.hourlyRate = hourlyRate;
		}

		public Date getEffectiveDate()
		{
			return effectiveDate;
		}

		public void setEffectiveDate(Date effectiveDate)
		{
			this.effectiveDate = effectiveDate;
		}

		public String getAssociateSalHistoryUUID()
		{
			return associateSalHistoryUUID;
		}

		public void setAssociateSalHistoryUUID(String associateSalHistoryUUID)
		{
			this.associateSalHistoryUUID = associateSalHistoryUUID;
		}

		public String getIncreasePercentage()
		{
			return increasePercentage;
		}

		public void setIncreasePercentage(String increasePercentage)
		{
			this.increasePercentage = increasePercentage;
		}

		public String getChangeReason()
		{
			return changeReason;
		}

		public void setChangeReason(String changeReason)
		{
			this.changeReason = changeReason;
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

		public Long getCreatedBy()
		{
			return createdBy;
		}

		public void setCreatedBy(Long createdBy)
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

		public Long getUpdatedBy()
		{
			return updatedBy;
		}

		public void setUpdatedBy(Long updatedBy)
		{
			this.updatedBy = updatedBy;
		}

	

		public String getPreviousPayFrequency()
		{
			return previousPayFrequency;
		}

		public String getPreviousStandardHours()
		{
			return previousStandardHours;
		}

		public BigDecimal getPreviousAnnualSalary()
		{
			return previousAnnualSalary;
		}

		public BigDecimal getPreviousGrossSalary()
		{
			return previousGrossSalary;
		}

		public BigDecimal getPreviousHourlyRate()
		{
			return previousHourlyRate;
		}

		public String getSalaryType()
		{
			return salaryType;
		}


		public String getPreviousPayType()
		{
			return previousPayType;
		}

		public void setPreviousPayType(String previousPayType)
		{
			this.previousPayType = previousPayType;
		}

		public void setPreviousPayFrequency(String previousPayFrequency)
		{
			this.previousPayFrequency = previousPayFrequency;
		}

		public void setPreviousStandardHours(String previousStandardHours)
		{
			this.previousStandardHours = previousStandardHours;
		}

		public void setPreviousAnnualSalary(BigDecimal previousAnnualSalary)
		{
			this.previousAnnualSalary = previousAnnualSalary;
		}

		public void setPreviousGrossSalary(BigDecimal previousGrossSalary)
		{
			this.previousGrossSalary = previousGrossSalary;
		}

		public void setPreviousHourlyRate(BigDecimal previousHourlyRate)
		{
			this.previousHourlyRate = previousHourlyRate;
		}

		public void setSalaryType(String salaryType)
		{
			this.salaryType = salaryType;
		}

		public Date getPreviousCreatedDate()
		{
			return previousCreatedDate;
		}

		public Long getPreviousCreatedBy()
		{
			return previousCreatedBy;
		}

		public Date getPreviousUpdatedDate()
		{
			return previousUpdatedDate;
		}

		
		public void setPreviousCreatedDate(Date previousCreatedDate)
		{
			this.previousCreatedDate = previousCreatedDate;
		}

		public void setPreviousCreatedBy(Long previousCreatedBy)
		{
			this.previousCreatedBy = previousCreatedBy;
		}

		public void setPreviousUpdatedDate(Date previousUpdatedDate)
		{
			this.previousUpdatedDate = previousUpdatedDate;
		}

		public Long getPreviousUpdatedBy()
		{
			return previousUpdatedBy;
		}

		public void setPreviousUpdatedBy(Long previousUpdatedBy)
		{
			this.previousUpdatedBy = previousUpdatedBy;
		}

		
		
		
}
