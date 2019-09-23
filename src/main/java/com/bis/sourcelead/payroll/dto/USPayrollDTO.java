package com.bis.sourcelead.payroll.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.dto.AssociateLoanAndAdvancesDTO;
import com.bis.sourcelead.dto.AssociatePayItemDTO;

public class USPayrollDTO {
	
	private Long empPayrollId;
	
	private String empId;
	
	private String empName;
	
	private String regularHours;	
	
	private double overtimeHours;
	
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
	
    private double totalHours;
	
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
    
    private String payrollId;
	
	private String payrollUUID;
	
	private String payItemId;
	
	private String isExcept;
	
	private Long userId;
	
	private Long paySheetFrequencyId;
	
	private Long candidateId;
	
	private BigDecimal totalDeduction;
	
	private String approverStatus;
	
	private BigDecimal localTax;
	
    private YTDPayrollDTO ytdDTO;
	
    private String assSalType;
    
    private BigDecimal assSalary;
    
    private String payType;
    
    private String employeeDesignationType;
    
    private BigDecimal payPerSal;
	
    private String depositType;
    
    private String departmentName;
    
    private Map<String, BigDecimal> tax;
    
    private List<AssociateLoanAndAdvancesDTO> loanAdvance;
    
    private BigDecimal loanAmountTotal;
    
    private List<PayrollLopHisDTO> lopHis;
    
    private BigDecimal lopAmount;
    
    
	
	public Map<String, BigDecimal> getTax()
	{
		return tax;
	}
	public void setTax(Map<String, BigDecimal> tax)
	{
		this.tax = tax;
	}
	public BigDecimal getPayPerSal()
	{
		return payPerSal;
	}
	public void setPayPerSal(BigDecimal payPerSal)
	{
		this.payPerSal = payPerSal;
	}
	public YTDPayrollDTO getYtdDTO()
	{
		return ytdDTO;
	}
	public void setYtdDTO(YTDPayrollDTO ytdDTO)
	{
		this.ytdDTO = ytdDTO;
	}
	public BigDecimal getLocalTax()
	{
		return localTax;
	}
	public void setLocalTax(BigDecimal localTax)
	{
		this.localTax = localTax;
	}
	public String getApproverStatus() {
		return approverStatus;
	}
	public void setApproverStatus(String approverStatus) {
		this.approverStatus = approverStatus;
	}
	public String getIsExcept()
	{
		return isExcept;
	}
	public void setIsExcept(String isExcept)
	{
		this.isExcept = isExcept;
	}
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
	
	public void setRegularHours(String regularHours)
	{
		this.regularHours = regularHours;
	}

	public double getOvertimeHours()
	{
		return overtimeHours;
	}
	public void setOvertimeHours(double overtimeHours)
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
	public double getTotalHours()
	{
		return totalHours;
	}
	public void setTotalHours(double totalHours)
	{
		this.totalHours = totalHours;
	}
	public String getPayrollId()
	{
		return payrollId;
	}
	public String getPayrollUUID()
	{
		return payrollUUID;
	}
	public void setPayrollId(String payrollId)
	{
		this.payrollId = payrollId;
	}
	public void setPayrollUUID(String payrollUUID)
	{
		this.payrollUUID = payrollUUID;
	}
	public String getPayItemId()
	{
		return payItemId;
	}
	public void setPayItemId(String payItemId)
	{
		this.payItemId = payItemId;
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
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getPaySheetFrequencyId() {
		return paySheetFrequencyId;
	}
	public void setPaySheetFrequencyId(Long paySheetFrequencyId) {
		this.paySheetFrequencyId = paySheetFrequencyId;
	}
	public Long getCandidateId() {
		return candidateId;
	}
	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}
	public BigDecimal getTotalDeduction()
	{
		return totalDeduction;
	}
	public void setTotalDeduction(BigDecimal totalDeduction)
	{
		this.totalDeduction = totalDeduction;
	}
	public String getRegularHours()
	{
		return regularHours;
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
	public String getPayType()
	{
		return payType;
	}
	public void setPayType(String payType)
	{
		this.payType = payType;
	}
	public String getEmployeeDesignationType() {
		return employeeDesignationType;
	}
	public void setEmployeeDesignationType(String employeeDesignationType) {
		this.employeeDesignationType = employeeDesignationType;
	}
	public String getDepositType()
	{
		return depositType;
	}
	public void setDepositType(String depositType)
	{
		this.depositType = depositType;
	}
	public String getDepartmentName()
	{
		return departmentName;
	}
	public void setDepartmentName(String departmentName)
	{
		this.departmentName = departmentName;
	}
	public List<AssociateLoanAndAdvancesDTO> getLoanAdvance() {
		return loanAdvance;
	}
	public void setLoanAdvance(List<AssociateLoanAndAdvancesDTO> loanAdvance) {
		this.loanAdvance = loanAdvance;
	}
	public BigDecimal getLoanAmountTotal() {
		return loanAmountTotal;
	}
	public void setLoanAmountTotal(BigDecimal loanAmountTotal) {
		this.loanAmountTotal = loanAmountTotal;
	}
	public List<PayrollLopHisDTO> getLopHis()
	{
		return lopHis;
	}
	public void setLopHis(List<PayrollLopHisDTO> lopHis)
	{
		this.lopHis = lopHis;
	}
	public BigDecimal getLopAmount()
	{
		return lopAmount;
	}
	public void setLopAmount(BigDecimal lopAmount)
	{
		this.lopAmount = lopAmount;
	}
	
	
}
