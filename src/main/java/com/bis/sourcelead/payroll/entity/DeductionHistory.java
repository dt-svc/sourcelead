package com.bis.sourcelead.payroll.entity;

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
import javax.persistence.Transient;

@Entity
@Table(name = "US_EMP_DEDUCTION_HIS")
public class DeductionHistory {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "CANDIDATE_ID")
	private Long candidateId;
	
	@ManyToOne
	@JoinColumn(name = "DEDUCTION_ID")
	private AssociateDeduction deductionId;
	
	@ManyToOne
	@JoinColumn(name = "CONTRIBUTION_ID")
	private AssociateContribution contributionId;
	
	@ManyToOne
	@JoinColumn(name = "PAYROLL_ID")
	private EmployeePayroll payroll;
	
	@Column(name = "FREQUENCY_START_DATE")
	private Date frequencyStartDate;
	
	@Column(name = "FREQUENCY_END_DATE")
	private Date frequencyEndDate;
	
	@Column(name = "PAY_CHECK_DATE")
	private Date payCheckDate;
	
	@Column(name = "US_EMP_DEDUCTION_HIS_UUID")
	private String deductionHisUUID;
	
	@Column(name = "PRE_DEDUCTION_AMOUNT")
	private BigDecimal preDeductionAmount;
	
	@Column(name = "PRE_CONTRIBUTION_AMOUNT")
	private BigDecimal preContributionAmount;
	
	@Column(name = "POST_DEDUCTION_AMOUNT")
	private BigDecimal postDeductionAmount;
	
	@Column(name = "POST_CONTRIBUTION_AMOUNT")
	private BigDecimal postContributionAmount;
	
	
	@Column(name = "CONTRIBUTION_AMOUNT")
	private BigDecimal contributionAmount;
	 
	@Column(name = "DEDUCTION_DATE")
	private Date deductionDate;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private Long createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private Long updatedBy;
	
	@Transient
	private String deductionType;
	
	@Transient
	private String contributionType;

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public Long getId()
	{
		return id;
	}



	public Date getFrequencyStartDate()
	{
		return frequencyStartDate;
	}

	public Date getFrequencyEndDate()
	{
		return frequencyEndDate;
	}

	public Date getPayCheckDate()
	{
		return payCheckDate;
	}

	public Date getDeductionDate()
	{
		return deductionDate;
	}

	public Date getCreatedDate()
	{
		return createdDate;
	}


	public Date getUpdatedDate()
	{
		return updatedDate;
	}


	public void setId(Long id)
	{
		this.id = id;
	}


	public void setFrequencyStartDate(Date frequencyStartDate)
	{
		this.frequencyStartDate = frequencyStartDate;
	}

	public void setFrequencyEndDate(Date frequencyEndDate)
	{
		this.frequencyEndDate = frequencyEndDate;
	}

	public void setPayCheckDate(Date payCheckDate)
	{
		this.payCheckDate = payCheckDate;
	}

	public void setDeductionDate(Date deductionDate)
	{
		this.deductionDate = deductionDate;
	}

	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}

	public void setUpdatedDate(Date updatedDate)
	{
		this.updatedDate = updatedDate;
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

	public String getDeductionHisUUID()
	{
		return deductionHisUUID;
	}

	public void setDeductionHisUUID(String deductionHisUUID)
	{
		this.deductionHisUUID = deductionHisUUID;
	}

	public EmployeePayroll getPayroll()
	{
		return payroll;
	}

	public void setPayroll(EmployeePayroll payroll)
	{
		this.payroll = payroll;
	}

	public Long getCandidateId()
	{
		return candidateId;
	}

	public void setCandidateId(Long candidateId)
	{
		this.candidateId = candidateId;
	}

	public AssociateDeduction getDeductionId()
	{
		return deductionId;
	}

	public AssociateContribution getContributionId()
	{
		return contributionId;
	}

	public void setDeductionId(AssociateDeduction deductionId)
	{
		this.deductionId = deductionId;
	}

	public void setContributionId(AssociateContribution contributionId)
	{
		this.contributionId = contributionId;
	}

	public BigDecimal getPreDeductionAmount()
	{
		return preDeductionAmount;
	}

	public BigDecimal getPreContributionAmount()
	{
		return preContributionAmount;
	}

	public BigDecimal getPostDeductionAmount()
	{
		return postDeductionAmount;
	}

	public BigDecimal getPostContributionAmount()
	{
		return postContributionAmount;
	}

	public void setPreDeductionAmount(BigDecimal preDeductionAmount)
	{
		if(preDeductionAmount !=null) {
			this.preDeductionAmount = preDeductionAmount;
		}else{
			this.preDeductionAmount = BigDecimal.ZERO;
		}
		
	}

	public void setPreContributionAmount(BigDecimal preContributionAmount)
	{
		if(preContributionAmount !=null) {
			this.preContributionAmount = preContributionAmount;
		}else{
			this.preContributionAmount = BigDecimal.ZERO;
		}
	}

	public void setPostDeductionAmount(BigDecimal postDeductionAmount)
	{
		if(postDeductionAmount != null) {
			this.postDeductionAmount = postDeductionAmount;	
		}else {
			this.postDeductionAmount = BigDecimal.ZERO;	
		}
		
	}

	public void setPostContributionAmount(BigDecimal postContributionAmount)
	{
		if(postContributionAmount != null) {
			this.postContributionAmount = postContributionAmount;	
		}else {
			this.postContributionAmount = BigDecimal.ZERO;
		}
		
	}

	public BigDecimal getContributionAmount()
	{
		return contributionAmount;
	}

	public void setContributionAmount(BigDecimal contributionAmount)
	{
		if(contributionAmount != null) {
			this.contributionAmount = contributionAmount;
		}else {
			this.contributionAmount = BigDecimal.ZERO;
		}
		
	}

	public String getDeductionType() {
		return deductionType;
	}

	public void setDeductionType(String deductionType) {
		this.deductionType = deductionType;
	}

	public String getContributionType() {
		return contributionType;
	}

	public void setContributionType(String contributionType) {
		this.contributionType = contributionType;
	}
	
	
	
}
