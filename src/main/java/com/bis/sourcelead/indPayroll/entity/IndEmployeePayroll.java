package com.bis.sourcelead.indPayroll.entity;

import java.io.Serializable;
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
import javax.persistence.Transient;

import com.bis.sourcelead.entity.AssociateLoanAndAdvanceHistory;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.PaymentAccount;
import com.bis.sourcelead.entity.PayrollPaySlipInd;
import com.bis.sourcelead.payroll.entity.PayrollLopHis;

@Entity
@Table(name = "IND_EMPLOYEE_PAYROLL")
public class IndEmployeePayroll implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "EMPLOYEE_ID")
	private Employees employee;

	@ManyToOne
	@JoinColumn(name = "CANDIDATE_ID")
	private Candidate candidate;

	@ManyToOne
	@JoinColumn(name = "IND_PAY_SHEET_FREQUENCY_ID", nullable = false)
	private IndPaysheetFrequency indPaysheetFrequency;
	
	@Column(name = "IND_EMP_PAYROLL_UUID")
	private String indEmpPayrollUUID;

	@Column(name = "REGULAR_HOURS_AMOUNT")
	private BigDecimal regularHoursAmount;

	@Column(name = "REGULAR_HOURS")
	private String regularHours;

	@Column(name = "OVER_TIME_AMOUNT")
	private BigDecimal overTimeAmount;

	@Column(name = "OVER_TIME_HOURS")
	private String overTimeHours;

	@Column(name = "NO_OF_LEAVES")
	private BigDecimal noOfLeaves;
	
	@Column(name = "NO_OF_DAYS")
	private BigDecimal noOfDays;
	
	@Column (name = "PAY_CHECK_DATE")
	private Date payCheckDate;
	
	@Column(name = "EMP_PAY_RATE")
	private BigDecimal empPayRate;
	
	@Column(name = "PAY_TYPE")
	private String payType; 
	 
	@Column(name = "PAY_PER_SALARY")
    private BigDecimal payPerSalary;
	
	@Column(name = "GROSS_PAY")
	private BigDecimal grossPay;

	@Column(name = "NET_PAY")
	private BigDecimal netPay;

	@Column(name = "PROFESSIONAL_TAX")
	private BigDecimal professionalTax;
	

	@Column(name = "TDS")
	private BigDecimal tds;

	@Column(name = "SUBMIT_FOR_APROVER_STATUS")
	private String submitForAproverStatus;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "indEmployeePayrollId")
	private Set<AssociatePayrollPayheadHistory> associatePayrollPayheadHistory;

	@Column(name = "STATUS")
	private Integer status;
	
	@Column (name="TYPE")
	private String type;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private Long createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private Long updatedBy;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "indEmployeePayrollId")
	private Set<AssociateLoanAndAdvanceHistory> loanHistory;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "indPayroll")
	private Set<PayrollLopHis> payrollLopHis;
	
	@Column(name = "LOP_AMOUNT")
	private BigDecimal lopAmount;
	
	@ManyToOne
	@JoinColumn(name = "IND_PAYSLIP_ID")
	private PayrollPaySlipInd payrollPaySlipInd;
	
	@ManyToOne
	@JoinColumn(name = "PAYMENT_ACCOUNT_ID")
	private PaymentAccount paymentAccount;
	
	@Transient
	private Long candidateId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Employees getEmployee() {
		return employee;
	}

	public void setEmployee(Employees employee) {
		this.employee = employee;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public IndPaysheetFrequency getIndPaysheetFrequency() {
		return indPaysheetFrequency;
	}

	public void setIndPaysheetFrequency(IndPaysheetFrequency indPaysheetFrequency) {
		this.indPaysheetFrequency = indPaysheetFrequency;
	}

	public BigDecimal getEmpPayRate() {
		return empPayRate;
	}

	public void setEmpPayRate(BigDecimal empPayRate) {
		this.empPayRate = empPayRate;
	}

	public String getRegularHours() {
		return regularHours;
	}

	public void setRegularHours(String regularHours) {
		this.regularHours = regularHours;
	}

	public BigDecimal getOverTimeAmount() {
		return overTimeAmount;
	}

	public void setOverTimeAmount(BigDecimal overTimeAmount) {
		this.overTimeAmount = overTimeAmount;
	}

	public String getOverTimeHours() {
		return overTimeHours;
	}

	public void setOverTimeHours(String overTimeHours) {
		this.overTimeHours = overTimeHours;
	}

	public BigDecimal getNoOfLeaves() {
		return noOfLeaves;
	}

	public void setNoOfLeaves(BigDecimal noOfLeaves) {
		this.noOfLeaves = noOfLeaves;
	}

	public BigDecimal getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(BigDecimal noOfDays) {
		this.noOfDays = noOfDays;
	}

	public BigDecimal getGrossPay() {
		return grossPay;
	}

	public void setGrossPay(BigDecimal grossPay) {
		this.grossPay = grossPay;
	}

	public BigDecimal getNetPay() {
		return netPay;
	}

	public void setNetPay(BigDecimal netPay) {
		this.netPay = netPay;
	}

	public BigDecimal getProfessionalTax() {
		return professionalTax;
	}

	public void setProfessionalTax(BigDecimal professionalTax) {
		this.professionalTax = professionalTax;
	}

	public BigDecimal getTds() {
		return tds;
	}

	public void setTds(BigDecimal tds) {
		this.tds = tds;
	}

	public String getSubmitForAproverStatus() {
		return submitForAproverStatus;
	}

	public void setSubmitForAproverStatus(String submitForAproverStatus) {
		this.submitForAproverStatus = submitForAproverStatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Set<AssociatePayrollPayheadHistory> getAssociatePayrollPayheadHistory() {
		return associatePayrollPayheadHistory;
	}

	public void setAssociatePayrollPayheadHistory(Set<AssociatePayrollPayheadHistory> associatePayrollPayheadHistory) {
		this.associatePayrollPayheadHistory = associatePayrollPayheadHistory;
	}

	public BigDecimal getRegularHoursAmount() {
		return regularHoursAmount;
	}

	public void setRegularHoursAmount(BigDecimal regularHoursAmount) {
		this.regularHoursAmount = regularHoursAmount;
	}

	public String getIndEmpPayrollUUID() {
		return indEmpPayrollUUID;
	}

	public void setIndEmpPayrollUUID(String indEmpPayrollUUID) {
		this.indEmpPayrollUUID = indEmpPayrollUUID;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Set<AssociateLoanAndAdvanceHistory> getLoanHistory() {
		return loanHistory;
	}

	public void setLoanHistory(Set<AssociateLoanAndAdvanceHistory> loanHistory) {
		this.loanHistory = loanHistory;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public BigDecimal getPayPerSalary() {
		return payPerSalary;
	}

	public void setPayPerSalary(BigDecimal payPerSalary) {
		this.payPerSalary = payPerSalary;
	}


	public Date getPayCheckDate() {
		return payCheckDate;
	}

	public void setPayCheckDate(Date payCheckDate) {
		this.payCheckDate = payCheckDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Set<PayrollLopHis> getPayrollLopHis() {
		return payrollLopHis;
	}

	public void setPayrollLopHis(Set<PayrollLopHis> payrollLopHis) {
		this.payrollLopHis = payrollLopHis;
	}

	public BigDecimal getLopAmount() {
		return lopAmount;
	}

	public void setLopAmount(BigDecimal lopAmount) {
		this.lopAmount = lopAmount;
	}

	public PayrollPaySlipInd getPayrollPaySlipInd() {
		return payrollPaySlipInd;
	}

	public void setPayrollPaySlipInd(PayrollPaySlipInd payrollPaySlipInd) {
		this.payrollPaySlipInd = payrollPaySlipInd;
	}

	public PaymentAccount getPaymentAccount() {
		return paymentAccount;
	}

	public void setPaymentAccount(PaymentAccount paymentAccount) {
		this.paymentAccount = paymentAccount;
	}

	public Long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}
}
