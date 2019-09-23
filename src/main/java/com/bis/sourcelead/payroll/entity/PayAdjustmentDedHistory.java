package com.bis.sourcelead.payroll.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PAY_ADJUSTMENT_DED_HISTORY")
public class PayAdjustmentDedHistory {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "PAY_ADJUSTMENT_HISTORY_ID", nullable = false)
	private PayAdjustmentHistory payAdjustmentHistory;
	
	@Column (name="DEDUCTION_ID")
	private Long deductionId;
	
	@Column (name="CONTRIBUTION_ID")
	private Long contributionId;
	
	@Column (name="PRE_DEDUCTION_TYPE")
	private String preDeductionType;
	
	@Column (name="PRE_DEDUCTION_AMOUNT")
	private BigDecimal preDeductionAmt;
	
	@Column (name="DEDUCTION_TYPE")
	private String deductionType;
	
	@Column (name="DEDUCTION_AMOUNT")
	private BigDecimal deductionAmt;
	
	@Column (name="PRE_CONTRIBUTION_TYPE")
	private String preContributionType;
	
	@Column (name="PRE_CONTRIBUTION_AMOUNT")
	private BigDecimal preContributionAmt;
	
	@Column (name="CONTRIBUTION_TYPE")
	private String contributionType;
	
	@Column (name="CONTRIBUTION_AMOUNT")
	private BigDecimal contributionAmt;

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public Long getId()
	{
		return id;
	}

	public PayAdjustmentHistory getPayAdjustmentHistory()
	{
		return payAdjustmentHistory;
	}

	public Long getDeductionId()
	{
		return deductionId;
	}

	public Long getContributionId()
	{
		return contributionId;
	}

	public String getPreDeductionType()
	{
		return preDeductionType;
	}

	public BigDecimal getPreDeductionAmt()
	{
		return preDeductionAmt;
	}

	public String getDeductionType()
	{
		return deductionType;
	}

	public BigDecimal getDeductionAmt()
	{
		return deductionAmt;
	}

	public String getPreContributionType()
	{
		return preContributionType;
	}

	public BigDecimal getPreContributionAmt()
	{
		return preContributionAmt;
	}

	public String getContributionType()
	{
		return contributionType;
	}

	public BigDecimal getContributionAmt()
	{
		return contributionAmt;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public void setPayAdjustmentHistory(PayAdjustmentHistory payAdjustmentHistory)
	{
		this.payAdjustmentHistory = payAdjustmentHistory;
	}

	public void setDeductionId(Long deductionId)
	{
		this.deductionId = deductionId;
	}

	public void setContributionId(Long contributionId)
	{
		this.contributionId = contributionId;
	}

	public void setPreDeductionType(String preDeductionType)
	{
		this.preDeductionType = preDeductionType;
	}

	public void setPreDeductionAmt(BigDecimal preDeductionAmt)
	{
		this.preDeductionAmt = preDeductionAmt;
	}

	public void setDeductionType(String deductionType)
	{
		this.deductionType = deductionType;
	}

	public void setDeductionAmt(BigDecimal deductionAmt)
	{
		this.deductionAmt = deductionAmt;
	}

	public void setPreContributionType(String preContributionType)
	{
		this.preContributionType = preContributionType;
	}

	public void setPreContributionAmt(BigDecimal preContributionAmt)
	{
		this.preContributionAmt = preContributionAmt;
	}

	public void setContributionType(String contributionType)
	{
		this.contributionType = contributionType;
	}

	public void setContributionAmt(BigDecimal contributionAmt)
	{
		this.contributionAmt = contributionAmt;
	}
	
	
}
