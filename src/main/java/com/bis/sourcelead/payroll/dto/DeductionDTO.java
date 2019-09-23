package com.bis.sourcelead.payroll.dto;

import java.math.BigDecimal;
import java.util.Set;

import com.bis.sourcelead.payroll.entity.DeductionHistory;

public class DeductionDTO {
	
	private String id;
	
	private String deductionType;
	
	private String method;
	
	private BigDecimal amount;
	
	private double dedAmount;
	
	private String dedSchedule;
	
	private BigDecimal[] dedTaxes;
	
	private Set<DeductionHistory> dedHis;
	
	private BigDecimal[] contributionTaxes;
	
	private String preDeductionType;
	
	private String preDeductionAmt;
	
	private double preDeductionAmount;
	
	private double postDeductionAmt;
	
	
	public double getDedAmount()
	{
		return dedAmount;
	}

	public void setDedAmount(double dedAmount)
	{
		this.dedAmount = dedAmount;
	}

	private String description;
	
	private String associateDeductionUUID;
	
	private String dedTaxType;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getDeductionType()
	{
		return deductionType;
	}

	public void setDeductionType(String deductionType)
	{
		this.deductionType = deductionType;
	}

	public String getMethod()
	{
		return method;
	}

	public void setMethod(String method)
	{
		this.method = method;
	}

	public BigDecimal getAmount()
	{
		return amount;
	}

	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getAssociateDeductionUUID()
	{
		return associateDeductionUUID;
	}

	public void setAssociateDeductionUUID(String associateDeductionUUID)
	{
		this.associateDeductionUUID = associateDeductionUUID;
	}

	public String getDedTaxType()
	{
		return dedTaxType;
	}

	public void setDedTaxType(String dedTaxType)
	{
		this.dedTaxType = dedTaxType;
	}

	public String getDedSchedule()
	{
		return dedSchedule;
	}

	public void setDedSchedule(String dedSchedule)
	{
		this.dedSchedule = dedSchedule;
	}

	public BigDecimal[] getDedTaxes()
	{
		return dedTaxes;
	}

	public Set<DeductionHistory> getDedHis()
	{
		return dedHis;
	}

	public void setDedTaxes(BigDecimal[] dedTaxes)
	{
		this.dedTaxes = dedTaxes;
	}

	public void setDedHis(Set<DeductionHistory> dedHis)
	{
		this.dedHis = dedHis;
	}

	public BigDecimal[] getContributionTaxes()
	{
		return contributionTaxes;
	}

	public void setContributionTaxes(BigDecimal[] contributionTaxes)
	{
		this.contributionTaxes = contributionTaxes;
	}

	public String getPreDeductionType()
	{
		return preDeductionType;
	}

	public String getPreDeductionAmt()
	{
		String s = "0";
		if(preDeductionAmt != null) {
			return preDeductionAmt;		
		}else {
			return s;
		}
	}

	public void setPreDeductionType(String preDeductionType)
	{
		this.preDeductionType = preDeductionType;
	}

	public void setPreDeductionAmt(String preDeductionAmt)
	{
		this.preDeductionAmt = preDeductionAmt;
	}

	public double getPostDeductionAmt()
	{
		return postDeductionAmt;
	}


	public void setPostDeductionAmt(double postDeductionAmt)
	{
		this.postDeductionAmt = postDeductionAmt;
	}

	public double getPreDeductionAmount()
	{
		return preDeductionAmount;
	}

	public void setPreDeductionAmount(double preDeductionAmount)
	{
		this.preDeductionAmount = preDeductionAmount;
	}

	
	
	
}
