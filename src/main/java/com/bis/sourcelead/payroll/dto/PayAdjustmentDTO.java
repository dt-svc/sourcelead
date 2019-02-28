package com.bis.sourcelead.payroll.dto;

import java.math.BigDecimal;
import java.util.List;

import com.bis.sourcelead.dto.AssociateLoanAndAdvancesDTO;
import com.bis.sourcelead.dto.AssociatePayItemDTO;

public class PayAdjustmentDTO {
	
	private String payrollId;
	
	private String payrollUUID;
	
	private String depositType;
	
	private String regularHours;
	
	private String regularHoursAmt;
	
	private String overTimeHours;
	
	private String overTimeHoursAmt;
	
    private String preRregularHours;
	
	private String preRegularHoursAmt;
	
	private String preOverTimeHours;
	
	private String preOverTimeHoursAmt;

	
	private List<AssociatePayItemDTO> payItem;
	
	private List<DeductionDTO> preDeduction;
	
	private List<DeductionDTO> postDeduction;
	
	private List<ContributionDTO> contribution;
	
	private String assSalType;
    
    private BigDecimal assSalary;
    
    private BigDecimal assSalaryAmount;
    
    private List<AssociateLoanAndAdvancesDTO> loanAdvance;
	
    private List<PayrollLopHisDTO> lopHis;
    
    private BigDecimal totLopAmount;
    
    private BigDecimal preAssSalaryAmount;
    
	public BigDecimal getAssSalaryAmount()
	{
		return assSalaryAmount;
	}

	public void setAssSalaryAmount(BigDecimal assSalaryAmount)
	{
		this.assSalaryAmount = assSalaryAmount;
	}

	public String getDepositType()
	{
		return depositType;
	}

	public String getRegularHours()
	{
		return regularHours;
	}

	public String getRegularHoursAmt()
	{
		return regularHoursAmt;
	}

	public String getOverTimeHours()
	{
		return overTimeHours;
	}

	public String getOverTimeHoursAmt()
	{
		return overTimeHoursAmt;
	}

	public void setDepositType(String depositType)
	{
		this.depositType = depositType;
	}

	public void setRegularHours(String regularHours)
	{
		this.regularHours = regularHours;
	}

	public void setRegularHoursAmt(String regularHoursAmt)
	{
		this.regularHoursAmt = regularHoursAmt;
	}

	public void setOverTimeHours(String overTimeHours)
	{
		this.overTimeHours = overTimeHours;
	}

	public void setOverTimeHoursAmt(String overTimeHoursAmt)
	{
		this.overTimeHoursAmt = overTimeHoursAmt;
	}

	public String getPayrollId()
	{
		return payrollId;
	}

	public String getPayrollUUID()
	{
		return payrollUUID;
	}

	public List<AssociatePayItemDTO> getPayItem()
	{
		return payItem;
	}

	public List<DeductionDTO> getPreDeduction()
	{
		return preDeduction;
	}

	public List<DeductionDTO> getPostDeduction()
	{
		return postDeduction;
	}

	public void setPayrollId(String payrollId)
	{
		this.payrollId = payrollId;
	}

	public void setPayrollUUID(String payrollUUID)
	{
		this.payrollUUID = payrollUUID;
	}

	public void setPayItem(List<AssociatePayItemDTO> payItem)
	{
		this.payItem = payItem;
	}

	public void setPreDeduction(List<DeductionDTO> preDeduction)
	{
		this.preDeduction = preDeduction;
	}

	public void setPostDeduction(List<DeductionDTO> postDeduction)
	{
		this.postDeduction = postDeduction;
	}

	public List<ContributionDTO> getContribution()
	{
		return contribution;
	}

	public void setContribution(List<ContributionDTO> contribution)
	{
		this.contribution = contribution;
	}

	public String getPreRregularHours()
	{
		return preRregularHours;
	}

	public String getPreRegularHoursAmt()
	{
		return preRegularHoursAmt;
	}

	public String getPreOverTimeHours()
	{
		return preOverTimeHours;
	}

	public String getPreOverTimeHoursAmt()
	{
		return preOverTimeHoursAmt;
	}

	public void setPreRregularHours(String preRregularHours)
	{
		this.preRregularHours = preRregularHours;
	}

	public void setPreRegularHoursAmt(String preRegularHoursAmt)
	{
		this.preRegularHoursAmt = preRegularHoursAmt;
	}

	public void setPreOverTimeHours(String preOverTimeHours)
	{
		this.preOverTimeHours = preOverTimeHours;
	}

	public void setPreOverTimeHoursAmt(String preOverTimeHoursAmt)
	{
		this.preOverTimeHoursAmt = preOverTimeHoursAmt;
	}

	public String getAssSalType()
	{
		return assSalType;
	}

	public void setAssSalType(String assSalType)
	{
		this.assSalType = assSalType;
	}

	public BigDecimal getAssSalary()
	{
		return assSalary;
	}

	public void setAssSalary(BigDecimal assSalary)
	{
		this.assSalary = assSalary;
	}

	public List<AssociateLoanAndAdvancesDTO> getLoanAdvance() {
		return loanAdvance;
	}

	public void setLoanAdvance(List<AssociateLoanAndAdvancesDTO> loanAdvance) {
		this.loanAdvance = loanAdvance;
	}

	public List<PayrollLopHisDTO> getLopHis()
	{
		return lopHis;
	}

	public void setLopHis(List<PayrollLopHisDTO> lopHis)
	{
		this.lopHis = lopHis;
	}

	public BigDecimal getTotLopAmount()
	{
		return totLopAmount;
	}

	public void setTotLopAmount(BigDecimal totLopAmount)
	{
		this.totLopAmount = totLopAmount;
	}

	public BigDecimal getPreAssSalaryAmount() {
		return preAssSalaryAmount;
	}

	public void setPreAssSalaryAmount(BigDecimal preAssSalaryAmount) {
		this.preAssSalaryAmount = preAssSalaryAmount;
	}
	
}
