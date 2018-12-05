package com.bis.sourcelead.payroll.entity;

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

import com.bis.sourcelead.entity.AssociateLoanAndAdvanceHistory;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Employees;

@Entity
@Table(name = "US_EMP_PAYROLL")
public class EmployeePayroll {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "EMPLOYEE_ID", nullable = false)
	private Employees employee;

	@ManyToOne
	@JoinColumn(name = "CANDIDATE_ID", nullable = false)
	private Candidate candidate;
	
	@ManyToOne
	@JoinColumn(name = "PAY_SHEET_FREQUENCY_ID", nullable = false)
	private PaysheetFrequency paysheetFrequency;
	
	
	@Column(name = "PAY_TYPE")
	private String payType; 
	 
	@Column(name = "PAY_PER_SALARY")
    private BigDecimal payPerSalary;
	
	@Column(name = "ADVANCE_AMOUNT")
    private BigDecimal advanceAmount;
	
	@Column(name = "LOAN_AMOUNT")
    private BigDecimal loanAmount;
	
	@Column(name = "EMP_PAY_RATE")
	private BigDecimal empPayRate;
	
	@Column(name = "REGULAR_HOURS")
	private String regularHours;
	
	@Column(name = "REGULAR_HOURS_AMOUNT")
	private BigDecimal regularHoursAmount;
	
	@Column(name = "OVER_TIME_AMOUNT")
	private BigDecimal overTimeAmount;
	
	@Column (name = "OVER_TIME_HOURS")
	private String overTimeHours;
	
	@Column(name = "GROSS_PAY")
	private BigDecimal grossPay;
	
	@Column(name = "PRE_TAX_DEDUCTION")
	private BigDecimal preTaxDeduction;
	
	@Column(name = "POST_TAX_DEDUCTION")
	private BigDecimal postTaxDeduction;
	
	@Column(name = "NET_PAY")
	private BigDecimal netPay;
	
	@Column(name = "PAY_PERIOD_NUM")
	private String payPeriodNum;
	
	@Column(name = "US_EMP_PAYROLL_UUID")
	private String usEmpPayrollUUID;
	
	@Column (name="SUBMIT_FOR_APROVER_STATUS")
	private String submitForAproverStatus;
	
	@Column (name="TYPE")
	private String type;
	
	@Column (name = "FREQUENCY")
	private Integer frequency;
	
	@Column (name = "PAY_CHECK_DATE")
	private Date payCheckDate;
	
	@Column (name = "DEPOSIT_TYPE")
	private String depositType;
	
	@Column (name = "PRE_TAX_CONTRIBUTION")
	private BigDecimal preTaxContribution;
	
	@Column (name = "POST_TAX_CONTRIBUTION")
	private BigDecimal postTaxContribution;
	
	@Column (name = "PAYITEM_SUM")
	private BigDecimal payitemSum;
	
	@Column (name = "PAY_SLIP_FILE_NAME")
	private String paySlipFileName;
	
	@Column (name = "PAY_SLIP_FILE_URL")
	private String paySlipFileURL;
	
	
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
	
	@Column(name = "TAXES")
	private BigDecimal taxes;
	
	
	@Column(name = "EXPENSES_AMOUNT")
	private BigDecimal expensesAmount;
	
	@Column(name = "LOP_AMOUNT")
	private BigDecimal lopAmount;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "employeePayroll")
	private Set<EmployeePayrollTax> usPayrollTax;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "employeePayroll")
	private Set<EmployerPayrollTax> employerpayrolltax;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "payroll")
	private Set<DeductionHistory> deductionHistory;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "payrollId")
	private Set<PayitemHistory> payitemHistory;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "payroll")
	private Set<BankDataHistory> bankDataHistory;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "employeePayroll")
	private Set<AssociateTaxHistory> associateTaxHistory;
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "payroll")
	private Set<AssociateLoanAndAdvanceHistory> loanHistory;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "payroll")
	private Set<PayrollLopHis> payrollLopHis;
	
	
	
	public Set<AssociateLoanAndAdvanceHistory> getLoanHistory() {
		return loanHistory;
	}

	public void setLoanHistory(Set<AssociateLoanAndAdvanceHistory> loanHistory) {
		this.loanHistory = loanHistory;
	}

	public String getSubmitForAproverStatus()
	{
		return submitForAproverStatus;
	}

	public void setSubmitForAproverStatus(String submitForAproverStatus)
	{
		this.submitForAproverStatus = submitForAproverStatus;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public Long getId()
	{
		return id;
	}



	public PaysheetFrequency getPaysheetFrequency()
	{
		return paysheetFrequency;
	}

	public BigDecimal getEmpPayRate()
	{
		return empPayRate;
	}

	public String getRegularHours()
	{
		return regularHours;
	}


	public BigDecimal getOverTimeAmount()
	{
		return overTimeAmount;
	}

	public BigDecimal getGrossPay()
	{
		return grossPay;
	}

	public BigDecimal getPreTaxDeduction()
	{
		return preTaxDeduction;
	}

	public BigDecimal getPostTaxDeduction()
	{
		return postTaxDeduction;
	}

	public BigDecimal getNetPay()
	{
		return netPay;
	}

	public String getPayPeriodNum()
	{
		return payPeriodNum;
	}

	public Integer getStatus()
	{
		return status;
	}

	public Date getCreatedDate()
	{
		return createdDate;
	}

	public Long getCreatedBy()
	{
		return createdBy;
	}

	public Date getUpdatedDate()
	{
		return updatedDate;
	}


	public Set<EmployerPayrollTax> getEmployerpayrolltax()
	{
		return employerpayrolltax;
	}

	public void setId(Long id)
	{
		this.id = id;
	}


	public void setPaysheetFrequency(PaysheetFrequency paysheetFrequency)
	{
		this.paysheetFrequency = paysheetFrequency;
	}

	public void setEmpPayRate(BigDecimal empPayRate)
	{
		this.empPayRate = empPayRate;
	}

	public void setRegularHours(String regularHours)
	{
		this.regularHours = regularHours;
	}


	public BigDecimal getRegularHoursAmount()
	{
		return regularHoursAmount;
	}

	public void setRegularHoursAmount(BigDecimal regularHoursAmount)
	{
		this.regularHoursAmount = regularHoursAmount;
	}

	public void setOverTimeAmount(BigDecimal overTimeAmount)
	{
		this.overTimeAmount = overTimeAmount;
	}

	public void setGrossPay(BigDecimal grossPay)
	{
		this.grossPay = grossPay;
	}

	public void setPreTaxDeduction(BigDecimal preTaxDeduction)
	{
		this.preTaxDeduction = preTaxDeduction;
	}

	public void setPostTaxDeduction(BigDecimal postTaxDeduction)
	{
		this.postTaxDeduction = postTaxDeduction;
	}

	public void setNetPay(BigDecimal netPay)
	{
		this.netPay = netPay;
	}

	public void setPayPeriodNum(String payPeriodNum)
	{
		this.payPeriodNum = payPeriodNum;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}

	public void setCreatedBy(Long createdBy)
	{
		this.createdBy = createdBy;
	}

	public void setUpdatedDate(Date updatedDate)
	{
		this.updatedDate = updatedDate;
	}


	public void setEmployerpayrolltax(Set<EmployerPayrollTax> employerpayrolltax)
	{
		this.employerpayrolltax = employerpayrolltax;
	}

	public String getUsEmpPayrollUUID()
	{
		return usEmpPayrollUUID;
	}

	public Long getUpdatedBy()
	{
		return updatedBy;
	}

	public void setUsEmpPayrollUUID(String usEmpPayrollUUID)
	{
		this.usEmpPayrollUUID = usEmpPayrollUUID;
	}

	public void setUpdatedBy(Long updatedBy)
	{
		this.updatedBy = updatedBy;
	}

	public BigDecimal getTaxes()
	{
		return taxes;
	}

	public void setTaxes(BigDecimal taxes)
	{
		this.taxes = taxes;
	}

	public Employees getEmployee()
	{
		return employee;
	}


	public Candidate getCandidate()
	{
		return candidate;
	}

	public void setCandidate(Candidate candidate)
	{
		this.candidate = candidate;
	}

	public void setEmployee(Employees employee)
	{
		this.employee = employee;
	}

	
	public String getOverTimeHours()
	{
		return overTimeHours;
	}

	public void setOverTimeHours(String overTimeHours)
	{
		this.overTimeHours = overTimeHours;
	}

	public Integer getFrequency()
	{
		return frequency;
	}

	public void setFrequency(Integer frequency)
	{
		this.frequency = frequency;
	}

	public Date getPayCheckDate()
	{
		return payCheckDate;
	}

	public void setPayCheckDate(Date payCheckDate)
	{
		this.payCheckDate = payCheckDate;
	}

	public String getDepositType()
	{
		return depositType;
	}

	public BigDecimal getPreTaxContribution()
	{
		return preTaxContribution;
	}

	public BigDecimal getPostTaxContribution()
	{
		return postTaxContribution;
	}

	public void setDepositType(String depositType)
	{
		this.depositType = depositType;
	}

	public void setPreTaxContribution(BigDecimal preTaxContribution)
	{
		this.preTaxContribution = preTaxContribution;
	}

	public void setPostTaxContribution(BigDecimal postTaxContribution)
	{
		this.postTaxContribution = postTaxContribution;
	}

	public Set<EmployeePayrollTax> getUsPayrollTax()
	{
		return usPayrollTax;
	}

	public void setUsPayrollTax(Set<EmployeePayrollTax> usPayrollTax)
	{
		this.usPayrollTax = usPayrollTax;
	}

	public Set<DeductionHistory> getDeductionHistory()
	{
		return deductionHistory;
	}

	public void setDeductionHistory(Set<DeductionHistory> deductionHistory)
	{
		this.deductionHistory = deductionHistory;
	}

	public BigDecimal getPayitemSum()
	{
		return payitemSum;
	}

	public void setPayitemSum(BigDecimal payitemSum)
	{
		this.payitemSum = payitemSum;
	}

	public Set<PayitemHistory> getPayitemHistory()
	{
		return payitemHistory;
	}

	public void setPayitemHistory(Set<PayitemHistory> payitemHistory)
	{
		this.payitemHistory = payitemHistory;
	}

	public String getPayType()
	{
		return payType;
	}

	public BigDecimal getPayPerSalary()
	{
		return payPerSalary;
	}

	public BigDecimal getAdvanceAmount()
	{
		if(advanceAmount != null) {
			return advanceAmount;
		}else {
			return BigDecimal.ZERO;
		}
		
	}

	public BigDecimal getLoanAmount()
	{
		if(loanAmount != null) {
			return loanAmount;
		}else {
			return BigDecimal.ZERO;
		}
		
	}

	public void setPayType(String payType)
	{
		this.payType = payType;
	}

	public void setPayPerSalary(BigDecimal payPerSalary)
	{
		this.payPerSalary = payPerSalary;
	}

	public void setAdvanceAmount(BigDecimal advanceAmount)
	{
		this.advanceAmount = advanceAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount)
	{
		this.loanAmount = loanAmount;
	}

	public String getPaySlipFileName()
	{
		return paySlipFileName;
	}

	public String getPaySlipFileURL()
	{
		return paySlipFileURL;
	}

	public void setPaySlipFileName(String paySlipFileName)
	{
		this.paySlipFileName = paySlipFileName;
	}

	public void setPaySlipFileURL(String paySlipFileURL)
	{
		this.paySlipFileURL = paySlipFileURL;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getExpensesAmount()
	{
		return expensesAmount;
	}

	public void setExpensesAmount(BigDecimal expensesAmount)
	{
		this.expensesAmount = expensesAmount;
	}

	public Set<BankDataHistory> getBankDataHistory() {
		return bankDataHistory;
	}

	public void setBankDataHistory(Set<BankDataHistory> bankDataHistory) {
		this.bankDataHistory = bankDataHistory;
	}

	public Set<AssociateTaxHistory> getAssociateTaxHistory() {
		return associateTaxHistory;
	}

	public void setAssociateTaxHistory(Set<AssociateTaxHistory> associateTaxHistory) {
		this.associateTaxHistory = associateTaxHistory;
	}

	public Set<PayrollLopHis> getPayrollLopHis()
	{
		return payrollLopHis;
	}

	public void setPayrollLopHis(Set<PayrollLopHis> payrollLopHis)
	{
		this.payrollLopHis = payrollLopHis;
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
