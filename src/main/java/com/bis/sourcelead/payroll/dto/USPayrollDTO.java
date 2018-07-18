package com.bis.sourcelead.payroll.dto;

import java.math.BigDecimal;
import java.util.List;

import com.bis.sourcelead.dto.AssociatePayItemDTO;

public class USPayrollDTO {
	
	private Long empPayrollId;
	
	private String empId;
	
	private String empName;
	
	private float regularHours;	
	
	private String overtimeHours;
	
	private BigDecimal grossPay;
	
	private BigDecimal preTaxDeduction;
	
	private BigDecimal postTaxDeduction;
	
	private BigDecimal taxes;	
	
	private BigDecimal benefits;
	
	private BigDecimal netPay;
	
	private BigDecimal regularHrsTotalAmt;	
	
	private BigDecimal otHoursTotalAmt;
	
	private BigDecimal payRate;
	
	private String empPayrollUUID;
	
	private String employeeAddress;
	
    private float totalHours;
	
	private BigDecimal totalEarnings;
	
    private BigDecimal federal;
     
    private BigDecimal medicare;
     
    private BigDecimal fica;
     
    private BigDecimal stateWithHolding;
     
    private BigDecimal aeic;
    
    private List<DeductionDTO> preDed;
    
    private List<DeductionDTO> postDed;
    
    private BigDecimal preDedTotal;
    
    private BigDecimal postDedTotal;
    
    private BigDecimal contributionTotal;
    
    private List<ContributionDTO> contribution;
    
    private List<AssociatePayItemDTO> payItem; 
    
    private BigDecimal payItemTotal;
	
	public List<ContributionDTO> getContribution()
	{
		return contribution;
	}
	public void setContribution(List<ContributionDTO> contribution)
	{
		this.contribution = contribution;
	}
	public Long getEmpPayrollId()
	{
		return empPayrollId;
	}
	public String getEmpPayrollUUID()
	{
		return empPayrollUUID;
	}
	public void setEmpPayrollId(Long empPayrollId)
	{
		this.empPayrollId = empPayrollId;
	}
	public void setEmpPayrollUUID(String empPayrollUUID)
	{
		this.empPayrollUUID = empPayrollUUID;
	}
	public String getEmpId()
	{
		return empId;
	}
	public void setEmpId(String empId)
	{
		this.empId = empId;
	}
	public String getEmpName()
	{
		return empName;
	}
	public void setEmpName(String empName)
	{
		this.empName = empName;
	}
	public float getRegularHours()
	{
		return regularHours;
	}
	public void setRegularHours(float regularHours)
	{
		this.regularHours = regularHours;
	}
	public String getOvertimeHours()
	{
		return overtimeHours;
	}
	public void setOvertimeHours(String overtimeHours)
	{
		this.overtimeHours = overtimeHours;
	}
	public BigDecimal getGrossPay()
	{
		return grossPay;
	}
	public void setGrossPay(BigDecimal grossPay)
	{
		this.grossPay = grossPay;
	}
	public BigDecimal getPreTaxDeduction()
	{
		return preTaxDeduction;
	}
	public void setPreTaxDeduction(BigDecimal preTaxDeduction)
	{
		this.preTaxDeduction = preTaxDeduction;
	}
	public BigDecimal getPostTaxDeduction()
	{
		return postTaxDeduction;
	}
	public void setPostTaxDeduction(BigDecimal postTaxDeduction)
	{
		this.postTaxDeduction = postTaxDeduction;
	}
	public BigDecimal getTaxes()
	{
		return taxes;
	}
	public void setTaxes(BigDecimal taxes)
	{
		this.taxes = taxes;
	}
	public BigDecimal getBenefits()
	{
		return benefits;
	}
	public void setBenefits(BigDecimal benefits)
	{
		this.benefits = benefits;
	}
	public BigDecimal getNetPay()
	{
		return netPay;
	}
	public void setNetPay(BigDecimal netPay)
	{
		this.netPay = netPay;
	}
	public BigDecimal getRegularHrsTotalAmt()
	{
		return regularHrsTotalAmt;
	}
	public void setRegularHrsTotalAmt(BigDecimal regularHrsTotalAmt)
	{
		this.regularHrsTotalAmt = regularHrsTotalAmt;
	}
	public BigDecimal getOtHoursTotalAmt()
	{
		return otHoursTotalAmt;
	}
	public void setOtHoursTotalAmt(BigDecimal otHoursTotalAmt)
	{
		this.otHoursTotalAmt = otHoursTotalAmt;
	}
	public BigDecimal getPayRate()
	{
		return payRate;
	}
	public void setPayRate(BigDecimal payRate)
	{
		this.payRate = payRate;
	}
	public float getTotalHours()
	{
		return totalHours;
	}
	public BigDecimal getTotalEarnings()
	{
		return totalEarnings;
	}
	public void setTotalHours(float totalHours)
	{
		this.totalHours = totalHours;
	}
	public void setTotalEarnings(BigDecimal totalEarnings)
	{
		this.totalEarnings = totalEarnings;
	}
	public String getEmployeeAddress()
	{
		return employeeAddress;
	}
	public void setEmployeeAddress(String employeeAddress)
	{
		this.employeeAddress = employeeAddress;
	}

	public BigDecimal getFederal()
	{
		return federal;
	}
	public BigDecimal getMedicare()
	{
		return medicare;
	}
	public BigDecimal getFica()
	{
		return fica;
	}
	public BigDecimal getStateWithHolding()
	{
		return stateWithHolding;
	}
	public BigDecimal getAeic()
	{
		return aeic;
	}
	public void setFederal(BigDecimal federal)
	{
		this.federal = federal;
	}
	public void setMedicare(BigDecimal medicare)
	{
		this.medicare = medicare;
	}
	public void setFica(BigDecimal fica)
	{
		this.fica = fica;
	}
	public void setStateWithHolding(BigDecimal stateWithHolding)
	{
		this.stateWithHolding = stateWithHolding;
	}
	public void setAeic(BigDecimal aeic)
	{
		this.aeic = aeic;
	}
	public List<DeductionDTO> getPreDed()
	{
		return preDed;
	}
	public List<DeductionDTO> getPostDed()
	{
		return postDed;
	}
	public void setPreDed(List<DeductionDTO> preDed)
	{
		this.preDed = preDed;
	}
	public void setPostDed(List<DeductionDTO> postDed)
	{
		this.postDed = postDed;
	}
	public BigDecimal getPreDedTotal()
	{
		return preDedTotal;
	}
	public BigDecimal getPostDedTotal()
	{
		return postDedTotal;
	}
	public void setPreDedTotal(BigDecimal preDedTotal)
	{
		this.preDedTotal = preDedTotal;
	}
	public void setPostDedTotal(BigDecimal postDedTotal)
	{
		this.postDedTotal = postDedTotal;
	}
	public BigDecimal getContributionTotal()
	{
		return contributionTotal;
	}
	public void setContributionTotal(BigDecimal contributionTotal)
	{
		this.contributionTotal = contributionTotal;
	}
	public List<AssociatePayItemDTO> getPayItem()
	{
		return payItem;
	}
	public void setPayItem(List<AssociatePayItemDTO> payItem)
	{
		this.payItem = payItem;
	}
	public BigDecimal getPayItemTotal()
	{
		return payItemTotal;
	}
	public void setPayItemTotal(BigDecimal payItemTotal)
	{
		this.payItemTotal = payItemTotal;
	}
	
	
	
}
